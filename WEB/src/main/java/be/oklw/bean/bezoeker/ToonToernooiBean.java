package be.oklw.bean.bezoeker;

import be.oklw.model.Club;
import be.oklw.model.Kampioenschap;
import be.oklw.model.Sponsor;
import be.oklw.model.Toernooi;
import be.oklw.service.IKampioenschapService;
import be.oklw.service.IToernooiService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class ToonToernooiBean {

    @EJB
    IToernooiService toernooiService;

    private int toernooiId = -1;
    private Toernooi toernooi;
    private Club club;
    private Kampioenschap kampioenschap;
    private List<Sponsor[]> sponsorsPerDrie;

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

    public List<Sponsor[]> getSponsorsPerDrie() {
        return sponsorsPerDrie;
    }

    public void setToernooiId(int toernooiId) {
        boolean veranderd = this.toernooiId != toernooiId;

        this.toernooiId = toernooiId;

        if(veranderd) {
            toernooi = toernooiService.getToernooi(toernooiId);
            kampioenschap = toernooi.getKampioenschap();
            club = kampioenschap.getClub();

            sponsorsPerDrie = new ArrayList<Sponsor[]>();

            // Alle resterende sponsors (dus niet de eerste 2) opdelen in groepjes van 3

            Sponsor[] sponsors = new Sponsor[3];
            for (int i = 0; i < kampioenschap.getSponsors().size(); i++) {
                if (i % 3 == 0) {
                    sponsors = new Sponsor[3];
                    sponsorsPerDrie.add(sponsors);
                }
                sponsors[i % 3] = kampioenschap.getSponsors().get(i);
            }
        }
    }
}
