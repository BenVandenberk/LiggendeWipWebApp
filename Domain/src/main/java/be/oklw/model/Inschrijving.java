package be.oklw.model;

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
    private Set<Ploeg> ploegen;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "inschrijving_id")
    private List<MenuBestelling> menuBestellingen;

    protected Inschrijving() {
        ploegen = new HashSet<>();
        menuBestellingen = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Set<Ploeg> getPloegen() {
        return Collections.unmodifiableSet(ploegen);
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

    public List<Ploeg> getPloegenList() {
        return new ArrayList<Ploeg>(ploegen);
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
