package be.oklw.service;

import be.oklw.hulp.LidInschrijving;
import be.oklw.model.Club;
import be.oklw.model.Lid;
import be.oklw.model.Ploeg;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ILedenService {

    List<Lid> ledenVanClub(Club club);

    Lid getLid(int id);

    List<Lid> alleLeden();

    List<LidInschrijving> alleToekomstigeInschrijvingen(Lid lid);
}
