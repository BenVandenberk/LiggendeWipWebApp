package be.oklw.bean.club;

import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.model.Kampioenschap;
import be.oklw.model.Toernooi;
import be.oklw.service.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "clubBeheerBean")
@SessionScoped
public class ClubBeheerBean implements Serializable {

    //region MEMBERS

    private static final long serialVersionUID = 2742323554076118994L;

    @EJB
    IClubService clubService;

    @EJB
    IKampioenschapService kampioenschapService;

    @EJB
    ISponsorService sponsorService;

    @EJB
    IToernooiService toernooiService;

    @EJB
    IInschrijfService inschrijfService;

    private Account user;
    private Club club;
    private Kampioenschap kampioenschap;
    private Toernooi toernooi;

    private int kampId;
    private int toerId;
    private int sponsId;

    private String inschrijvingenGeopendMessage;

    private boolean isNieuwToernooi;

    //endregion

    //region PROPERTIES

    public void setToerId(int toerId) {
        this.toerId = toerId;
    }

    public void setKampId(int kampId) {
        this.kampId = kampId;
    }

    public int getSponsId() {
        return sponsId;
    }

    public void setSponsId(int sponsId) {
        this.sponsId = sponsId;
    }

    public String getInschrijvingenGeopendMessage() {
        return inschrijvingenGeopendMessage;
    }

    public List<Kampioenschap> getKampioenschappenVerleden() {
        init();
        return clubService.kampioenschappenVan(club, true);
    }

    public List<Kampioenschap> getKampioenschappenToekomst() {
        init();
        return clubService.kampioenschappenVan(club, false);
    }

    public Kampioenschap getKampioenschap() {
        return kampioenschap;
    }

    public void setKampioenschap(Kampioenschap kampioenschap) {
        this.kampioenschap = kampioenschap;
    }

    public Toernooi getToernooi() {
        return toernooi;
    }

    public void setToernooi(Toernooi toernooi) {
        this.toernooi = toernooi;
    }

    public String kampioenschapKlik() {
        kampioenschap = kampioenschapService.getKampioenschap(kampId);
        return "club_kampioenschapspagina?faces-redirect=true";
    }

    public Club getClub() {
        return club;
    }

    public boolean isNieuwToernooi() {
        return isNieuwToernooi;
    }

    //endregion

    //region METHODS

    public String toernooiKlik() {
        isNieuwToernooi = false;
        toernooi = toernooiService.getToernooi(toerId);
        kampioenschap = toernooi.getKampioenschap();
        if (toernooi != null) {
            return "club_toernooi_aanpassen?faces-redirect=true";
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niet gevonden", "Toernooi niet gevonden"));
        }
        return "";
    }

    public String nieuwToernooi() {
        toernooi = new Toernooi();
        isNieuwToernooi = true;
        return "club_toernooi_aanpassen?faces-redirect=true";
    }

    public String opslaan() {
        kampioenschapService.opslaan(kampioenschap);
        return "club_beheer?faces-redirect=true";
    }

    public String opslaanToernooi() {
        if (isNieuwToernooi) {
            kampioenschapService.nieuwToernooi(toernooi, kampioenschap);
        } else {
            kampioenschapService.opslaanToernooi(toernooi);
        }
        return "club_kampioenschapspagina?faces-redirect=true";
    }

    public void verwijderSponsor() {
        sponsorService.verwijderSponsorVan(sponsId, kampioenschap);
        refresh();
    }

    public void openInschrijvingen() {
        try {
            inschrijvingenGeopendMessage = inschrijfService.openInschrijvingen(toernooi);
            toernooi = toernooiService.getToernooi(toernooi.getId());
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    ex.getMessage()));
        }
    }

    public String inschrijvingenBeheren() {
        return "club_inschrijvingen_beheren?faces-redirect=true&toerID=" + toernooi.getId();
    }


    public void refresh() {
        kampioenschap = kampioenschapService.getKampioenschap(kampioenschap.getId());
        club = clubService.getClub(user);
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session != null) {
            user = (Account) session.getAttribute("user");
            club = clubService.getClub(user);
        }
    }

    //endregion
}
