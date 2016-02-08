package be.oklw;

import be.oklw.service.IContactService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.validation.constraints.NotNull;

@ManagedBean
public class ContactController {

    @NotNull(message= "Naam van contactpersoon is verplicht")
    private String naam;

    private String telefoonnummer;
    private String email;
    private boolean isBeheerder;

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

    public void maakNieuwContactAan(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            contactService.maakNieuwContactAan(naam, telefoonnummer, email, isBeheerder);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nieuw contact werd aangemaakt", "Nieuw contact werd aangemaakt");
            facesContext.addMessage(null, message);
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            facesContext.addMessage(null, message);
        }
    }
}
