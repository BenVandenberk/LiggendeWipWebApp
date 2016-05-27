package be.oklw.layout;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "TopDown")
public class TopDownGradient extends Gradient {

    public TopDownGradient() {
    }

    public TopDownGradient(String startKleur, String eindKleur, int start, int stop) {
        super(startKleur, eindKleur, start, stop);
    }

    @Override
    public String firefox() {
        return backgroundProperty(String.format(
                "-moz-linear-gradient(top,  %s %d%%, %s %d%%)",
                getStartKleur(),
                getStart(),
                getEindKleur(),
                getStop()
        ));
    }

    @Override
    public String chromeAndSafari() {
        return backgroundProperty(String.format(
                "-webkit-linear-gradient(top,  %s %d%%,%s %d%%)",
                getStartKleur(),
                getStart(),
                getEindKleur(),
                getStop()
        ));
    }

    @Override
    public String modernBrowsers() {
        return backgroundProperty(String.format(
                "linear-gradient(to bottom,  %s %d%%,%s %d%%)",
                getStartKleur(),
                getStart(),
                getEindKleur(),
                getStop()
        ));
    }

    @Override
    public String type() {
        return "TopDown";
    }

    @Override
    public String internetExplorerFallback() {
        return String.format(
                "filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='%s', endColorstr='%s',GradientType=0 );",
                getStartKleur(),
                getEindKleur()
        );
    }
}
