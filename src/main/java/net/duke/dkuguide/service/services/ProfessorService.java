package net.duke.dkuguide.service.services;

import net.duke.dkuguide.data.models.Course;
import net.duke.dkuguide.data.models.Professor;
import net.duke.dkuguide.data.models.ProfessorTag;
import net.duke.dkuguide.data.models.Review;
import net.duke.dkuguide.web.payload.response.ProfessorResponse;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProfessorService {

    Optional<Professor> findById(Long id);

    ProfessorResponse getFullInfoById(Long id);

    Iterable<Professor> findAll();

    Professor save(Professor prof);

    Professor editProf(Long id, Professor prof);

    void deleteById(Long id);

    Professor updateProfessorDetails(Review review, Course course, Set<ProfessorTag> tags);

    Professor addCourseToProfessor(Long profId, Long courseId);

}
