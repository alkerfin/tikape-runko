package tikape.runko.domain;

public class Raakaaine {

    private Integer id;
    private String nimi;
    private String ohje;
    private String maara;

    public Raakaaine(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }

    public Integer getId() {
        return id;
    }

    public void setMaara(String maara) {
      this.maara = maara;
    }

    public void setOhje(String ohje) {
      this.ohje = ohje;
    }

    public String getOhje() {
      return this.ohje;
    }

    public String getMaara() {
      return this.maara;
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
