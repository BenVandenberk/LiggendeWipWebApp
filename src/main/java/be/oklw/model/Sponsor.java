package be.oklw.model;


public class Sponsor {

    private int id;
    private String naam;
    private String linksTo;
    private int logoHoogte;
    private int logoBreedte;
    private boolean isLogoOnline;
    private String logoUrl;

    protected Sponsor(Club club) {
        club.addSponsor(this);
    }
}
