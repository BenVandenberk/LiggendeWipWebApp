package be.oklw.bean.club;

import be.oklw.model.*;
import be.oklw.service.IClubService;
import be.oklw.service.IInschrijfService;
import be.oklw.service.IToernooiService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.List;

@ManagedBean
@ViewScoped
public class ToonInschrijvingBean {

    @EJB
    IClubService clubService;

    @EJB
    IToernooiService toernooiService;

    @EJB
    IInschrijfService inschrijfService;

    private int toernooiId = -1;
    private Toernooi toernooi;
    private Kampioenschap kampioenschap;
    private Club club;

    private int teVerwijderenPloegId;

    public int getToernooiId() {
        return toernooiId;
    }

    public void setToernooiId(int toernooiId) {
        boolean veranderd = this.toernooiId != toernooiId;

        this.toernooiId = toernooiId;

        try {
            if (veranderd) {
                toernooi = toernooiService.getToernooi(toernooiId);
                if (toernooi != null) {
                    kampioenschap = toernooi.getKampioenschap();
                } else {
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    externalContext.redirect(externalContext.getRequestContextPath() + "club_inschrijven.xhtml");
                }
            }
        } catch (Exception ex) {

        }
    }

    public int getTeVerwijderenPloegId() {
        return teVerwijderenPloegId;
    }

    public void setTeVerwijderenPloegId(int teVerwijderenPloegId) {
        this.teVerwijderenPloegId = teVerwijderenPloegId;
    }

    public Toernooi getToernooi() {
        return toernooi;
    }

    public Club getClub() {
        return club;
    }

    public Kampioenschap getKampioenschap() {
        return kampioenschap;
    }

    public List<Ploeg> getIngeschrevenPloegen() {
        return toernooi.getPloegenVan(club);
    }

    public void addPloeg() {
        int volgendePloegIndex = toernooi.getPloegenVan(club).size() + 1;

        try {
            Ploeg.schrijfPloegInVoorToernooi(club, toernooi, club.getNaam() + volgendePloegIndex);
            toernooi = toernooiService.save(toernooi);
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    ex.getMessage()
            ));
        }
    }

    public void verwijderPloeg() {
        try {
            toernooi.removePloeg(teVerwijderenPloegId);
            club.removePloeg(teVerwijderenPloegId);
            toernooi = toernooiService.save(toernooi);
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    ex.getMessage()
            ));
        }
    }

    public String opslaan() {
        try {
            toernooi = toernooiService.save(toernooi);
            return "club_inschrijven?faces-redirect=true";
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    ex.getMessage()
            ));
        }
        return "";
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session != null) {
            Account user = (Account) session.getAttribute("user");
            club = clubService.getClub(user);
        }
    }
}
