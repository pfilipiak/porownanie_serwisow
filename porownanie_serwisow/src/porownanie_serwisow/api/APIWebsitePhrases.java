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
public class APIWebsitePhrases {
   
   private String phrase; //fraza
   private String url; //strona docelowa tj strona ktora wyswietla sie na dana fraze, na danej pozycji
   private Byte position; //API zwraca tylko 1-20, my musimy obsłużć 'braki'
   private Integer volumen; //srednia liczba wyszukiwan frazy miesiecznie
   private Float trafficShare; //procent, ddd.dd - przekonwertowasc na d.dddd
   private Long timestamp; //data aktualizacji wyniku - zmienia sie gdy zaszla zmiana pozycji pracy Keyword, LandingPage

   public APIWebsitePhrases() {
        this.position = -1;
        this.volumen = 0;
        this.trafficShare = 0.0f;
        this.timestamp = 0L;
    }

    public APIWebsitePhrases(APIWebsitePhrases apiDE) {
        this.phrase = apiDE.getPhrase();
        this.url = apiDE.getUrl();
        this.position = apiDE.position;
        this.volumen = apiDE.volumen;
        this.trafficShare = apiDE.trafficShare;
        this.timestamp = apiDE.timestamp;
    }
    
    public String EntityToString() {
        return 
            "[" + 
            this.phrase + ", " +
            this.url + ", " +
            Byte.toString(this.position) + ", " + 
            Integer.toString(this.volumen) + ", " +
            Float.toString(this.trafficShare) + ", " +
            Long.toString(this.timestamp) + "]";
            
    }
     
    //    
    public void setAttribute(String attr_name, String attr_value) {
        String attr = attr_name.toLowerCase().trim();
        switch (attr) {
            case "phrase" : this.setPhrase(attr_value); break;
            case "url": this.setUrl(attr_value); break;
            case "position": this.setPosition(attr_value); break;
            case "volumen": this.setVolumen(attr_value); break;
            case "trafficshare": this.setTrafficShare(attr_value); break;
            case "timestamp": this.setTimestamp(attr_value); break;
        default: break;
        }
    }   

    
    //
    public String getPhrase() {
        return this.phrase;
    }

    public void setPhrase(String keyword) {
        this.phrase = keyword;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String landingPage) {
        this.url = landingPage;
    }

    public Byte getPosition() {
        return this.position;
    }

    public void setPosition(Byte position) {
        this.position = position;
    }

    public void setPosition(String position) {
        this.position = Byte.parseByte(position.trim());
    }
        
    public Integer getVolumen() {
        return this.volumen; 
    }

    public void setVolumen(Integer volumen) {
        this.volumen = volumen;
    }

    
    public void setVolumen(String volumen) {
        this.volumen = Integer.parseInt(volumen.trim());
    }

    public Float getTrafficShare() {
        return this.trafficShare;
    }

    public void setTrafficShare(Float trafficShare) {
        this.trafficShare = trafficShare;
    }

    public void setTrafficShare(String trafficShare) {
        trafficShare = trafficShare.replace(",", ".").trim();
        this.trafficShare = Float.parseFloat(trafficShare);
    }
        
    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }  
    
    public void setTimestamp(String timestamp) {
        this.timestamp = Long.parseLong(timestamp.trim());
    }   

      
    
}
