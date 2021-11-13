package net.duke.dkuguide.web.payload.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProfessorResponse {

    private Long id;
    private String firstname;
    private String lastname;
    private float overallRating;
    private float difficultyLevel;
    private float takeAgainPercentage;
    private String divisionName;
    private String directoryLink;
    private List<String> courses = new ArrayList<>();
    private List<ReviewResponse> reviews = new ArrayList<>();
    private List<String> topTags = new ArrayList<>();
}
