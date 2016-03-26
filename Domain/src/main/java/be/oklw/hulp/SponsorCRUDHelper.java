package be.oklw.hulp;

import java.io.Serializable;

public class SponsorCRUDHelper implements Serializable {

    private static final long serialVersionUID = 5539692784807474408L;
    
    private boolean nieuw;
    private int id;

    public SponsorCRUDHelper(boolean isNieuw, int id) {
        this.nieuw = isNieuw;
        this.id = id;
    }

    public boolean isNieuw() {
        return nieuw;
    }

    public int getId() {
        return id;
    }
}
