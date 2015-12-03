package be.oklw.model.state;

import be.oklw.model.Ploeg;

import javax.persistence.*;
import java.io.Serializable;

public abstract class ToernooiStatus implements Serializable {

    protected boolean verwijderbaar;
    protected boolean aanpasbaar;
    protected boolean inschrijvenMogelijk;

    public abstract void openInschrijvingen();
    public abstract void annuleerInschrijving(Ploeg ploeg);
    public abstract void annuleerInschrijving(int id);
    public abstract void sluitInschrijvingen();
    public abstract void loot();
    public abstract void start();
    public abstract void stop();
    public abstract void heropenInschrijvingen();

    public boolean isVerwijderbaar() {
        return verwijderbaar;
    }

    public boolean isAanpasbaar() {
        return aanpasbaar;
    }

    public boolean isInschrijvenMogelijk() {
        return inschrijvenMogelijk;
    }

    @Override
    public String toString() {
        return String.format("Verwijderbaar: %s, Aanpasbaar: %s, Inschrijven Mogelijk: %s", verwijderbaar, aanpasbaar, inschrijvenMogelijk);
    }

    public String toStringSimple() {
        return "Abstracte ToernooiStatus";
    }

    public static ToernooiStatus maak(String status) {
        switch (status) {
            case "Aangemaakt":
                return new Aangemaakt();
            case "Ingesteld":
                return new Ingesteld();
            case "Inschrijvingen Open":
                return new InschrijvingenOpen();
            case "Vol":
                return new Vol();
            case "Inschrijvingen Afgesloten":
                return new InschrijvingenAfgesloten();
            case "Geloot":
                return new Geloot();
            case "Lopend":
                return new Lopend();
            case "Afgerond":
                return new Afgerond();
            case "Gearchiveerd":
                return new Gearchiveerd();
            default:
                return null;
        }
    }
}
