package be.oklw.bean.common;

import be.oklw.model.*;
import be.oklw.service.IClubService;
import be.oklw.service.IGebruikerService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@RequestScoped
@ManagedBean
public class GebruikerController {

    private Account user;
    private boolean loggedIn;
    private Club club;
    private Lid lid;
    private boolean heeftLogo;
    private String logoUrl;
    private String newEmail;
    private String newTelefoonNummer;

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
        return String.format("upload/clublogos/%s?time=%s", club.getLogoFileName(), new Date().getTime());
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public boolean isHeeftLogo() {
        if (club == null) {
            return false;
        }
        return isNotBlank(club.getLogoFileName());
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

    public Lid getLid() {
        return lid;
    }

    public String getNewEmail() {
        SysteemAccount sysUser = (SysteemAccount) user;
        return sysUser.getEmail();
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewTelefoonNummer() {
        return ((SysteemAccount) user).getTelefoonnummer();
    }

    public void setNewTelefoonNummer(String newTelefoonNummer) {
        this.newTelefoonNummer = newTelefoonNummer;
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

    public void opslaan() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage();

        try {
            clubService.save(club);
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Geslaagd", "Registratiecode is succesvol gewijzigd");
        } catch (EJBException EJBEx) {
            if (EJBEx.getCause().getCause() instanceof PersistenceException) {
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Oeps", "Deze registratiecode is al eens gebruikt. Kies een andere code");
            } else {
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", EJBEx.getMessage());
            }
        } catch (Exception ex) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage());
        } finally {
            facesContext.addMessage(null, facesMessage);
        }
    }

    public void saveUser() {
        try {
            gebruikerService.saveAccount(user);

            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Geslaagd",
                            "Gegevens succesvol opgeslagen"
                    )
            );
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
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

    public String logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
        httpSession.setAttribute("user", null);
        user = null;
        loggedIn = false;
        return "home?faces-redirect=true";
    }

    public void veranderPaswoord() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            if (loggedIn) {
                user = gebruikerService.veranderPaswoord(user, oudPaswoord, nieuwPaswoord);
                HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(false);
                httpSession.setAttribute("user", user);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Geslaagd!", "Paswoord succesvol gewijzigd");
                facesContext.addMessage(null, message);
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", "Om je paswoord te veranderen moet je ingelogd zijn");
                facesContext.addMessage(null, message);
            }
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage());
            facesContext.addMessage(null, message);
        }
    }

    public void wijzigAdminContactGegevens() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            if (loggedIn && user instanceof SysteemAccount) {
                user = gebruikerService.wijzigAdminContactGegevens((SysteemAccount) user, newEmail, newTelefoonNummer);
                HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(false);
                httpSession.setAttribute("user", user);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Geslaagd!", "Contactgegevens succesvol gewijzigd");
                facesContext.addMessage(null, message);
                reset();
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", "Om je contactgegevens te veranderen moet je ingelogd zijn");
                facesContext.addMessage(null, message);
            }
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage());
            facesContext.addMessage(null, message);
        }
    }

    public void reset() {
        newEmail = "";
        newTelefoonNummer = "";
    }

    public void bewaarAfmetingen(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            clubService.save(club);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Opgeslagen", "Afmetingen succesvol opgeslagen");
            facesContext.addMessage("frm_afmetingen:btn_bewaar", message);
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage());
            facesContext.addMessage("frm_afmetingen:btn_bewaar", message);
        }
    }

    public List<String> getAdminContactGegevens() {
        List<SysteemAccount> systeemAccountList = gebruikerService.getAllSysteemAccount();
        SysteemAccount sysAcc = systeemAccountList.get(0);
        String email = sysAcc.getEmail();
        String telefoonNummer = sysAcc.getTelefoonnummer();
        List<String> lijst = Arrays.asList(new String[2]);
        ;

        if (isNotBlank(email) && email != null) {
            lijst.set(0, email);
        }
        if (isNotBlank(telefoonNummer) && telefoonNummer != null) {
            lijst.set(1, telefoonNummer);
        }
        if (lijst.get(0) == null) {
            lijst.set(0, "");
        }
        if (lijst.get(1) == null) {
            lijst.set(1, "");
        }
        return lijst;
    }

    public void updateLidGegevens() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        try {
            gebruikerService.updateLid(lid);

            facesContext.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Geslaagd",
                            "Gegevens zijn succesvol gewijzigd"
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

    public boolean emailIngevuld() {
        if (!loggedIn) {
            return true;
        }
        return StringUtils.isNotBlank(user.getEmail());
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
        user = (Account) httpSession.getAttribute("user");
        loggedIn = user != null;

        if (loggedIn && user.getPermissieNiveau() == PermissieNiveau.CLUB) {
            try {
                club = clubService.getClub(user);
            } catch (Exception ex) {
                System.err.println("Ongeldige session state: " + ex.getMessage());
            }
        }

        if (loggedIn && user.getPermissieNiveau() == PermissieNiveau.LID) {
            lid = user.getLid();
        }
    }


}
