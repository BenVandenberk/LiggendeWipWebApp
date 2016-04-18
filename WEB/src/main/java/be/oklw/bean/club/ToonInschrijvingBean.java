package be.oklw.bean.club;

import be.oklw.model.*;
import be.oklw.service.IClubService;
import be.oklw.service.IInschrijfService;
import be.oklw.service.ILedenService;
import be.oklw.service.IToernooiService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class ToonInschrijvingBean {

    @EJB
    IClubService clubService;

    @EJB
    IToernooiService toernooiService;

    @EJB
    IInschrijfService inschrijfService;

    @EJB
    ILedenService ledenService;

    private int toernooiId = -999;
    private Toernooi toernooi;
    private Kampioenschap kampioenschap;
    private Club club;
    private Inschrijving inschrijving;

    private List<Lid> alleClubLeden = new ArrayList<>();

    private int teVerwijderenPloegId;

    public int getToernooiId() {
        return toernooiId;
    }

    // Deze setter wordt aangeroepen bij elke HTTP GET van club_inschrijving?toerId=<TOERNOOI_ID>
    public void setToernooiId(int toernooiId) {
        boolean veranderd = this.toernooiId != toernooiId;

        this.toernooiId = toernooiId;

        try {
            if (veranderd) {
                toernooi = toernooiService.getToernooi(toernooiId);

                if (toernooi == null) {
                    redirect();
                }
                if (!toernooi.getStatus().isInschrijvingenOpen()) {
                    redirect();
                }
                kampioenschap = toernooi.getKampioenschap();
                inschrijving = toernooi.getInschrijvingVan(club);

                // De club is nog niet ingeschreven
                if (inschrijving == null) {
                    inschrijving = Inschrijving.nieuweInschrijving(club, toernooi);
                }
            }
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    ex.getMessage()
            ));
        }
    }

    public int getTeVerwijderenPloegId() {
        return teVerwijderenPloegId;
    }

    public void setTeVerwijderenPloegId(int teVerwijderenPloegId) {
        this.teVerwijderenPloegId = teVerwijderenPloegId;
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

    public Inschrijving getInschrijving() {
        return inschrijving;
    }

    public List<Lid> getAlleClubLeden() {
        return alleClubLeden;
    }

    public void addPloeg() {
        if (toernooi == null) {
            redirect();
        }

        int volgendePloegIndex = inschrijving == null ? 1 : inschrijving.getPloegen().size() + 1;

        try {
            toernooi.addPloeg(club, club.getNaam() + " " + volgendePloegIndex, false);
            toernooi = toernooiService.save(toernooi);
            inschrijving = toernooi.getInschrijvingVan(club);
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    ex.getMessage()
            ));
        }
    }

    public void verwijderPloeg() {
        if (toernooi == null) {
            redirect();
        }

        try {
            toernooi.removePloeg(teVerwijderenPloegId, club);
            toernooi = toernooiService.save(toernooi);
            inschrijving = toernooi.getInschrijvingVan(club);
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    ex.getMessage()
            ));
        }
    }

    public String opslaan() {
        if (toernooi == null) {
            redirect();
        }

        try {
            toernooi = toernooiService.saveToernooiInschrijvingen(toernooi, inschrijving);
            return "club_inschrijven?faces-redirect=true";
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    ex.getMessage()
            ));
        }
        return "";
    }

    private void redirect() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "club_inschrijven.xhtml");
        } catch (Exception ex) {
            System.err.println(String.format("Fout bij redirection: %s", ex.getMessage()));
        }
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session != null) {
            Account user = (Account) session.getAttribute("user");

            try {
                club = clubService.getClub(user);
            } catch (Exception ex) {
                System.err.println("Ongeldige session state: " + ex.getMessage());
            }
        }

        if (club != null) {
            alleClubLeden = ledenService.ledenVanClub(club);
            alleClubLeden.sort(
                    (lid, lid2) -> lid.getFullName().compareTo(lid2.getFullName())
            );
        }
    }
}
