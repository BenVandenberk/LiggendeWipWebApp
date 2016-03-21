package be.oklw.bean.lid;

import be.oklw.hulp.LidInschrijving;
import be.oklw.model.Account;
import be.oklw.model.PermissieNiveau;
import be.oklw.service.ILedenService;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class LidInschrijvingenBean {

    @EJB
    ILedenService ledenService;

    private List<LidInschrijving> lidInschrijvingen = new ArrayList<>();

    public List<LidInschrijving> getLidInschrijvingen() {
        return lidInschrijvingen;
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(true);

        Account user = (Account)session.getAttribute("user");

        boolean isIngelogdLid = (user != null) &&
                                (user.getLid() != null) &&
                                (user.getPermissieNiveau().equals(PermissieNiveau.LID));

        if (isIngelogdLid) {
            lidInschrijvingen = ledenService.alleToekomstigeInschrijvingen(user.getLid());
        }
    }
}
