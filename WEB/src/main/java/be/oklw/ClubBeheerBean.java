package be.oklw;

import be.oklw.model.*;
import be.oklw.service.IClubService;
import be.oklw.service.IKampioenschapService;
import be.oklw.service.ISponsorService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@ManagedBean(name = "clubBeheerBean")
@SessionScoped
public class ClubBeheerBean implements Serializable {

    private static final long serialVersionUID = 2742323554076118994L;

    @EJB
    IClubService clubService;

    @EJB
    IKampioenschapService kampioenschapService;

    @EJB
    ISponsorService sponsorService;

    private Account user;
    private Club club;
    private Kampioenschap kampioenschap;
    private Toernooi toernooi;

    private List<Kampioenschap> kampioenschappenVerleden;
    private List<Kampioenschap> kampioenschappenToekomst;

    private int kampId;
    private int toerId;
    private int sponsId;

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

    public List<Kampioenschap> getKampioenschappenVerleden() {
        return clubService.kampioenschappenVan(club, true);
    }

    public List<Kampioenschap> getKampioenschappenToekomst() {
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

    public String toernooiKlik() {
        Optional<Toernooi> toernooiOptional = kampioenschap.getToernooien().stream().filter(t -> t.getId() == toerId).findFirst();
        if (toernooiOptional.isPresent()) {
            toernooi = toernooiOptional.get();
            return "club_toernooi_aanpassen?faces-redirect=true";
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niet gevonden", "Toernooi niet gevonden"));
        }
        return "";
    }

    public String nieuwToernooi() {
        return "club_nieuw_toernooi?faces-redirect=true";
    }

    public String opslaan() {
        kampioenschapService.opslaan(kampioenschap);
        return "club_beheer?faces-redirect=true";
    }

    public String opslaanToernooi() {
        kampioenschapService.opslaanToernooi(toernooi);
        return "club_kampioenschapspagina?faces-redirect=true";
    }

    public void verwijderSponsor() {
        sponsorService.verwijderSponsorVan(sponsId, kampioenschap);
        refresh();
    }


    public void refresh() {
        kampioenschap = kampioenschapService.getKampioenschap(kampioenschap.getId());
        club = clubService.getClub(user);
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);

        if (session != null) {
            user = (Account)session.getAttribute("user");
            club = clubService.getClub(user);
        }
    }
}
