package be.oklw.model.state;

import be.oklw.model.Ploeg;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by java on 28.11.15.
 */
public class InschrijvingenAfgesloten extends ToernooiStatus {

    public InschrijvingenAfgesloten() {
        verwijderbaar = false;
        aanpasbaar = false;
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
        return "Inschrijvingen Afgesloten --- " + super.toString();
    }

    @Override
    public String toStringSimple() {
        return "Inschrijvingen Afgesloten";
    }

    @Override
    public String toUserFriendlyString() {
        return "De inschrijvingen van het toernooi zijn afgesloten";
    }
}
