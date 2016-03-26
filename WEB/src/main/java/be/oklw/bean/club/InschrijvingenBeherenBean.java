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
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ManagedBean
@ViewScoped
public class InschrijvingenBeherenBean {

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

    private List<Club> alleClubs;
    private List<SelectItem> clubs;
    private int selectedClubId = -1;

    private int teVerwijderenPloegId = -1;
    private int clubIdTeVerwijderenPloeg = -1;

    private HashMap<Integer, ArrayList<Lid>> alleLedenPerClub = new HashMap<>();

    public int getToernooiId() {
        return toernooiId;
    }

    // Deze setter wordt aangeroepen bij elke HTTP GET van club_inschrijvingen_beheren?id=<TOERNOOI_ID>
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
                if (!toernooi.getKampioenschap().getClub().equals(club)) {
                    redirect();
                }
                kampioenschap = toernooi.getKampioenschap();

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

    public Toernooi getToernooi() {
        return toernooi;
    }

    public Kampioenschap getKampioenschap() {
        return kampioenschap;
    }

    public Club getClub() {
        return club;
    }

    public List<Ploeg> getIngeschrevenPloegen() {
        if (toernooi == null) {
            redirect();
            return null;
        }

        List<Ploeg> ingeschrevenPloegen = toernooi.getIngeschrevenPloegen();
        ingeschrevenPloegen.sort(
                (ploeg1, ploeg2) -> ploeg1.getInschrijving().getClub().getNaam().compareTo(ploeg2.getInschrijving().getClub().getNaam())
        );

        return ingeschrevenPloegen;
    }

    public List<SelectItem> getClubs() {
        return clubs;
    }

    public int getSelectedClubId() {
        return selectedClubId;
    }

    public void setSelectedClubId(int selectedClubId) {
        this.selectedClubId = selectedClubId;
    }

    public int getTeVerwijderenPloegId() {
        return teVerwijderenPloegId;
    }

    public void setTeVerwijderenPloegId(int teVerwijderenPloegId) {
        this.teVerwijderenPloegId = teVerwijderenPloegId;
    }

    public int getClubIdTeVerwijderenPloeg() {
        return clubIdTeVerwijderenPloeg;
    }

    public void setClubIdTeVerwijderenPloeg(int clubIdTeVerwijderenPloeg) {
        this.clubIdTeVerwijderenPloeg = clubIdTeVerwijderenPloeg;
    }

    public HashMap<Integer, ArrayList<Lid>> getAlleLedenPerClub() {
        return alleLedenPerClub;
    }

    public void maakInschrijvingVoorSelected() {
        if (toernooi == null) {
            redirect();
        }

        Club selectedClub = alleClubs.stream().filter(c -> c.getId() == selectedClubId).findFirst().get();

        Inschrijving inschrijving = toernooi.getInschrijvingVan(selectedClub);

        // De club is nog niet ingeschreven
        if (inschrijving == null) {
            inschrijving = Inschrijving.nieuweInschrijving(selectedClub, toernooi);
        }

        int volgendePloegIndex = inschrijving.getAantalPloegenIngeschreven() + 1;

        try {
            toernooi.addPloeg(selectedClub, selectedClub.getNaam() + " " + volgendePloegIndex);
            toernooi = toernooiService.save(toernooi);
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
        if (teVerwijderenPloegId < 0 || clubIdTeVerwijderenPloeg < 0) {
            return;
        }

        Club geassocieerdeClub = alleClubs.stream().filter(c -> c.getId() == clubIdTeVerwijderenPloeg).findFirst().get();

        try {
            toernooi.removePloeg(teVerwijderenPloegId, geassocieerdeClub);
            toernooi = toernooiService.save(toernooi);
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
            toernooi = toernooiService.save(toernooi);
            return "club_toernooi_aanpassen?faces-redirect=true";
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

    public boolean afrekeningZichtbaar() {
        return toernooi.isHeeftMaaltijd() || toernooi.isMetInleg();
    }

    private void redirect() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "club_toernooi_aanpassen.xhtml");
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

        // CLUBLIJST VULLEN
        clubs = new ArrayList<>();
        alleClubs = clubService.getAllClubs();
        for (Club c : alleClubs) {
            clubs.add(new SelectItem(
                    c.getId(),
                    c.getNaam()
            ));
        }

        // LEDENLIJST VULLEN
        List<Lid> alleLeden = ledenService.alleLeden();

        int clubId = -1;
        for (Lid lid : alleLeden) {

            clubId = lid.getClub().getId();
            if (alleLedenPerClub.containsKey(clubId)) {
                alleLedenPerClub.get(clubId).add(lid);
            } else {
                alleLedenPerClub.put(clubId, new ArrayList<Lid>());
                alleLedenPerClub.get(clubId).add(lid);
            }

        }
    }
}
