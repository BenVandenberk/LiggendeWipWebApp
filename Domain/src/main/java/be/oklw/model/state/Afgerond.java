package be.oklw.model.state;

import be.oklw.model.Ploeg;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by java on 28.11.15.
 */
public class Afgerond extends ToernooiStatus {

    public Afgerond() {
        verwijderbaar = false;
        aanpasbaar = false;
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
        return "Afgerond --- " + super.toString();
    }

    @Override
    public String toStringSimple() {
        return "Afgerond";
    }
}
