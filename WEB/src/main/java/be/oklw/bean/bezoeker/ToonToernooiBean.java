package be.oklw.bean.bezoeker;

import be.oklw.model.Club;
import be.oklw.model.Kampioenschap;
import be.oklw.model.Toernooi;
import be.oklw.service.IKampioenschapService;
import be.oklw.service.IToernooiService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ToonToernooiBean {

    @EJB
    IToernooiService toernooiService;

    private int toernooiId = -1;
    private Toernooi toernooi;
    private Club club;
    private Kampioenschap kampioenschap;

    public int getToernooiId() {
        return toernooiId;
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

    public void setToernooiId(int toernooiId) {
        boolean veranderd = this.toernooiId != toernooiId;

        this.toernooiId = toernooiId;

        if(veranderd) {
            toernooi = toernooiService.getToernooi(toernooiId);
            kampioenschap = toernooi.getKampioenschap();
            club = kampioenschap.getClub();
        }
    }
}
