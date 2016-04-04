package be.oklw.bean.systeem;

import be.oklw.model.Contact;
import be.oklw.service.IContactService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
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

            Flash flash = facesContext.getExternalContext().getFlash();
            flash.setKeepMessages(true);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Geslaagd!", "Nieuw contact werd aangemaakt, vergeet  niet om de club nog op te slaan!"));

            reset();

        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage());
            facesContext.addMessage(null, message);
        }
        return "to_nieuwe_club";
    }

    public String wijzigContact(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            contactService.wijzigContact(naam, telefoonnummer, email, beheerder, selectedContact.getId());
            contactLijstBean.wijzigContact(selectedContact.getId());
            Flash flash = facesContext.getExternalContext().getFlash();
            flash.setKeepMessages(true);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Geslaagd!", "Contact werd gewijzigd, vergeet niet om de club nog op te slaan!"));

            reset();

        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage());
            facesContext.addMessage(null, message);
        }
        return "to_nieuwe_club";
    }

    public void reset(){
        this.email="";
        this.naam="";
        this.telefoonnummer="";
        this.beheerder = false;
    }

}
