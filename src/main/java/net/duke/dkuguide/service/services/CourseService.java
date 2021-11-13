package net.duke.dkuguide.service.services;

import javassist.NotFoundException;
import net.duke.dkuguide.data.models.Course;
import net.duke.dkuguide.web.payload.response.CourseResponse;

import java.util.Optional;

public interface CourseService {

    Iterable<Course> findAll();

    Optional<Course> findById(Long id);

    Course save(Course course);

    CourseResponse getFullInfo(Long id);

    Course editCourse(Long id, Course course);

    void deleteById(Long id);

    Course updateDifficultyLevel(Long id, int difficulty) throws NotFoundException;
}
