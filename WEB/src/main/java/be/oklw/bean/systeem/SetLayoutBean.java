package be.oklw.bean.systeem;

import be.oklw.layout.*;
import be.oklw.service.ILayoutService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SetLayoutBean {

    @EJB
    ILayoutService layoutService;

    private Background siteBackground;

    private String kleur1Banners;
    private String kleur2Banners;
    private String gradientTypeBanners;

    public Background getSiteBackground() {
        return siteBackground;
    }

    public void setSiteBackground(Background siteBackground) {
        this.siteBackground = siteBackground;
    }

    public String getKleur1Banners() {
        return kleur1Banners;
    }

    public void setKleur1Banners(String kleur1Banners) {
        this.kleur1Banners = "#" + kleur1Banners;

        siteBackground.setKleurBanners(this.kleur1Banners);

        if (siteBackground.isGradientBanners()) {
            siteBackground.getGradientBanners().setStartKleur(this.kleur1Banners);
        }
    }

    public String getKleur2Banners() {
        return kleur2Banners;
    }

    public void setKleur2Banners(String kleur2Banners) {
        this.kleur2Banners = "#" + kleur2Banners;

        siteBackground.setKleurBanners(this.kleur2Banners);

        if (siteBackground.isGradientBanners()) {
            siteBackground.getGradientBanners().setEindKleur(this.kleur2Banners);
        }
    }

    public String getGradientTypeBanners() {
        return gradientTypeBanners;
    }

    public void setGradientTypeBanners(String gradientTypeBanners) {
        this.gradientTypeBanners = gradientTypeBanners;

        switch (gradientTypeBanners) {
            case "Radial":
                siteBackground.setGradientBanners(
                        new RadialGradiant(kleur1Banners, kleur2Banners)
                );
                break;
            case "DiagonalUpDown":
                siteBackground.setGradientBanners(
                        new DiagonalUpDownGradient(kleur1Banners, kleur2Banners)
                );
                break;
            case "DiagonalDownUp":
                siteBackground.setGradientBanners(
                        new DiagonalDownUpGradient(kleur1Banners, kleur2Banners)
                );
                break;
            case "LeftRight":
                siteBackground.setGradientBanners(
                        new LeftRightGradient(kleur1Banners, kleur2Banners)
                );
                break;
        }
    }

    public void opslaan() {
        layoutService.opslaan(siteBackground);
    }

    @PostConstruct
    private void init() {
        siteBackground = layoutService.siteBackground();

        if (siteBackground.isGradientBanners()) {
            kleur1Banners = siteBackground.getGradientBanners().getStartKleur();
            kleur2Banners = siteBackground.getGradientBanners().getEindKleur();
        } else {
            kleur1Banners = siteBackground.getKleurBanners();
            kleur2Banners = "#ffffff";
        }
    }
}
