package net.duke.dkuguide.web.payload.response;

import lombok.Data;

@Data
public class StudentProfileResponse {

    private String firstname;
    private String lastname;
    private int classYear;

    public StudentProfileResponse(String firstname, String lastname, int classYear) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.classYear = classYear;
    }
}
