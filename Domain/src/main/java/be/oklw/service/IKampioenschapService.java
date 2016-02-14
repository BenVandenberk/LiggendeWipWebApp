package be.oklw.service;

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

}
