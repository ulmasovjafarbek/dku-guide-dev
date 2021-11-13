package net.duke.dkuguide.web.rest;

import net.duke.dkuguide.data.models.Student;
import net.duke.dkuguide.service.implement.StudentServiceImpl;
import net.duke.dkuguide.web.payload.response.ResponseMessage;
import net.duke.dkuguide.web.payload.response.SavedProfResponse;
import net.duke.dkuguide.web.payload.response.StudentReviewResponse;
import net.duke.dkuguide.web.payload.response.StudentProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/v1/student", produces = "application/json")
public class StudentRestController {

    private final StudentServiceImpl studentService;

    public StudentRestController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    private Iterable<Student> getAllStudents() {
        return studentService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<ResponseMessage> addStudent(@RequestBody Student student) {
        String message = "Successfully added: " + student.getFirstname();
        try {
            studentService.save(student);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not add: " + student.getFirstname();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/{id}")
    private Optional<Student> getStudent(@PathVariable Long id) {
        return studentService.findById(id);
    }

    @GetMapping("/profile/{id}")
    private StudentProfileResponse getProfileInfo(@PathVariable Long id) {
        return studentService.getProfileById(id);
    }

    @GetMapping("/profile/{id}/reviews")
    private Iterable<StudentReviewResponse> getReviewsForStudent(@PathVariable Long id) {
        return studentService.getReviewsForStudent(id);
    }

    @GetMapping("/profile/{id}/savedProfessors")
    private Iterable<SavedProfResponse> getSavedProfsForStudent(@PathVariable Long id) {
        return studentService.getSavedProfessorsForStudent(id);
    }

    @PostMapping("/{studentId}/saveProf/{profId}")
    private ResponseEntity<ResponseMessage> saveProfessor(@PathVariable Long studentId,
                                                          @PathVariable Long profId) {
        String message = "Saved professor!";
        try {
            studentService.bookmarkProfessor(studentId, profId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not save professor";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PutMapping("/details/{id}")
    private ResponseEntity<ResponseMessage> editStudentDetails(@PathVariable Long id, @RequestBody Student student) {
        String message = "Successfully edited: " + student.getFirstname();
        try {
            studentService.editStudentDetails(id, student);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Failed to edit student";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @DeleteMapping("/{id}")
    private void deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
    }
}
