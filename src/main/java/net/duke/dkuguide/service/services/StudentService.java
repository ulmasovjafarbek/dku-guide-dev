package net.duke.dkuguide.service.services;

import net.duke.dkuguide.data.models.Review;
import net.duke.dkuguide.data.models.Student;
import net.duke.dkuguide.security.verification.VerificationToken;
import net.duke.dkuguide.web.payload.response.SavedProfResponse;
import net.duke.dkuguide.web.payload.response.StudentProfileResponse;
import net.duke.dkuguide.web.payload.response.StudentReviewResponse;

import java.util.Optional;
import java.util.Set;

public interface StudentService {

    Optional<Student> findById(Long id);

    StudentProfileResponse getProfileById(Long id);

    Iterable<Student> findAll();

    Iterable<StudentReviewResponse> getReviewsForStudent(Long id);

    Iterable<SavedProfResponse> getSavedProfessorsForStudent(Long id);

    Student save(Student student);

    boolean checkUsername(String username);

    boolean checkEmail(String email);

    void bookmarkProfessor(Long studentId, Long profId);

    Student assignRoles(Student student, Set<String> strRoles);

    Student editStudentDetails(Long id, Student student);

    void deleteById(Long id);

    Student addReview(Review review);

    void createVerificationToken(Student student, String token);

    VerificationToken getVerificationToken(String token);
}
