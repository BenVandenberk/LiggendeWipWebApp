package be.oklw.bean.club;

import be.oklw.model.Kampioenschap;
import be.oklw.service.IKampioenschapService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean
@ViewScoped
public class ClubInschrijvenBean {

    @EJB
    IKampioenschapService kampioenschapService;

    private List<Kampioenschap> kampioenschappenToekomst;

    public List<Kampioenschap> getKampioenschappenToekomst() {
        return kampioenschappenToekomst;
    }

    @PostConstruct
    public void init() {
        kampioenschappenToekomst = kampioenschapService.getKampioenschappen(false);
    }
}
