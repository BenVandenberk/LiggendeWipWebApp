package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.*;

import javax.ejb.Local;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Local
public interface IClubService {

    /**
     * Verandert het logo van een Club
     * @param logoFileContent de binaire representatie van de file (byte[])
     * @param logoFileName de naam van de file
     * @param club de Club waarvoor het logo aangepast wordt
     * @throws BusinessException als er iets mis loopt met de file I/O of de data-access
     */
    void veranderClubLogo(byte[] logoFileContent, String logoFileName, Club club) throws BusinessException;

    void maakNieuweClubAan(String naam, String locatie, String adres, Set<Contact> contactLijst) throws BusinessException;

    void wijzigClub(String naam, String locatie, String adres, Set<Contact> contactLijst, int id) throws BusinessException;

    /**
     * Zoekt een Club op basis van een Account. Dit Account een clubaccount zijn (PermissieNiveau.CLUB)
     * @param account het Account
     * @return de Club gelinkt aan het Account
     * @throws BusinessException als
     * <ul>
     *     <li>Het Account geen clubaccount is</li>
     *     <li>Aan het Account geen Club verbonden is</li>
     *     <li>Aan het Account meerdere Clubs verbonden zijn</li>
     *     <li>Als er iets mis gaat met de data-access</li>
     * </ul>
     */
    Club getClub(Account account) throws BusinessException;

    /**
     * Zoekt een Club op basis van id
     * @param id de id van de Club (int)
     * @return de Club of null als er geen Club bestaat met de meegegeven id
     */
    Club getClub(int id);

    void bewaarAfmetingen(Club club);

    /**
     * [DEZE METHOD GAAT NIET NAAR DE DATABASE. ZE WERKT OP DE IN-MEMORY OBJECTEN] <br />
     * Haalt alle Kampioenschappen uit het verleden of uit de toekomst op van een Club. Een Kampioenschap behoort tot het verleden wanneer [einddatum van het kampioenschap < vandaag]
     * Geeft een lege lijst terug als er geen Kampioenschappen gevonden worden.
     *
     * @param club de Club wiens Kampioenschappen teruggegeven worden
     * @param uitVerleden True voor Kampioenschappen uit het verleden. False voor Kampioenschappen uit de toekomst.
     * @return
     */
    List<Kampioenschap> kampioenschappenVan(Club club, boolean uitVerleden);

    void verwijderContact(Club club, Contact contact);

    void verwijderClub(Club club) throws BusinessException;

    List<Club> getAllClubs();

    Club addSponsor(Sponsor sponsor, Club club);

    void save(Club club) throws BusinessException;


}
