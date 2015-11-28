package be.oklw.model;

import be.oklw.model.state.ToernooiStatus;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Toernooi {

    private int id;
    private String naam;
    private Date datum;
    private Time startTijdstip;
    private int personenPerPloeg;
    private BigDecimal inlegPerPloeg;
    private int maximumAantalPloegen;
    private int aantalWippen;
    private String omschrijving;
    private ToernooiStatus status;
    private Date inschrijfDeadline;
    private boolean heeftMaaltijd;
    private String cateringInfo;

    private Kampioenschap kampioenschap;

    private ArrayList<Ploeg> ploegen;

    protected Toernooi(Kampioenschap kampioenschap) {
        this.kampioenschap = kampioenschap;
        ploegen = new ArrayList<Ploeg>();
    }


    //region GETTERS en SETTERS

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Time getStartTijdstip() {
        return startTijdstip;
    }

    public void setStartTijdstip(Time startTijdstip) {
        this.startTijdstip = startTijdstip;
    }

    public int getPersonenPerPloeg() {
        return personenPerPloeg;
    }

    public void setPersonenPerPloeg(int personenPerPloeg) {
        this.personenPerPloeg = personenPerPloeg;
    }

    public BigDecimal getInlegPerPloeg() {
        return inlegPerPloeg;
    }

    public void setInlegPerPloeg(BigDecimal inlegPerPloeg) {
        this.inlegPerPloeg = inlegPerPloeg;
    }

    public int getMaximumAantalPloegen() {
        return maximumAantalPloegen;
    }

    public void setMaximumAantalPloegen(int maximumAantalPloegen) {
        this.maximumAantalPloegen = maximumAantalPloegen;
    }

    public int getAantalWippen() {
        return aantalWippen;
    }

    public void setAantalWippen(int aantalWippen) {
        this.aantalWippen = aantalWippen;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public ToernooiStatus getStatus() {
        return status;
    }

    public void setStatus(ToernooiStatus status) {
        this.status = status;
    }

    public Date getInschrijfDeadline() {
        return inschrijfDeadline;
    }

    public void setInschrijfDeadline(Date inschrijfDeadline) {
        this.inschrijfDeadline = inschrijfDeadline;
    }

    public boolean isHeeftMaaltijd() {
        return heeftMaaltijd;
    }

    public void setHeeftMaaltijd(boolean heeftMaaltijd) {
        this.heeftMaaltijd = heeftMaaltijd;
    }

    public String getCateringInfo() {
        return cateringInfo;
    }

    public void setCateringInfo(String cateringInfo) {
        this.cateringInfo = cateringInfo;
    }
    //endregion

    //region PUBLIC METHODS

    public boolean isInstellingenVolledig() {
        throw new NotImplementedException();
    }

    public void schrijfPloegInVoor(Club club) {
        // CONSISTENT
    }

    public void openInschrijvingen() {
        status.openInschrijvingen();
    }

    public void annuleerInschrijving(Ploeg ploeg) {
        status.annuleerInschrijving(ploeg);
    }

    public void annuleerInschrijving(int id) {
        status.annuleerInschrijving(id);
    }

    public void sluitInschrijvingen() {
        status.sluitInschrijvingen();
    }

    public void loot() {
        status.loot();
    }

    public void start() {
        status.start();
    }

    public void stop() {
        status.stop();
    }

    public void heropenInschrijvingen() {
        status.heropenInschrijvingen();
    }

    public boolean isVerwijderbaar() {
        return status.isVerwijderbaar();
    }

    public boolean isAanpasbaar() {
        return status.isAanpasbaar();
    }

    public int aantalIngeschrevenPloegen() {
        return ploegen.size();
    }

    //endregion
}
