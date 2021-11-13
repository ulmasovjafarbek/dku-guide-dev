package net.duke.dkuguide.web.payload.response;

import lombok.Data;

@Data
public class StudentReviewResponse {

    private Long id;
    private String profName;
    private String courseName;
    private int rate;
    private int difficulty;
    private String takeAgain;
    private String attendance;
    private String textbookRequired;
    private String online;
    private String grade;
    private String comment;
    private int upVote;
    private String[] tags = new String[3];
    private String date;
}
