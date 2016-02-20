package be.oklw;

import be.oklw.model.Club;
import be.oklw.model.Contact;
import be.oklw.service.IContactService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@SessionScoped
@ManagedBean(name="contactController")
public class ContactController implements Serializable{

    private String naam;

    @ManagedProperty(value = "#{contactLijstBean}")
    private ContactLijstBean contactLijstBean;

    private String telefoonnummer;
    private String email;
    private boolean beheerder;

    private boolean showWijzig;
    private Contact selectedContact;

    @EJB
    IContactService contactService;

    public void setContactLijstBean(ContactLijstBean contactLijstBean) {
        this.contactLijstBean = contactLijstBean;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBeheerder() {
        return beheerder;
    }

    public void setBeheerder(boolean beheerder) {
        this.beheerder = beheerder;
    }

    public boolean isShowWijzig() {
        return showWijzig;
    }

    public void setShowWijzig(boolean showWijzig) {
        this.showWijzig = showWijzig;
    }

    public Contact getSelectedContact() {
        return selectedContact;
    }

    public void setSelectedContact(Contact selectedContact) {
        this.selectedContact = selectedContact;
        naam = selectedContact.getNaam();
        email = selectedContact.getEmail();
        telefoonnummer = selectedContact.getTelefoonnummer();
        beheerder = selectedContact.isBeheerder();
    }

    public String maakNieuwContactAan(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            contactService.maakNieuwContactAan(naam, telefoonnummer, email, beheerder);
            contactLijstBean.addNieuwsteContact();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nieuw contact werd aangemaakt", "Nieuw contact werd aangemaakt");
            facesContext.addMessage(null, message);
            reset();
            return "to_nieuwe_club";
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            facesContext.addMessage(null, message);
        }
        return "";
    }

    public String wijzigContact(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            contactService.wijzigContact(naam, telefoonnummer, email, beheerder, selectedContact.getId());
            contactLijstBean.wijzigContact(selectedContact.getId());
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Contact werd gewijzigd", "Contact werd gewijzigd");
            facesContext.addMessage(null, message);
            reset();
            return "to_nieuwe_club";
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            facesContext.addMessage(null, message);
        }
        return "";
    }

    public void reset(){
        this.email="";
        this.naam="";
        this.telefoonnummer="";
        this.beheerder = false;
    }

}
