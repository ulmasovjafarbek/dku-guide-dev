package net.duke.dkuguide.service.implement;

import net.duke.dkuguide.data.models.Course;
import net.duke.dkuguide.data.models.Professor;
import net.duke.dkuguide.data.models.ProfessorTag;
import net.duke.dkuguide.data.models.Review;
import net.duke.dkuguide.data.repositories.CourseRepo;
import net.duke.dkuguide.data.repositories.ProfessorRepo;
import net.duke.dkuguide.data.repositories.ProfessorTagRepo;
import net.duke.dkuguide.service.services.ProfessorService;
import net.duke.dkuguide.web.payload.response.ProfessorResponse;
import net.duke.dkuguide.web.payload.response.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    @Autowired
    private ProfessorRepo profRepo;
    private CourseRepo courseRepo;
    private ProfessorTagRepo tagRepo;

    public ProfessorServiceImpl(ProfessorRepo profRepo, CourseRepo courseRepo, ProfessorTagRepo tagRepo) {
        this.profRepo = profRepo;
        this.courseRepo = courseRepo;
        this.tagRepo = tagRepo;
    }

    @Override
    public Optional<Professor> findById(Long id) {
        return profRepo.findById(id);
    }

    @Override
    public ProfessorResponse getFullInfoById(Long id) {
        ProfessorResponse prof = new ProfessorResponse();
        profRepo.findById(id).map(p -> {
                prof.setId(p.getId());
                prof.setFirstname(p.getFirstname());
                prof.setLastname(p.getLastname());
                prof.setOverallRating(p.getOverallRating());
                prof.setDifficultyLevel(p.getDifficultyLevel());
                prof.setTakeAgainPercentage(p.getTakeAgainPercentage());
                prof.setDivisionName(p.getDivisionName().toString());
                prof.setDirectoryLink(p.getDirectoryLink());
                prof.setCourses(parseCourses(p.getCourses()));
                prof.setReviews(parseReviews(p.getReviews()));
                prof.setTopTags(getTopTags(p.getId()));
                return prof;
            }).orElseThrow();

        return prof;
    }

    @Override
    public Iterable<Professor> findAll() {
        return profRepo.findAll();
    }

    @Override
    public Professor updateProfessorDetails(Review review, Course course,
                                            Set<ProfessorTag> tags) {
        Professor prof = profRepo.findById(review.getProfessorId())
                .orElseThrow();
        prof.addCourse(course);
        prof.addReview(review);
        prof.updateRating(review.getRate(), review.getDifficultyLevel(),
                review.isTakeAgain());
        for (ProfessorTag tag : tags) {
            if (prof.getTags().contains(tag)) {
                profRepo.incrementTagCounterByOne(prof.getId(), tag.getId());
            } else {
                prof.addTag(tag);
            }
        }

        return prof;
    }

    @Override
    public Professor addCourseToProfessor(Long profId, Long courseId) {
        Professor prof = profRepo.findById(profId).orElseThrow();
        Course course = courseRepo.findById(courseId).orElseThrow();
        prof.addCourse(course);

        return prof;
    }

    @Override
    public Professor save(Professor prof) {
        return profRepo.save(prof);
    }

    @Override
    public Professor editProf(Long id, Professor prof) {
        return profRepo.findById(id)
                .map(p -> {
                    p.setFirstname(prof.getFirstname());
                    p.setLastname(prof.getLastname());
                    p.setDivisionName(prof.getDivisionName());
                    p.setDirectoryLink(prof.getDirectoryLink());

                    return profRepo.save(p);
                }).orElseGet(() -> {
                    prof.setId(id);
                    return profRepo.save(prof);
                });
    }

    @Override
    public void deleteById(Long id) {
        profRepo.deleteById(id);
    }

    private List<String> parseCourses(Set<Course> profCourses) {
        List<String> courses = new ArrayList<>();
        profCourses.forEach(course -> {
            courses.add(course.getFullName());
        });
        return courses;
    }

    private List<ReviewResponse> parseReviews(Set<Review> profReviews) {
        List<ReviewResponse> reviews = new ArrayList<>();
        profReviews.forEach(r -> {
            ReviewResponse currentReview = new ReviewResponse();
            currentReview.setId(r.getId());
            currentReview.setCourseName(r.getCourseName());
            currentReview.setRate(r.getRate());
            currentReview.setDifficulty(r.getDifficultyLevel());
            currentReview.setTakeAgain(convert(r.isTakeAgain()));
            currentReview.setAttendance(convert(r.isAttendance()));
            currentReview.setTextbookRequired(convert(r.isTextbookRequired()));
            currentReview.setOnline(convert(r.isOnlineCourse()));
            currentReview.setGrade(r.getGrade());
            currentReview.setComment(r.getComment());
            currentReview.setUpVote(r.getUpVote());
//            currentReview.setDate(convertDate());
            reviews.add(currentReview);
        });

        return reviews;
    }

    private List<String> getTopTags(Long profId) {
        Set<Integer> topTagIds = profRepo.getTopTags(profId);
        List<String> tags = new ArrayList<>();
        topTagIds.forEach(i -> {
            ProfessorTag tag = tagRepo.findById(i).orElseThrow(() -> new RuntimeException("Tag Not Found"));
            tags.add(tag.getTagName().toString());
        });
        return tags;
    }

    private String convert(Boolean value) {
        return value ? "Yes" : "No";
    }

    private String convertDate(Date date) {
        String[] res = date.toString().split("\\s");
        return res[1] + " " + res[2];
    }
}
