package be.oklw;

import be.oklw.model.Kampioenschap;
import be.oklw.service.IKampioenschapService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ToonKampioenschapBean {

    @EJB
    IKampioenschapService kampioenschapService;

    private int kampioenschapsId = -1;
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
            kampioenschap = kampioenschapService.getKampioenschap(kampioenschapsId);
        }
    }
}
