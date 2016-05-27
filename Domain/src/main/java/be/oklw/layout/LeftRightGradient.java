package be.oklw.layout;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "LeftRight")
public class LeftRightGradient extends Gradient {

    public LeftRightGradient(String startKleur, String eindKleur, int start, int stop) {
        super(startKleur, eindKleur, start, stop);
    }

    public LeftRightGradient() {

    }

    @Override
    public String firefox() {
        return backgroundProperty(String.format(
                "-moz-linear-gradient(left,  %s %d%%, %s %d%%)",
                getStartKleur(),
                getStart(),
                getEindKleur(),
                getStop()
        ));
    }

    @Override
    public String chromeAndSafari() {
        return backgroundProperty(String.format(
                "-webkit-linear-gradient(left,  %s %d%%,%s %d%%)",
                getStartKleur(),
                getStart(),
                getEindKleur(),
                getStop()
        ));
    }

    @Override
    public String modernBrowsers() {
        return backgroundProperty(String.format(
                "linear-gradient(to right,  %s %d%%,%s %d%%)",
                getStartKleur(),
                getStart(),
                getEindKleur(),
                getStop()
        ));
    }

    @Override
    public String type() {
        return "LeftRight";
    }
}
