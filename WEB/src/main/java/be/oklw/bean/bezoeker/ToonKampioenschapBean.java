package be.oklw.bean.bezoeker;

import be.oklw.model.Club;
import be.oklw.model.Kampioenschap;
import be.oklw.model.Sponsor;
import be.oklw.service.IKampioenschapService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean
@ViewScoped
public class ToonKampioenschapBean {

    @EJB
    IKampioenschapService kampioenschapService;

    private int kampioenschapsId = -999;
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

    // Deze setter wordt aangeroepen bij elke HTTP GET van bezoeker_kampioenschapspagina?id=<KAMPIOENSCHAP_ID>
    public void setKampioenschapsId(int kampioenschapsId) {
        boolean veranderd = this.kampioenschapsId != kampioenschapsId;

        this.kampioenschapsId = kampioenschapsId;

        if (veranderd) {
            kampioenschap = kampioenschapService.getKampioenschap(kampioenschapsId);

            // Als er geen geldige id meegegeven wordt in de url, redirecten naar de indexpagina
            if (kampioenschap == null) {
                redirect();
            }

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

    private void redirect() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "bezoeker_kampioenschapsindex.xhtml");
        } catch (Exception ex) {
            System.err.println(String.format("Fout bij redirection: %s", ex.getMessage()));
        }
    }
}
