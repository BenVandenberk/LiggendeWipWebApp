package be.oklw.bean.bezoeker;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class NewsFeedBean implements Serializable {

    private List<String> newsList = new ArrayList<>();

    private String nieuwtje;

    public boolean isClubAccount(){
        return true;
    }

    public List<String> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<String> newsList) {
        this.newsList = newsList;
    }

    public String getNieuwtje() {
        return nieuwtje;
    }

    public void setNieuwtje(String nieuwtje) {
        this.nieuwtje = nieuwtje;
    }

    public void voegToe(){
        newsList.add(nieuwtje);
    }
}
