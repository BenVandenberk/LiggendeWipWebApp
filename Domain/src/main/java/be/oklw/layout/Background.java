package be.oklw.layout;

import javax.persistence.*;

@Entity
public class Background {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private boolean isGradientBody;
    private String kleurBody;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Gradient gradientBody;

    private boolean isGradientBanners;
    private String kleurBanners;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Gradient gradientBanners;

    public Background() {

    }

    public Background(String kleurBody, String kleurBanners) {
        isGradientBody = false;
        isGradientBanners = false;
        this.kleurBanners = kleurBanners;
        this.kleurBody = kleurBody;
    }

    public boolean isGradientBody() {
        return isGradientBody;
    }

    public void setGradientBody(boolean gradientBody) {
        isGradientBody = gradientBody;
    }

    public String getKleurBody() {
        return kleurBody;
    }

    public void setKleurBody(String kleurBody) {
        this.kleurBody = kleurBody;
    }

    public Gradient getGradientBody() {
        return gradientBody;
    }

    public void setGradientBody(Gradient gradientBody) {
        isGradientBody = true;
        this.gradientBody = gradientBody;
    }

    public boolean isGradientBanners() {
        return isGradientBanners;
    }

    public void setGradientBanners(boolean gradientBanners) {
        isGradientBanners = gradientBanners;
    }

    public String getKleurBanners() {
        return kleurBanners;
    }

    public void setKleurBanners(String kleurBanners) {
        this.kleurBanners = kleurBanners;
    }

    public Gradient getGradientBanners() {
        return gradientBanners;
    }

    public void setGradientBanners(Gradient gradientBanners) {
        isGradientBanners = true;
        this.gradientBanners = gradientBanners;
    }

    public String bodyCss() {
        if (isGradientBody) {
            return gradientBody.css();
        }

        return String.format(
                "background: %s;",
                kleurBody
        );
    }

    public String bannersCss() {
        if (isGradientBanners) {
            return gradientBanners.css();
        }

        return String.format(
                "background: %s;",
                kleurBanners
        );
    }
}
