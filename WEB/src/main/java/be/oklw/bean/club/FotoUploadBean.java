package be.oklw.bean.club;

import be.oklw.model.Kampioenschap;
import be.oklw.service.IKampioenschapService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class FotoUploadBean {

    @EJB
    IKampioenschapService kampioenschapService;

    @ManagedProperty(value = "#{clubBeheerBean}")
    ClubBeheerBean clubBeheerBean;

    private UploadedFile file;
    private int geselecteerdeFotoId;

    //region GETTERS en SETTERS

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void setClubBeheerBean(ClubBeheerBean clubBeheerBean) {
        this.clubBeheerBean = clubBeheerBean;
    }

    public int getGeselecteerdeFotoId() {
        return geselecteerdeFotoId;
    }

    public void setGeselecteerdeFotoId(int geselecteerdeFotoId) {
        this.geselecteerdeFotoId = geselecteerdeFotoId;
    }

    public Kampioenschap getKampioenschap() {
        return clubBeheerBean.getKampioenschap();
    }

    //endregion

    public void uploadFoto(FileUploadEvent fileUploadEvent) {
        fileUploadEvent.getComponent().setTransient(false);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage facesMessage;

        file = fileUploadEvent.getFile();

        if (file.getContents().length > 0) {

            try {
                clubBeheerBean.setKampioenschap(
                        kampioenschapService.addFoto(file.getContents(), file.getFileName(), getKampioenschap())
                );

                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Geslaagd!", file.getFileName() + " opgeladen");
                facesContext.addMessage(null, facesMessage);
            } catch (Exception ex) {
                facesMessage = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Fout",
                        ex.getMessage()
                );
                facesContext.addMessage(null, facesMessage);
            }
        } else {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", "Geen file geselecteerd om up te loaden");
            facesContext.addMessage(null, facesMessage);
        }
    }

    public void verwijderFoto() {
        try {
            clubBeheerBean.setKampioenschap(
                    kampioenschapService.verwijderFoto(geselecteerdeFotoId, getKampioenschap())
            );
            geselecteerdeFotoId = -1;
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    "Er ging iets mis bij het verwijderen"
            ));
        }
    }

    public void saveTags() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        try {
            kampioenschapService.opslaan(getKampioenschap());

            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Geslaagd",
                    "Tags opgeslagen"
            ));
        } catch (Exception ex) {
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    "Er ging iets mis bij het opslaan"
            ));
        }
    }
}
