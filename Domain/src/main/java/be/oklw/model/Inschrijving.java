package be.oklw.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Entity
public class Inschrijving implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean betaald;

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
        return Collections.unmodifiableList(menuBestellingen);
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

    public boolean isBetaald() {
        return betaald;
    }

    public void setBetaald(boolean betaald) {
        this.betaald = betaald;
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

        // De nodige checks worden in deze method, door het Toernooi object gedaan
        toernooi.addInschrijving(inschrijving);
        inschrijving.setToernooi(toernooi);

        if (toernooi.isHeeftMaaltijd()) {
            for (Menu menu : toernooi.getMenus()) {
                inschrijving.addMenuBestelling(menu);
            }
        }

        return inschrijving;
    }

    public boolean namenZijnIngevuld() {
        boolean ingevuld = true;

        Iterator<Ploeg> ploegIterator = ploegen.iterator();
        while (ingevuld && ploegIterator.hasNext()) {
            ingevuld = ploegIterator.next().namenZijnIngevuld();
        }

        return ingevuld;
    }

    public BigDecimal totaleInleg() {
        return toernooi.getInlegPerPloeg().multiply(new BigDecimal(getAantalPloegenIngeschreven()));
    }

    public BigDecimal totalePrijs() {
        BigDecimal maaltijdPrijs = BigDecimal.ZERO;

        for (MenuBestelling menuBestelling : menuBestellingen) {
            maaltijdPrijs = maaltijdPrijs.add(menuBestelling.bestellingPrijs());
        }

        return maaltijdPrijs.add(totaleInleg());
    }

    public boolean betalingVereist() {
        return toernooi.isMetInleg() || toernooi.isHeeftMaaltijd();
    }

    protected boolean removePloeg(int ploegId) {
        return ploegen.removeIf(ploeg -> ploeg.getId() == ploegId);
    }

    protected void addPloeg(Ploeg ploeg) {
        ploegen.add(ploeg);
        ploeg.setInschrijving(this);
    }

    protected void addMenuBestelling(Menu menu) {
        menuBestellingen.add(
                new MenuBestelling(menu)
        );
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
