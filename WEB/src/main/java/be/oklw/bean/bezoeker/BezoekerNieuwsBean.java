package be.oklw.bean.bezoeker;

import be.oklw.exception.BusinessException;
import be.oklw.model.Account;
import be.oklw.model.Nieuws;
import be.oklw.model.PermissieNiveau;
import be.oklw.service.INieuwsService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ManagedBean
@ViewScoped
public class BezoekerNieuwsBean {

    @EJB
    INieuwsService nieuwsService;

    private Account account;

    private List<Nieuws> nieuwtjes;

    private int teEditerenId = -1;
    private Nieuws teEditeren = new Nieuws();

    public List<Nieuws> getNieuwtjes() {
        return nieuwtjes;
    }

    public Nieuws getTeEditeren() {
        return teEditeren;
    }

    public int getTeEditerenId() {
        return teEditerenId;
    }

    public void setTeEditerenId(int teEditerenId) {
        this.teEditerenId = teEditerenId;

        Optional<Nieuws> nieuwsOpt = nieuwtjes.stream().filter(n -> n.getId() == teEditerenId).findFirst();
        if (nieuwsOpt.isPresent()) {
            teEditeren = nieuwsOpt.get();
        }
    }

    public String getLogoPad(Nieuws nieuws) {
        Account account = nieuws.getAccount();
        PermissieNiveau permissieNiveau = account.getPermissieNiveau();

        switch (permissieNiveau) {
            case SYSTEEM:
                return "images/webmaster.png";
            case CLUB:
                return account.getClub().getLogoFullPath();
            default:
                return "";
        }
    }

    public boolean editable(Nieuws nieuws) {
        if (account == null) {
            return false;
        }

        return nieuws.getAccount().equals(account);
    }

    public void saveNieuwtje() {
        try {
            nieuwsService.saveNieuwtje(teEditeren);
        } catch (BusinessException bEx) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fout",
                    bEx.getMessage()
            ));
        } finally {
            nieuwtjes = nieuwsService.getAlleTeTonenNieuwtjes();
            Collections.sort(
                    nieuwtjes,
                    Collections.reverseOrder((nieuwtje, nieuwtje2) -> nieuwtje.getDatum().compareTo(nieuwtje2.getDatum()))
            );
        }
    }

    @PostConstruct
    private void init() {
        nieuwtjes = nieuwsService.getAlleTeTonenNieuwtjes();
        Collections.sort(
                nieuwtjes,
                Collections.reverseOrder((nieuwtje, nieuwtje2) -> nieuwtje.getDatum().compareTo(nieuwtje2.getDatum()))
        );

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
        account = (Account) httpSession.getAttribute("user");
    }
}
