package net.duke.dkuguide.data.repositories;

import net.duke.dkuguide.data.models.Professor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
public interface ProfessorRepo extends CrudRepository<Professor, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE professor_tags SET tag_count = tag_count + 1 " +
            "WHERE professor_id = :profId AND tags_id = :tagId", nativeQuery = true)
    void incrementTagCounterByOne(@Param("profId") Long profId,
                                  @Param("tagId") int tagId);

    @Query(value = "SELECT tags_id FROM professor_tags " +
            "ORDER BY professor_tags.tag_count FETCH FIRST 3 ROWS ONLY", nativeQuery = true)
    Set<Integer> getTopTags(@Param("profId") Long profId);

    @Query(value = "SELECT professor_id FROM professor_courses " +
            "WHERE professor_courses.courses_id = :courseId", nativeQuery = true)
    Set<Long> getProfsForCourse(@Param("courseId") Long courseId);
}
