package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Club;
import be.oklw.model.Kampioenschap;
import be.oklw.model.Sponsor;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface ISponsorService {

    void saveSponsor(Sponsor sponsor);

    void removeSponsor(Club club, int sponsorId) throws BusinessException;

    List<Sponsor> getSponsorsVanExcludeVan(Club club, Kampioenschap kampioenschap);

    void voegSponsorToeAan(int sponsorId, Kampioenschap kampioenschap);

    void verwijderSponsorVan(int sponsorId, Kampioenschap kampioenschap);
}
