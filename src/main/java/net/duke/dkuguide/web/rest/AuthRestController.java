package net.duke.dkuguide.web.rest;

import net.duke.dkuguide.data.models.Student;
import net.duke.dkuguide.security.jwt.JwtUtils;
import net.duke.dkuguide.security.verification.OnRegistrationCompleteEvent;
import net.duke.dkuguide.security.verification.VerificationToken;
import net.duke.dkuguide.service.implement.StudentServiceImpl;
import net.duke.dkuguide.service.implement.UserDetailsImpl;
import net.duke.dkuguide.web.payload.request.LoginRequest;
import net.duke.dkuguide.web.payload.request.SignupRequest;
import net.duke.dkuguide.web.payload.response.JwtResponse;
import net.duke.dkuguide.web.payload.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    StudentServiceImpl studentService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> register(@Valid @RequestBody SignupRequest signupRequest,
                                                    HttpServletRequest request) {
        if (studentService.checkUsername(signupRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Username already taken"));
        }
        if (studentService.checkEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Email already taken"));
        }

        try {
            Student student = new Student(signupRequest.getUsername(), passwordEncoder.encode(signupRequest.getPassword()),
                    signupRequest.getEmail(), signupRequest.getFirstname(), signupRequest.getLastname());
            studentService.assignRoles(student, signupRequest.getRoles());
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(student, request.getLocale(), appUrl));
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    new ResponseMessage("Use your @duke.edu or @dukekunshan.edu.cn email"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Failed to register"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("User registered successfully!"));
    }

    @GetMapping(value = "/verify", produces = "application/json")
    public ResponseEntity<ResponseMessage> confirmRegistration(@RequestParam("token") String token) {
        String message = "Your registration is confirmed!";
        VerificationToken verificationToken = studentService.getVerificationToken(token);
        if (verificationToken == null) {
            message = "Invalid token";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }

        Student student = verificationToken.getStudent();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            message = "Token is already expired";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }

        student.setEnabled(true);
        studentService.save(student);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.isEnabled()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(
                    "Your account is not activated yet. We have sent the activation link to your email."
            ));
        }

        List<String> roles = userDetails.getAuthorities().stream()
                .map(r -> r.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(
                jwt, userDetails.getId(), userDetails.getUsername(),
                userDetails.getEmail(), roles));
    }
}
