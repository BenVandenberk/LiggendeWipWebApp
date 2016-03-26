package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.model.Lid;
import be.oklw.model.SysteemAccount;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IGebruikerService {

    Account login(String userName, String password) throws BusinessException;

    void createAdmin();

    Account veranderPaswoord(Account account, String oud, String nieuw) throws BusinessException;

    SysteemAccount getSysteemAccount(int id);

    List<SysteemAccount> getAllSysteemAccount();

    Account wijzigAdminContactGegevens(SysteemAccount systeemAccount, String newEmail, String newTelefoonNummer) throws BusinessException;

    /**
     * Controleert of de meegegeven code een registratiecode is voor een Club en geeft die Club terug. Geeft null terug als de code niet bij een Club hoort.
     *
     * @param code de te valideren registratiecode
     * @return de Club waarvoor de code geldt of null als de code niet bij een Club hoort
     * @throws BusinessException als de code meerdere clubs toebehoort of als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    Club valideerRegistratieCode(String code) throws BusinessException;

    /**
     * Maakt een lidaccount aan dat verbonden is aan een Club
     * @param lid het Lid waarvoor een Account aangemaakt wordt
     * @param club de Club waartoe het Lid behoort
     * @param userName de gebruikersnaam van het Lid
     * @param password het paswoord van het Lid
     * @return het geregistreerde Account
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    Account registreer(Lid lid, Club club, String userName, String password) throws BusinessException;

    /**
     * Save een bestaand Lid in de database ('merge')
     * @param lid het te saven Lid
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    void updateLid(Lid lid) throws BusinessException;

    void saveAccount(Account account) throws BusinessException;

    Account getAccount(String userName) throws BusinessException;

    void resetPaswoord(Account account) throws BusinessException;
}
