package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Foto;
import be.oklw.model.Kampioenschap;
import be.oklw.model.Toernooi;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IKampioenschapService {

    Kampioenschap getKampioenschap(int id);

    void opslaan(Kampioenschap kampioenschap);

    void nieuwToernooi(Toernooi toernooi, Kampioenschap kampioenschap);

    void opslaanToernooi(Toernooi toernooi);

    void verwijderToernooi(Toernooi toernooi, Kampioenschap kampioenschap) throws BusinessException;

    Kampioenschap addFoto(byte[] fileContent, String fileName, Kampioenschap kampioenschap) throws BusinessException;

    List<Foto> getFotos(Kampioenschap kampioenschap);

    List<Foto> getFotos(int kampioenschapId);

    void verwijderFoto(int fotoId, Kampioenschap kampioenschap);

    void saveFoto(Foto foto);

    List<Kampioenschap> getKampioenschappenMetFotos();

    List<Kampioenschap> getKampioenschappen(boolean uitVerleden);

    List<Kampioenschap> getKampioenschapenInschrijvenMogelijk();
}
