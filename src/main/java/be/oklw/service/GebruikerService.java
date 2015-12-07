package be.oklw.service;

import be.oklw.data.DBFacade;
import be.oklw.data.IDBFacade;
import be.oklw.model.Account;
import be.oklw.util.Authentication;

public class GebruikerService {

    private IDBFacade dbFacade;

    public GebruikerService() {
        dbFacade = DBFacade.getUniekeInstantie();
    }

    public Account login(String userName, String password) {

        Account account = dbFacade.getAccount(userName);
        if (account == null) {
            throw new IllegalArgumentException("Onbestaande gebruiker");
        }

        if (!Authentication.isJuistPaswoord(password, account.getPwHash(), account.getPwSalt())) {
            throw new IllegalArgumentException("Onjuist paswoord");
        }

        return account;
    }
}
