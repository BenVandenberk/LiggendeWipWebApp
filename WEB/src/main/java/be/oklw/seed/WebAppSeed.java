package be.oklw.seed;

import be.oklw.service.IClubService;
import be.oklw.service.IGebruikerService;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebAppSeed implements ServletContextListener {

    @EJB
    IGebruikerService gebruikerService;

    @EJB
    IClubService clubService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        gebruikerService.createAdmin();
        clubService.dummyKampioenschappen();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
