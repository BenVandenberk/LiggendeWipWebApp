package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Foto;
import be.oklw.model.Kampioenschap;
import be.oklw.model.Toernooi;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IKampioenschapService {

    /**
     * Zoekt een Kampioenschap op basis van zijn id
     *
     * @param id de id van het Kampioenschap (int)
     * @return het Kampioenschap of null als er geen Kampioenschap is met de meegegeven id
     */
    Kampioenschap getKampioenschap(int id);

    /**
     * Save een bestaand Kampioenschap in de database ('merge')
     * @param kampioenschap het te saven Kampioenschap
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    void opslaan(Kampioenschap kampioenschap) throws BusinessException;

    /**
     * Voegt een nieuw Toernooi toe aan een Kampioenschap.
     * @param toernooi het Toernooi om toe te voegen
     * @param kampioenschap het Kampioenschap waar het Toernooi aan toegevoegd wordt
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    void nieuwToernooi(Toernooi toernooi, Kampioenschap kampioenschap) throws BusinessException;

    /**
     * Save een bestaand Toernooi in de database ('merge')
     * @param toernooi het te saven Toernooi
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    void opslaanToernooi(Toernooi toernooi) throws BusinessException;

    /**
     * Verwijdert een Toernooi uit een Kampioenschap
     *
     * @param toernooi het te verwijderen Toernooi
     * @param kampioenschap het Kampioenschap waaruit het Toernooi verwijderd wordt
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    void verwijderToernooi(Toernooi toernooi, Kampioenschap kampioenschap) throws BusinessException;

    Kampioenschap addFoto(byte[] fileContent, String fileName, Kampioenschap kampioenschap) throws BusinessException;

    /**
     * Geeft alle Fotos van een Kampioenschap terug
     *
     * @param kampioenschap het Kampioenschap
     * @return een List&lt;Foto&gt;
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    List<Foto> getFotos(Kampioenschap kampioenschap) throws BusinessException;

    /**
     * Geeft alle Fotos van een Kampioenschap terug
     *
     * @param kampioenschapId de id van het Kampioenschap
     * @return een List&lt;Foto&gt;
     * @throws BusinessException als er iets misloopt bij de data-access (entity state, transaction state, ...)
     */
    List<Foto> getFotos(int kampioenschapId) throws BusinessException;

    void verwijderFoto(int fotoId, Kampioenschap kampioenschap);

    void saveFoto(Foto foto);

    /**
     * Geeft een lijst terug van alle Kampioenschappen waar Foto's aan verbonden zijn. Geeft een lege lijst terug als er geen Kampioenschappen gevonden worden.
     *
     * @return een List&lt;Kampioenschap&gt;
     */
    List<Kampioenschap> getKampioenschappenMetFotos();

    /**
     * Geeft een lijst terug van alle Kampioenschappen uit het verleden of in de toekomst. Een Kampioenschap behoort tot het verleden wanneer [einddatum van het kampioenschap < vandaag]
     * Geeft een lege lijst terug als er geen Kampioenschappen gevonden worden.
     *
     * @param uitVerleden true voor Kampioenschappen uit het verleden, false voor Kampioenschappen uit de toekomst.
     * @return een List&lt;Kampioenschap&gt;
     */
    List<Kampioenschap> getKampioenschappen(boolean uitVerleden);

    List<Kampioenschap> getKampioenschapenInschrijvenMogelijk();
}
