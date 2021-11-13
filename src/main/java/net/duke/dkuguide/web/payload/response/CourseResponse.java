package net.duke.dkuguide.web.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class CourseResponse {

    private Long id;
    private String courseCode;
    private String courseName;
    private Double difficultyLevel;
    private int totalReviews;
    private List<String> profNames;

    public CourseResponse(Long id, String courseCode, String name,
                          Double difficultyLevel, int totalReviews,
                          List<String> profs) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = name;
        this.difficultyLevel = difficultyLevel;
        this.totalReviews = totalReviews;
        this.profNames = profs;
    }
}
