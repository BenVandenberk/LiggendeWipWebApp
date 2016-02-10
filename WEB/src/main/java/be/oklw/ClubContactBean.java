package be.oklw;

import be.oklw.model.Contact;
import be.oklw.service.IContactService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@RequestScoped
@ManagedBean
public class ClubContactBean {

    @EJB
    private IContactService contactService;

    public Contact getNieuwsteContact(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            Contact contact = contactService.getNieuwsteContact();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nieuwe club werd aangemaakt", "Nieuwe club werd aangemaakt");
            facesContext.addMessage(null, message);
            return contact;
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            facesContext.addMessage(null, message);
        }
        return null;
    }
}
