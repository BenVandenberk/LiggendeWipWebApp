package be.oklw.data;

import be.oklw.model.Account;
import be.oklw.model.PermissieNiveau;
import be.oklw.model.SysteemAccount;
import be.oklw.util.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by java on 07.12.15.
 */
public class DataSeedServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();

        IDBFacade dbFacade = DBFacade.getUniekeInstantie();

        SysteemAccount systeemAccount = new SysteemAccount("admin");
        systeemAccount.setUserName("admin");
        byte[] salt = Authentication.nextSalt();
        byte[] pwHash = Authentication.hashPw("admin", salt);
        systeemAccount.setPwHash(pwHash);
        systeemAccount.setPwSalt(salt);
        dbFacade.saveAccount(systeemAccount);
    }
}
