package be.oklw.bean.bezoeker;

import be.oklw.model.Foto;
import be.oklw.model.Kampioenschap;
import be.oklw.service.IKampioenschapService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean
@ViewScoped
public class ToonFotosBean {

    @EJB
    IKampioenschapService kampioenschapService;

    private int kampioenschapsId = -1;
    private List<Foto> fotos;
    private Kampioenschap kampioenschap;

    public int getKampioenschapsId() {
        return kampioenschapsId;
    }

    public Kampioenschap getKampioenschap() {
        return kampioenschap;
    }

    public void setKampioenschapsId(int kampioenschapsId) {
        boolean veranderd = this.kampioenschapsId != kampioenschapsId;

        this.kampioenschapsId = kampioenschapsId;

        if(veranderd) {
            fotos = kampioenschapService.getFotos(kampioenschapsId);
            kampioenschap = kampioenschapService.getKampioenschap(kampioenschapsId);
        }
    }

    public List<Foto> getFotos() {
        return fotos;
    }
}
