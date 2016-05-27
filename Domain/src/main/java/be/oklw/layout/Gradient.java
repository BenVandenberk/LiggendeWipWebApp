package be.oklw.layout;

import javax.persistence.*;

@Entity
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Gradient_type")
public abstract class Gradient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String startKleur;
    private String eindKleur;

    private int start;
    private int stop;

    public Gradient() {
        start = 0;
        stop = 100;
    }

    public Gradient(String startKleur, String eindKleur, int start, int stop) {
        this.startKleur = startKleur;
        this.eindKleur = eindKleur;
        this.start = start;
        this.stop = stop;
    }

    /**
     * Factory method om een Gradient aan te maken. De Magic Strings voor gradienttypes zijn:
     * <ul>
     *     <li>LeftRight -> LeftRightGradient</li>
     *     <li>DiagonalUpDown -> DiagonalUpDownGradient</li>
     *     <li>DiagonalDownUp -> DiagonalDownUpGradient</li>
     *     <li>Radial -> RadialGradient</li>
     *     <li>TopDown -> TopDownGradient</li>
     * </ul>
     * @param type de String met het type Gradient
     * @param startKleur de kleur waarmee de Gradient start. Formaat: #xxxxxx
     * @param eindKleur de kleur waarmee de Gradient eindigt. Formaat: #xxxxxx
     * @return het aangemaakte Gradient object of null als er een ongeldige typestring meegegeven is
     */
    public static Gradient maak(String type, String startKleur, String eindKleur, int start, int stop) {
        
        switch (type) {
            case "Radial":
                    return new RadialGradiant(startKleur, eindKleur, start, stop);
            case "DiagonalUpDown":;
                    return new DiagonalUpDownGradient(startKleur, eindKleur, start, stop);
            case "DiagonalDownUp":
                return new DiagonalDownUpGradient(startKleur, eindKleur, start, stop);
            case "LeftRight":
                return new LeftRightGradient(startKleur, eindKleur, start, stop);
            case "TopDown":
                return new TopDownGradient(startKleur, eindKleur, start, stop);
            default:
                return null;
        }
        
    }

    public String getStartKleur() {
        return startKleur;
    }

    public void setStartKleur(String startKleur) {
        this.startKleur = startKleur;
    }

    public String getEindKleur() {
        return eindKleur;
    }

    public void setEindKleur(String eindKleur) {
        this.eindKleur = eindKleur;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public int getId() {
        return id;
    }

    public String css() {
        return String.format(
                "%s%s%s%s%s",
                oldBrowsers(),
                firefox(),
                chromeAndSafari(),
                modernBrowsers(),
                internetExplorerFallback()
        );
    }

    public String internetExplorerFallback() {
        return String.format(
                "filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='%s', endColorstr='%s',GradientType=1 );",
                startKleur,
                eindKleur
        );
    }

    public String oldBrowsers() {
        return backgroundProperty(getStartKleur());
    }

    public String backgroundProperty(String value) {
        return String.format(
                "%s: %s;",
                "background",
                value
        );
    }

    public abstract String firefox();
    public abstract String chromeAndSafari();
    public abstract String modernBrowsers();

    public abstract String type();
}
