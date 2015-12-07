package be.oklw.data;

import be.oklw.model.Account;

/**
 * Created by java on 28.11.15.
 */
public interface IDBFacade {

    public Account getAccount(String gebruikersnaam);

    public void saveAccount(Account account);
}
