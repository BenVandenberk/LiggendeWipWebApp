package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.model.Lid;
import be.oklw.model.SysteemAccount;

import javax.ejb.Local;
import java.util.List;
import java.util.UUID;

@Local
public interface IGebruikerService {

    /**
     * Geeft een Account terug op basis van username en password dat gebruikt kan worden om de user in te loggen
     *
     * @param userName de gebruikersnaam (String)
     * @param password het paswoord (String)
     * @return het Account
     * @throws BusinessException als
     *                           <ul>
     *                           <li>De gebruikersnaam niet bestaat</li>
     *                           <li>De gebruikersnaam niet uniek is</li>
     *                           <li>Het paswoord onjuist is</li>
     *                           <li>Er is mis gaat met de data-access (entity state, transaction state, ...)</li>
     *                           </ul>
     */
    Account login(String userName, String password) throws BusinessException;

    /**
     * SEED METHOD - TE VERWIJDEREN
     */
    void createAdmin();

    /**
     * Verandert het paswoord van een Account
     *
     * @param account het Account waarvoor het paswoord veranderd wordt
     * @param oud     het oude paswoord (String)
     * @param nieuw   het nieuwe paswoord (String)
     * @return het geüpdatete Account
     * @throws BusinessException als het oude paswoord onjuist is of als er iets mis gaat met de data-access (entity state, transaction state, ...)
     */
    Account veranderPaswoord(Account account, String oud, String nieuw) throws BusinessException;

    /**
     * Zoekt een SysteemAccount op basis van id
     *
     * @param id de id (int)
     * @return het SysteemAccount of null als er geen SysteemAccount bestaat met de meegegeven id
     */
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
     *
     * @param lid      het Lid waarvoor een Account aangemaakt wordt
     * @param club     de Club waartoe het Lid behoort
     * @param userName de gebruikersnaam van het Lid
     * @param password het paswoord van het Lid
     * @return het geregistreerde Account
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    Account registreer(Lid lid, Club club, String userName, String password) throws BusinessException;

    /**
     * Save een bestaand Lid in de database ('merge')
     *
     * @param lid het te saven Lid
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    void updateLid(Lid lid) throws BusinessException;

    /**
     * Save een bestaand Account in de database ('merge')
     *
     * @param account het te saven Account
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    void saveAccount(Account account) throws BusinessException;

    /**
     * Zoekt een Account op basis van username.
     *
     * @param userName de gebruikersnaam (String)
     * @return het Account of null als er geen Account bestaat met de meegegeven username
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    Account getAccount(String userName) throws BusinessException;

    /**
     * Genereert een random paswoord, stelt het in als paswoord van het meegegeven Account en verstuurt een mail naar de eigenaar van het Account om hem/haar in kennis
     * te stellen van de wijziging.
     *
     * @param account het Account waarvan het paswoord gewijzigd wordt
     * @throws BusinessException als er iets mis gaat met de data-access of het versturen van de mail
     */
    void resetPaswoord(Account account) throws BusinessException;

    /**
     * Genereert een resetlink en persisteert die. Stelt een timer in voor de validiteit van de resetlink. Verstuurt een mail naar het emailadres van het account met de resetlink.
     *
     * @param account het Account waarvan het paswoord gereset moet worden
     * @throws BusinessException als er iets mis gaat met de data-access of het versturen van de mail
     */
    void verstuurPaswoordResetLink(Account account) throws BusinessException;

    Account valideerResetLink(UUID linkUUUD) throws BusinessException;
}
