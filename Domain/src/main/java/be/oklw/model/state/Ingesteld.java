package be.oklw.model.state;

import be.oklw.model.Ploeg;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by java on 28.11.15.
 */
public class Ingesteld extends ToernooiStatus {

    public Ingesteld() {
        verwijderbaar = true;
        aanpasbaar = true;
        inschrijvenMogelijk = false;
        inschrijvingenOpen = false;
    }

    @Override
    public void openInschrijvingen() {
        // OKE
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
        return "Ingesteld --- " + super.toString();
    }

    @Override
    public String toStringSimple() {
        return "Ingesteld";
    }

    @Override
    public String toUserFriendlyString() {
        return "Alle gegevens en instellingen met betrekking tot het toernooi zijn volledig";
    }
}
