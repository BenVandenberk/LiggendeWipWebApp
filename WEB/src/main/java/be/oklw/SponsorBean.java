package be.oklw;

import be.oklw.model.Sponsor;
import be.oklw.service.IFileService;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.Date;

@ManagedBean
@ViewScoped
public class SponsorBean {

    @ManagedProperty(value= "#{clubSponsorBean}")
    ClubSponsorBean clubSponsorBean;

    @EJB
    IFileService fileService;

    private UploadedFile file;
    private Sponsor sponsor;
    private boolean heeftLogo;
    private String logoUrl;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean isHeeftLogo() {
        if (sponsor == null) {
            return false;
        }
        return StringUtils.isNotBlank(sponsor.getLogoUrl()) || StringUtils.isNotBlank(sponsor.getLogoUrlOnline());
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public String getLogoUrl() {
        if (sponsor.isLogoOnline()) {
            return sponsor.getLogoUrlOnline();
        }
        return String.format("upload/clubsponsors/%s?time=%s", sponsor.getLogoUrl(), new Date().getTime());
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public void setClubSponsorBean(ClubSponsorBean clubSponsorBean) {
        this.clubSponsorBean = clubSponsorBean;
    }

    public void uploadSponsorLogo(FileUploadEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage facesMessage;

        file = event.getFile();

        if(file.getContents().length > 0) {

            try {
                String logoNaam = fileService.upload(file.getContents(), file.getFileName(), "clubsponsors");
                sponsor.setLogoUrl(logoNaam);
                sponsor.setLogoHoogte(120);
                sponsor.setLogoBreedte(120);
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

    public String opslaan() {
        clubSponsorBean.addSponsor(sponsor);
        return "success";
    }

    @PostConstruct
    public void init() {
        sponsor = new Sponsor();
        sponsor.setLogoBreedte(120);
        sponsor.setLogoHoogte(120);
    }
}
