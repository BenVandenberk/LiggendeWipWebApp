package be.oklw.bean.club;

import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.model.Kampioenschap;
import be.oklw.model.hulp.Inschrijving;
import be.oklw.service.IClubService;
import be.oklw.service.IInschrijfService;
import be.oklw.service.IKampioenschapService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.List;

@ManagedBean
@ViewScoped
public class ClubInschrijvenBean {

    @EJB
    IKampioenschapService kampioenschapService;

    @EJB
    IInschrijfService inschrijfService;

    @EJB
    IClubService clubService;

    private List<Kampioenschap> kampioenschappenInschrijvenMogelijk;
    private List<Inschrijving> inschrijvingenVanClub;

    public List<Kampioenschap> getKampioenschappenInschrijvenMogelijk() {
        return kampioenschappenInschrijvenMogelijk;
    }

    public List<Inschrijving> getInschrijvingenVanClub() {
        return inschrijvingenVanClub;
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        Club club = null;
        if (session != null) {
            Account user = (Account) session.getAttribute("user");
            club = clubService.getClub(user);
        }

        kampioenschappenInschrijvenMogelijk = kampioenschapService.getKampioenschapenInschrijvenMogelijk();

        if (club != null) {
        inschrijvingenVanClub = inschrijfService.getInschrijvingenVoor(club);
        }
    }
}
