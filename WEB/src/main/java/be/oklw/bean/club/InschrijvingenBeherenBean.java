package be.oklw.bean.club;

import be.oklw.model.*;
import be.oklw.service.IClubService;
import be.oklw.service.IInschrijfService;
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

    private int toernooiId = -1;
    private Toernooi toernooi;
    private Kampioenschap kampioenschap;
    private Club club;

    private List<Club> alleClubs;
    private List<SelectItem> clubs;
    private int selectedClubId = -1;

    private int teVerwijderenPloegId = -1;
    private int clubIdTeVerwijderenPloeg = -1;

    public int getToernooiId() {
        return toernooiId;
    }

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
        return new ArrayList<Ploeg>(toernooi.getPloegen());
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

    public void maakInschrijvingVoorSelected() {
        if (toernooi == null) {
            redirect();
        }

        Club selectedClub = alleClubs.stream().filter(c -> c.getId() == selectedClubId).findFirst().get();

        int volgendePloegIndex = toernooi.getPloegenVan(selectedClub).size() + 1;

        try {
            Ploeg.schrijfPloegInVoorToernooi(selectedClub, toernooi, selectedClub.getNaam() + " " + volgendePloegIndex);
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
            toernooi.removePloeg(teVerwijderenPloegId);
            geassocieerdeClub.removePloeg(teVerwijderenPloegId);
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
            club = clubService.getClub(user);
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
    }
}
