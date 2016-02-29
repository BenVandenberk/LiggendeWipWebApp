package be.oklw.bean.club;

import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.model.Sponsor;
import be.oklw.service.IClubService;
import be.oklw.service.ISponsorService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Optional;

@ManagedBean(name = "clubSponsorBean")
@SessionScoped
public class ClubSponsorBean implements Serializable {

    private static final long serialVersionUID = -6799949902003663470L;

    @EJB
    IClubService clubService;

    @EJB
    ISponsorService sponsorService;

    private Account user;
    private Club club;
    private Sponsor sponsor;

    private int sponsId;

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public int getSponsId() {
        return sponsId;
    }

    public void setSponsId(int sponsId) {
        this.sponsId = sponsId;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public String sponsorKlik() {
        Optional<Sponsor> optSponsor = club.getSponsors().stream().filter(s -> s.getId() == sponsId).findFirst();
        if (optSponsor.isPresent()) {
            sponsor = optSponsor.get();
            return "club_sponsor_aanpassen?faces-redirect=true";
        }
        return "";
    }

    public String nieuweSponsor() {
        sponsId = -1;
        sponsor = null;
        return "club_nieuwe_sponsor?faces-redirect=true";
    }

    public void addSponsor(Sponsor sponsor) {
        try {
            club = clubService.addSponsor(sponsor, club);
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage()));
        }
    }

    public void removeSponsor() {
        try {
            sponsorService.removeSponsor(club, sponsId);
            club = clubService.getClub(user);
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage()));
        }
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);

        sponsId = -1;

        if (session != null) {
            user = (Account)session.getAttribute("user");
            club = clubService.getClub(user);
        }
    }
}
