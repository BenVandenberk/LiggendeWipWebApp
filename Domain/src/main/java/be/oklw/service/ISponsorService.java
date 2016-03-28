package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.*;

import javax.ejb.Local;

import java.util.List;

@Local
public interface ISponsorService {

    /**
     * Save een bestaande Sponsor in de database ('merge')
     * @param sponsor de te saven Sponsor
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    void saveSponsor(Sponsor sponsor) throws BusinessException;

    /**
     * Verwijdert een Sponsor. Een Sponsor kan enkel verwijderd worden als ze nog niet gekoppeld is aan een Kampioenschap.
     * @param club de Club waarvan een Sponsor verwijderd wordt
     * @param sponsorId de id van de te verwijderen Sponsor (int)
     * @return de geüpdatete Club
     * @throws BusinessException als de Sponsor al gekoppeld is aan een Kampioenschap of als er iets mis gaat met de data-access
     */
    Club removeSponsor(Club club, int sponsorId) throws BusinessException;

    /**
     * Haalt alle Sponsors van de meegegeven Club op behalve die Sponsors die al gelinkt zijn aan het meegegeven Kampioenschap.
     * @param club de Club waar Sponsors van opgehaald worden
     * @param kampioenschap het Kampioenschap wiens Sponsors niet meegenomen mogen worden in het resultaat
     * @return een List&lt;Sponsor&gt; die leeg kan zijn
     */
    List<Sponsor> getSponsorsVanExcludeVan(Club club, Kampioenschap kampioenschap);

    /**
     * Koppelt een Sponsor aan een Kampioenschap
     * @param sponsorId de id van de te koppelen Sponsor (int)
     * @param kampioenschap het Kampioenschap waar de Sponsor aan gekoppeld wordt
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    void voegSponsorToeAan(int sponsorId, Kampioenschap kampioenschap) throws BusinessException;

    /**
     * Verwijdert een clubsponsor van een Kampioenschap
     * @param sponsorId de id van de Sponsor (int)
     * @param kampioenschap het Kampioenschap
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    void verwijderSponsorVan(int sponsorId, Kampioenschap kampioenschap) throws BusinessException;

    /**
     * Voegt een SiteSponsor toe
     * @param siteSponsor de toe te voegen SiteSponsor
     * @param systeemAccount het SysteemAccount
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    void nieuweSiteSponsor(SiteSponsor siteSponsor, SysteemAccount systeemAccount) throws BusinessException;

    /**
     * Save een bestaande SiteSponsor in de database ('merge')
     * @param siteSponsor de te saven SiteSponsor
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    void saveSiteSponsor(SiteSponsor siteSponsor) throws BusinessException;

    /**
     * Verwijdert een SiteSponsor
     * @param systeemAccount het SysteemAccount
     * @param siteSponsorId de id van de te verwijderen SiteSponsor (int)
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    void removeSiteSponsor(SysteemAccount systeemAccount, int siteSponsorId) throws BusinessException;

    /**
     * Haalt alle SiteSponsors op
     * @return een List&lt;SiteSponsor&gt;
     */
    List<SiteSponsor> getSiteSponsors();

    /**
     * Zoekt een Sponsor op basis van id
     * @param id de id van de Sponsor (int)
     * @return de Sponsor of null als er geen Sponsor met de meegegeven id bestaat
     */
    Sponsor getSponsor(int id);
}
