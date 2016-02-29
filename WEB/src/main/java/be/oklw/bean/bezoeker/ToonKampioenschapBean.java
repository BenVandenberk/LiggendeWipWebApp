package be.oklw.bean.bezoeker;

import be.oklw.model.Club;
import be.oklw.model.Kampioenschap;
import be.oklw.model.Sponsor;
import be.oklw.service.IKampioenschapService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean
@ViewScoped
public class ToonKampioenschapBean {

    @EJB
    IKampioenschapService kampioenschapService;

    private int kampioenschapsId = -1;
    private Kampioenschap kampioenschap;
    private Club club;
    private List<Sponsor[]> nevenSponsorsPerDrie;

    public int getKampioenschapsId() {
        return kampioenschapsId;
    }

    public Kampioenschap getKampioenschap() {
        return kampioenschap;
    }

    public Club getClub() {
        return club;
    }

    public List<Sponsor[]> getNevenSponsorsPerDrie() {
        return nevenSponsorsPerDrie;
    }

    public void setKampioenschapsId(int kampioenschapsId) {
        boolean veranderd = this.kampioenschapsId != kampioenschapsId;

        this.kampioenschapsId = kampioenschapsId;

        if (veranderd) {
            kampioenschap = kampioenschapService.getKampioenschap(kampioenschapsId);
            club = kampioenschap.getClub();
            List<Sponsor> nevenSponsors = kampioenschap.getSponsors().stream().skip(2).collect(Collectors.toList());
            nevenSponsorsPerDrie = new ArrayList<Sponsor[]>();

            // Alle resterende sponsors (dus niet de eerste 2) opdelen in groepjes van 3

            Sponsor[] sponsors = new Sponsor[3];
            for (int i = 0; i < nevenSponsors.size(); i++) {
                if (i % 3 == 0) {
                    sponsors = new Sponsor[3];
                    nevenSponsorsPerDrie.add(sponsors);
                }
                sponsors[i % 3] = nevenSponsors.get(i);
            }
        }
    }
}
