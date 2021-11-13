package net.duke.dkuguide.web.rest;

import net.duke.dkuguide.data.models.Course;
import net.duke.dkuguide.service.implement.CourseServiceImpl;
import net.duke.dkuguide.web.payload.response.CourseResponse;
import net.duke.dkuguide.web.payload.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/v1/course", produces = "application/json")
public class CourseRestController {

    private CourseServiceImpl courseService;

    public CourseRestController(CourseServiceImpl courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    private Iterable<Course> getAllCourses() {
        return courseService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<ResponseMessage> addCourse(@RequestBody Course course) {
        String message = "Course successfully added: " + course.getName();
        try {
            courseService.save(course);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not add course: " + course.getName();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/{id}")
    private Optional<Course> getCourse(@PathVariable Long id) {
        return courseService.findById(id);
    }

    @GetMapping("/{id}/getFullInfo")
    private CourseResponse getCourseFullInfo(@PathVariable Long id) {
        return courseService.getFullInfo(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<ResponseMessage> editCourse(@PathVariable Long id, @RequestBody Course course) {
        String message = "Edited course successfully: " + course.getName();
        try {
            courseService.editCourse(id, course);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not edit course";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    @DeleteMapping("/{id}")
    private void deleteCourse(@PathVariable Long id) {
        courseService.deleteById(id);
    }

}
