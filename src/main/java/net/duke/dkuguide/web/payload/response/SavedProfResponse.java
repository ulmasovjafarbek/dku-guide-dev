package net.duke.dkuguide.web.payload.response;

import lombok.Data;

@Data
public class SavedProfResponse {

    private Long id;
    private String firstname;
    private String lastname;
    private float overallRating;
    private float difficultyLevel;

    public SavedProfResponse(Long id, String firstname, String lastname,
                             float overallRating, float difficultyLevel) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.overallRating = overallRating;
        this.difficultyLevel = difficultyLevel;
    }
}
