package be.oklw.bean.common;

import be.oklw.model.Account;
import be.oklw.service.IGebruikerService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@RequestScoped
@ManagedBean
public class LoginBean {

    @EJB
    private IGebruikerService gebruikerService;

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        try {
            Account account = gebruikerService.login(userName, password);
            HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
            httpSession.setAttribute("user", account);
            return "success";

        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Oeps", ex.getMessage());
            facesContext.addMessage("", message);
        }
        return "";
    }

    public void preRenderListener() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

        String redirectMessage = (String) session.getAttribute("redirectMessage");

        if (redirectMessage != null) {
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN,
                    "Oeps",
                    redirectMessage));
            session.setAttribute("redirectMessage", null);
        }

    }
}
