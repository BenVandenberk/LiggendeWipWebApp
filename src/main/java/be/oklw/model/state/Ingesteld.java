package be.oklw.model.state;

import be.oklw.model.Ploeg;

/**
 * Created by java on 28.11.15.
 */
public class Ingesteld extends ToernooiStatus {

    public Ingesteld() {
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
