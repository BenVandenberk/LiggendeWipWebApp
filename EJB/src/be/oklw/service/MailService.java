package be.oklw.service;

import be.oklw.exception.BusinessException;

import javax.ejb.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MailService implements IMailService {

    final String CONFIG_PATH = "/home/oklwbb/conf/owm.properties";

    @Override
    public boolean sendMail(String subject, String body, List<String> toAdressen) throws BusinessException {
        Properties l = new Properties();
        try {
            Path path = Paths.get(CONFIG_PATH);
            List<String> lines = Files.readAllLines(path);

            String[] keyValue;
            for (String s : lines) {
                keyValue = s.split("=");
                l.setProperty(keyValue[0], keyValue[1]);
            }
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis met de mail config file: " + ex.getMessage());
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(l.get("g").toString(), l.get("w").toString());
                    }
                });

        try {

            InternetAddress[] adressen = new InternetAddress[toAdressen.size()];
            for (int i = 0; i < toAdressen.size(); i++) {
                adressen[i] = new InternetAddress(toAdressen.get(i));
            }

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(l.get("g").toString()));
            message.setRecipients(Message.RecipientType.TO,
                    adressen);
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new BusinessException(e.getMessage());
        }
        return true;
    }
}
