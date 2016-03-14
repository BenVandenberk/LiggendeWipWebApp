package be.oklw.service;

import be.oklw.model.Club;
import be.oklw.model.Toernooi;
import be.oklw.model.Inschrijving;

import javax.ejb.Local;

import java.util.List;

@Local
public interface IInschrijfService {

    void verwijderPloeg(int ploegId);

    List<Inschrijving> getInschrijvingenVoor(Club club);

    /**
     * Opent inschrijvingen van het toernooi en stuurt een mail naar alle clubcontacten
     * @param toernooi het Toernooi waarvan de inschrijvingen geopend worden
     * @return een message met info over de verstuurde mails
     */
    String openInschrijvingen(Toernooi toernooi);

    void save(Inschrijving inschrijving);
}
