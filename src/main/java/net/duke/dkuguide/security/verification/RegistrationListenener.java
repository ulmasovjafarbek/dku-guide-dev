package net.duke.dkuguide.security.verification;

import net.duke.dkuguide.data.models.Student;
import net.duke.dkuguide.service.implement.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.UUID;

@Component
public class RegistrationListenener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Value("${duke.app.email}")
    private String senderAddress;

    @Value("${duke.app.emailPassword}")
    private String senderPassword;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        Student student = event.getStudent();
        String token = UUID.randomUUID().toString();
        studentService.createVerificationToken(student, token);

        String recipientAddress = student.getEmail();
        String subject = "Registration Confirmation - DKU Guide";
        String confirmationUrl = event.getAppUrl() + "/api/v1/auth/verify?token=" + token;
        String message = "Activate your account: ";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
        mailSender.send(email);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);


        mailSender.setUsername(senderAddress);
        mailSender.setPassword(senderPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
