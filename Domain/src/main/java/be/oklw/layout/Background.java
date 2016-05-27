package be.oklw.layout;

import javax.persistence.*;

@Entity
public class Background {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean bodyHasGradient;
    private String kleurBody;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Gradient gradientBody;

    private boolean bannersHaveGradient;
    private String kleurBanners;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Gradient gradientBanners;

    public Background() {

    }

    public Background(String kleurBody, String kleurBanners) {
        bodyHasGradient = false;
        bannersHaveGradient = false;
        this.kleurBanners = kleurBanners;
        this.kleurBody = kleurBody;
    }

    public boolean isBodyHasGradient() {
        return bodyHasGradient;
    }

    public void setBodyHasGradient(boolean bodyHasGradient) {
        this.bodyHasGradient = bodyHasGradient;

        if (gradientBody == null) {
            gradientBody = new LeftRightGradient();
        }
    }

    public String getKleurBody() {
        return kleurBody;
    }

    public Gradient getGradientBody() {
        return gradientBody;
    }

    public void setGradientBody(Gradient gradientBody) {
        bodyHasGradient = true;
        this.gradientBody = gradientBody;
    }

    public boolean isBannersHaveGradient() {
        return bannersHaveGradient;
    }

    public void setBannersHaveGradient(boolean bannersHaveGradient) {
        this.bannersHaveGradient = bannersHaveGradient;

        if (gradientBanners == null) {
            gradientBanners = new LeftRightGradient();
        }
    }

    public String getKleurBanners() {
        return kleurBanners;
    }

    public Gradient getGradientBanners() {
        return gradientBanners;
    }

    public void setGradientBanners(Gradient gradientBanners) {
        bannersHaveGradient = true;
        this.gradientBanners = gradientBanners;
    }

    /**
     * Stelt de primaire kleur van de body in. De primaire kleur is de kleur die als achtergrond gebruikt wordt als er geen gradient ingesteld is.
     * Als er wel een gradient ingesteld is, wordt de primaire kleur ook de startkleur van de gradient.
     *
     * @param primaireKleurBody Formaat: #xxxxxx
     */
    public void setPrimaireKleurBody(String primaireKleurBody) {
        this.kleurBody = primaireKleurBody;

        if (bodyHasGradient) {
            gradientBody.setStartKleur(primaireKleurBody);
        }
    }

    /**
     * Stelt de primaire kleur van de banners in. De primaire kleur is de kleur die als achtergrond gebruikt wordt als er geen gradient ingesteld is.
     * Als er wel een gradient ingesteld is, wordt de primaire kleur ook de startkleur van de gradient.
     *
     * @param primaireKleurBanners Formaat: #xxxxxx
     */
    public void setPrimaireKleurBanners(String primaireKleurBanners) {
        this.kleurBanners = primaireKleurBanners;

        if (bannersHaveGradient) {
            gradientBanners.setStartKleur(primaireKleurBanners);
        }
    }

    /**
     * Stelt de eindkleur van de gradient van de body in. Dit gebeurt enkel als er voor de body een gradient gebruikt wordt.
     *
     * @param secundaireKleurBody Formaat: #xxxxxx
     */
    public void setSecundaireKleurBody(String secundaireKleurBody) {
        if (bodyHasGradient) {
            gradientBody.setEindKleur(secundaireKleurBody);
        }
    }

    /**
     * Stelt de eindkleur van de gradient van de banners in. Dit gebeurt enkel als er voor de body een gradient gebruikt wordt.
     *
     * @param secundaireKleurBanners Formaat: #xxxxxx
     */
    public void setSecundaireKleurBanners(String secundaireKleurBanners) {
        if (bannersHaveGradient) {
            gradientBanners.setEindKleur(secundaireKleurBanners);
        }
    }

    public String bodyCss() {
        if (bodyHasGradient) {
            return gradientBody.css();
        }

        return String.format(
                "background: %s;",
                kleurBody
        );
    }

    public String bannersCss() {
        if (bannersHaveGradient) {
            return gradientBanners.css();
        }

        return String.format(
                "background: %s;",
                kleurBanners
        );
    }
}
