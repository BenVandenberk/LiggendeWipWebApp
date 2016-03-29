package be.oklw.bean.systeem;

import be.oklw.model.Club;
import be.oklw.model.Contact;
import be.oklw.service.IClubService;
import be.oklw.service.IContactService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import java.io.Serializable;
import java.util.*;

@SessionScoped
@ManagedBean(name = "clubController")
public class ClubController implements Serializable{

    private String naam;

    private String locatie;

    private String adres;

    private Contact selectedContact;

    private Club selectedClub;

    private boolean showWijzig;

    @EJB
    IClubService clubService;

    @EJB
    IContactService contactService;

    @ManagedProperty(value = "#{contactController}")
    private ContactController contactController;

    @ManagedProperty(value="#{contactLijstBean}")
    private ContactLijstBean contactLijstBean;

    public void setContactController(ContactController contactController) {
        this.contactController = contactController;
    }

    public void setContactLijstBean(ContactLijstBean contactLijstBean) {
        this.contactLijstBean = contactLijstBean;
    }

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

    public Contact getSelectedContact() {
        return selectedContact;
    }

    public void setSelectedContact(Contact selectedContact) {
        this.selectedContact = selectedContact;
    }

    public String maakNieuweClubAan(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
                clubService.maakNieuweClubAan(naam, locatie, adres, contactLijstBean.getContactLijst());
                Flash flash = facesContext.getExternalContext().getFlash();
                flash.setKeepMessages(true);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Geslaagd!", "Nieuwe club werd aangemaakt"));
                reset();
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage());
            facesContext.addMessage(null, message);
        }
        return "to_systeem_clubbeheer";
    }

    public String wijzigClub(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            clubService.wijzigClub(naam, locatie, adres, contactLijstBean.getContactLijst(), selectedClub.getId());

            Flash flash = facesContext.getExternalContext().getFlash();
            flash.setKeepMessages(true);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Geslaagd!", "Club werd aangepast"));
            reset();

        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage());
            facesContext.addMessage(null, message);
        }
        return "to_systeem_clubbeheer";
    }

    public void verwijderContact(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;
        try{
        if(selectedContact!=null){
            contactLijstBean.verwijderContact(selectedContact.getId());
            clubService.verwijderContact(selectedClub, selectedContact);
        }
        else {
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN,
                    "Oeps",
                    "Geen contact geselecteerd"
            ));
        }
        }
        catch (Exception ex){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage());
            facesContext.addMessage(null, message);
        }
    }

    public void reset(){
        this.adres = "";
        this.contactLijstBean.setContactLijst(new HashSet<>());
        this.locatie = "";
        this.naam = "";
    }

    public Club getSelectedClub() {
        return selectedClub;
    }

    public void setSelectedClub(Club selectedClub) {
        this.selectedClub = selectedClub;

        if (selectedClub!=null){
        naam = selectedClub.getNaam();
        locatie = selectedClub.getLocatie();
        adres = selectedClub.getAdres();
        if(selectedClub.getContacten()!=null){
            contactLijstBean.setContactLijst(selectedClub.getContacten());
        }
        }
    }

    public String naarNieuwContact(){
        contactController.reset();
        contactController.setShowWijzig(false);
        return "to_contact";
    }

    public String naarGeselecteerdContact(){
        contactController.reset();
        if(selectedContact!=null){
        contactController.setSelectedContact(selectedContact);
        contactController.setShowWijzig(true);
        return "to_contact";}
        else{
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN,
                    "Oeps",
                    "Geen contact geselecteerd"
            ));
            return "";
        }
    }

    public boolean isShowWijzig() {
        return showWijzig;
    }

    public void setShowWijzig(boolean showWijzig) {
        this.showWijzig = showWijzig;
    }

}
