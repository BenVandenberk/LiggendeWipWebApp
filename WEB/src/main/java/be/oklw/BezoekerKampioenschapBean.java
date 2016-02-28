package be.oklw;

import be.oklw.model.Kampioenschap;
import be.oklw.service.IKampioenschapService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean
@ViewScoped
public class BezoekerKampioenschapBean {

    @EJB
    IKampioenschapService kampioenschapService;

    private List<Kampioenschap> kampioenschappenVerleden;
    private List<Kampioenschap> kampioenschappenToekomst;

    public List<Kampioenschap> getKampioenschappenVerleden() {
        return kampioenschappenVerleden;
    }

    public List<Kampioenschap> getKampioenschappenToekomst() {
        return kampioenschappenToekomst;
    }

    @PostConstruct
    public void init() {
        kampioenschappenToekomst = kampioenschapService.getKampioenschappen(false);
        kampioenschappenVerleden = kampioenschapService.getKampioenschappen(true);
    }
}
