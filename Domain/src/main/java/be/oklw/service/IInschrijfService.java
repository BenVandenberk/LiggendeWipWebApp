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

    /**
     * Geeft alle Inschrijvingen van de meegegeven Club terug voor toekomstige Toernooien.
     * @param club de Club waarvoor Inschrijvingen opgehaald worden
     * @return een List &lt;Inschrijving&gt; die leeg kan zijn
     */
    List<Inschrijving> getInschrijvingenVoor(Club club);

    /**
     * Opent inschrijvingen van het toernooi en stuurt een mail naar alle clubcontacten
     * @param toernooi het Toernooi waarvan de inschrijvingen geopend worden
     * @return een message met info over de verstuurde mails
     * @throws BusinessException als er iets mis gaat met de data-access of met het versturen van de mails
     */
    String openInschrijvingen(Toernooi toernooi) throws BusinessException;

    /**
     * Save een bestaande Inschrijving in de database ('merge')
     * @param inschrijving de te saven Inschrijving
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    void save(Inschrijving inschrijving) throws BusinessException;
}
