package be.oklw.bean.club;

import be.oklw.hulp.SponsorCRUDHelper;
import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.model.Sponsor;
import be.oklw.service.IClubService;
import be.oklw.service.IFileService;
import be.oklw.service.ISponsorService;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.Date;

@ManagedBean
@ViewScoped
public class SponsorBean {

    @EJB
    IFileService fileService;

    @EJB
    ISponsorService sponsorService;

    @EJB
    IClubService clubService;

    private UploadedFile file;
    private Sponsor sponsor;
    private Club club;

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
        return StringUtils.isNotBlank(sponsor.getLogoFileName()) || StringUtils.isNotBlank(sponsor.getLogoUrlOnline());
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public String getLogoUrl() {
        if (sponsor.isLogoOnline()) {
            return sponsor.getLogoUrlOnline();
        }
        return String.format("upload/clubsponsors/%s?time=%s", sponsor.getLogoFileName(), new Date().getTime());
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public void uploadSponsorLogo(FileUploadEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage facesMessage;

        file = event.getFile();

        if (file.getContents().length > 0) {

            try {
                String logoNaam = fileService.upload(file.getContents(), file.getFileName(), "clubsponsors");
                sponsor.setLogoFileName(logoNaam);
                sponsor.setLogoHoogte(120);
                sponsor.setLogoBreedte(120);

                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Geslaagd!", file.getFileName() + " opgeladen");
                facesContext.addMessage("frm:file_logo", facesMessage);
            } catch (Exception ex) {
                facesMessage = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Fout",
                        ex.getMessage()
                );
                facesContext.addMessage("frm:file_logo", facesMessage);
            }
        } else {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", "Geen file geselecteerd om up te loaden");
            facesContext.addMessage("frm:file_logo", facesMessage);
        }
    }

    public String opslaanNieuw() {
        try {
            clubService.addSponsor(sponsor, club);
            return "success";
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage facesMessage = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    ex.getMessage()
            );
            facesContext.addMessage(null, facesMessage);

            return "";
        }
    }

    public String opslaanBestaand() {
        try {
            sponsorService.saveSponsor(sponsor);
            return "success";
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage()));
            return "";
        }
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session != null) {
            SponsorCRUDHelper sponsorCRUDHelper = (SponsorCRUDHelper) session.getAttribute("sponsorCRUDHelper");
            Account user = (Account) session.getAttribute("user");

            try {
                club = clubService.getClub(user);
            } catch (Exception ex) {
                System.err.println("Ongeldige session state: " + ex.getMessage());
            }

            // Nieuwe Sponsor
            if (sponsorCRUDHelper.isNieuw()) {
                sponsor = new Sponsor();
                sponsor.setLogoBreedte(120);
                sponsor.setLogoHoogte(120);
            } else { // Bestaande Sponsor
                sponsor = sponsorService.getSponsor(sponsorCRUDHelper.getId());
            }
        }
    }
}
