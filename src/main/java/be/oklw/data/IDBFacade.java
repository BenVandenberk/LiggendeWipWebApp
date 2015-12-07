package be.oklw.data;

import be.oklw.model.Account;
import be.oklw.model.Club;

/**
 * Created by java on 28.11.15.
 */
public interface IDBFacade {

    public Account getAccount(String gebruikersnaam);

    public void saveAccount(Account account);

    public void saveClub(Club club);
}
