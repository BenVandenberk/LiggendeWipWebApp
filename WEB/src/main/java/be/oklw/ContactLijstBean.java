package be.oklw;

import be.oklw.model.Contact;
import be.oklw.service.IContactService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SessionScoped
@ManagedBean
public class ContactLijstBean implements Serializable{

    @EJB
    IContactService contactService;

    private List<Contact> contactLijst = new ArrayList<>();

    public List<Contact> getContactLijst() {
        return contactLijst;
    }

    public void setContactLijst(List<Contact> contactLijst) {
        this.contactLijst = contactLijst;
    }

    public void addNieuwsteContact(){
        contactLijst.add(contactService.getNieuwsteContact());
    }

    public void wijzigContact(int contactId){
        Contact aangepastContact = contactService.getContact(contactId);
        int i;
        for (Contact c : contactLijst){
            if (c.getId() == contactId){
                i = contactLijst.indexOf(c);
                contactLijst.set(i, aangepastContact);
            }
        }
    }

}
