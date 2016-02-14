package be.oklw;

import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.model.Sponsor;
import be.oklw.service.IClubService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ManagedBean(name = "clubSponsorBean")
@SessionScoped
public class ClubSponsorBean implements Serializable {

    private static final long serialVersionUID = -6799949902003663470L;

    @EJB
    IClubService clubService;

    private Account user;
    private Club club;

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

    public void addSponsor(Sponsor sponsor) {
        try {
            club = clubService.addSponsor(sponsor, club);
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage()));
        }
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
