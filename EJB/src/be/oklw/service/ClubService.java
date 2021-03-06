package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.*;
import be.oklw.util.Datum;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ClubService implements IClubService {

    @EJB
    IFileService fileService;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void veranderClubLogo(byte[] logoFileContent, String logoFileName, Club club) throws BusinessException {
        try {

            // VERWIJDEREN OUDE LOGO VAN FILESYSTEEM
            if (StringUtils.isNotBlank(club.getLogoFileName())) {
                fileService.delete(
                        club.getLogoFileName(),
                        Club.getRelativePad()
                );
                club.setLogoFileName("");
            }

            // UPLOADEN EN INSTELLEN NIEUWE LOGO
            String logoPad = fileService.upload(logoFileContent, logoFileName, "clublogos");

            club.setLogoBreedte(120);
            club.setLogoHoogte(120);
            club.setLogoFileName(logoPad);
            entityManager.merge(club);
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public void maakNieuweClubAan(String naam, String locatie, String adres, Set<Contact> contactLijst) throws BusinessException {
        Club club = new Club(naam, locatie);
        club.setAdres(adres);

        for (Club c : getAllClubs()){
            if (c.getNaam().equals(naam)){
                throw new BusinessException("De opgegeven clubnaam bestaat al, gelieve een andere te kiezen");
            }
        }

        entityManager.persist(club);

        if (contactLijst.size() != 0) {
            for (Contact c : contactLijst) {
                club.addContact(c);
            }
            entityManager.merge(club);
        }
    }

    @Override
    public void wijzigClub(String naam, String locatie, String adres, Set<Contact> contactLijst, int id) throws BusinessException {

        for (Club c : getAllClubs()){
            if (c.getNaam().equals(naam) && c.getId()!= id){
                throw new BusinessException("De opgegeven clubnaam bestaat al, gelieve een andere te kiezen");
            }
        }

        Club club = entityManager.find(Club.class, id);
        club.setAdres(adres);
        club.setLocatie(locatie);
        club.setNaam(naam);
        club.setContacten(contactLijst);
        entityManager.merge(club);
    }

    @Override
    public Club getClub(Account account) throws BusinessException {

        if (!account.getPermissieNiveau().equals(PermissieNiveau.CLUB)) {
            throw new BusinessException("Het meegegeven Account is geen ClubAccount");
        }

        try {
            return entityManager.createQuery("select c from Club c where c.account.id = :accId", Club.class)
                    .setParameter("accId", account.getId())
                    .getSingleResult();
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public Club getClub(int id) {
        return entityManager.find(Club.class, id);
    }


    @Override
    public List<Club> getAllClubs() {
        return entityManager.createQuery("select c from Club c", Club.class).getResultList();
    }

    @Override
    public List<Kampioenschap> kampioenschappenVan(Club club, boolean uitVerleden) {
        List<Kampioenschap> kampioenschappen = new ArrayList<Kampioenschap>();

        for (Evenement e : club.getEvenementen()) {
            if (e instanceof Kampioenschap) {
                kampioenschappen.add((Kampioenschap) e);
            }
        }


        Datum vandaag = new Datum();
        List<Kampioenschap> result;
        if (uitVerleden) {
            result = kampioenschappen.stream().filter(k -> k.getEindDatum().compareTo(vandaag) < 0).collect(Collectors.toList());
        } else {
            result = kampioenschappen.stream().filter(k -> k.getEindDatum().compareTo(vandaag) >= 0).collect(Collectors.toList());
        }

        return result;
    }

    @Override
    public void verwijderContact(Club club, Contact contact) throws BusinessException {
        try{
        Contact teVerwijderenContact = entityManager.find(Contact.class, contact.getId());

        if (club != null) {
            Club selectedClub = entityManager.find(Club.class, club.getId());
            if (selectedClub != null) {
                Set<Contact> contactList = selectedClub.getContacten();
                if (contactList != null) {
                    Iterator<Contact> i = contactList.iterator();
                    while (i.hasNext()) {
                        Contact c = i.next();
                        if (c.getId() == teVerwijderenContact.getId()) {
                            i.remove();
                        }
                    }
                }
                selectedClub.setContacten(contactList);
                entityManager.merge(selectedClub);
            }
        }
        } catch (Exception ex) {
            throw new BusinessException(String.format("Er liep iets mis: %s", ex.getMessage()));
        }

    }

    @Override
    public void verwijderClub(Club club) throws BusinessException {
        try{
        Club teVerwijderenClub = entityManager.find(Club.class, club.getId());
        if (teVerwijderenClub.getEvenementen().isEmpty()) {
            Set<Contact> contactList = teVerwijderenClub.getContacten();
            Iterator<Contact> i = contactList.iterator();
            while (i.hasNext()) {
                Contact c = i.next();
                i.remove();
                entityManager.remove(c);
            }

            entityManager.remove(teVerwijderenClub);
        } else {
            throw new BusinessException("Deze club kan niet verwijderd worden");
        }
        } catch (Exception ex) {
            throw new BusinessException(String.format("Er liep iets mis: %s", ex.getMessage()));
        }
    }

    @Override
    public Club addSponsor(Sponsor sponsor, Club club) throws BusinessException {
        try {
            club.addSponsor(sponsor);
            return entityManager.merge(club);
        } catch (Exception ex) {
            throw new BusinessException(String.format("Er liep iets mis: %s", ex.getMessage()));
        }
    }

    @Override
    public void save(Club club) throws BusinessException {
        try {
            entityManager.merge(club);
        } catch (PersistenceException pEx) {
            if (pEx.getCause() instanceof ConstraintViolationException) { // Bij saven van een niet-unieke registratiecode
                throw new BusinessException("Deze registratiecode is al eens gebruikt. Kies een andere code.");
            }
            throw new BusinessException(String.format("Er liep iets mis: %s", pEx.getMessage()));
        } catch (Exception ex) {
            throw new BusinessException(String.format("Er liep iets mis: %s", ex.getMessage()));
        }
    }


}
