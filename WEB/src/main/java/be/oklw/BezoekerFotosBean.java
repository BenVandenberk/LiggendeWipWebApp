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
public class BezoekerFotosBean {

    @EJB
    IKampioenschapService kampioenschapService;

    private List<Kampioenschap> kampioenschappenMetFotos;

    public List<Kampioenschap> getKampioenschappenMetFotos() {
        return kampioenschappenMetFotos;
    }

    public void setKampioenschappenMetFotos(List<Kampioenschap> kampioenschappenMetFotos) {
        this.kampioenschappenMetFotos = kampioenschappenMetFotos;
    }

    @PostConstruct
    public void init() {
        kampioenschappenMetFotos = kampioenschapService.getKampioenschappenMetFotos();
    }
}
