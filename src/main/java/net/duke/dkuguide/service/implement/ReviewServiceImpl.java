package net.duke.dkuguide.service.implement;

import net.duke.dkuguide.data.models.Review;
import net.duke.dkuguide.data.repositories.ReviewRepo;
import net.duke.dkuguide.service.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepo reviewRepo;

    @Autowired
    public ReviewServiceImpl(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewRepo.findById(id);
    }

    @Override
    public Iterable<Review> findAll() {
        return reviewRepo.findAll();
    }

    @Override
    public Review addReview(Review review) {
        return reviewRepo.save(review);
    }

    @Override
    public void upVoteReview(Long reviewId) {
        reviewRepo.upVoteReview(reviewId);
    }

    @Override
    public Review editReview(Long id, Review review) {
        return reviewRepo.findById(id)
                .map(r -> {
                    r.setProfessorId(review.getProfessorId());
                    r.setOnlineCourse(review.isOnlineCourse());
                    r.setTakeAgain(review.isTakeAgain());
                    r.setRate(review.getRate());
                    r.setCourseName(review.getCourseName());
                    r.setDifficultyLevel(review.getDifficultyLevel());
                    r.setComment(review.getComment());
                    return reviewRepo.save(r);
                }).orElseGet(() -> {
                    review.setId(id);
                    return reviewRepo.save(review);
                });
    }

    @Override
    public void deleteById(Long id) {
        reviewRepo.deleteById(id);
    }

}
