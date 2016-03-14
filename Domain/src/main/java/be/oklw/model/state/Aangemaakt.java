package be.oklw.model.state;


import be.oklw.model.Ploeg;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Aangemaakt extends ToernooiStatus {


    public Aangemaakt() {
        verwijderbaar = true;
        aanpasbaar = true;
        inschrijvenMogelijk = false;
        inschrijvingenOpen = false;
    }

    @Override
    public void openInschrijvingen() {
        throw new IllegalStateException("De inschrijvingen van dit toernooi kunnen nog niet geopend worden omdat het toernooi nog niet ingesteld is");
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
        return "Aangemaakt --- " + super.toString();
    }

    @Override
    public String toStringSimple() {
        return "Aangemaakt";
    }

    @Override
    public String toUserFriendlyString() {
        return "Het toernooi is juist aangemaakt";
    }
}
