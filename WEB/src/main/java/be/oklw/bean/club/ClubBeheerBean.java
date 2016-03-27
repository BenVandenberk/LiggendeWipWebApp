package be.oklw.bean.club;

import be.oklw.model.*;
import be.oklw.service.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "clubBeheerBean")
@SessionScoped
public class ClubBeheerBean implements Serializable {

    //region MEMBERS

    private static final long serialVersionUID = 2742323554076118994L;

    @EJB
    IClubService clubService;

    @EJB
    IKampioenschapService kampioenschapService;

    @EJB
    ISponsorService sponsorService;

    @EJB
    IToernooiService toernooiService;

    @EJB
    IInschrijfService inschrijfService;

    private Account user;
    private Club club;
    private Kampioenschap kampioenschap;
    private Toernooi toernooi;

    private int kampId;
    private int toerId;
    private int sponsId;

    private String inschrijvingenGeopendMessage;

    private boolean isNieuwToernooi;

    private String geselecteerdMenuKey;

    //endregion

    //region PROPERTIES

    public void setToerId(int toerId) {
        this.toerId = toerId;
    }

    public void setKampId(int kampId) {
        this.kampId = kampId;
    }

    public int getSponsId() {
        return sponsId;
    }

    public void setSponsId(int sponsId) {
        this.sponsId = sponsId;
    }

    public String getInschrijvingenGeopendMessage() {
        return inschrijvingenGeopendMessage;
    }

    public List<Kampioenschap> getKampioenschappenVerleden() {
        init();
        return clubService.kampioenschappenVan(club, true);
    }

    public List<Kampioenschap> getKampioenschappenToekomst() {
        init();
        return clubService.kampioenschappenVan(club, false);
    }

    public Kampioenschap getKampioenschap() {
        return kampioenschap;
    }

    public void setKampioenschap(Kampioenschap kampioenschap) {
        this.kampioenschap = kampioenschap;
    }

    public Toernooi getToernooi() {
        return toernooi;
    }

    public void setToernooi(Toernooi toernooi) {
        this.toernooi = toernooi;
    }

    public String kampioenschapKlik() {
        kampioenschap = kampioenschapService.getKampioenschap(kampId);

        // Als er geen juiste id gepost is
        if (kampioenschap == null) {
            return "";
        }
        return "club_kampioenschapspagina?faces-redirect=true";
    }

    public Club getClub() {
        return club;
    }

    public boolean isNieuwToernooi() {
        return isNieuwToernooi;
    }

    public String getGeselecteerdMenuKey() {
        return geselecteerdMenuKey;
    }

    public void setGeselecteerdMenuKey(String geselecteerdMenuKey) {
        this.geselecteerdMenuKey = geselecteerdMenuKey;
    }

    public String getInschrijvingenOpenBevestigingMessage() {
        StringBuilder message = new StringBuilder();

        message.append("Na het openstellen van de inschrijvingen is het niet meer mogelijk om onderstaande gegevens aan te passen.<br/>Controleer voor u doorgaat of de gegevens kloppen.");
        message.append(String.format("<br/><br/>Naam: %s", toernooi.getNaam()));
        message.append(String.format("<br/>Datum: %s", toernooi.getDatum().getDatumInEuropeesFormaat()));
        message.append(String.format("<br/>Startuur: %s", toernooi.getStartTijdstip()));
        message.append(String.format("<br/>Aantal Ploegen: %d", toernooi.getMaximumAantalPloegen()));
        message.append(String.format("<br/>Aantal Personen per Ploeg: %d", toernooi.getPersonenPerPloeg()));
        message.append(String.format("<br/>Inschrijfdeadline: %s", toernooi.getInschrijfDeadline().getDatumInEuropeesFormaat()));

        message.append(String.format("<br/><br/>Met Inleg: %s", toernooi.isMetInleg() ? "Ja" : "Nee"));
        if (toernooi.isMetInleg()) {
            message.append(String.format("<br/>Inleg per Ploeg: €%s", toernooi.getInlegPerPloeg().toString()));
        }

        message.append(String.format("<br/><br/>Met Maaltijd: %s<br/>", toernooi.isHeeftMaaltijd() ? "Ja" : "Nee"));

        if (toernooi.isHeeftMaaltijd()) {
            for (Menu menu : toernooi.getMenus()) {
                message.append(String.format("<br/>%s - €%s per persoon", menu.getNaam(), menu.getPrijs().toString()));
                message.append(String.format("<br/>%s", menu.getOmschrijving()));
            }
        }

        return message.toString();
    }

    //endregion

    //region METHODS

    public String verwijderToernooi() {

        if (!toernooi.isVerwijderbaar()) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN,
                    "Oeps",
                    String.format("Toernooi is niet verwijderbaar: %s", toernooi.getStatus().toUserFriendlyString())
            ));
            return "";
        }

        try {
            kampioenschap = kampioenschapService.verwijderToernooi(toernooi, kampioenschap);
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    ex.getMessage()
            ));
        }

        return "club_kampioenschapspagina?faces-redirect=true";
    }

    public String toernooiKlik() {
        isNieuwToernooi = false;

        try {
            toernooi = toernooiService.getToernooi(toerId);
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Fout",
                            ex.getMessage()
                    )
            );
        }

        if (toernooi != null) {
            kampioenschap = toernooi.getKampioenschap();
            return "club_toernooi_aanpassen?faces-redirect=true";
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niet gevonden", "Toernooi niet gevonden"));
        }
        return "";
    }

    public String nieuwToernooi() {
        toernooi = new Toernooi();
        isNieuwToernooi = true;
        return "club_toernooi_aanpassen?faces-redirect=true";
    }

    public String opslaan() {
        try {
            kampioenschapService.opslaan(kampioenschap);
            return "club_beheer?faces-redirect=true";
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Fout",
                            ex.getMessage()
                    )
            );
        }
        return "";
    }

    public String opslaanToernooi() {
        try {
            if (isNieuwToernooi) {
                kampioenschapService.nieuwToernooi(toernooi, kampioenschap);
            } else {
                kampioenschapService.opslaanToernooi(toernooi);
            }

            return "club_kampioenschapspagina?faces-redirect=true";
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Fout",
                            ex.getMessage()));
        }
        return "";
    }

    public void verwijderSponsor() {
        try {
            sponsorService.verwijderSponsorVan(sponsId, kampioenschap);
            refresh();
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    ex.getMessage()));
        }
    }

    public void openInschrijvingen() {
        try {
            inschrijvingenGeopendMessage = inschrijfService.openInschrijvingen(toernooi);
            toernooi = toernooiService.getToernooi(toernooi.getId());
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    ex.getMessage()));
        }
    }

    public String inschrijvingenBeheren() {

        if (toernooi == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Fout",
                            "Ongeldig Toernooi")
            );
            return "";
        }

        return "club_inschrijvingen_beheren?faces-redirect=true&toerID=" + toernooi.getId();
    }

    public void addMenu() {
        toernooi.addMenu();
    }

    public void removeMenu() {
        toernooi.removeMenu(geselecteerdMenuKey);
    }

    public void refresh() {
        try {
            kampioenschap = kampioenschapService.getKampioenschap(kampioenschap.getId());
            club = clubService.getClub(user);
        } catch (Exception ex) {
            System.err.println("Ongeldige session state: " + ex.getMessage());
        }
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session != null) {
            user = (Account) session.getAttribute("user");

            try {
                club = clubService.getClub(user);
            } catch (Exception ex) {
                System.err.println("Ongeldige session state: " + ex.getMessage());
            }
        }
    }

    //endregion
}
