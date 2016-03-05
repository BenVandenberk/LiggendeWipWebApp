package be.oklw.bean.club;

import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.model.Kampioenschap;
import be.oklw.model.Toernooi;
import be.oklw.service.IClubService;
import be.oklw.service.IToernooiService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class ToonInschrijvingBean {

    @EJB
    IClubService clubService;

    @EJB
    IToernooiService toernooiService;

    private int toernooiId = -1;
    private Toernooi toernooi;
    private Kampioenschap kampioenschap;
    private Club club;

    public int getToernooiId() {
        return toernooiId;
    }

    public void setToernooiId(int toernooiId) {
        boolean veranderd = this.toernooiId != toernooiId;

        this.toernooiId = toernooiId;

        if (veranderd) {
            toernooi = toernooiService.getToernooi(toernooiId);
            kampioenschap = toernooi.getKampioenschap();
        }
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

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);

        if (session != null) {
            Account user = (Account)session.getAttribute("user");
            club = clubService.getClub(user);
        }
    }
}
