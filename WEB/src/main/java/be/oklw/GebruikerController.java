package be.oklw;

import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.model.SysteemAccount;
import be.oklw.service.IGebruikerService;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

@ViewScoped
@ManagedBean
public class GebruikerController {
    private Account user;
    private boolean loggedIn;
    private Club club;

    @EJB
    IGebruikerService gebruikerService;

    @NotNull(message = "'Oud paswoord' is verplicht")
    private String oudPaswoord;

    @NotNull(message = "'Nieuw paswoord' is verplicht")
    private String nieuwPaswoord;

    @NotNull(message = "'Herhaal nieuw paswoord' is verplicht")
    private String nieuwPaswoordHerhaald;

    public String getOudPaswoord() {
        return oudPaswoord;
    }

    public void setOudPaswoord(String oudPaswoord) {
        this.oudPaswoord = oudPaswoord;
    }

    public String getNieuwPaswoord() {
        return nieuwPaswoord;
    }

    public void setNieuwPaswoord(String nieuwPaswoord) {
        this.nieuwPaswoord = nieuwPaswoord;
    }

    public String getNieuwPaswoordHerhaald() {
        return nieuwPaswoordHerhaald;
    }

    public void setNieuwPaswoordHerhaald(String nieuwPaswoordHerhaald) {
        this.nieuwPaswoordHerhaald = nieuwPaswoordHerhaald;
    }

    public Account getUser() {
        return user;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public boolean heeftRol(String rol) {
        if (user == null) {
            return false;
        }
        return user.heeftRol(rol);
    }

    public String logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(true);
        httpSession.setAttribute("user", null);
        user = null;
        loggedIn = false;
        return "home";
    }

    public void veranderPaswoord() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            if (loggedIn) {
                gebruikerService.veranderPaswoord(user, oudPaswoord, nieuwPaswoord);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Paswoord succesvol gewijzigd", "Paswoord succesvol gewijzigd");
                facesContext.addMessage(null, message);
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Om je paswoord te veranderen moet je ingelogd zijn", "Om je paswoord te veranderen moet je ingelogd zijn");
                facesContext.addMessage(null, message);
            }
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            facesContext.addMessage(null, message);
        }
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(true);
        user = (Account)httpSession.getAttribute("user");
        loggedIn = user != null;

        if (loggedIn) {
            club = user.getClub();
        }
    }
}
