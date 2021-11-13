package net.duke.dkuguide.security.verification;

import lombok.Data;
import net.duke.dkuguide.data.models.Student;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
public class VerificationToken {

    private static final int EXPIRATION = 60*24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Student.class, fetch = FetchType.EAGER)
    private Student student;

    private Date expiryDate;

    public VerificationToken() {
    }

    public VerificationToken(String token, Student student) {
        this.token = token;
        this.student = student;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
