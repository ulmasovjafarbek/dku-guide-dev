package net.duke.dkuguide.web.rest;

import net.duke.dkuguide.data.models.Course;
import net.duke.dkuguide.data.models.ProfessorTag;
import net.duke.dkuguide.data.models.Review;
import net.duke.dkuguide.service.implement.*;
import net.duke.dkuguide.web.payload.request.ReviewRequest;
import net.duke.dkuguide.web.payload.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/v1/review", produces = "application/json")
public class ReviewRestController {

    private ReviewServiceImpl reviewService;
    private CourseServiceImpl courseService;
    private StudentServiceImpl studentService;
    private ProfessorServiceImpl profService;
    private ProfessorTagServiceImpl tagService;

    public ReviewRestController(ReviewServiceImpl reviewService, CourseServiceImpl courseService,
                                ProfessorServiceImpl profService, StudentServiceImpl studentService,
                                ProfessorTagServiceImpl tagService) {
        this.reviewService = reviewService;
        this.courseService = courseService;
        this.studentService = studentService;
        this.profService = profService;
        this.tagService = tagService;
    }

    @GetMapping
    private Iterable<Review> getAllReviews() {
        return reviewService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<ResponseMessage> createReview(@RequestBody ReviewRequest review) {
        String message = "";

        try {
            Set<ProfessorTag> profTags = tagService.findTagsByStringNames(
                    review.getProfTags());
            Course course = courseService.updateDifficultyLevel(
                    review.getCourseId(), review.getDifficultyLevel()
            );

            Review newReview = new Review(
                    review.getStudentId(), review.getProfessorId(), course.getFullName(),
                    review.getRate(), review.getDifficultyLevel(), review.isTakeAgain(),
                    review.isTextbookRequired(), review.isAttendance(),
                    review.isOnlineCourse(), review.getGrade(), review.getComment()
            );
            reviewService.addReview(newReview);
            profService.updateProfessorDetails(newReview, course, profTags);
            studentService.addReview(newReview);

            message = "Posted review successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not post review";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/{id}")
    private Optional<Review> getReview(@PathVariable Long id) {
        return reviewService.findById(id);
    }

    @PostMapping("/upVote/{id}")
    private ResponseEntity<ResponseMessage> upVoteReview(@PathVariable Long id) {
        String message = "Upvoted successfully";
        try {
            reviewService.upVoteReview(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upvote";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<ResponseMessage> updateReview(@PathVariable Long id, @RequestBody Review review) {
        String message = "Updated review successfully";
        try {
            reviewService.editReview(id, review);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Failed to edit review";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @DeleteMapping("/{id}")
    private void deleteReview(@PathVariable Long id) {
        reviewService.deleteById(id);
    }

}
