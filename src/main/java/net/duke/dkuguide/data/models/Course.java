package net.duke.dkuguide.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 15)
    @Pattern(regexp = "^[A-Z]{2,10} [0-9]{2,3}$",
            message = "Must be formatted MATH 101 (with space in between)")
    private String courseCode;

    private String name;

    private Double difficultyLevel = 0.0;

    @JsonIgnore
    private Double levelSum = 0.0;

    private int totalReviews = 0;

    public void updateDifficultyLevel(int level) {
        this.totalReviews++;
        this.levelSum += level;
        this.difficultyLevel = levelSum / Double.valueOf(totalReviews);
    }

    public String getFullName() {
        return courseCode;
    }
}
