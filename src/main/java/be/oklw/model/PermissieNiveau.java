package be.oklw.model;

public enum PermissieNiveau {
    SYSTEEM, CLUB, LID;

    @Override
    public String toString() {
        switch(this) {
            case SYSTEEM: return "systeem";
            case CLUB: return "club";
            case LID: return "lid";
            default: throw new IllegalArgumentException();
        }
    }
}
