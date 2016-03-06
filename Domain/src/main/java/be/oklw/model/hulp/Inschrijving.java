package be.oklw.model.hulp;

import be.oklw.model.Ploeg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by java on 06.03.16.
 */
public class Inschrijving {

    private String kampioenschapsnaam;
    private String toernooinaam;
    private int aantalPloegenIngeschreven;
    private int deelnemersPerPloeg;
    private List<Ploeg> ploegen;

    public Inschrijving() {
        ploegen = new ArrayList<>();
    }

    public String getKampioenschapsnaam() {
        return kampioenschapsnaam;
    }

    public void setKampioenschapsnaam(String kampioenschapsnaam) {
        this.kampioenschapsnaam = kampioenschapsnaam;
    }

    public String getToernooinaam() {
        return toernooinaam;
    }

    public void setToernooinaam(String toernooinaam) {
        this.toernooinaam = toernooinaam;
    }

    public int getAantalPloegenIngeschreven() {
        return aantalPloegenIngeschreven;
    }

    public void setAantalPloegenIngeschreven(int aantalPloegenIngeschreven) {
        this.aantalPloegenIngeschreven = aantalPloegenIngeschreven;
    }

    public int getDeelnemersPerPloeg() {
        return deelnemersPerPloeg;
    }

    public void setDeelnemersPerPloeg(int deelnemersPerPloeg) {
        this.deelnemersPerPloeg = deelnemersPerPloeg;
    }

    public List<Ploeg> getPloegen() {
        return ploegen;
    }

    public void setPloegen(List<Ploeg> ploegen) {
        this.ploegen = ploegen;
    }

    public boolean namenIngevuld() {
        boolean namenIngevuld = true;
        
        Iterator<Ploeg> ploegIterator = ploegen.iterator();
        while(namenIngevuld && ploegIterator.hasNext()) {
            namenIngevuld = ploegIterator.next().namenZijnIngevuld();
        }

        return namenIngevuld;
    }
}
