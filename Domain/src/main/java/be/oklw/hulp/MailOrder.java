package be.oklw.hulp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MailOrder implements Serializable {

    private static final long serialVersionUID = 1450948957444868666L;

    private List<String> emailAdressen = new ArrayList<>();
    private String subject;
    private String message;

    public MailOrder() {

    }

    public MailOrder(String subject, String message, List<String> emailAdressen) {
        this.subject = subject;
        this.message = message;
        this.emailAdressen = new ArrayList<>(emailAdressen);
    }

    public List<String> getEmailAdressen() {
        return emailAdressen;
    }

    public void setEmailAdressen(List<String> emailAdressen) {
        this.emailAdressen = emailAdressen;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
