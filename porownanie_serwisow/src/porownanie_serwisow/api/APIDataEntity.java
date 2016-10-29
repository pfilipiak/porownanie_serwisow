/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porownanie_serwisow.api;

/**
 *
 * @author Adrian
 */
public class APIDataEntity {
   
   private String keyword; //fraza
   private String landingPage; //strona docelowa tj strona ktora wyswietla sie na dana fraze, na danej pozycji
   private Byte position; //API zwraca tylko 1-20, my musimy obsłużć 'braki'
   private Integer volumen; //srednia liczba wyszukiwan frazy miesiecznie
   private Float trafficShare; //procent, ddd.dd - przekonwertowasc na d.dddd
   private Long timestamp; //data aktualizacji wyniku - zmienia sie gdy zaszla zmiana pozycji pracy Keyword, LandingPage

   public APIDataEntity() {
        this.position = -1;
        this.volumen = -1;
    }

    public APIDataEntity(APIDataEntity apiDE) {
        this.keyword = apiDE.getKeyword();
        this.landingPage = apiDE.getLandingPage();
        this.position = apiDE.position;
        this.volumen = apiDE.volumen;
        this.trafficShare = apiDE.trafficShare;
        this.timestamp = apiDE.timestamp;
    }
    
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLandingPage() {
        return landingPage;
    }

    public void setLandingPage(String landingPage) {
        this.landingPage = landingPage;
    }

    public Byte getPosition() {
        return position;
    }

    public void setPosition(Byte position) {
        this.position = position;
    }

    public Integer getVolumen() {
        return volumen;
    }

    public void setVolumen(Integer volumen) {
        this.volumen = volumen;
    }

    public Float getTrafficShare() {
        return trafficShare;
    }

    public void setTrafficShare(Float trafficShare) {
        this.trafficShare = trafficShare;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }   
      
}
