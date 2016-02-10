package be.oklw;

import be.oklw.model.Club;
import be.oklw.model.Contact;
import be.oklw.service.IContactService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@RequestScoped
@ManagedBean
public class ContactController implements Serializable{

    @NotNull(message= "Naam van contactpersoon is verplicht")
    private String naam;

    private String telefoonnummer;
    private String email;
    private boolean isBeheerder;

    private Club club;
    private Contact contact;

    @EJB
    IContactService contactService;

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
        return isBeheerder;
    }

    public void setIsBeheerder(boolean isBeheerder) {
        this.isBeheerder = isBeheerder;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String maakNieuwContactAan(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            contactService.maakNieuwContactAan(naam, telefoonnummer, email, false);
            setShow(true);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nieuw contact werd aangemaakt", "Nieuw contact werd aangemaakt");
            facesContext.addMessage(null, message);
            return "to_nieuwe_club";
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            facesContext.addMessage(null, message);
        }
        return "";
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }


    private boolean show;

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }


    /*public String naarContact(Club club){
        this.club = club;
        return "to_nieuw_contact";
    }*/
}
