package net.duke.dkuguide.web.rest;

import net.duke.dkuguide.data.models.Professor;
import net.duke.dkuguide.service.implement.ProfessorServiceImpl;
import net.duke.dkuguide.web.payload.response.ProfessorResponse;
import net.duke.dkuguide.web.payload.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/v1/professor", produces = "application/json")
public class ProfessorRestController {

    private ProfessorServiceImpl professorService;

    public ProfessorRestController(ProfessorServiceImpl professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    private Iterable<Professor> getAllProfessors() {
        return professorService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<ResponseMessage> addProfessor(@RequestBody Professor professor) {
        String message = "Added professor successfully: " + professor.getFirstname();
        try {
            professorService.save(professor);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not add professor: " + professor.getFirstname();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/getFullInfo/{id}")
    private ProfessorResponse getProfessorFullInfo(@PathVariable Long id) {
        return professorService.getFullInfoById(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<ResponseMessage> editProfessor(@PathVariable Long id, @RequestBody Professor professor) {
        String message = "Edited professor successfully: " + professor.getFirstname();
        try {
            professorService.editProf(id, professor);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Failed to edit professor";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping("/{profId}/addCourse/{courseId}")
    private ResponseEntity<ResponseMessage> addCourseToProfessor(
            @PathVariable Long profId, @PathVariable Long courseId) {
        String message = "Successfully added course to prof";
        try {
            professorService.addCourseToProfessor(profId, courseId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not add course to prof";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @DeleteMapping("/{id}")
    private void deleteProfessor(@PathVariable Long id) {
        professorService.deleteById(id);
    }
}
