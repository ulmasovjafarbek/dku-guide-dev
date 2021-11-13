package net.duke.dkuguide.security.verification;

import net.duke.dkuguide.data.models.Student;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private String appUrl;
    private Locale locale;
    private Student student;

    public OnRegistrationCompleteEvent(Student student, Locale locale, String url) {
        super(student);
        this.student = student;
        this.locale = locale;
        this.appUrl = url;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
