package be.oklw.service;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IMailService {

    boolean sendMail(String subject, String body, List<String> toAdressen);
}
