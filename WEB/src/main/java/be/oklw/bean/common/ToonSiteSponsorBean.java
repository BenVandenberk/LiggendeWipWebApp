package be.oklw.bean.common;

import be.oklw.model.SiteSponsor;
import be.oklw.service.ISponsorService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean
@ViewScoped
public class ToonSiteSponsorBean {

    @EJB
    ISponsorService sponsorService;

    private List<SiteSponsor> siteSponsors;

    public List<SiteSponsor> getSiteSponsors() {
        return siteSponsors;
    }

    @PostConstruct
    public void init() {
        siteSponsors = sponsorService.getSiteSponsors();
    }
}
