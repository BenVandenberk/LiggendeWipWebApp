package be.oklw;

import be.oklw.model.Club;
import be.oklw.service.IClubService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ViewScoped
@ManagedBean
public class SysteemClubBeheerController {

    private Club selectedClub;

    @EJB
    IClubService clubService;

    @ManagedProperty(value = "#{clubController}")
    private ClubController clubController;

    public void setClubController(ClubController clubController) {
        this.clubController = clubController;
    }

    public List<Club> getClubLijst() {
        return clubService.getAllClubs();
    }

    public void verwijderClub(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            clubService.verwijderClub(selectedClub);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Club werd verwijderd", "Club werd verwijderd");
            facesContext.addMessage(null, message);
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            facesContext.addMessage(null, message);
        }
    }

    public Club getSelectedClub() {
        return selectedClub;
    }

    public void setSelectedClub(Club selectedClub) {
        this.selectedClub = selectedClub;
    }

    public String naarGeselecteerdeClub(){
        clubController.reset();
        clubController.setSelectedClub(selectedClub);
        clubController.setShowWijzig(true);
        return "to_nieuwe_club";
    }

    public String naarNieuweClub(){
        clubController.reset();
        clubController.setSelectedClub(null);
        clubController.setShowWijzig(false);
        return "to_nieuwe_club";
    }

}
