package be.oklw.bean.systeem;

import be.oklw.model.Contact;
import be.oklw.service.IContactService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.*;

@SessionScoped
@ManagedBean
public class ContactLijstBean implements Serializable{

    @EJB
    IContactService contactService;

    private Set<Contact> contactLijst = new HashSet<>();

    public Set<Contact> getContactLijst() {
        return contactLijst;
    }

    public void setContactLijst(Set<Contact> contactLijst) {
        this.contactLijst = contactLijst;
    }

    public void addNieuwsteContact(){
        contactLijst.add(contactService.getNieuwsteContact());
    }

    public void wijzigContact(int contactId){
        Contact aangepastContact = contactService.getContact(contactId);

        Set<Contact> newContactSet = new HashSet<>();
        for (Iterator<Contact> i = contactLijst.iterator(); i.hasNext();) {
            Contact c = i.next();
            if (c.getId() == contactId) {
                i.remove();
                newContactSet.add(aangepastContact);
            }
            else{newContactSet.add(c);}
        }

        contactLijst = newContactSet;
    }

    public void verwijderContact(int contactId){

        Set<Contact> newContactSet = new HashSet<>();
        for (Iterator<Contact> i = contactLijst.iterator(); i.hasNext();) {
            Contact c = i.next();
            if (c.getId() != contactId) {
                newContactSet.add(c);
            }
        }
        contactLijst = newContactSet;
    }

}
