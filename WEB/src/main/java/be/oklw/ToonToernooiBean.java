package be.oklw;

import be.oklw.model.Kampioenschap;
import be.oklw.model.Toernooi;
import be.oklw.service.IKampioenschapService;
import be.oklw.service.IToernooiService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ToonToernooiBean {

    @EJB
    IToernooiService toernooiService;

    private int toernooiId = -1;
    private Toernooi toernooi;

    public int getToernooiId() {
        return toernooiId;
    }

    public Toernooi getToernooi() {
        return toernooi;
    }

    public void setToernooiId(int toernooiId) {
        boolean veranderd = this.toernooiId != toernooiId;

        this.toernooiId = toernooiId;

        if(veranderd) {
            toernooi = toernooiService.getToernooi(toernooiId);
        }
    }
}
