package be.oklw.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Inschrijving implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Club club;

    @ManyToOne(fetch = FetchType.EAGER)
    private Toernooi toernooi;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "inschrijving_id")
    @Fetch(FetchMode.SELECT)
    private List<Ploeg> ploegen;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "inschrijving_id")
    private List<MenuBestelling> menuBestellingen;

    protected Inschrijving() {
        ploegen = new ArrayList<>();
        menuBestellingen = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Ploeg> getPloegen() {
        return Collections.unmodifiableList(ploegen);
    }

    public List<MenuBestelling> getMenuBestellingen() {
        return menuBestellingen;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Toernooi getToernooi() {
        return toernooi;
    }

    public void setToernooi(Toernooi toernooi) {
        this.toernooi = toernooi;
    }

    //region HELPERS

    public String getKampioenschapsnaam() {
        return toernooi.getKampioenschap().getNaam();
    }

    public String getToernooinaam() {
        return toernooi.getNaam();
    }

    public int getAantalPloegenIngeschreven() {
        return ploegen.size();
    }

    public int getDeelnemersPerPloeg() {
        return toernooi.getPersonenPerPloeg();
    }

    public boolean toernooiHeeftMaaltijd() {
        return toernooi.isHeeftMaaltijd();
    }

    //endregion

    /**
     * Maakt een inschrijving aan voor een club-toernooi combinatie
     *
     * @param club
     * @param toernooi
     * @return de aangemaakte Inschrijving
     * @throws IllegalStateException als er al een inschrijving bestaat voor de club-toernooi combinatie of
     *                               als inschrijven voor het toernooi niet mogelijk is
     */
    public static Inschrijving nieuweInschrijving(Club club, Toernooi toernooi) throws IllegalStateException {
        Inschrijving inschrijving = new Inschrijving();
        inschrijving.setClub(club);
        inschrijving.setToernooi(toernooi);

        // De nodige checks worden in deze method, door het Toernooi object gedaan
        toernooi.addInschrijving(inschrijving);

        return inschrijving;
    }

    public boolean namenZijnIngevuld() {
        boolean ingevuld = true;

        Iterator<Ploeg> ploegIterator = ploegen.iterator();
        while(ingevuld && ploegIterator.hasNext()) {
            ingevuld = ploegIterator.next().namenZijnIngevuld();
        }

        return ingevuld;
    }

    protected boolean removePloeg(int ploegId) {
        return ploegen.removeIf(ploeg -> ploeg.getId() == ploegId);
    }

    protected void addPloeg(Ploeg ploeg) {
        ploegen.add(ploeg);
        ploeg.setInschrijving(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inschrijving that = (Inschrijving) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
