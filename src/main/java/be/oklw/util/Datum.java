package be.oklw.util;

import static java.lang.Math.abs;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * Custom Datum klasse die de java.util.GregorianCalendar klasse wrapt
 *
 * @author Bert Neyt
 * @author Adriaan Kuipers
 * @author Ben Vandenberk
 * @version 4/10/2014
 *
 */
public class Datum implements Comparable<Datum> {

    private GregorianCalendar gregorianCalendarDatum;
    private static Locale locale = Locale.getDefault();

    /**
     * Maakt een datum object aan met als waarde de dag van vandaag
     */
    public Datum() {
        GregorianCalendar vandaag = new GregorianCalendar();
        setDatum(vandaag.get(Calendar.DAY_OF_MONTH), vandaag.get(Calendar.MONTH) + 1, vandaag.get(Calendar.YEAR));
    }

    /**
     * Maakt een datum object aan
     *
     * @param dag
     *            de dag van de maand
     * @param maand
     *            de maand
     * @param jaar
     *            het jaar
     *
     * @throws IllegalArgumentException
     *             als een van de argumenten of de combinatie van argumenten ongeldig is
     */
    public Datum(int dag, int maand, int jaar) {
        boolean b = this.setDatum(dag, maand, jaar);
        if (!b) {
            throw new IllegalArgumentException("Ongeldige datum!");
        }
    }

    /**
     * Maakt een datum object aan op basis van een ander datum object
     *
     * @param datum
     *            het datum object op basis waarvan de constructor een nieuw datum object aanmaakt
     */
    public Datum(Datum datum) {
        this(datum.getDag(), datum.getMaand(), datum.getJaar());
    }

    /**
     * Maakt een datum object aan op basis van een string.
     * Format: DD/MM/YYYY of DD-MM-YYYY of in MySQL notatie YYYY-MM-DD of 'YYYY-MM-DD'
     *
     * @param datum
     *            de String met een datum waarde (DD/MM/YYYY of DD-MM-YYYY)
     *            de String met een MySQL datum waarde (YYYY-MM-DD of 'YYYY-MM-DD')
     * @throws IllegalArgumentException
     *             als de input String van een foutief formaat is of als de datumwaarde ongeldig is
     */
    public Datum(String datum) {
        String[] parts = datum.split(" ");
        String datumString = parts[0].trim();
        String[] dateParts = datumString.split("/|-");

        //Europese notatie = DD/MM/YYYY
        String dag = dateParts[0];
        String maand = dateParts[1];
        String jaar = dateParts[2];

        //MySQL notatie = YYYY-MM-DD
        if (dag.length() == 4 && jaar.length() <= 2) {
            dag = dateParts[2];
            jaar = dateParts[0];
        }

        try {
            boolean b = this.setDatum(Integer.parseInt(dag), Integer.parseInt(maand), Integer.parseInt(jaar));
            if (!b) {
                throw new IllegalArgumentException("Ongeldige datum");
            }
        } catch (NumberFormatException N) {
            throw new IllegalArgumentException("Foutieve input", N);
        }
    }

    /**
     * Haalt het jaar-gedeelte van het datum object op
     *
     * @return het jaar
     */
    public int getJaar() {
        return this.gregorianCalendarDatum.get(GregorianCalendar.YEAR);
    }

    /**
     * Haalt het maand-gedeelte van het datum object op
     *
     * @return de maand
     */
    public int getMaand() {
        return this.gregorianCalendarDatum.get(GregorianCalendar.MONTH) + 1;
    }

    /**
     * Haalt het dag-gedeelte van het datum object op
     *
     * @return de dag
     */
    public int getDag() {
        return this.gregorianCalendarDatum.get(GregorianCalendar.DAY_OF_MONTH);
    }

