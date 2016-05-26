package be.oklw.layout;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Radial")
public class RadialGradiant extends Gradient {

    public RadialGradiant() {
    }

    public RadialGradiant(String startKleur, String eindKleur) {
        super(startKleur, eindKleur);
    }

    @Override
    public String firefox() {
        return backgroundProperty(String.format(
                "-moz-radial-gradient(center, ellipse cover,  %s 51%%, %s 90%%)",
                getStartKleur(),
                getEindKleur()
        ));
    }

    @Override
    public String chromeAndSafari() {
        return backgroundProperty(String.format(
                "-webkit-radial-gradient(center, ellipse cover,  %s 51%%,%s 90%%)",
                getStartKleur(),
                getEindKleur()
        ));
    }

    @Override
    public String modernBrowsers() {
        return backgroundProperty(String.format(
                "radial-gradient(ellipse at center,  %s 51%%,%s 90%%)",
                getStartKleur(),
                getEindKleur()
        ));
    }
}
