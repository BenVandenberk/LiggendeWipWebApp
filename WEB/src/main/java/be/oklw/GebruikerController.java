package be.oklw;

import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.service.IClubService;
import be.oklw.service.IGebruikerService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Date;

@RequestScoped
@ManagedBean
public class GebruikerController {

    private Account user;
    private boolean loggedIn;
    private Club club;
    private boolean heeftLogo;
    private String logoUrl;

    @EJB
    IGebruikerService gebruikerService;

    @EJB
    IClubService clubService;

    @NotNull(message = "'Oud paswoord' is verplicht")
    private String oudPaswoord;

    @NotNull(message = "'Nieuw paswoord' is verplicht")
    private String nieuwPaswoord;

    @NotNull(message = "'Herhaal nieuw paswoord' is verplicht")
    private String nieuwPaswoordHerhaald;


    public String getLogoUrl() {
        return String.format("upload/clublogos/%s?time=%s", club.getLogoPad(), new Date().getTime());
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public boolean isHeeftLogo() {
        if (club == null) {
            return false;
        }
        return StringUtils.isNotBlank(club.getLogoPad());
    }

    public void setHeeftLogo(boolean heeftLogo) {
        this.heeftLogo = heeftLogo;
    }

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
                user = gebruikerService.veranderPaswoord(user, oudPaswoord, nieuwPaswoord);
                HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(false);
                httpSession.setAttribute("user", user);
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

    public void bewaarAfmetingen(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            clubService.bewaarAfmetingen(club);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Opgeslagen", "Afmetingen succesvol opgeslagen");
            facesContext.addMessage("frm_afmetingen:btn_bewaar", message);
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage());
            facesContext.addMessage("frm_afmetingen:btn_bewaar", message);
        }
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(true);
        user = (Account)httpSession.getAttribute("user");
        loggedIn = user != null;

        if (loggedIn) {
            club = clubService.getClub(user);
        }
    }
}
