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

    Lid getLid(int id);

    /**
     * Haalt alle Leden op
     * @return een List&lt;Lid&gt; die leeg kan zijn
     */
    List<Lid> alleLeden();

    List<LidInschrijving> alleToekomstigeInschrijvingen(Lid lid);
}
