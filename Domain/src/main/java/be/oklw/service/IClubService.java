package be.oklw.service;

import be.oklw.model.Account;
import be.oklw.model.Club;

import javax.ejb.Remote;
import java.io.IOException;

@Remote
public interface IClubService {

    void veranderClubLogo(byte[] fileContent, String fileName, Club club) throws IOException;

    Club getClub(Account account);

    void bewaarAfmetingen(Club club);
}
