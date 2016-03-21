package be.oklw.service;

import be.oklw.model.Club;
import be.oklw.model.Lid;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ILedenService {

    List<Lid> ledenVanClub(Club club);

    Lid getLid(int id);

    List<Lid> alleLeden();
}
