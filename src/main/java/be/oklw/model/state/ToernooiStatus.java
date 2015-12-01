package be.oklw.model.state;

import be.oklw.model.Ploeg;

public abstract class ToernooiStatus {

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
}
