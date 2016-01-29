package be.oklw;

import be.oklw.model.Account;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ViewScoped
@ManagedBean
public class GebruikerController {
    private Account user;
    private boolean loggedIn;

    public Account getUser() {
        return user;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public boolean heeftRol(String rol) {
        if (user == null) {
            return false;
        }
        return user.heeftRol(rol);
    }

    public String logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(true);
        httpSession.setAttribute("user", null);
        user = null;
        loggedIn = false;
        return "home";
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(true);
        user = (Account)httpSession.getAttribute("user");
        loggedIn = user != null;
    }
}
