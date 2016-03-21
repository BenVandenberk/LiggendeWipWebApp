package be.oklw.hulp;

import be.oklw.model.Kampioenschap;
import be.oklw.model.Ploeg;
import be.oklw.model.Toernooi;
import be.oklw.util.Datum;

public class LidInschrijving {

    private Kampioenschap kampioenschap;
    private Toernooi toernooi;
    private Ploeg ploeg;

    private int kampId;
    private int toerId;

    private String kampNaam;
    private String toerNaam;

    private Datum toerDatum;

    public LidInschrijving(Ploeg ploeg, Kampioenschap kampioenschap, Toernooi toernooi) {
        setPloeg(ploeg);
        setKampioenschap(kampioenschap);
        setToernooi(toernooi);
    }

    public Kampioenschap getKampioenschap() {
        return kampioenschap;
    }

    public void setKampioenschap(Kampioenschap kampioenschap) {
        this.kampioenschap = kampioenschap;
        kampId = kampioenschap.getId();
        kampNaam = kampioenschap.getNaam();
    }

    public Toernooi getToernooi() {
        return toernooi;
    }

    public void setToernooi(Toernooi toernooi) {
        this.toernooi = toernooi;
        toerId = toernooi.getId();
        toerNaam = toernooi.getNaam();
        toerDatum = toernooi.getDatum();
    }

    public Ploeg getPloeg() {
        return ploeg;
    }

    public void setPloeg(Ploeg ploeg) {
        this.ploeg = ploeg;
    }

    public int getKampId() {
        return kampId;
    }

    public int getToerId() {
        return toerId;
    }

    public String getKampNaam() {
        return kampNaam;
    }

    public String getToerNaam() {
        return toerNaam;
    }

    public Datum getToerDatum() {
        return toerDatum;
    }
}
