package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Evenement;
import be.oklw.util.Datum;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IEvenementService {

    List<Evenement> getAlleEvenementen();

    List<Evenement> getAlleEvenementenToekomst();

    void maakNieuwEvenementAan(String naam, String clubNaam, Datum start, Datum eind, String locatie, String omschrijving) throws BusinessException;

    void maakNieuwKampioenschapAan(String naam, String clubNaam, Datum start, Datum eind) throws BusinessException;

    void verwijderEvenement(Evenement evenement) throws BusinessException;
}
