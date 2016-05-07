package be.oklw.bean.common;

import be.oklw.model.Account;
import be.oklw.service.IGebruikerService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class PaswoordVergetenBean {

    @EJB
    IGebruikerService gebruikerService;

    private Account account;
    private boolean accountFound = false;
    private String gebruikersNaam;

    public Account getAccount() {
        return account;
    }

    public boolean isAccountFound() {
        return accountFound;
    }

    public String getGebruikersNaam() {
        return gebruikersNaam;
    }

    public void setGebruikersNaam(String gebruikersNaam) {
        this.gebruikersNaam = gebruikersNaam;
    }

    public String getEmail() {
        return account != null ? account.getEmail() : "";
    }

    public void zoekAccount() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        accountFound = false;

        try {
            account = gebruikerService.getAccount(gebruikersNaam);

            if (account == null) {
                facesContext.addMessage(
                        null,
                        new FacesMessage(
                                FacesMessage.SEVERITY_WARN,
                                "Oeps",
                                "Onbestaande gebruikersnaam"
                        )
                );

                return;
            }

            accountFound = true;
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

    public void verstuurLink() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        try {
            gebruikerService.verstuurPaswoordResetLink(account);

            facesContext.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Geslaagd",
                            String.format("Er werd een mail met resetlink verstuurd naar %s", account.getEmail())
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
