package be.oklw.model;

import be.oklw.model.state.*;
import be.oklw.usertype.DatumConverter;
import be.oklw.usertype.ToernooiStatusConverter;
import be.oklw.util.Datum;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.*;

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
    private String cateringInfo;

    @Convert(converter = DatumConverter.class)
    private Datum datum;

    @Convert(converter = DatumConverter.class)
    private Datum inschrijfDeadline;

    @Convert(converter = ToernooiStatusConverter.class)
    private ToernooiStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    private Kampioenschap kampioenschap;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "toernooi", orphanRemoval = true)
    private Set<Ploeg> ploegen;

    //endregion

    //region CONSTRUCTORS

    public Toernooi() {
        ploegen = new HashSet<Ploeg>();
        status = new Aangemaakt();
    }

    //endregion

    //region GETTERS en SETTERS

    public Set<Ploeg> getPloegen() {
        return Collections.unmodifiableSet(ploegen);
    }

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

    public String getCateringInfo() {
        return cateringInfo;
    }

    public void setCateringInfo(String cateringInfo) {
        this.cateringInfo = cateringInfo;
    }

    public boolean isMetInleg() {
        return metInleg;
    }

    public void setMetInleg(boolean metInleg) {
        this.metInleg = metInleg;
        updateIngesteldStatus();
    }

    //endregion

    //region PUBLIC METHODS

    protected void addPloeg(Ploeg ploeg) {
        ploegen.add(ploeg);
        if (aantalIngeschrevenPloegen() >= maximumAantalPloegen) {
            status = new Vol();
        }
    }

    protected void removePloeg(Ploeg ploeg) {
        ploegen.remove(ploeg);
        if (status instanceof Vol) {
            status = new InschrijvingenOpen();
        }
    }

    public void removePloeg(int ploegId) {
        Ploeg ploeg = null;

        for (Ploeg p : ploegen) {
            if (p.getId() == ploegId) {
                ploeg = p;
            }
        }

        ploegen.remove(ploeg);

        if (status instanceof Vol) {
            status = new InschrijvingenOpen();
        }
    }

    public List<Ploeg> getPloegenVan(Club club) {
        List<Ploeg> result = new ArrayList<>();

        for (Ploeg ploeg : ploegen) {
            if (ploeg.getClub().equals(club)) {
                result.add(ploeg);
            }
        }

        return result;
    }

    public boolean inschrijvenMogelijk() {
        return status.isInschrijvenMogelijk();
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

    public void annuleerInschrijving(Ploeg ploeg) {
        status.annuleerInschrijving(ploeg);
    }

    public void annuleerInschrijving(int id) {
        status.annuleerInschrijving(id);
    }

    public void sluitInschrijvingen() {
        status.sluitInschrijvingen();
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
        return ploegen.size();
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
