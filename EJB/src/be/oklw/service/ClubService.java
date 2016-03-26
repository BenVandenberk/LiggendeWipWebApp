package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.*;
import be.oklw.util.Datum;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
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
    public void veranderClubLogo(byte[] fileContent, String fileName, Club club) throws IOException {
        String logoPad = fileService.upload(fileContent, fileName, "clublogos");

        club.setLogoBreedte(120);
        club.setLogoHoogte(120);
        club.setLogoPad(logoPad);
        entityManager.merge(club);
        entityManager.flush();
    }

    @Override
    public void maakNieuweClubAan(String naam, String locatie, String adres, Set<Contact> contactLijst) throws BusinessException {
        Club club = new Club(naam, locatie);
        if (adres != "") {
            club.setAdres(adres);
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
            Club club = entityManager.createQuery("select c from Club c where c.account.id = :accId", Club.class)
                    .setParameter("accId", account.getId())
                    .getSingleResult();

            // Sponsors laden wegens lazy loading
            club.getSponsors().size();

            return club;
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
        List<Club> allClubs = (List<Club>) entityManager.createQuery("select c from Club c").getResultList();
        return allClubs;
    }

    @Override
    public void bewaarAfmetingen(Club club) {
        entityManager.merge(club);
        entityManager.flush();
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
    public void verwijderContact(Club club, Contact contact) {
        Contact teVerwijderenContact = entityManager.find(Contact.class, contact.getId());

        if(club!=null){
        Club selectedClub = entityManager.find(Club.class, club.getId());
        if(selectedClub!=null){
            Set<Contact> contactList = selectedClub.getContacten();
            if(contactList != null){
                Iterator<Contact> i = contactList.iterator();
                while(i.hasNext()){
                    Contact c = i.next();
                    if (c.getId() == teVerwijderenContact.getId()){
                        i.remove();
                    }
                }
            }
            selectedClub.setContacten(contactList);
            entityManager.merge(selectedClub);
        }
        }

    }

    @Override
    public void verwijderClub(Club club) throws BusinessException {
        Club teVerwijderenClub = entityManager.find(Club.class, club.getId());
        if (teVerwijderenClub.getEvenementen().isEmpty()) {
            Set<Contact> contactList = teVerwijderenClub.getContacten();
            Iterator<Contact> i = contactList.iterator();
            while (i.hasNext()) {
                Contact c = i.next();
                i.remove();
                entityManager.remove(c);
                entityManager.flush();
            }

            entityManager.remove(teVerwijderenClub);
            entityManager.flush();
        } else {
            throw new BusinessException("Deze club kan niet verwijderd worden");
        }
    }

    @Override
    public Club addSponsor(Sponsor sponsor, Club club) {
//        entityManager.merge(club);
//        club.getSponsors().size();
//        club.addSponsor(sponsor);
//        entityManager.merge(club);
//        entityManager.flush();
//        Club metNieuweSponsor = entityManager.find(Club.class, club.getId());
//        metNieuweSponsor.getSponsors().size();
//        return metNieuweSponsor;

        club.addSponsor(sponsor);
        entityManager.merge(club);
        return club;
    }

    @Override
    public void save(Club club) throws BusinessException {
        try {
            entityManager.merge(club);
        } catch (Exception ex) {
            throw new BusinessException(String.format("Er ging iets mis: %s", ex.getMessage()));
        }
    }


}
