package be.oklw.model;

import java.io.Serializable;

public enum PermissieNiveau implements Serializable {
    SYSTEEM, CLUB, LID, BEZOEKER;

    @Override
    public String toString() {
        switch(this) {
            case SYSTEEM: return "systeem";
            case CLUB: return "club";
            case LID: return "lid";
            case BEZOEKER: return "bezoeker";
            default: return "";
        }
    }

    public boolean heeftRol(String rol) {
        return this.toString().toLowerCase().equals(rol.toLowerCase());
    }
}
