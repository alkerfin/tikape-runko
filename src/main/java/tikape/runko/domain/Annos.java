package tikape.runko.domain;
import java.util.List;
import java.util.ArrayList;
import tikape.runko.domain.Raakaaine;

public class Annos {

    private Integer id;
    private String nimi;
    private List<Raakaaine> aineet;

    public Annos(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
        this.aineet = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void addAineet(List<Raakaaine> aineet) {
      this.aineet = aineet;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

}
