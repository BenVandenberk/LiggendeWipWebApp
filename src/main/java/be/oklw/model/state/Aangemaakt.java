package be.oklw.model.state;


import be.oklw.model.Ploeg;

public class Aangemaakt extends ToernooiStatus {


    public Aangemaakt() {
        verwijderbaar = true;
        aanpasbaar = true;
        inschrijvenMogelijk = false;
    }

    @Override
    public void openInschrijvingen() {

    }

    @Override
    public void annuleerInschrijving(Ploeg ploeg) {

    }

    @Override
    public void annuleerInschrijving(int id) {

    }

    @Override
    public void sluitInschrijvingen() {

    }

    @Override
    public void loot() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void heropenInschrijvingen() {

    }
}
