package be.oklw.bean.bezoeker;

import be.oklw.bean.common.GebruikerController;
import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.model.Lid;
import be.oklw.model.PermissieNiveau;
import be.oklw.service.IGebruikerService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class RegistratieBean {

    @EJB
    IGebruikerService gebruikerService;

    private String registratieCode;
    private Lid lid;
    private String gebruikersNaam;
    private String paswoord;
    private String herhaaldPaswoord;

    private boolean clubConfirmed = false;

    private Club club;

    public String getRegistratieCode() {
        return registratieCode;
    }

    public void setRegistratieCode(String registratieCode) {
        this.registratieCode = registratieCode;
    }

    public Club getClub() {
        return club;
    }

    public Lid getLid() {
        return lid;
    }

    public String getGebruikersNaam() {
        return gebruikersNaam;
    }

    public void setGebruikersNaam(String gebruikersNaam) {
        this.gebruikersNaam = gebruikersNaam;
    }

    public String getPaswoord() {
        return paswoord;
    }

    public void setPaswoord(String paswoord) {
        this.paswoord = paswoord;
    }

    public String getHerhaaldPaswoord() {
        return herhaaldPaswoord;
    }

    public void setHerhaaldPaswoord(String herhaaldPaswoord) {
        this.herhaaldPaswoord = herhaaldPaswoord;
    }

    public boolean isClubConfirmed() {
        return clubConfirmed;
    }

    public void controleerCode() {
        club = null;
        clubConfirmed = false;
        FacesContext facesContext = FacesContext.getCurrentInstance();

        try {
            club = gebruikerService.valideerRegistratieCode(registratieCode);

            if (club == null) {
                facesContext.addMessage(
                        null,
                        new FacesMessage(
                                FacesMessage.SEVERITY_WARN,
                                "Oeps",
                                "Onjuiste Registratie Code"
                        )
                );
            } else {
                clubConfirmed = true;
            }
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

    public String registreer() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (!paswoord.equals(herhaaldPaswoord)) {
            facesContext.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Oeps",
                            "Paswoord en herhaald paswoord moeten identiek zijn"
                    )
            );
            return "";
        }

        if (!clubConfirmed) {
            facesContext.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Oeps",
                            "Geef eerst een geldige registratiecode en klik op 'Controleer Code'"
                    )
            );
            return "";
        }

        try {
            Account lidAccount = gebruikerService.registreer(lid, club, gebruikersNaam, paswoord);
            HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
            httpSession.setAttribute("user", lidAccount);

            return "home?faces-redirect=true";
        } catch (Exception ex) {
            facesContext.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Fout",
                            ex.getMessage()
                    )
            );
            return "";
        }
    }

    public void veranderVanClub() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (!clubConfirmed) {
            facesContext.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Oeps",
                            "Geef eerst een geldige registratiecode en klik op 'Controleer Code'"
                    )
            );
            return;
        }

        try {
            HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
            Account user = (Account) httpSession.getAttribute("user");
            boolean loggedIn = user != null;

            if (!loggedIn || !(user.getPermissieNiveau().equals(PermissieNiveau.LID))) {
                throw new Exception("Om van club te veranderen moet je ingelogd zijn als Lid");
            }

            Lid lid = user.getLid();
            lid.setClub(club);
            gebruikerService.updateLid(lid);
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

    @PostConstruct
    public void init() {
        lid = new Lid();
    }
}
