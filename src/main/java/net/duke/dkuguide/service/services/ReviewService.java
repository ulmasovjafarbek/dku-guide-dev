package net.duke.dkuguide.service.services;

import net.duke.dkuguide.data.models.Review;

import java.util.Optional;

public interface ReviewService {

    Optional<Review> findById(Long id);
    Iterable<Review> findAll();
    Review addReview(Review review);
    void upVoteReview(Long reviewId);
    Review editReview(Long id, Review review);
    void deleteById(Long id);
}
