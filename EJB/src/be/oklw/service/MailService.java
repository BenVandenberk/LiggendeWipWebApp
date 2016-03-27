package be.oklw.service;

import be.oklw.exception.BusinessException;

import javax.ejb.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MailService implements IMailService {

    @Override
    public boolean sendMail(String subject, String body, List<String> toAdressen) throws BusinessException {
        final String username = "oklw.webmaster@gmail.com";
        final String password = "oklwBertBen2016";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            InternetAddress[] adressen = new InternetAddress[toAdressen.size()];
            for (int i = 0; i < toAdressen.size(); i++) {
                adressen[i] = new InternetAddress(toAdressen.get(i));
            }

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
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
