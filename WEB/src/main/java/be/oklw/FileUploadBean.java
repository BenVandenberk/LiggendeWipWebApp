package be.oklw;

import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.service.IClubService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

@ManagedBean
@RequestScoped
public class FileUploadBean {

    @EJB
    IClubService clubService;

    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void uploadClubLogo(FileUploadEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage facesMessage;

        file = event.getFile();

        if(file.getContents().length > 0) {

            try {
                clubService.veranderClubLogo(file.getContents(), file.getFileName(), club());
            } catch (Exception ex) {
                facesMessage = new FacesMessage(ex.getMessage());
                facesContext.addMessage(null, facesMessage);
            }

            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Geslaagd!", file.getFileName() + " opgeladen");
            facesContext.addMessage("frm_upload:messages_upload", facesMessage);
        } else {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", "Geen file geselecteerd om up te loaden");
            facesContext.addMessage(null, facesMessage);
        }
    }

    private Club club() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        if (session == null) {
            throw new IllegalStateException("Ongeldige sessie / sessie verlopen");
        }

        Account loggedIn = (Account)session.getAttribute("user");
        if (loggedIn == null) {
            throw new IllegalStateException("Niet ingelogd");
        }

        if (loggedIn.getClub() == null) {
            throw new IllegalStateException("Account is niet gekoppeld aan een club");
        }

        return loggedIn.getClub();
    }
}
