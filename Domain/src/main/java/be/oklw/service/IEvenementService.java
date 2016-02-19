package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Evenement;
import be.oklw.model.Kampioenschap;
import be.oklw.util.Datum;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface IEvenementService {

    List<Evenement> getAlleEvenementen();

    void maakNieuwEvenementAan(String naam, String clubNaam, Datum start, Datum eind, String locatie, String omschrijving) throws BusinessException;

    void maakNieuwKampioenschapAan(String naam, String clubNaam, Datum start, Datum eind) throws BusinessException;

    void verwijderEvenement(Evenement evenement) throws BusinessException;
}
