package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Foto;
import be.oklw.model.Kampioenschap;
import be.oklw.model.Toernooi;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface IKampioenschapService {

    Kampioenschap getKampioenschap(int id);

    void opslaan(Kampioenschap kampioenschap);

    void nieuwToernooi(Toernooi toernooi, Kampioenschap kampioenschap);

    void opslaanToernooi(Toernooi toernooi);

    Kampioenschap addFoto(byte[] fileContent, String fileName, Kampioenschap kampioenschap) throws BusinessException;

    List<Foto> getFotos(Kampioenschap kampioenschap);

    void verwijderFoto(int fotoId, Kampioenschap kampioenschap);

    void saveFoto(Foto foto);

}
