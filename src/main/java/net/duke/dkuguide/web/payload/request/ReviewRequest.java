package net.duke.dkuguide.web.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Data
public class ReviewRequest {

    private Long studentId;
    private Long professorId;
    private Long courseId;
    @NotEmpty
    private Set<String> profTags = new HashSet<>();
    private int rate;
    private int difficultyLevel;
    private boolean takeAgain;
    private boolean onlineCourse;
    private boolean attendance;
    private boolean textbookRequired;
    private String grade;
    private String comment;

}
