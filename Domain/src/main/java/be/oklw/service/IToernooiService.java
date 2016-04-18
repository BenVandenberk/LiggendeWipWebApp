package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Inschrijving;
import be.oklw.model.Toernooi;

import javax.ejb.Local;

@Local
public interface IToernooiService {

    /**
     * Zoekt een Toernooi op basis van id.
     * @param id de id (int)
     * @return een Toernooi object als er een bestaat met de meegegeven id
     * @throws BusinessException als er geen Toernooi gevonden is met de meegegeven id
     */
    Toernooi getToernooi(int id) throws BusinessException;

    /**
     * Save een bestaand toernooi in de database ('merge')
     * @param toernooi het te saven Toernooi
     * @return het gesavede Toernooi
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    Toernooi save(Toernooi toernooi) throws BusinessException;

    /**
     * Save een Toernooi waar wijzigigen aan de Inschrijvingen zijn aangebracht. Deze method controleert of de Inschrijvingen geldig zijn.
     * @param toernooi het te saven Toernooi
     * @param inschrijving de Inschrijving die gecontroleerd moet worden. GEEF NULL MEE ALS ALLE INSCHRIJVINGEN GECONTROLEERD MOETEN WORDEN.
     * @return het gesavede toernooi
     * @throws BusinessException als de Inschrijving(en) niet geldig zijn of als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    Toernooi saveToernooiInschrijvingen(Toernooi toernooi, Inschrijving inschrijving) throws BusinessException;

    /**
     * Loopt over alle toernooien en kijkt of de inschrijfdeadline verstreken is. Als dit het geval is, wordt de toernooistatus geüpdate naar 'Inschrijvingen Afgesloten'
     */
    void updateInschrijfStatusAlleToernooien();
}
