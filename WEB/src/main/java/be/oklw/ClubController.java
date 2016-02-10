package be.oklw;

import be.oklw.model.Club;
import be.oklw.model.Contact;
import be.oklw.service.IClubService;
import be.oklw.service.IContactService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@ManagedBean(name = "clubController")
public class ClubController implements Serializable{

    @NotNull(message= "Naam van de club is verplicht")
    private String naam;

    @NotNull(message = "Locatie van de club is verplicht")
    private String locatie;

    private String adres;

    private List<Contact> contactLijst = new ArrayList<>();

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

    public List<Contact> getContactLijst() {
        return contactLijst;
    }

    public void setContactLijst(List<Contact> contactLijst) {
        this.contactLijst = contactLijst;
    }

    public String maakNieuweClubAan(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
                clubService.maakNieuweClubAan(naam, locatie, adres, contactLijst);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nieuwe club werd aangemaakt", "Nieuwe club werd aangemaakt");
                facesContext.addMessage(null, message);
                this.reset();
                return "to_systeem_clubbeheer";
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            facesContext.addMessage(null, message);
        }
        return "";
    }

    public void verwijderContact(Club club, Contact contact){
        clubService.verwijderContact(club, contact);
    }

    public void addContact(Contact contact){
        contactLijst.add(contact);
    }

    public void refreshContacten() {
        contactLijst = contactService.alleContacten();
    }

    public void reset(){
        this.adres = "";
        this.contactLijst = new ArrayList<>();
        this.locatie = "";
        this.naam = "";
    }


}
