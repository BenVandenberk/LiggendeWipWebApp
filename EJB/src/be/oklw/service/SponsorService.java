package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.*;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SponsorService implements ISponsorService {

    @PersistenceContext
    EntityManager entityManager;

    @EJB
    IFileService fileService;

    @Override
    public void saveSponsor(Sponsor sponsor) throws BusinessException {
        try {
            entityManager.merge(sponsor);
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public Club removeSponsor(Club club, int sponsorId) throws BusinessException {
        List results = entityManager.createNativeQuery("SELECT * FROM Evenement_Sponsor e WHERE e.sponsors_id = :sponsid")
                .setParameter("sponsid", sponsorId)
                .getResultList();
        if (results.size() > 0) {
            throw new BusinessException("Deze sponsor kan niet verwijderd worden omdat ze reeds gekoppeld is aan een Evenement");
        }

        try {
            Sponsor teVerwijderen = entityManager.find(Sponsor.class, sponsorId);
            if (teVerwijderen != null) {
                club.removeSponsor(teVerwijderen);
                Club geupdate = entityManager.merge(club);

                fileService.delete(
                        teVerwijderen.getLogoFileName(),
                        Sponsor.getRelativePad()
                );

                return geupdate;
            }

            return club;
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
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
    public void voegSponsorToeAan(int sponsorId, Kampioenschap kampioenschap) throws BusinessException {
        try {
            Sponsor toeTeVoegen = entityManager.find(Sponsor.class, sponsorId);
            kampioenschap.addSponsor(toeTeVoegen);
            entityManager.merge(kampioenschap);
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public void verwijderSponsorVan(int sponsorId, Kampioenschap kampioenschap) throws BusinessException {
        try {
            kampioenschap.removeSponsor(sponsorId);
            entityManager.merge(kampioenschap);
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public void nieuweSiteSponsor(SiteSponsor siteSponsor, SysteemAccount systeemAccount) throws BusinessException {
        try {
            systeemAccount.addSiteSponsor(siteSponsor);
            entityManager.merge(systeemAccount);
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public void saveSiteSponsor(SiteSponsor siteSponsor) throws BusinessException {
        try {
            entityManager.merge(siteSponsor);
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public void removeSiteSponsor(SysteemAccount systeemAccount, int siteSponsorId) throws BusinessException {
        try {
            Optional<SiteSponsor> siteSponsorOpt = systeemAccount.getSiteSponsors().stream().filter(siteSponsor -> siteSponsor.getId() == siteSponsorId).findFirst();

            if (siteSponsorOpt.isPresent()) {
                systeemAccount.removeSiteSponsor(siteSponsorId);
                entityManager.merge(systeemAccount);

                if (!siteSponsorOpt.get().isLogoOnline()) { // Alleen verwijderen van filesysteem als logo niet online staat
                    fileService.delete(
                            siteSponsorOpt.get().getLogoFileName(),
                            SiteSponsor.getRelativePad()
                    );
                }
            }
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public List<SiteSponsor> getSiteSponsors() {
        List<SiteSponsor> siteSponsors = entityManager.createQuery("SELECT s FROM SiteSponsor s", SiteSponsor.class).getResultList();
        int aantalSiteSponsors = siteSponsors.size();

        Random random = new Random();
        List<SiteSponsor> siteSponsorsWilleukeurigeVolgorde = new ArrayList<>();
        int randomIndex;

        for (int i = 0; i < aantalSiteSponsors; i++) {
            randomIndex = random.nextInt(aantalSiteSponsors - i);
            siteSponsorsWilleukeurigeVolgorde.add(
              siteSponsors.get(randomIndex)
            );
            siteSponsors.remove(randomIndex);
        }

        return siteSponsorsWilleukeurigeVolgorde;
    }

    @Override
    public Sponsor getSponsor(int id) {
        return entityManager.find(Sponsor.class, id);
    }
}
