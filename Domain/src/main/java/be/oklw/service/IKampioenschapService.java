package be.oklw.service;

import be.oklw.model.Kampioenschap;

import javax.ejb.Remote;

@Remote
public interface IKampioenschapService {

    Kampioenschap getKampioenschap(int id);

    void opslaan(Kampioenschap kampioenschap);
}
