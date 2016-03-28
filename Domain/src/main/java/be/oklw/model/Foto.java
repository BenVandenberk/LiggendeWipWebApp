package be.oklw.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Foto implements Serializable {

    private static final long serialVersionUID = 8266089329013397594L;
    private static final String PAD = "upload/fotos/";
    private static final String RELATIVE_PAD = "fotos";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String filename;
    private String caption;
    private String originalFilename;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kampioenschap kampioenschap;

    public Foto() {
    }

    public int getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getFullPath() {
        return String.format("%s%s", PAD, filename);
    }

    public Kampioenschap getKampioenschap() {
        return kampioenschap;
    }

    protected void setKampioenschap(Kampioenschap kampioenschap) {
        this.kampioenschap = kampioenschap;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public static String getRelativePath() {
        return RELATIVE_PAD;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Foto foto = (Foto) o;

        return id == foto.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Foto %s, Caption: %s", filename, caption);
    }
}
