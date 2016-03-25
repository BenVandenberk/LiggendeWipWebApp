package be.oklw.service;

import be.oklw.exception.BusinessException;
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
}
