package be.oklw.bean.bezoeker;

import be.oklw.model.Club;
import be.oklw.model.Kampioenschap;
import be.oklw.service.IClubService;
import be.oklw.service.IKampioenschapService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean
@ViewScoped
public class BezoekerKampioenschapBean {

    @EJB
    IKampioenschapService kampioenschapService;

    @EJB
    IClubService clubService;

    private List<Kampioenschap> kampioenschappenVerleden;
    private List<Kampioenschap> kampioenschappenToekomst;

    private List<Kampioenschap> alleKampioenschappenToekomst;
    private List<Kampioenschap> alleKampioenschappenVerleden;

    private List<Club> alleClubs;
    private List<SelectItem> clubs;
    private int selectedClubId = -1;

    public List<Kampioenschap> getKampioenschappenVerleden() {
        return kampioenschappenVerleden;
    }

    public List<Kampioenschap> getKampioenschappenToekomst() {
        return kampioenschappenToekomst;
    }

    public int getSelectedClubId() {
        return selectedClubId;
    }

    public void setSelectedClubId(int selectedClubId) {
        this.selectedClubId = selectedClubId;
    }

    public List<Club> getAlleClubs() {
        return alleClubs;
    }

    public List<SelectItem> getClubs() {
        return clubs;
    }

    public void clubChanged(AjaxBehaviorEvent event) {
        // Twee lijsten filteren. De id waarde voor geen filtering is -1
        if (selectedClubId < 0) {
            kampioenschappenToekomst = new ArrayList<>(alleKampioenschappenToekomst);
            kampioenschappenVerleden = new ArrayList<>(alleKampioenschappenVerleden);
        } else {
            kampioenschappenToekomst = alleKampioenschappenToekomst.stream().filter(k -> k.getClub().getId() == selectedClubId).collect(Collectors.toList());
            kampioenschappenVerleden = alleKampioenschappenVerleden.stream().filter(k -> k.getClub().getId() == selectedClubId).collect(Collectors.toList());
        }
    }

    @PostConstruct
    public void init() {
        alleKampioenschappenToekomst = kampioenschapService.getKampioenschappen(false);
        alleKampioenschappenVerleden = kampioenschapService.getKampioenschappen(true);

        kampioenschappenToekomst = new ArrayList<>(alleKampioenschappenToekomst);
        kampioenschappenVerleden = new ArrayList<>(alleKampioenschappenVerleden);

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
