package be.oklw;

import be.oklw.model.Club;
import be.oklw.model.Contact;
import be.oklw.service.IClubService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@ManagedBean
public class ClubController {

    @NotNull(message= "Naam van de club is verplicht")
    private String naam;

    @NotNull(message = "Locatie van de club is verplicht")
    private String locatie;

    private String adres;

    private ArrayList<Contact> contactList;

    @EJB
    IClubService clubService;

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<Contact> contactList) {
        this.contactList = contactList;
    }

    public void maakNieuweClubAan(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
                clubService.maakNieuweClubAan(naam, locatie, adres);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nieuwe club werd aangemaakt", "Nieuwe club werd aangemaakt");
                facesContext.addMessage(null, message);
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            facesContext.addMessage(null, message);
        }
    }

    public String gaNaarContact(){
        return "to_contact";
    }

    public void verwijderContact(Club club, Contact contact){
        clubService.verwijderContact(club, contact);
    }
}
