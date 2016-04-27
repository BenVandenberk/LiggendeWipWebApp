package be.oklw.service;

import be.oklw.hulp.MailOrder;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "jms/oklwQueue/"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "connectionFactoryLookup",
                propertyValue = "jms/oklwConnectionFactory")
})
public class OklwMessageBean implements MessageListener {

    @EJB
    IMailService mailService;

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage)message;

        try {
            MailOrder mailOrder = (MailOrder) objectMessage.getObject();
            mailService.sendMail(
                    mailOrder.getSubject(),
                    mailOrder.getMessage(),
                    mailOrder.getEmailAdressen()
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
