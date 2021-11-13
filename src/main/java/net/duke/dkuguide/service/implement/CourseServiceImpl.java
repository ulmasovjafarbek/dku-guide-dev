package net.duke.dkuguide.service.implement;

import javassist.NotFoundException;
import net.duke.dkuguide.data.models.Course;
import net.duke.dkuguide.data.models.Professor;
import net.duke.dkuguide.data.repositories.CourseRepo;
import net.duke.dkuguide.data.repositories.ProfessorRepo;
import net.duke.dkuguide.service.services.CourseService;
import net.duke.dkuguide.web.payload.response.CourseResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepo courseRepo;
    private ProfessorRepo professorRepo;

    public CourseServiceImpl(CourseRepo courseRepo, ProfessorRepo professorRepo) {
        this.courseRepo = courseRepo;
        this.professorRepo = professorRepo;
    }

    @Override
    public Iterable<Course> findAll() {
        return courseRepo.findAll();
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepo.findById(id);
    }

    @Override
    public Course save(Course course) {
        return courseRepo.save(course);
    }

    @Override
    public CourseResponse getFullInfo(Long id) {
        Course course = courseRepo.findById(id).orElseThrow();
        CourseResponse courseResponse = new CourseResponse(course.getId(),
                course.getCourseCode(), course.getName(), course.getDifficultyLevel(),
                course.getTotalReviews(), getProfessorsForCourse(id));
        return courseResponse;
    }

    @Override
    public Course editCourse(Long id, Course course) {
        return courseRepo.findById(id)
                .map(c -> {
                    c.setName(course.getName());
                    c.setCourseCode(course.getCourseCode());

                    return courseRepo.save(c);
                }).orElseGet(() -> {
                    course.setId(id);
                    return courseRepo.save(course);
                });
    }

    @Override
    public void deleteById(Long id) {
        courseRepo.deleteById(id);
    }

    @Override
    public Course updateDifficultyLevel(Long id, int difficulty) throws NotFoundException {
        Course course = courseRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Course Not Found"));
        course.updateDifficultyLevel(difficulty);
        courseRepo.save(course);
        return course;
    }

    private List<String> getProfessorsForCourse(Long id) {
        List<String> profs = professorRepo.getProfsForCourse(id)
                .stream().map(profId -> {
                    Professor prof = professorRepo.findById(profId).orElseThrow();
                    return prof.getFirstname() + " " + prof.getLastname();
                }).collect(Collectors.toList());
        return profs;
    }

}
