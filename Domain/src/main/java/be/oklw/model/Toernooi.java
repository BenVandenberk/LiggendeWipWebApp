package be.oklw.model;

import be.oklw.model.state.*;
import be.oklw.usertype.DatumConverter;
import be.oklw.usertype.ToernooiStatusConverter;
import be.oklw.util.Datum;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Entity
public class Toernooi implements Serializable {

    private static final long serialVersionUID = -5743128667112131869L;

    //region PRIVATE MEMBERS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String naam;
    private LocalTime startTijdstip;
    private int personenPerPloeg;
    private BigDecimal inlegPerPloeg;
    private int maximumAantalPloegen;
    private int aantalWippen;
    private String omschrijving;
    private boolean heeftMaaltijd;
    private boolean metInleg;

    @Convert(converter = DatumConverter.class)
    private Datum datum;

    @Convert(converter = DatumConverter.class)
    private Datum inschrijfDeadline;

    @Convert(converter = ToernooiStatusConverter.class)
    private ToernooiStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    private Kampioenschap kampioenschap;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "toernooi_id")
    @Fetch(FetchMode.SELECT)
    private List<Menu> menus;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "toernooi_id")
    @Fetch(FetchMode.SELECT)
    private List<Inschrijving> inschrijvingen;

    //endregion

    //region CONSTRUCTORS

    public Toernooi() {
        menus = new ArrayList<Menu>();
        status = new Aangemaakt();
        inschrijvingen = new ArrayList<>();
    }

    //endregion

    //region GETTERS en SETTERS

    public Kampioenschap getKampioenschap() {
        return kampioenschap;
    }

    public void setKampioenschap(Kampioenschap kampioenschap) {
        this.kampioenschap = kampioenschap;
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
        updateIngesteldStatus();
    }

    public Datum getDatum() {
        return datum;
    }

    public void setDatum(Datum datum) {
        this.datum = datum;
        updateIngesteldStatus();
    }

    public LocalTime getStartTijdstip() {
        return startTijdstip;
    }

    public void setStartTijdstip(LocalTime startTijdstip) {
        this.startTijdstip = startTijdstip;
        updateIngesteldStatus();
    }

    public int getPersonenPerPloeg() {
        return personenPerPloeg;
    }

    public void setPersonenPerPloeg(int personenPerPloeg) {
        this.personenPerPloeg = personenPerPloeg;
        updateIngesteldStatus();
    }

    public BigDecimal getInlegPerPloeg() {
        return inlegPerPloeg;
    }

    public void setInlegPerPloeg(BigDecimal inlegPerPloeg) {
        this.inlegPerPloeg = inlegPerPloeg;
        updateIngesteldStatus();
    }

    public int getMaximumAantalPloegen() {
        return maximumAantalPloegen;
    }

    public void setMaximumAantalPloegen(int maximumAantalPloegen) {
        this.maximumAantalPloegen = maximumAantalPloegen;
        updateIngesteldStatus();
    }

    public int getAantalWippen() {
        return aantalWippen;
    }

    public void setAantalWippen(int aantalWippen) {
        this.aantalWippen = aantalWippen;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public ToernooiStatus getStatus() {
        return status;
    }

    public void setStatus(ToernooiStatus status) {
        this.status = status;
    }

    public Datum getInschrijfDeadline() {
        return inschrijfDeadline;
    }

    public void setInschrijfDeadline(Datum inschrijfDeadline) {
        this.inschrijfDeadline = inschrijfDeadline;
        updateIngesteldStatus();
    }

    public boolean isHeeftMaaltijd() {
        return heeftMaaltijd;
    }

    public void setHeeftMaaltijd(boolean heeftMaaltijd) {
        this.heeftMaaltijd = heeftMaaltijd;
    }

    public boolean isMetInleg() {
        return metInleg;
    }

    public void setMetInleg(boolean metInleg) {
        this.metInleg = metInleg;
        updateIngesteldStatus();
    }

    public List<Menu> getMenus() {
        return Collections.unmodifiableList(menus);
    }

    public List<Inschrijving> getInschrijvingen() {
        return Collections.unmodifiableList(inschrijvingen);
    }

    //endregion

    //region PUBLIC METHODS

    protected void addInschrijving(Inschrijving inschrijving) throws IllegalStateException {
        if (inschrijvingen.stream()
                .anyMatch(ins -> ins.getClub()
                        .equals(inschrijving.getClub()))) {
            throw new IllegalStateException("Voor deze club is er al een inschrijving voor het toernooi");
        }

        if (!inschrijvenMogelijk()) {
            throw new IllegalStateException("Voor dit toernooi is inschrijven niet mogelijk");
        }

        inschrijvingen.add(inschrijving);
    }

    public Inschrijving addPloeg(Club club, String naam) {
        if (!inschrijvenMogelijk()) {
            throw new IllegalStateException(String.format("Inschrijven voor dit toernooi is niet mogelijk. %s", status.toUserFriendlyString()));
        }

        Inschrijving inschrijving = getInschrijvingVan(club);

        if (inschrijving == null) {
            inschrijving = Inschrijving.nieuweInschrijving(club, this);
        }

        Ploeg ploeg = new Ploeg(naam);
        ploeg.setAantalLeden(personenPerPloeg);
        Deelnemer deelnemer;
        for (int i = 0; i < ploeg.getAantalLeden(); i++) {
            deelnemer = new Deelnemer();
            deelnemer.setNaam(String.format("Ploeglid %d", i + 1));
            ploeg.addDeelnemer(deelnemer);
        }

        inschrijving.addPloeg(ploeg);

        if (aantalIngeschrevenPloegen() >= maximumAantalPloegen) {
            status = new Vol();
        }

        return inschrijving;
    }

    public Inschrijving removePloeg(int ploegId, Club club) {
        Inschrijving inschrijving = getInschrijvingVan(club);

        if (inschrijving == null) {
            throw new IllegalStateException("Voor deze club is er nog geen inschrijving");
        }

        boolean ploegVerwijderd = inschrijving.removePloeg(ploegId);

        if (inschrijving.getAantalPloegenIngeschreven() == 0) {
            inschrijvingen.remove(inschrijving);
            inschrijving.setClub(null);
        }

        if (ploegVerwijderd && status instanceof Vol) {
            status = new InschrijvingenOpen();
        }

        return inschrijving;
    }

    public void addMenu() {
        int volgendeMenu = menus.size() + 1;

        Menu menu = new Menu();
        menu.setNaam("Menu " + volgendeMenu);

        menus.add(menu);
    }

    public void removeMenu(int menuId) {
        menus.removeIf(menu -> menu.getId() == menuId);
    }

    public void removeMenu(String menuMemoryKey) {
        menus.removeIf(menu -> menu.getInMemoryKey().toString().equals(menuMemoryKey));
    }

    /**
     * Geeft de inschrijving voor dit toernooi van de meegegeven club terug
     *
     * @param club
     * @return de gevraagde inschrijving of null als er voor dit toernooi nog geen inschrijving is voor de meegegeven club
     */
    public Inschrijving getInschrijvingVan(Club club) {
        Optional<Inschrijving> optInschrijving = inschrijvingen.stream()
                .filter(ins -> ins.getClub().equals(club))
                .findFirst();

        if (optInschrijving.isPresent()) {
            return optInschrijving.get();
        }

        return null;
    }

    public void updateInschrijvingVan(Club club, Inschrijving inschrijving) {
        Inschrijving oud = getInschrijvingVan(club);

        if (oud != null) {
            oud = inschrijving;
        }
    }

    public boolean inschrijvenMogelijk() {
        return status.isInschrijvenMogelijk();
    }

    public boolean inschrijvingenOpen() {
        return status.isInschrijvingenOpen();
    }

    public boolean clubAlIngeschreven(Club club) {
        return inschrijvingen.stream().anyMatch(ins -> ins.getClub().equals(club));
    }

    public boolean openstellenInschrijvingenMogelijk() {
        return (status instanceof Ingesteld);
    }

    public boolean inschrijvingenAfgesloten() {
        return !(
                    (status instanceof Aangemaakt) ||
                    (status instanceof Ingesteld) ||
                    (status instanceof InschrijvingenOpen) ||
                    (status instanceof Vol)
                );
    }

    public boolean inschrijvenBeherenMogelijk() {
        return (
                    (status instanceof InschrijvingenOpen) ||
                    (status instanceof Vol) ||
                    (status instanceof InschrijvingenAfgesloten)
                );
    }

    /**
     * Geeft true als volgende properties geset zijn:
     * <ul>
     * <li>Naam (niet null, niet empty)</li>
     * <li>Datum</li>
     * <li>StartTijdstip</li>
     * <li>PersonenPerPloeg (groter of gelijk aan 1)</li>
     * <li>MaximumAantalPloegen (groter of gelijk aan 1)</li>
     * <li>InlegPerPloeg (niet null, minimum 0)</li>
     * </ul>
     *
     * @return
     */
    public boolean isInstellingenVolledig() {
        if (StringUtils.isBlank(naam)) {
            return false;
        }
        if (datum == null) {
            return false;
        }
        if (startTijdstip == null) {
            return false;
        }
        if (personenPerPloeg < 1) {
            return false;
        }
        if (maximumAantalPloegen < 1) {
            return false;
        }
        if (metInleg && (inlegPerPloeg == null || inlegPerPloeg.floatValue() < 0f)) {
            return false;
        }
        return true;
    }

    public void openInschrijvingen() {
        status.openInschrijvingen();
        status = new InschrijvingenOpen();
    }

    public void sluitInschrijvingen() {
        status.sluitInschrijvingen();
        status = new InschrijvingenAfgesloten();
    }

    public void loot() {
        status.loot();
    }

    public void start() {
        status.start();
    }

    public void stop() {
        status.stop();
    }

    public void heropenInschrijvingen() {
        status.heropenInschrijvingen();
    }

    public boolean isVerwijderbaar() {
        return status.isVerwijderbaar();
    }

    public boolean isAanpasbaar() {
        return status.isAanpasbaar();
    }

    public int aantalIngeschrevenPloegen() {
        return inschrijvingen.stream().mapToInt(Inschrijving::getAantalPloegenIngeschreven).sum();
    }

    public List<Ploeg> getIngeschrevenPloegen() {
        List<Ploeg> ploegen = new ArrayList<>();

        for (Inschrijving inschrijving : inschrijvingen) {
            ploegen.addAll(inschrijving.getPloegen());
        }

        return ploegen;
    }

    //endregion

    private void updateIngesteldStatus() {
        if (!(status instanceof Aangemaakt || status instanceof Ingesteld)) {
            return;
        }

        if (isInstellingenVolledig()) {
            status = new Ingesteld();
        } else {
            status = new Aangemaakt();
        }
    }

    //region OBJECT METHODS

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Toernooi toernooi = (Toernooi) o;

        if (id != toernooi.id) return false;
        return !(naam != null ? !naam.equals(toernooi.naam) : toernooi.naam != null);

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

        result += String.format("Toernooi '%s', ID=%d%n", naam, id);
        result += String.format("Op %s, start: %s%n", datum.getDatumInEuropeesFormaat(), startTijdstip);
        result += String.format("%d ploegen, %d/ploeg, €%f/ploeg inleg, %d wippen%n", maximumAantalPloegen, personenPerPloeg, inlegPerPloeg, aantalWippen);
        result += String.format("In het kader van: %s", kampioenschap.getNaam());

        return result;
    }

    //endregion
}
