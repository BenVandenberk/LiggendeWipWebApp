package be.oklw.controller;

import be.oklw.model.Account;
import be.oklw.service.GebruikerService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


@RequestScoped
@ManagedBean
public class LoginBean {

    private String userName;
    private String password;

    private GebruikerService gebruikerService;

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
            HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(true);
            httpSession.setAttribute("user", account);
            return "success";

        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(ex.getMessage());
            facesContext.addMessage("", message);
        }
        return "";
    }

    @PostConstruct
    public void init() {
        gebruikerService = new GebruikerService();
    }
}
