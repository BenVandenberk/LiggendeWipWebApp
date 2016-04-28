package be.oklw.bean.bezoeker;

import be.oklw.model.Club;
import be.oklw.model.Kampioenschap;
import be.oklw.service.IClubService;
import be.oklw.service.IKampioenschapService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean
@ViewScoped
public class BezoekerFotosBean {

    @EJB
    IKampioenschapService kampioenschapService;

    @EJB
    IClubService clubService;

    private List<Kampioenschap> kampioenschappenMetFotos;
    private List<Kampioenschap> alleKampioenschappenMetFotos;

    private List<Club> alleClubs;
    private List<SelectItem> clubs;
    private int selectedClubId = -1;

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

    public List<Kampioenschap> getKampioenschappenMetFotos() {
        return kampioenschappenMetFotos;
    }

    public void clubChanged(AjaxBehaviorEvent event) {
        if (selectedClubId < 0) {
            kampioenschappenMetFotos = new ArrayList<>(alleKampioenschappenMetFotos);
        } else {
            kampioenschappenMetFotos = alleKampioenschappenMetFotos.stream().filter(k -> k.getClub().getId() == selectedClubId).collect(Collectors.toList());
        }
    }

    @PostConstruct
    public void init() {
        alleKampioenschappenMetFotos = kampioenschapService.getKampioenschappenMetFotos();
        kampioenschappenMetFotos = new ArrayList<>(alleKampioenschappenMetFotos);

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
