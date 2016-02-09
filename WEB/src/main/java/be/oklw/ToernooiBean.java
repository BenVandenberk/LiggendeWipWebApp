package be.oklw;

import be.oklw.model.Toernooi;
import be.oklw.service.IKampioenschapService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ToernooiBean {

    @EJB
    IKampioenschapService kampioenschapService;

    @ManagedProperty(value = "#{clubBeheerBean}")
    ClubBeheerBean clubBeheerBean;

    private Toernooi toernooi;

    public Toernooi getToernooi() {
        return toernooi;
    }

    public void setToernooi(Toernooi toernooi) {
        this.toernooi = toernooi;
    }

    public void setClubBeheerBean(ClubBeheerBean clubBeheerBean) {
        this.clubBeheerBean = clubBeheerBean;
    }

    public String voegNieuwToernooiToe() {
        kampioenschapService.nieuwToernooi(toernooi, clubBeheerBean.getKampioenschap());
        return "success";
    }

    @PostConstruct
    public void init() {
        toernooi = new Toernooi();
    }
}
