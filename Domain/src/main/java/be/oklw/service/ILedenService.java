package be.oklw.service;

import be.oklw.hulp.LidInschrijving;
import be.oklw.model.Club;
import be.oklw.model.Lid;
import be.oklw.model.Ploeg;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ILedenService {

    /**
     * Haalt alle leden van een Club op
     * @param club de Club
     * @return een List&lt;Lid&gt; die leeg kan zijn
     */
    List<Lid> ledenVanClub(Club club);

    /**
     * Zoekt een Lid op basis van id
     * @param id de id van het Lid (int)
     * @return het Lid of null als er geen Lid bestaat met de meegegeven id
     */
    Lid getLid(int id);

    /**
     * Haalt alle Leden op
     * @return een List&lt;Lid&gt; die leeg kan zijn
     */
    List<Lid> alleLeden();

    /**
     * Haalt alle inschrijvingen van een Lid voor toekomstige Toernooien op. Deze method vult een lijst met hulpobjecten van het type LidInschrijving
     * @param lid het Lid waarvoor Inschrijvingen gezocht worden
     * @return een List&lt;LidInschrijving&gt;
     */
    List<LidInschrijving> alleToekomstigeInschrijvingen(Lid lid);
}
