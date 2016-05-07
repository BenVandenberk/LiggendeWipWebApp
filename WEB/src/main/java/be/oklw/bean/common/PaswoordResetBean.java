package be.oklw.bean.common;

import be.oklw.model.Account;
import be.oklw.service.IGebruikerService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.UUID;

@ManagedBean
@ViewScoped
public class PaswoordResetBean {

    @EJB
    IGebruikerService gebruikerService;

    private String linkUUID;
    private UUID uuid;

    private String messageToUser;
    private boolean validLink;

    private Account account;

    public String getLinkUUID() {
        return linkUUID;
    }

    // De parameter uit de url wordt hier geset en gevalideerd
    public void setLinkUUID(String linkUUID) {
        this.linkUUID = linkUUID;

        try {
            this.uuid = UUID.fromString(linkUUID);
        } catch (IllegalArgumentException ex) {
            messageToUser = "Ongeldige link";
            validLink = false;
            return;
        }

        try {
            account = gebruikerService.valideerResetLink(uuid);

            if (account == null) {
                messageToUser = "Ongeldige link";
                validLink = false;
            } else {
                messageToUser = String.format("Klik op 'Reset Paswoord' om een nieuw paswoord toegestuurd te krijgen op [%s]", account.getEmail());
                validLink = true;
            }
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    ex.getMessage()
            ));
        }
    }

    public String getMessageToUser() {
        return messageToUser;
    }

    public Account getAccount() {
        return account;
    }

    public boolean isValidLink() {
        return validLink;
    }

    public void resetPaswoord() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        try {
            gebruikerService.resetPaswoord(account);

            facesContext.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Geslaagd",
                            String.format("Paswoord succesvol gereset. Er werd een mail gestuurd naar %s", account.getEmail())
                    )
            );
        } catch (Exception ex) {
            facesContext.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Fout",
                            ex.getMessage()
                    )
            );
        }
    }
}
