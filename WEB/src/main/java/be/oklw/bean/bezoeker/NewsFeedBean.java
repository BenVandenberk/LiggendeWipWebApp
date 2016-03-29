package be.oklw.bean.bezoeker;

import be.oklw.bean.common.GebruikerController;
import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.model.Nieuws;
import be.oklw.model.PermissieNiveau;
import be.oklw.service.INieuwsService;
import be.oklw.util.Datum;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class NewsFeedBean implements Serializable {

    @EJB
    INieuwsService nieuwsService;

    private String nieuwtje;
    private Datum tonenTot;
    private Account account;
    private Nieuws selectedNieuwtje;

    public void maakNieuwtjeAan(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        FacesMessage message;

        try {
            if (session != null) {
                Account user = (Account) session.getAttribute("user");
                account = user;
                nieuwsService.maakNieuwtjeAan(nieuwtje, new Datum(), tonenTot, account);
            }
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Geslaagd!", "Nieuwtje werd toegevoegd");
            facesContext.addMessage(null, message);
            reset();
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            facesContext.addMessage(null, message);
        }
    }

    public void verwijderNieuwtje(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            nieuwsService.verwijderNieuwtje(selectedNieuwtje);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Geslaagd!", "Nieuwtje werd verwijderd uit de feed");
            facesContext.addMessage(null, message);
            reset();
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            facesContext.addMessage(null, message);
        }
    }

    public List<Nieuws> getLaatsteNieuwtjes(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        try {
            return nieuwsService.getLaatsteNieuwtjes();

        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            facesContext.addMessage(null, message);
        }
        return null;
    }



    public void reset(){
        this.nieuwtje="";
        this.selectedNieuwtje=null;
        this.tonenTot=null;
    }

    public void setTonenTot(Datum tonenTot) {
        this.tonenTot = tonenTot;
    }

    public Datum getTonenTot(){
        return tonenTot;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getNieuwtje() {
        return nieuwtje;
    }

    public void setNieuwtje(String nieuwtje) {
        this.nieuwtje = nieuwtje;
    }

    public Nieuws getSelectedNieuwtje() {
        return selectedNieuwtje;
    }

    public void setSelectedNieuwtje(Nieuws selectedNieuwtje) {
        this.selectedNieuwtje = selectedNieuwtje;
    }
}
