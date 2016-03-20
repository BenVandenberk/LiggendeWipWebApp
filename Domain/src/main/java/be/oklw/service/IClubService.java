package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.*;

import javax.ejb.Local;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Local
public interface IClubService {

    void veranderClubLogo(byte[] fileContent, String fileName, Club club) throws IOException;

    void maakNieuweClubAan(String naam, String locatie, String adres, Set<Contact> contactLijst) throws BusinessException;

    void wijzigClub(String naam, String locatie, String adres, Set<Contact> contactLijst, int id) throws BusinessException;

    Club getClub(Account account);

    Club getClub(int id);

    void bewaarAfmetingen(Club club);

    void dummyKampioenschappen();

    List<Kampioenschap> kampioenschappenVan(Club club, boolean uitVerleden);

    void verwijderContact(Club club, Contact contact);

    void verwijderClub(Club club) throws BusinessException;

    List<Club> getAllClubs();

    //List<Contact> getNieuweContactLijst(Club club);

    Club addSponsor(Sponsor sponsor, Club club);

    Club loadSponsors(Club club);

    void save(Club club) throws BusinessException;

    List<Lid> ledenVanClub(Club club);
}
