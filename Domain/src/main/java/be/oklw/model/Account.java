package be.oklw.model;

import be.oklw.util.Authentication;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "accountType", discriminatorType = DiscriminatorType.STRING)
public class Account implements Serializable {

    private static final long serialVersionUID = -5674423074060993828L;
    //region PRIVATE MEMBERS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "account")
    private List<Nieuws> nieuwsList;

    @Enumerated(EnumType.STRING)
    private PermissieNiveau permissieNiveau;

    @Lob
    private byte[] pwHash;
    @Lob
    private byte[] pwSalt;

    @OneToOne(fetch = FetchType.EAGER, optional = true)
    private Club club;

    @OneToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.ALL)
    private Lid lid;

    //endregion

    //region GETTERS & SETTERS


    public byte[] getPwHash() {
        return pwHash;
    }

    public byte[] getPwSalt() {
        return pwSalt;
    }

    public void setPwHash(byte[] pwHash) {
        this.pwHash = pwHash;
    }

    public void setPwSalt(byte[] pwSalt) {
        this.pwSalt = pwSalt;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Club getClub() {
        return club;
    }


    public PermissieNiveau getPermissieNiveau() {
        return permissieNiveau;
    }

    public void setPermissieNiveau(PermissieNiveau permissieNiveau) {
        this.permissieNiveau = permissieNiveau;
    }

    public List<Nieuws> getNieuwsList() {
        return nieuwsList;
    }

    public void setNieuwsList(List<Nieuws> nieuwsList) {
        this.nieuwsList = nieuwsList;
    }

    public Lid getLid() {
        return lid;
    }

    public void setLid(Lid lid) {
        this.lid = lid;
    }

    //endregion

    //region CONSTRUCTORS

    protected Account() {

    }

    /**
     * Deze constructor wordt gebruikt om een club-account aan te maken
     * en wordt automatisch gegenereerd bij het aanmaken van de club
     */

    public Account(Club club){
        this.club = club;
        this.userName = club.getNaam();
        this.pwSalt = Authentication.nextSalt();
        this.pwHash = Authentication.hashPw(club.getNaam(), pwSalt);
        this.permissieNiveau = PermissieNiveau.CLUB;
    }

    /**
     * Deze constructor wordt gebruikt om een lid-account aan te maken
     * en wordt NIET automatisch gegenereerd bij het aanmaken van de club
     */

    public Account(Lid lid, String userName, String password){
        this.lid = lid;
        this.userName = userName;
        this.pwSalt = Authentication.nextSalt();
        this.pwHash = Authentication.hashPw(password, pwSalt);
        this.permissieNiveau = PermissieNiveau.LID;
    }

    //endregion

    //region PUBLIC METHODS

    public boolean heeftRol(String rol) {
        return permissieNiveau.heeftRol(rol);
    }

    public void removeNieuwtje(Nieuws nieuws){
        Iterator<Nieuws> it = nieuwsList.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == nieuws.getId()) {
                it.remove();
            }
        }
    }

    //endregion
}
