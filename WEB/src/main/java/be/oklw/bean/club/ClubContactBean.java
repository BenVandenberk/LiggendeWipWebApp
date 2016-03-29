package be.oklw.bean.club;

import be.oklw.exception.BusinessException;
import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.model.Contact;
import be.oklw.service.IClubService;
import be.oklw.service.IContactService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

@ManagedBean(name = "clubContactBean")
@ViewScoped
public class ClubContactBean {

    private String naam;
    private String telefoonNummer;
    private String email;
    private boolean beheerder;
    private boolean showWijzig = false;
    private boolean showNieuw = false;

    private Contact selectedContact;
    private Club thisClub;
    private Set<Contact> contactList;

    @EJB
    IClubService clubService;

    @EJB
    IContactService contactService;

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        Club club = null;
        if (session != null) {
            Account user = (Account) session.getAttribute("user");

            try {
                club = clubService.getClub(user);
            } catch (Exception ex) {
                System.err.println("Ongeldige session state: " + ex.getMessage());
            }
        }

        thisClub = club;
        contactList = thisClub.getContacten();
    }

    public void showContactPanel(){
        showNieuw = true;
        showWijzig =false;
        selectedContact = null;
    }

    public void showWijzigContactPanel(){
        if (selectedContact != null){
            showWijzig = true;
            showNieuw = false;
            naam = selectedContact.getNaam();
            email = selectedContact.getEmail();
            telefoonNummer = selectedContact.getTelefoonnummer();
            beheerder = selectedContact.isBeheerder();
        }
        else{
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN,
                    "Oeps",
                    "Geen contact geselecteerd"
            ));
        }
    }

    public void verwijderContact(){
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            if(selectedContact!=null){
                clubService.verwijderContact(thisClub, selectedContact);
                contactList.remove(selectedContact);
                facesContext.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Geslaagd!",
                        "Contact werd verwijderd"
                ));
                reset();
            }
            else {
                facesContext.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN,
                        "Oeps",
                        "Geen contact geselecteerd"
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void maakNieuwContactAan(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;
        try {
            contactList.add(contactService.maakNieuwContactAan(naam, telefoonNummer, email, beheerder));
            clubService.wijzigClub(thisClub.getNaam(), thisClub.getLocatie(), thisClub.getAdres(), contactList, thisClub.getId());
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Geslaagd!", "Nieuw contact werd aangemaakt");
            facesContext.addMessage(null, message);
            reset();
        } catch (BusinessException ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage());
            facesContext.addMessage(null, message);
        }
    }

    public void wijzigContact(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;
        try {
            Contact contact = contactService.wijzigContact(naam, telefoonNummer, email, beheerder, selectedContact.getId());
            if(contactList != null){
                Iterator<Contact> i = contactList.iterator();
                while(i.hasNext()){
                    Contact c = i.next();
                    if (c.getId() == contact.getId()){
                        i.remove();
                    }
                }
            }
            contactList.add(contact);
            //clubService.wijzigClub(thisClub.getNaam(), thisClub.getLocatie(), thisClub.getAdres(), contactList, thisClub.getId());
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Geslaagd!", "Contact werd gewijzigd");
            facesContext.addMessage(null, message);
            reset();
        } catch (BusinessException ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage());
            facesContext.addMessage(null, message);
        }
    }

    public void reset(){
        showWijzig = false;
        showNieuw = false;
        naam = null;
        email = null;
        telefoonNummer = null;
        beheerder = false;
        selectedContact = null;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getTelefoonNummer() {
        return telefoonNummer;
    }

    public void setTelefoonNummer(String telefoonNummer) {
        this.telefoonNummer = telefoonNummer;
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

    public Contact getSelectedContact() {
        return selectedContact;
    }

    public void setSelectedContact(Contact selectedContact) {
        this.selectedContact = selectedContact;
    }

    public boolean isShowWijzig() {
        return showWijzig;
    }

    public void setShowWijzig(boolean showWijzig) {
        this.showWijzig = showWijzig;
    }

    public Set<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(Set<Contact> contactList) {
        this.contactList = contactList;
    }

    public boolean isShowNieuw() {
        return showNieuw;
    }

    public void setShowNieuw(boolean showNieuw) {
        this.showNieuw = showNieuw;
    }
}


