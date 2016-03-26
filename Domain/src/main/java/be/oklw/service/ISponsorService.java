package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.*;

import javax.ejb.Local;

import java.util.List;

@Local
public interface ISponsorService {

    void saveSponsor(Sponsor sponsor);

    void removeSponsor(Club club, int sponsorId) throws BusinessException;

    List<Sponsor> getSponsorsVanExcludeVan(Club club, Kampioenschap kampioenschap);

    void voegSponsorToeAan(int sponsorId, Kampioenschap kampioenschap);

    /**
     * Verwijdert een clubsponsor van een Kampioenschap
     * @param sponsorId de id van de Sponsor (int)
     * @param kampioenschap het Kampioenschap
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    void verwijderSponsorVan(int sponsorId, Kampioenschap kampioenschap) throws BusinessException;

    void nieuweSiteSponsor(SiteSponsor siteSponsor, SysteemAccount systeemAccount);

    void saveSiteSponsor(SiteSponsor siteSponsor);

    void removeSiteSponsor(SysteemAccount systeemAccount, int siteSponsorId);

    List<SiteSponsor> getSiteSponsors();
}
