package be.oklw.model;

import be.oklw.usertype.DatumConverter;
import be.oklw.util.Datum;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Ploeg implements Serializable {

    private static final long serialVersionUID = 6543517911693869965L;

    //region PRIVATE MEMBERS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String naam;
    private int aantalLeden;

    @Convert(converter = DatumConverter.class)
    private Datum inschrijfDatum;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Ploeg_id")
    private Set<Deelnemer> deelnemers;

    @ManyToOne(fetch = FetchType.EAGER)
    private Inschrijving inschrijving;

    //endregion

    //region CONSTRUCTORS

    protected Ploeg() {

    }

    protected Ploeg(String naam) {
        this.naam = naam;
        inschrijfDatum = new Datum();
        deelnemers = new HashSet<>();
    }

    //endregion

    //region GETTERS & SETTERS

    public List<Deelnemer> getDeelnemers() {
        return Collections.unmodifiableList(new ArrayList<>(deelnemers));
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getAantalLeden() {
        return aantalLeden;
    }

    public void setAantalLeden(int aantalLeden) {
        this.aantalLeden = aantalLeden;
    }

    public Datum getInschrijfDatum() {
        return inschrijfDatum;
    }

    public void setInschrijfDatum(Datum inschrijfDatum) {
        this.inschrijfDatum = inschrijfDatum;
    }

    public Inschrijving getInschrijving() {
        return inschrijving;
    }

    public void setInschrijving(Inschrijving inschrijving) {
        this.inschrijving = inschrijving;
    }

    //endregion

    //region PUBLIC METHODS

    public void addDeelnemer(Deelnemer deelnemer)
            throws IllegalStateException {
        if (!isVolzet()) {
            deelnemers.add(deelnemer);
        } else {
            throw new IllegalStateException(String.format("Ploeg '%s' is volzet", naam));
        }
    }

    public void removeDeelnemer(Deelnemer deelnemer) {
        deelnemers.remove(deelnemer);
    }

    public void removeDeelnemer(int id) {
        deelnemers.remove(id);
    }

    public boolean isVolzet() {
        if (deelnemers.size() == aantalLeden) {
            return true;
        } else return false;
    }

    public boolean namenZijnIngevuld() {
        boolean namenZijnIngevuld = true;

        Iterator<Deelnemer> deelnemerIterator = deelnemers.iterator();
        Deelnemer deelnemer;

        while (namenZijnIngevuld && deelnemerIterator.hasNext()) {
            deelnemer = deelnemerIterator.next();
            namenZijnIngevuld = deelnemer.naamBekend();
        }

        return namenZijnIngevuld;
    }

    public boolean lidZitInPloeg(Lid lid) {
        boolean lidZitInPloeg = false;

        Iterator<Deelnemer> deelnemerIterator = deelnemers.iterator();
        while(!lidZitInPloeg && deelnemerIterator.hasNext()) {
            lidZitInPloeg = deelnemerIterator.next().isLid(lid);
        }

        return lidZitInPloeg;
    }

    public boolean heeftZelfdeLeden() {
        boolean tweeDezelfdeDeelnemers = false;
        List<Deelnemer> deelnemerList = new ArrayList(deelnemers);
        deelnemerList = deelnemerList.stream().filter(d -> d.isDeelnemerIsLid()).collect(Collectors.toList());

        Lid lid1, lid2;

        for (int i = 0; !tweeDezelfdeDeelnemers && i < deelnemerList.size() - 1; i++) {
            for (int j = i + 1; !tweeDezelfdeDeelnemers && j < deelnemerList.size(); j++) {
                lid1 = deelnemerList.get(i).getLid();
                lid2 = deelnemerList.get(j).getLid();
                tweeDezelfdeDeelnemers = lid1.equals(lid2);
            }
        }

        return tweeDezelfdeDeelnemers;
    }

    //endregion

    //region OBJECT METHODS


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ploeg ploeg = (Ploeg) o;

        if (id != ploeg.id) return false;
        return !(naam != null ? !naam.equals(ploeg.naam) : ploeg.naam != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (naam != null ? naam.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String result = "";

        result += String.format("Ploeg '%s', ID=%d%n", naam, id);
        result += String.format("met deelnemers %n");
        for (Deelnemer d : deelnemers) {
            result += String.format("%s%n", d.getNaam());
        }

        return result;
    }

    //endregion

}
