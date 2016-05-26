package be.oklw.bean.common;

import be.oklw.layout.Background;
import be.oklw.service.ILayoutService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class LayoutBean {

    @EJB
    ILayoutService layoutService;

    private Background siteBackground;

    public Background getSiteBackground() {
        return siteBackground;
    }

    public void setSiteBackground(Background siteBackground) {
        this.siteBackground = siteBackground;
    }

    @PostConstruct
    private void init() {
        siteBackground = layoutService.siteBackground();
    }
}
