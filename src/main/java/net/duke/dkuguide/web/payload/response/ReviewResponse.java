package net.duke.dkuguide.web.payload.response;

import lombok.Data;

@Data
public class ReviewResponse {

    private Long id;
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
    private String date;
}
