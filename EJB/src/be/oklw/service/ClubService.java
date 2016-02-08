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
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Remote
@TransactionManagement(TransactionManagementType.CONTAINER)
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
    public void maakNieuweClubAan(String naam, String locatie, String adres) throws BusinessException{
        Club club = new Club(naam, locatie);
        if (adres != ""){club.setAdres(adres);}
        entityManager.persist(club);
        entityManager.flush();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Club getClub(Account account) {
        Club club = (Club)entityManager.createQuery("select c from Club c where c.account.id = :accId")
                                        .setParameter("accId", account.getId())
                                        .getSingleResult();
        return club;
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





        Toernooi toer1 = new Toernooi();
        toer1.setNaam("De Hoef Duo Toernooi");
        toer1.setDatum(new Datum(1, 1, 2017));
        toer1.setPersonenPerPloeg(2);
        toer1.setInlegPerPloeg(BigDecimal.TEN);
        toer1.setAantalWippen(24);
        toer1.setHeeftMaaltijd(true);
        toer1.setCateringInfo("MENU");
        toer1.setOmschrijving("TOER 1");
        toer1.setMaximumAantalPloegen(48);
        toer1.setKampioenschap(kamp3);
        entityManager.persist(toer1);
//        kamp3.addToernooi(toer1);

        entityManager.flush();






//        Toernooi toer2 = new Toernooi();
//        toer2.setNaam("De Hoef Ploegen Toernooi");
//        toer2.setDatum(new Datum(1, 1, 2017));
//        toer2.setPersonenPerPloeg(4);
//        toer2.setInlegPerPloeg(BigDecimal.TEN);
//        toer2.setAantalWippen(24);
//        toer2.setHeeftMaaltijd(true);
//        toer2.setCateringInfo("MENU");
//        toer2.setOmschrijving("TOER 1");
//        toer2.setMaximumAantalPloegen(20);

//        entityManager.persist(toer1);
//        entityManager.persist(toer2);

//        kamp3.addToernooi(toer1);
//        kamp3.addToernooi(toer2);
//        entityManager.flush();
    }
}
