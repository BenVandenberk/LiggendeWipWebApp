package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Club;
import be.oklw.model.*;
import be.oklw.util.Datum;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Remote
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ClubService implements IClubService {

    @EJB
    IFileService fileService;

    @PersistenceContext
    EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void veranderClubLogo(byte[] fileContent, String fileName, Club club) throws IOException {
        String logoPad = fileService.upload(fileContent, fileName, "clublogos");

        club.setLogoBreedte(120);
        club.setLogoHoogte(120);
        club.setLogoPad(logoPad);
        entityManager.merge(club);
        entityManager.flush();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void maakNieuweClubAan(String naam, String locatie, String adres, List<Contact> contactLijst) throws BusinessException{
        Club club = new Club(naam, locatie);
        if (adres != ""){club.setAdres(adres);}
        if (contactLijst.size()!=0){
            for (Contact c : contactLijst){
                club.addContact(c);
            }
        }
        entityManager.merge(club);
        entityManager.flush();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void wijzigClub(String naam, String locatie, String adres, List<Contact> contactLijst, int id) throws BusinessException{

        Club club = (Club)entityManager.createQuery("SELECT c FROM Club c WHERE c.id = :selId")
                .setParameter("selId", id)
                .getSingleResult();
        club.setAdres(adres);
        club.setLocatie(locatie);
        club.setNaam(naam);
        club.setContacten(contactLijst);

        entityManager.merge(club);
        entityManager.flush();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Club getClub(Account account) {
        Club club = (Club)entityManager.createQuery("select c from Club c where c.account.id = :accId")
                                        .setParameter("accId", account.getId())
                                        .getSingleResult();
        club.getSponsors().size();
        return club;
    }

    @Override
    public List<Club> getAllClubs(){
        List<Club> allClubs = (List<Club>)entityManager.createQuery("select c from Club c").getResultList();
        return allClubs;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void bewaarAfmetingen(Club club) {
        entityManager.merge(club);
        entityManager.flush();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public List<Kampioenschap> kampioenschappenVan(Club club, boolean uitVerleden) {
        List<Kampioenschap> kampioenschappen = new ArrayList<Kampioenschap>();

        for (Evenement e : club.getEvenementen()) {
            if (e instanceof Kampioenschap) {
                kampioenschappen.add((Kampioenschap)e);
            }
        }


        Datum vandaag = new Datum();
        List<Kampioenschap> result;
        if (uitVerleden) {
            result = kampioenschappen.stream().filter(k -> k.getEindDatum().compareTo(vandaag) <= 0).collect(Collectors.toList());
        } else {
            result = kampioenschappen.stream().filter(k -> k.getEindDatum().compareTo(vandaag) > 0).collect(Collectors.toList());
        }

        return result;
    }

    @Override
    public void dummyKampioenschappen() {
        Club club = entityManager.find(Club.class, 1);
        Kampioenschap kamp1 =  club.maakKampioenschap("21e Grote prijs De Hoef", new Datum(1, 1, 2015), new Datum(2, 1, 2015));
        Kampioenschap kamp2 = club.maakKampioenschap("22e Grote prijs De Hoef", new Datum(1, 1, 2016), new Datum(2, 1, 2016));
        Kampioenschap kamp3 = club.maakKampioenschap("23e Grote prijs De Hoef", new Datum(1, 1, 2017), new Datum(2, 1, 2017));

        kamp3.setContact("Contact HIER");
        kamp3.setOmschrijving("Omschrijving HIER");
        kamp3.setOvernachtingInfo("Hotel zusenzo");
        kamp3.setRekeningnummer("BE8700157382214");
    }

    @Override
    public void verwijderContact(Club club, Contact contact){
        Contact teVerwijderenContact = (Contact)entityManager.createQuery("select c from Contact c where c.id = :contactId")
                .setParameter("contactId", contact.getId())
                .getSingleResult();
        if(club!=null){
        Club selectedClub = (Club)entityManager.createQuery("select c from Club c where c.id = :clubId")
                .setParameter("clubId", club.getId())
                .getSingleResult();
        if(selectedClub!=null){
            List<Contact> contactList = selectedClub.getContacten();
            if(contactList != null){
                Iterator<Contact> i = contactList.iterator();
                while(i.hasNext()){
                    Contact c = i.next();
                    if (c.getId() == teVerwijderenContact.getId()){
                        i.remove();
                    }
                }
                entityManager.merge(selectedClub);
                entityManager.flush();
            }
        }
        }
        entityManager.remove(teVerwijderenContact);
        entityManager.flush();
    }

    @Override
    public List<Contact> getNieuweContactLijst(Club club){
        Club nieuweContactLijstClub = (Club)entityManager.createQuery("select c from Club c where c.id = :clubId")
                .setParameter("clubId", club.getId())
                .getSingleResult();
        List<Contact> contactList = nieuweContactLijstClub.getContacten();
        return contactList;
    }

    @Override
    public void verwijderClub(Club club){
        Club teVerwijderenClub = (Club)entityManager.createQuery("select c from Club c where c.id = :clubId")
                .setParameter("clubId", club.getId())
                .getSingleResult();

        List<Contact> contactList = teVerwijderenClub.getContacten();
        Iterator<Contact> i = contactList.iterator();
        while(i.hasNext()){
            Contact c = i.next();
            i.remove();
            entityManager.remove(c);
            entityManager.flush();
        }

        entityManager.remove(teVerwijderenClub);
        entityManager.flush();
    }

    @Override
    public Club addSponsor(Sponsor sponsor, Club club) {
        entityManager.merge(club);
        club.getSponsors().size();
        club.addSponsor(sponsor);
        entityManager.merge(club);
        entityManager.flush();
        Club metNieuweSponsor = entityManager.find(Club.class, club.getId());
        metNieuweSponsor.getSponsors().size();
        return metNieuweSponsor;
    }

    @Override
    public Club loadSponsors(Club club) {
        Club dbClub = entityManager.find(Club.class, club.getId());
        dbClub.getSponsors().size();
        return dbClub;
    }
}
