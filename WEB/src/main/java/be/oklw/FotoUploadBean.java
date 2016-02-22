package be.oklw;

import be.oklw.model.Foto;
import be.oklw.model.Kampioenschap;
import be.oklw.service.IKampioenschapService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Optional;

@ManagedBean
@ViewScoped
public class FotoUploadBean {

    @EJB
    IKampioenschapService kampioenschapService;

    @ManagedProperty(value = "#{clubBeheerBean}")
    ClubBeheerBean clubBeheerBean;

    private UploadedFile file;
    private List<Foto> fotos;
    private Kampioenschap kampioenschap;
    private int geselecteerdeFotoId;

    private String tag;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void setClubBeheerBean(ClubBeheerBean clubBeheerBean) {
        this.clubBeheerBean = clubBeheerBean;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }

    public int getGeselecteerdeFotoId() {
        return geselecteerdeFotoId;
    }

    public void setGeselecteerdeFotoId(int geselecteerdeFotoId) {
        this.geselecteerdeFotoId = geselecteerdeFotoId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void uploadFoto(FileUploadEvent fileUploadEvent) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage facesMessage;

        file = fileUploadEvent.getFile();

        if(file.getContents().length > 0) {

            try {
                kampioenschap = kampioenschapService.addFoto(file.getContents(), file.getFileName(), kampioenschap);
                fotos = kampioenschapService.getFotos(kampioenschap);
            } catch (Exception ex) {
                facesMessage = new FacesMessage(ex.getMessage());
                facesContext.addMessage(null, facesMessage);
            }

            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Geslaagd!", file.getFileName() + " opgeladen");
            facesContext.addMessage(null, facesMessage);
        } else {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", "Geen file geselecteerd om up te loaden");
            facesContext.addMessage(null, facesMessage);
        }
    }

    public void verwijderFoto() {
        try {
            kampioenschapService.verwijderFoto(geselecteerdeFotoId, kampioenschap);
            geselecteerdeFotoId = -1;
            fotos = kampioenschapService.getFotos(kampioenschap);
        } catch(Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    "Er ging iets mis bij het verwijderen"
            ));
        }
    }

    public void saveTag() {
        try {
            Optional<Foto> foto = fotos.stream().filter(f -> f.getId() == geselecteerdeFotoId).findFirst();
            if (foto.isPresent()) {
                foto.get().setCaption(tag);
                kampioenschapService.saveFoto(foto.get());
                tag = "";
            }
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    "Er ging iets mis bij het aanpassen van de tag"
            ));
        }
    }

    @PostConstruct
    public void init() {
        kampioenschap = clubBeheerBean.getKampioenschap();
        fotos = kampioenschapService.getFotos(kampioenschap);
    }
}
