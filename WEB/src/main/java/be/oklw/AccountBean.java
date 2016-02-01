package be.oklw;

import be.oklw.service.IGebruikerService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.NotNull;

@ManagedBean
@ViewScoped
public class AccountBean {

    @EJB
    IGebruikerService gebruikerService;

    @NotNull
    private String oudPaswoord;

    @NotNull
    private String nieuwPaswoord;

    @NotNull
    private String nieuwPaswoordHerhaald;

    public String getOudPaswoord() {
        return oudPaswoord;
    }

    public void setOudPaswoord(String oudPaswoord) {
        this.oudPaswoord = oudPaswoord;
    }

    public String getNieuwPaswoord() {
        return nieuwPaswoord;
    }

    public void setNieuwPaswoord(String nieuwPaswoord) {
        this.nieuwPaswoord = nieuwPaswoord;
    }

    public String getNieuwPaswoordHerhaald() {
        return nieuwPaswoordHerhaald;
    }

    public void setNieuwPaswoordHerhaald(String nieuwPaswoordHerhaald) {
        this.nieuwPaswoordHerhaald = nieuwPaswoordHerhaald;
    }

    public void veranderPaswoord() {

    }
}
