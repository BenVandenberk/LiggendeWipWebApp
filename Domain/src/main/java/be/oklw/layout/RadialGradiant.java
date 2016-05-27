package be.oklw.layout;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Radial")
public class RadialGradiant extends Gradient {

    public RadialGradiant() {
    }

    public RadialGradiant(String startKleur, String eindKleur, int start, int stop) {
        super(startKleur, eindKleur, start, stop);
    }

    @Override
    public String firefox() {
        return backgroundProperty(String.format(
                "-moz-radial-gradient(center, ellipse cover,  %s %d%%, %s %d%%)",
                getStartKleur(),
                getStart(),
                getEindKleur(),
                getStop()
        ));
    }

    @Override
    public String chromeAndSafari() {
        return backgroundProperty(String.format(
                "-webkit-radial-gradient(center, ellipse cover,  %s %d%%,%s %d%%)",
                getStartKleur(),
                getStart(),
                getEindKleur(),
                getStop()
        ));
    }

    @Override
    public String modernBrowsers() {
        return backgroundProperty(String.format(
                "radial-gradient(ellipse at center,  %s %d%%,%s %d%%)",
                getStartKleur(),
                getStart(),
                getEindKleur(),
                getStop()
        ));
    }

    @Override
    public String type() {
        return "Radial";
    }
}
