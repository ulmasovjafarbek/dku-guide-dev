package net.duke.dkuguide.data.repositories;

import net.duke.dkuguide.data.models.Review;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ReviewRepo extends CrudRepository<Review, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE review SET up_vote = up_vote + 1 " +
            "WHERE id = :id", nativeQuery = true)
    void upVoteReview(@Param("id") Long id);
}
