package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Club;

import javax.ejb.Remote;
import java.io.IOException;

@Remote
public interface IClubService {

    void veranderClubLogo(byte[] fileContent, String fileName, Club club) throws IOException;

    void maakNieuweClubAan(String naam, String locatie, String adres) throws BusinessException;
}
