package be.oklw.model.state;

import be.oklw.model.Ploeg;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by java on 28.11.15.
 */
public class Vol extends ToernooiStatus {

    public Vol() {
        verwijderbaar = false;
        aanpasbaar = false;
        inschrijvenMogelijk = false;
        inschrijvingenOpen = true;
    }

    @Override
    public void openInschrijvingen() {
        throw new IllegalStateException("Inschrijvingen zijn al open");
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
        throw new NotImplementedException();
    }

    @Override
    public void start() {
        throw new NotImplementedException();
    }

    @Override
    public void stop() {
        throw new NotImplementedException();
    }

    @Override
    public void heropenInschrijvingen() {

    }

    @Override
    public String toString() {
        return "Vol --- " + super.toString();
    }

    @Override
    public String toStringSimple() {
        return "Vol";
    }

    @Override
    public String toUserFriendlyString() {
        return "Het toernooi is volzet";
    }
}
