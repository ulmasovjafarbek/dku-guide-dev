package net.duke.dkuguide.security.verification;

import net.duke.dkuguide.data.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByStudent(Student student);
}