    /**
     * Zet het datum object op een geldige waarde
     *
     * @param dag
     *            de dag van de maand
     * @param maand
     *            de maand
     * @param jaar
     *            het jaar
     * @return true als het zetten van de datum gelukt is
     */
    public boolean setDatum(int dag, int maand, int jaar) {
        try {
            if (jaar < 1 || jaar > 9999) {
                return false;
            }
            this.gregorianCalendarDatum = new GregorianCalendar(jaar, maand - 1, dag);
            this.gregorianCalendarDatum.setLenient(false);
            this.gregorianCalendarDatum.getTime();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Overrides de default JVM Locale met de meegegeven Locale
     *
     * @param loc
     *            de Locale die door de Datum klasse gebruikt wordt om datums te formateren
     */
    public static void setLocale(Locale loc) {
        locale = loc;
    }

    /**
     * Geeft een string terug in Amerikaans formaat (YYYY/MM/DD)
     *
     * @return een string in Amerikaans formaat (YYYY/MM/DD)
     */
    public String getDatumInAmerikaansFormaat() {
        return this.getJaar() + "/" + this.getMaand() + "/" + this.getDag();
    }

    /**
     * Geeft een string terug in Europees formaat (DD/MM/YYYY)
     *
     * @return een string in Europees formaat (DD/MM/YYYY)
     */
    public String getDatumInEuropeesFormaat() {
        return this.getDag() + "/" + this.getMaand() + "/" + this.getJaar();
    }

    /**
     * Geeft een niet-afgebakende string in MySQL formaat ("YYYY-MM-DD")
     * Te gebruiken voor vb. MySQL datum notatie
     * @return een niet-afgebakende string in MySQL formaat ("YYY-MM-DD")
     */
    public String getDatumInMySQLFormaat() {
        return String.format("%d-%02d-%02d", this.getJaar(), this.getMaand(), this.getDag());
    }

    @Override
    public String toString() {
        DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance(locale);
        return this.getDag() + " " + dateFormatSymbols.getMonths()[this.getMaand() - 1] + " " + this.getJaar();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Datum) {
            return this.getDatumInAmerikaansFormaat().equals(((Datum) obj).getDatumInAmerikaansFormaat());
        } else {
            return false;
        }
    }

    public int compareTo(Datum datum) {
        GregorianCalendar inputDatum = new GregorianCalendar(datum.getJaar(), datum.getMaand() - 1, datum.getDag());
        return this.gregorianCalendarDatum.compareTo(inputDatum);
    }

    /**
     * Geeft true terug als het datum object zich chronologisch vóór het meegegeven datum object bevindt
     *
     * @param datum
     *            het datum object waarmee vergeleken wordt
     * @return true als het datum object zich chronologisch vóór het meegegeven datum object bevindt
     */
    public boolean kleinerDan(Datum datum) {
        GregorianCalendar inputDatum = new GregorianCalendar(datum.getJaar(), datum.getMaand() - 1, datum.getDag());
        return this.gregorianCalendarDatum.before(inputDatum);
    }

    /**
     * Geeft het aantal gehele jaren terug dat zich bevindt tussen het datum object en het meegegeven datum object
     *
     * @param datum
     *            het datum object waarmee vergeleken wordt
     * @return het aantal gehele jaren dat zich bevindt tussen het datum object en het meegegeven datum object
     */
    public int verschilInJaren(Datum datum) {
        GregorianCalendar inputDatum = new GregorianCalendar(datum.getJaar(), datum.getMaand() - 1, datum.getDag());
        if (this.equals(datum)) {
            return 0;
        }
        int verschil = -1;
        if (this.gregorianCalendarDatum.after(inputDatum)) {
            while (inputDatum.before(this.gregorianCalendarDatum)) {
                inputDatum.add(Calendar.YEAR, 1);
                verschil++;
            }
        } else {
            GregorianCalendar thisClone = new GregorianCalendar(this.getJaar(), this.getMaand() - 1, this.getDag());
            while (thisClone.before(inputDatum)) {
                thisClone.add(Calendar.YEAR, 1);
                verschil++;
            }
        }
        return verschil;
    }

    /**
     * Geeft het aantal gehele maanden terug dat zich bevindt tussen het datum object en het meegegeven datum object
     *
     * @param datum
     *            het datum object waarmee vergeleken wordt
     * @return het aantal gehele maanden dat zich bevindt tussen het datum object en het meegegeven datum object
     */
    public int verschilInMaanden(Datum datum) {
        GregorianCalendar inputDatum = new GregorianCalendar(datum.getJaar(), datum.getMaand() - 1, datum.getDag());
        if (this.equals(datum)) {
            return 0;
        }
        int verschil = -1;
        if (this.gregorianCalendarDatum.after(inputDatum)) {
            while (inputDatum.before(this.gregorianCalendarDatum)) {
                inputDatum.add(Calendar.MONTH, 1);
                verschil++;
            }
        } else {
            GregorianCalendar thisClone = new GregorianCalendar(this.getJaar(), this.getMaand() - 1, this.getDag());
            while (thisClone.before(inputDatum)) {
                thisClone.add(Calendar.MONTH, 1);
                verschil++;
            }
        }
        return verschil;
    }

    /**
     * Geeft het aantal gehele dagen terug dat zich bevindt tussen het datum object en het meegegeven datum object
     *
     * @param datum
     *            het datum object waarmee vergeleken wordt
     * @return het aantal gehele dagen dat zich bevindt tussen het datum object en het meegegeven datum object
     */
    public int verschilInDagen(Datum datum) {
        GregorianCalendar inputDatum = new GregorianCalendar(datum.getJaar(), datum.getMaand() - 1, datum.getDag());
        long verschil = abs(inputDatum.getTimeInMillis() - this.gregorianCalendarDatum.getTimeInMillis());
        return (int) (verschil / (1000 * 60 * 60 * 24));
    }

    /**
     * Verandert de datum met het meegegeven aantal dagen. Een positief getal verhoogt de datum, een negatief getal
     * verlaagt ze
     *
     * @param aantalDagen
     *            het aantal dagen waarmee de datum aangepast wordt
     */
    public void veranderDatum(int aantalDagen) {
        this.gregorianCalendarDatum.add(Calendar.DAY_OF_YEAR, aantalDagen);
        if (this.gregorianCalendarDatum.get(Calendar.ERA) == 0 || this.getJaar() > 9999) {
            this.gregorianCalendarDatum.add(Calendar.DAY_OF_YEAR, -aantalDagen);
            throw new IllegalArgumentException("Jaar buiten bereik");
        }
    }

    /**
     * Geeft een datum object terug dat verschilt van het huidige met het meegegeven aantal dagen. Een positieve waarde
     * geeft een latere datum terug, een negatieve een eerdere
     *
     * @param aantalDagen
     *            het aantal dagen waarmee de teruggegeven datum verschilt van het datum object
     * @return het datum object dat met het aantal dagen is veranderd
     */
    public Datum getVeranderdeDatum(int aantalDagen) {
        Datum resultaatDatum = new Datum(this);
        resultaatDatum.veranderDatum(aantalDagen);
        return resultaatDatum;
    }
}
