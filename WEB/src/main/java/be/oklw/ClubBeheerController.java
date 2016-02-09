package be.oklw;

import be.oklw.model.Club;
import be.oklw.service.IClubService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;

@ViewScoped
@ManagedBean
public class ClubBeheerController {
    private ArrayList<Club> clubLijst;

    @EJB
    IClubService clubService;

    public ArrayList<Club> getClubLijst() {
        return clubLijst;
    }

    public void setClubLijst(ArrayList<Club> clubLijst) {
        this.clubLijst = clubLijst;
    }

    public void verwijderClub(Club club){
        clubService.verwijderClub(club);
    }

    public String naarNieuweClub(){
        return "to_nieuwe_club";
    }


}
