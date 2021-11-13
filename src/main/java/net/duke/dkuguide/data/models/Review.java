package net.duke.dkuguide.data.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Table(name = "review")
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;

    private Long professorId;

    private String courseName;

    private int rate;

    private int difficultyLevel;

    private boolean takeAgain;

    private boolean onlineCourse;

    private boolean attendance;

    private boolean textbookRequired;

    private String grade;

    private int upVote = 0;

    private Date createdAt;

    private String comment;

    public Review(Long studentId, Long professorId, String courseName, int rate,
                  int difficultyLevel, boolean takeAgain, boolean textbookRequired,
                  boolean attendance, boolean onlineCourse, String grade, String comment) {
        this.studentId = studentId;
        this.professorId = professorId;
        this.courseName = courseName;
        this.rate = rate;
        this.difficultyLevel = difficultyLevel;
        this.takeAgain = takeAgain;
        this.textbookRequired = textbookRequired;
        this.attendance = attendance;
        this.grade = grade;
        this.onlineCourse = onlineCourse;
        this.comment = comment;
    }

    @PrePersist
    public void createdAt() {
        this.createdAt = new Date();
    }

}
