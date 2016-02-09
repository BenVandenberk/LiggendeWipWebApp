package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.model.Contact;
import be.oklw.model.Kampioenschap;

import javax.ejb.Remote;
import java.io.IOException;
import java.util.List;

@Remote
public interface IClubService {

    void veranderClubLogo(byte[] fileContent, String fileName, Club club) throws IOException;

    void maakNieuweClubAan(String naam, String locatie, String adres) throws BusinessException;

    Club getClub(Account account);

    void bewaarAfmetingen(Club club);

    void dummyKampioenschappen();

    List<Kampioenschap> kampioenschappenVan(Club club, boolean uitVerleden);

    void verwijderContact(Club club, Contact contact);

    void verwijderClub(Club club);
}
