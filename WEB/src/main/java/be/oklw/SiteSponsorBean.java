package be.oklw;

import be.oklw.model.SiteSponsor;
import be.oklw.model.Sponsor;
import be.oklw.model.SysteemAccount;
import be.oklw.service.IFileService;
import be.oklw.service.IGebruikerService;
import be.oklw.service.ISponsorService;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@ManagedBean
@SessionScoped
public class SiteSponsorBean implements Serializable{

    private static final long serialVersionUID = -668703136227523415L;

    @EJB
    IFileService fileService;

    @EJB
    ISponsorService sponsorService;

    @EJB
    IGebruikerService gebruikerService;

    private SysteemAccount sysAccount;

    private UploadedFile file;

    private int sponsId;
    private SiteSponsor siteSponsor;
    private boolean sponsorNieuw;
    private boolean heeftLogo;
    private String logoUrl;

    public boolean isHeeftLogo() {
        if (siteSponsor == null) {
            return false;
        }
        return StringUtils.isNotBlank(siteSponsor.getLogoUrl()) || StringUtils.isNotBlank(siteSponsor.getLogoUrlOnline());
    }

    public String getLogoUrl() {
        if (siteSponsor.isLogoOnline()) {
            return siteSponsor.getLogoUrlOnline();
        }
        return String.format("upload/sitesponsors/%s?time=%s", siteSponsor.getLogoUrl(), new Date().getTime());
    }

    public int getSponsId() {
        return sponsId;
    }

    public void setSponsId(int sponsId) {
        this.sponsId = sponsId;
    }

    public SysteemAccount getSysAccount() {
        return sysAccount;
    }

    public SiteSponsor getSiteSponsor() {
        return siteSponsor;
    }

    public void setSiteSponsor(SiteSponsor siteSponsor) {
        this.siteSponsor = siteSponsor;
    }

    public boolean isSponsorNieuw() {
        return sponsorNieuw;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String sponsorKlik() {
        Optional<SiteSponsor> optSponsor = sysAccount.getSiteSponsors().stream().filter(s -> s.getId() == sponsId).findFirst();
        if (optSponsor.isPresent()) {
            siteSponsor = optSponsor.get();
            sponsorNieuw = false;
            return "systeem_sponsor?faces-redirect=true";
        }
        return "";
    }

    public String nieuweSponsor() {
        sponsId = -1;
        siteSponsor = new SiteSponsor();
        siteSponsor.setLogoBreedte(120);
        siteSponsor.setLogoHoogte(120);
        sponsorNieuw = true;
        return "systeem_sponsor?faces-redirect=true";
    }

    public void removeSponsor() {
        try {
            sponsorService.removeSiteSponsor(sysAccount, sponsId);
            sponsId = -1;
            refresh();
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", ex.getMessage()));
        }
    }


    public void uploadSponsorLogo(FileUploadEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage facesMessage;

        file = event.getFile();

        if(file.getContents().length > 0) {

            try {
                String logoNaam = fileService.upload(file.getContents(), file.getFileName(), "sitesponsors");
                siteSponsor.setLogoUrl(logoNaam);
                siteSponsor.setLogoHoogte(120);
                siteSponsor.setLogoBreedte(120);
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
        if (sponsorNieuw) {
            sponsorService.nieuweSiteSponsor(siteSponsor, sysAccount);
        } else {
            sponsorService.saveSiteSponsor(siteSponsor);
        }

        refresh();
        return "systeem_site_sponsoring?faces-redirect=true";
    }

    public String annuleren() {
        siteSponsor = null;
        sponsId = -1;
        return "systeem_site_sponsoring?faces-redirect=true";
    }

    private void refresh() {
        sysAccount = gebruikerService.getSysteemAccount(sysAccount.getId());
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(true);
        sysAccount = (SysteemAccount)httpSession.getAttribute("user");
    }
}
