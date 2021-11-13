package net.duke.dkuguide.data.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
                    property = "id")
@Table(name = "professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private float overallRating = 0;

    private float difficultyLevel = 0;

    private float takeAgainPercentage = 0;

    private Division divisionName;

    private String directoryLink;

    @ManyToMany(targetEntity = Course.class, fetch = FetchType.LAZY)
    private Set<Course> courses = new HashSet<>();

    @OneToMany(targetEntity = Review.class, fetch = FetchType.LAZY)
    private Set<Review> reviews = new HashSet<>();

    @ManyToMany(targetEntity = ProfessorTag.class, fetch = FetchType.LAZY)
    private Set<ProfessorTag> tags = new HashSet<>();

    public enum Division {
        ARTS_AND_HUMANITIES,
        NATURAL_SCIENCES,
        SOCIAL_SCIENCES,
        QUANTITATIVE_SCIENCES
    }

    private int totalReviews;

    @JsonIgnore
    private int ratingSum;
    @JsonIgnore
    private int difficultySum;
    @JsonIgnore
    private int takeAgainSum;

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void updateRating(int rate, int difficulty, boolean takeAgain) {
        this.totalReviews++;

        this.ratingSum += rate;
        this.overallRating = Float.valueOf(ratingSum) / totalReviews;

        this.difficultySum += difficulty;
        this.difficultyLevel = Float.valueOf(difficultySum) / totalReviews;

        if (takeAgain == true) {
            takeAgainSum += 1;
        }
        takeAgainPercentage = Float.valueOf(takeAgainSum * 100) / totalReviews;
    }

    public void addTag(ProfessorTag tag) {
        this.tags.add(tag);
    }

}
