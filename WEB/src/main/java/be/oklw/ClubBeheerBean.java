package be.oklw;

import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.model.Kampioenschap;
import be.oklw.service.IClubService;
import be.oklw.service.IKampioenschapService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
public class ClubBeheerBean implements Serializable {

    private static final long serialVersionUID = 2742323554076118994L;

    @EJB
    IClubService clubService;

    @EJB
    IKampioenschapService kampioenschapService;

    private Account user;
    private Club club;
    private Kampioenschap kampioenschap;

    private List<Kampioenschap> kampioenschappenVerleden;
    private List<Kampioenschap> kampioenschappenToekomst;

    private int kampId;

    public void setKampId(int kampId) {
        this.kampId = kampId;
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

    public String kampioenschapKlik() {
        kampioenschap = kampioenschapService.getKampioenschap(kampId);
        return "detail";
    }

    public void opslaan(ActionEvent event) {
        kampioenschapService.opslaan(kampioenschap);
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
