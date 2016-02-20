package be.oklw;

import be.oklw.model.Club;
import be.oklw.model.Evenement;
import be.oklw.model.Kampioenschap;
import be.oklw.service.IClubService;
import be.oklw.service.IEvenementService;
import be.oklw.service.IKampioenschapService;
import be.oklw.util.Datum;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SessionScoped
@ManagedBean
public class EvenementController implements Serializable{

    private List<Evenement> alleEvenementen;
    private List<String> alleClubs;

    private String naamKampioenschap;
    private String clubKampioenschap;
    private Datum startDatumKampioenschap;
    private Datum eindDatumKampioenschap;

    private String naamEvenement;
    private String clubEvenement;
    private Datum startDatumEvenement;
    private Datum eindDatumEvenement;
    private String locatieEvenement;
    private String omschrijvingEvenement;

    private Evenement selectedEvenement;

    @EJB
    IEvenementService evenementService;

    @EJB
    IClubService clubService;

    public List<Evenement> getAlleEvenementen(){
        return evenementService.getAlleEvenementen();
    }

    public List<String> getAlleClubs(){
        List<Club> clubList = clubService.getAllClubs();
        List<String> stringList = new ArrayList<>();
        for (Club c : clubList){
            stringList.add(c.getNaam());
        }
        return stringList;
    }

    public String getNaamKampioenschap() {
        return naamKampioenschap;
    }

    public void setNaamKampioenschap(String naamKampioenschap) {
        this.naamKampioenschap = naamKampioenschap;
    }

    public String getClubKampioenschap() {
        return clubKampioenschap;
    }

    public void setClubKampioenschap(String clubKampioenschap) {
        this.clubKampioenschap = clubKampioenschap;
    }

    public Datum getStartDatumKampioenschap() {
        return startDatumKampioenschap;
    }

    public void setStartDatumKampioenschap(Datum startDatumKampioenschap) {
        this.startDatumKampioenschap = startDatumKampioenschap;
    }

    public Datum getEindDatumKampioenschap() {
        return eindDatumKampioenschap;
    }

    public void setEindDatumKampioenschap(Datum eindDatumKampioenschap) {
        this.eindDatumKampioenschap = eindDatumKampioenschap;
    }

    public String getClubEvenement() {
        return clubEvenement;
    }

    public void setClubEvenement(String clubEvenement) {
        this.clubEvenement = clubEvenement;
    }

    public String getNaamEvenement() {
        return naamEvenement;
    }

    public void setNaamEvenement(String naamEvenement) {
        this.naamEvenement = naamEvenement;
    }

    public String getOmschrijvingEvenement() {
        return omschrijvingEvenement;
    }

    public void setOmschrijvingEvenement(String omschrijvingEvenement) {
        this.omschrijvingEvenement = omschrijvingEvenement;
    }

    public String getLocatieEvenement() {
        return locatieEvenement;
    }

    public void setLocatieEvenement(String locatieEvenement) {
        this.locatieEvenement = locatieEvenement;
    }

    public Datum getStartDatumEvenement() {
        return startDatumEvenement;
    }

    public void setStartDatumEvenement(Datum startDatumEvenement) {
        this.startDatumEvenement = startDatumEvenement;
    }

    public Datum getEindDatumEvenement() {
        return eindDatumEvenement;
    }

    public void setEindDatumEvenement(Datum eindDatumEvenement) {
        this.eindDatumEvenement = eindDatumEvenement;
    }

    public Evenement getSelectedEvenement(){
        return selectedEvenement;
    }

    public void setSelectedEvenement(Evenement selectedEvenement) {
        this.selectedEvenement = selectedEvenement;
    }

    public void maakNieuwKampioenschapAan(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            evenementService.maakNieuwKampioenschapAan(naamKampioenschap, clubKampioenschap, startDatumKampioenschap, eindDatumKampioenschap);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nieuw kampioenschap werd aangemaakt", "Nieuw kampioenschap werd aangemaakt");
            facesContext.addMessage(null, message);
            reset();
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            facesContext.addMessage(null, message);
        }
    }

    public void maakNieuwEvenementAan(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            evenementService.maakNieuwEvenementAan(naamEvenement, clubEvenement, startDatumEvenement, eindDatumEvenement, locatieEvenement, omschrijvingEvenement);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nieuw evenement werd aangemaakt", "Nieuw evenement werd aangemaakt");
            facesContext.addMessage(null, message);
            reset();
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            facesContext.addMessage(null, message);
        }
    }

    public void verwijderEvenement(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try{
            evenementService.verwijderEvenement(selectedEvenement);
            reset();
        }
        catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            facesContext.addMessage(null, message);
        }
    }

    public void reset(){
        this.clubEvenement=null;
        this.clubKampioenschap = null;
        this.naamEvenement = "";
        this.naamKampioenschap = "";
        this.startDatumEvenement=null;
        this.eindDatumEvenement = null;
        this.eindDatumKampioenschap= null;
        this.startDatumKampioenschap = null;
        this.locatieEvenement = "";
        this.omschrijvingEvenement = "";
        this.selectedEvenement = null;
    }

    public boolean isEvenement(Evenement evenement){
        if (evenement instanceof Kampioenschap){
            return false;
        }
        else return true;
    }


}
