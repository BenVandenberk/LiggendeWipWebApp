package be.oklw.model;

import be.oklw.model.state.Aangemaakt;
import be.oklw.model.state.ToernooiStatus;
import be.oklw.util.Datum;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class Toernooi {

    //region PRIVATE MEMBERS

    private int id;
    private String naam;
    private Datum datum;
    private LocalTime startTijdstip;
    private int personenPerPloeg;
    private BigDecimal inlegPerPloeg;
    private int maximumAantalPloegen;
    private int aantalWippen;
    private String omschrijving;
    private ToernooiStatus status;
    private Datum inschrijfDeadline;
    private boolean heeftMaaltijd;
    private String cateringInfo;

    private Kampioenschap kampioenschap;

    private ArrayList<Ploeg> ploegen;

    //endregion

    //region CONSTRUCTORS

    public Toernooi() {
        ploegen = new ArrayList<Ploeg>();
        status = new Aangemaakt();
    }

    public Toernooi(Kampioenschap kampioenschap) {
        this();
        this.kampioenschap = kampioenschap;
    }

    //endregion

    //region GETTERS en SETTERS

    public Iterable<Ploeg> getPloegen() {
        return Collections.unmodifiableList(ploegen);
    }

    public Kampioenschap getKampioenschap() {
        return kampioenschap;
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Datum getDatum() {
        return datum;
    }

    public void setDatum(Datum datum) {
        this.datum = datum;
    }

    public LocalTime getStartTijdstip() {
        return startTijdstip;
    }

    public void setStartTijdstip(LocalTime startTijdstip) {
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

    public Datum getInschrijfDeadline() {
        return inschrijfDeadline;
    }

    public void setInschrijfDeadline(Datum inschrijfDeadline) {
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

    //region OBJECT METHODS

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Toernooi toernooi = (Toernooi) o;

        if (id != toernooi.id) return false;
        return !(naam != null ? !naam.equals(toernooi.naam) : toernooi.naam != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (naam != null ? naam.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String result = "";

        result += String.format("Toernooi '%s', ID=%d%n", naam, id);
        result += String.format("Op %s, start: %s%n", datum.getDatumInEuropeesFormaat(), startTijdstip);
        result += String.format("%d ploegen, %d/ploeg, €%f/ploeg inleg, %d wippen%n", maximumAantalPloegen, personenPerPloeg, inlegPerPloeg, aantalWippen);
        result += String.format("In het kader van: %s", kampioenschap.getNaam());

        return result;
    }

    //endregion
}
