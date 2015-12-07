package be.oklw.data;

import be.oklw.model.Club;
import be.oklw.model.SysteemAccount;
import be.oklw.util.Authentication;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebAppSeed implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        IDBFacade dbFacade = DBFacade.getUniekeInstantie();

        Club club = new Club("club", "Leuven");
        dbFacade.saveClub(club);

        SysteemAccount systeemAccount = new SysteemAccount("admin");
        systeemAccount.setUserName("admin");
        byte[] salt = Authentication.nextSalt();
        byte[] pwHash = Authentication.hashPw("admin", salt);
        systeemAccount.setPwHash(pwHash);
        systeemAccount.setPwSalt(salt);
        dbFacade.saveAccount(systeemAccount);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
