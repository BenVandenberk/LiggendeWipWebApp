package be.oklw;

import be.oklw.model.Club;
import be.oklw.service.IClubService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@ManagedBean
public class SysteemClubBeheerController {

    @EJB
    IClubService clubService;

    public List<Club> getClubLijst() {
        return clubService.getAllClubs();
    }

    public void verwijderClub(Club club){
        clubService.verwijderClub(club);
    }

    public String naarNieuweClub(){
        return "to_nieuwe_club";
    }

}
