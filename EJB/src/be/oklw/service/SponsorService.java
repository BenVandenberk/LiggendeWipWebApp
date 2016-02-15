package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Club;
import be.oklw.model.Kampioenschap;
import be.oklw.model.Sponsor;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Remote
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SponsorService implements ISponsorService {

    @PersistenceContext(unitName = "myunitname")
    EntityManager entityManager;

    @Override
    public void saveSponsor(Sponsor sponsor) {
        entityManager.merge(sponsor);
        entityManager.flush();
    }

    @Override
    public void removeSponsor(Club club, int sponsorId) throws BusinessException {
        List results = entityManager.createNativeQuery("SELECT * FROM Evenement_Sponsor e WHERE e.sponsors_id = :sponsid")
                                        .setParameter("sponsid", sponsorId)
                                        .getResultList();
        if (results.size() > 0) {
            throw new BusinessException("Deze sponsor kan niet verwijderd worden omdat ze reeds gekoppeld is aan een Evenement");
        }

        Sponsor teVerwijderen = entityManager.find(Sponsor.class, sponsorId);
        club.removeSponsor(teVerwijderen);
        entityManager.remove(teVerwijderen);
        entityManager.merge(club);
        entityManager.flush();
    }

    @Override
    public List<Sponsor> getSponsorsVanExcludeVan(Club club, Kampioenschap kampioenschap) {
        List<Sponsor> clubSponsors = entityManager.createQuery("select s from Sponsor s WHERE s.club.id = :clubId", Sponsor.class)
                                              .setParameter("clubId", club.getId())
                                              .getResultList();
        List<Sponsor> kampioenschapSponsors = kampioenschap.getSponsors();
        return clubSponsors.stream().filter(s -> !kampioenschapSponsors.contains(s)).collect(Collectors.toList());
    }

    @Override
    public void voegSponsorToeAan(int sponsorId, Kampioenschap kampioenschap) {
        Sponsor toeTeVoegen = entityManager.find(Sponsor.class, sponsorId);
        kampioenschap.addSponsor(toeTeVoegen);
        entityManager.merge(kampioenschap);
        entityManager.flush();
    }

    @Override
    public void verwijderSponsorVan(int sponsorId, Kampioenschap kampioenschap) {
        kampioenschap.removeSponsor(sponsorId);
        entityManager.merge(kampioenschap);
        entityManager.flush();
    }
}
