package net.duke.dkuguide.service.implement;

import net.duke.dkuguide.data.models.*;
import net.duke.dkuguide.data.repositories.ProfessorRepo;
import net.duke.dkuguide.data.repositories.RoleRepo;
import net.duke.dkuguide.data.repositories.StudentRepo;
import net.duke.dkuguide.security.verification.VerificationToken;
import net.duke.dkuguide.security.verification.VerificationTokenRepo;
import net.duke.dkuguide.service.services.StudentService;
import net.duke.dkuguide.web.payload.response.SavedProfResponse;
import net.duke.dkuguide.web.payload.response.StudentProfileResponse;
import net.duke.dkuguide.web.payload.response.StudentReviewResponse;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepo studentRepo;
    private RoleRepo roleRepo;
    private ProfessorRepo profRepo;
    private VerificationTokenRepo tokenRepo;

    public StudentServiceImpl(StudentRepo studentRepo, RoleRepo roleRepo,
                              ProfessorRepo profRepo, VerificationTokenRepo tokenRepo) {
        this.studentRepo = studentRepo;
        this.roleRepo = roleRepo;
        this.profRepo = profRepo;
        this.tokenRepo = tokenRepo;
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepo.findById(id);
    }

    @Override
    public StudentProfileResponse getProfileById(Long id) {
        Student student = studentRepo.findById(id).orElseThrow();
        return new StudentProfileResponse(
                student.getFirstname(), student.getLastname(),
                student.getClassYear()
        );
    }

    @Override
    public Iterable<Student> findAll() {
        return studentRepo.findAll();
    }

    @Override
    public Iterable<StudentReviewResponse> getReviewsForStudent(Long id) {
        Student student = studentRepo.findById(id).orElseThrow();
        List<StudentReviewResponse> reviews = student.getReviews()
                .stream().map(r -> {
                    StudentReviewResponse review = new StudentReviewResponse();
                    review.setId(r.getId());
                    review.setProfName(getProfName(r.getProfessorId()));
                    review.setCourseName(r.getCourseName());
                    review.setRate(r.getRate());
                    review.setDifficulty(r.getDifficultyLevel());
                    review.setTakeAgain(convert(r.isTakeAgain()));
                    review.setAttendance(convert(r.isAttendance()));
                    review.setTextbookRequired(convert(r.isTextbookRequired()));
                    review.setOnline(convert(r.isOnlineCourse()));
                    review.setGrade(r.getGrade());
                    review.getComment();
                    review.getUpVote();
//                    review.setDate();
                    return review;
                }).collect(Collectors.toList());
        return reviews;
    }

    @Override
    public Iterable<SavedProfResponse> getSavedProfessorsForStudent(Long id) {
        Student student = studentRepo.findById(id).orElseThrow();
        List<SavedProfResponse> savedProfs = student.getSavedProfessors()
                .stream().map(p -> {
                    return new SavedProfResponse(
                            p.getId(), p.getFirstname(), p.getLastname(),
                            p.getOverallRating(), p.getDifficultyLevel()
                    );
                }).collect(Collectors.toList());
        return savedProfs;
    }

    @Override
    public Student save(Student student) {
        return studentRepo.save(student);
    }

    @Override
    public boolean checkUsername(String username) {
        return studentRepo.existsByUsername(username);
    }

    public boolean checkEmail(String email) {
        return studentRepo.existsByEmail(email);
    }

    @Override
    public void bookmarkProfessor(Long studentId, Long profId) {
        Student student = studentRepo.findById(studentId).orElseThrow();
        student.saveProfessor(profRepo.findById(profId).orElseThrow());
        studentRepo.save(student);
    }

    @Override
    public Student assignRoles(Student student, Set<String> strRoles)
            throws RuntimeException {
        HashSet<UserRole> roles = new HashSet<>();
        String roleNotFound = "Role not found";

        if (strRoles == null) {
            UserRole studentRole = roleRepo.findByName(UserRoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(roleNotFound));
            roles.add(studentRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        UserRole adminRole = roleRepo.findByName(UserRoleEnum.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException(roleNotFound));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        UserRole modRole = roleRepo.findByName(UserRoleEnum.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException(roleNotFound));
                        roles.add(modRole);
                        break;
                    default:
                        UserRole userRole = roleRepo.findByName(UserRoleEnum.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException(roleNotFound));
                        roles.add(userRole);
                }
            });
        }
        student.setRoles(roles);
        return studentRepo.save(student);
    }

    @Override
    public Student editStudentDetails(Long id, Student student) {
        return studentRepo.findById(id)
                .map(s -> {
                    s.setUsername(student.getUsername());
                    s.setFirstname(student.getFirstname());
                    s.setLastname(student.getLastname());
                    s.setClassYear(student.getClassYear());
                    s.setUniversity(student.getUniversity());
                    return studentRepo.save(s);
                }).orElseGet(() -> {
                    student.setId(id);
                    return studentRepo.save(student);
                });
    }

    @Override
    public void deleteById(Long id) {
        studentRepo.deleteById(id);
    }

    @Override
    public Student addReview(Review review) {
        Student student = studentRepo.findById(review.getStudentId())
                .orElseThrow();
        student.addReview(review);
        return studentRepo.save(student);
    }

    @Override
    public void createVerificationToken(Student student, String token) {
        VerificationToken newToken = new VerificationToken(token, student);
        tokenRepo.save(newToken);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return tokenRepo.findByToken(token);
    }

    private String convert(Boolean value) {
        return value ? "Yes" : "No";
    }

    private String getProfName(Long id) {
        Professor prof = profRepo.findById(id).orElseThrow();
        return prof.getFirstname() + " " + prof.getLastname();
    }
}
