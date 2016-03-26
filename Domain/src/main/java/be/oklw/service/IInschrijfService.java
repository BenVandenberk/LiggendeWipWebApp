package be.oklw.service;

import be.oklw.exception.BusinessException;
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
     * @throws BusinessException als er iets mis gaat met de data-access of met het versturen van de mails
     */
    String openInschrijvingen(Toernooi toernooi) throws BusinessException;

    void save(Inschrijving inschrijving) throws BusinessException;
}
