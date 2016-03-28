package be.oklw.bean.club;

import be.oklw.hulp.SponsorCRUDHelper;
import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.service.IClubService;
import be.oklw.service.ISponsorService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class ClubSponsorBean {

    @EJB
    IClubService clubService;

    @EJB
    ISponsorService sponsorService;

    private Account user;
    private Club club;

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

    public String sponsorKlik() {
        if (sponsId > 0) {
            SponsorCRUDHelper sponsorCRUDHelper = new SponsorCRUDHelper(false, sponsId);

            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            session.setAttribute("sponsorCRUDHelper", sponsorCRUDHelper);

            return "club_sponsor_aanpassen?faces-redirect=true";
        }
        return "";
    }

    public String nieuweSponsor() {
        sponsId = -999;

        SponsorCRUDHelper sponsorCRUDHelper = new SponsorCRUDHelper(true, -999);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute("sponsorCRUDHelper", sponsorCRUDHelper);

        return "club_nieuwe_sponsor?faces-redirect=true";
    }

    public void removeSponsor() {
        try {
            club = sponsorService.removeSponsor(club, sponsId);
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage()));
        }
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        sponsId = -999;

        if (session != null) {
            user = (Account) session.getAttribute("user");

            try {
                club = clubService.getClub(user);
            } catch (Exception ex) {
                System.err.println("Ongeldige session state: " + ex.getMessage());
            }
        }
    }
}
