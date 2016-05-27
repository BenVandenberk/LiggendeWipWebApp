package be.oklw.bean.systeem;

import be.oklw.layout.Background;
import be.oklw.layout.Gradient;
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

    private String primaireKleurBanners;
    private String secundaireKleurBanners;
    private String gradientTypeBanners;
    private int bannerStart;
    private int bannerStop;

    private String primaireKleurBody;
    private String secundaireKleurBody;
    private String gradientTypeBody;
    private int bodyStart;
    private int bodyStop;

    public Background getSiteBackground() {
        return siteBackground;
    }

    public void setSiteBackground(Background siteBackground) {
        this.siteBackground = siteBackground;
    }

    //region BANNERS

    public String getPrimaireKleurBanners() {
        return primaireKleurBanners;
    }

    public void setPrimaireKleurBanners(String primaireKleurBanners) {
        this.primaireKleurBanners = hekje(primaireKleurBanners);
        siteBackground.setPrimaireKleurBanners(this.primaireKleurBanners);
    }

    public String getSecundaireKleurBanners() {
        return secundaireKleurBanners;
    }

    public void setSecundaireKleurBanners(String secundaireKleurBanners) {
        this.secundaireKleurBanners = hekje(secundaireKleurBanners);
        siteBackground.setSecundaireKleurBanners(this.secundaireKleurBanners);
    }

    public String getGradientTypeBanners() {
        return gradientTypeBanners;
    }

    public void setGradientTypeBanners(String gradientTypeBanners) {
        this.gradientTypeBanners = gradientTypeBanners;

        siteBackground.setGradientBanners(
                Gradient.maak(gradientTypeBanners, primaireKleurBanners, secundaireKleurBanners, bannerStart, bannerStop)
        );
    }

    public int getBannerStart() {
        return bannerStart;
    }

    public void setBannerStart(int bannerStart) {
        this.bannerStart = bannerStart;

        if (siteBackground.isBannersHaveGradient()) {
            siteBackground.getGradientBanners().setStart(bannerStart);
        }
    }

    public int getBannerStop() {
        return bannerStop;
    }

    public void setBannerStop(int bannerStop) {
        this.bannerStop = bannerStop;

        if (siteBackground.isBannersHaveGradient()) {
            siteBackground.getGradientBanners().setStop(bannerStop);
        }
    }

    //endregion

    //region BODY

    public String getPrimaireKleurBody() {
        return primaireKleurBody;
    }

    public void setPrimaireKleurBody(String primaireKleurBody) {
        this.primaireKleurBody = hekje(primaireKleurBody);
        siteBackground.setPrimaireKleurBody(this.primaireKleurBody);
    }

    public String getSecundaireKleurBody() {
        return secundaireKleurBody;
    }

    public void setSecundaireKleurBody(String secundaireKleurBody) {
        this.secundaireKleurBody = hekje(secundaireKleurBody);
        siteBackground.setSecundaireKleurBody(this.secundaireKleurBody);
    }

    public String getGradientTypeBody() {
        return gradientTypeBody;
    }

    public void setGradientTypeBody(String gradientTypeBody) {
        this.gradientTypeBody = gradientTypeBody;

        siteBackground.setGradientBody(
                Gradient.maak(gradientTypeBody, primaireKleurBody, secundaireKleurBody, bodyStart, bodyStop)
        );
    }

    public int getBodyStart() {
        return bodyStart;
    }

    public void setBodyStart(int bodyStart) {
        this.bodyStart = bodyStart;

        if (siteBackground.isBodyHasGradient()) {
            siteBackground.getGradientBody().setStart(bodyStart);
        }
    }

    public int getBodyStop() {
        return bodyStop;
    }

    public void setBodyStop(int bodyStop) {
        this.bodyStop = bodyStop;

        if (siteBackground.isBodyHasGradient()) {
            siteBackground.getGradientBody().setStop(bodyStop);
        }
    }


    //endregion

    public void opslaan() {
        layoutService.opslaan(siteBackground);
    }

    private String hekje(String kleurString) {
        if (!kleurString.startsWith("#")) {
            kleurString = "#" + kleurString;
        }
        return kleurString;
    }

    @PostConstruct
    private void init() {
        siteBackground = layoutService.siteBackground();

        if (siteBackground.isBannersHaveGradient()) {
            primaireKleurBanners = siteBackground.getGradientBanners().getStartKleur();
            secundaireKleurBanners = siteBackground.getGradientBanners().getEindKleur();
            gradientTypeBanners = siteBackground.getGradientBanners().type();
            bannerStart = siteBackground.getGradientBanners().getStart();
            bannerStop = siteBackground.getGradientBanners().getStop();
        } else {
            primaireKleurBanners = siteBackground.getKleurBanners();
            secundaireKleurBanners = "#ffffff";
            bannerStart = 0;
            bannerStop = 100;
        }

        if (siteBackground.isBodyHasGradient()) {
            primaireKleurBody = siteBackground.getGradientBody().getStartKleur();
            secundaireKleurBody = siteBackground.getGradientBody().getEindKleur();
            gradientTypeBody = siteBackground.getGradientBody().type();
            bodyStart = siteBackground.getGradientBody().getStart();
            bodyStop = siteBackground.getGradientBody().getStop();
        } else {
            primaireKleurBody = siteBackground.getKleurBody();
            secundaireKleurBody = "#ffffff";
            bodyStart = 0;
            bodyStop = 100;
        }
    }
}
