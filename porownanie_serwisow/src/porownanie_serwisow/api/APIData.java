/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porownanie_serwisow.api;

import java.util.*;
import java.text.*;
/**
 *
 * @author Adrian
 */
public class APIData {
    
    private String apiKey; //apikey po ktorym nastąpiło (skuteczne) połaczenie
    private int apiCode; //kod odpowiedzi api. Kod: 40x - błąd klucza api, kod 200 - działa. Możliwy tekst 'Error Code: XX' - api dziala ale brak danych dla serwisu
    private String date; //data pobrania dla live - dany dzien YYYY-MM-DD, dla historycznych (miesiecznych) YYYY-MM
    private Map<String[], APIWebsitePhrases> resultsWebsitePhrases; //mapa <keyword, landinPage> i obiekt typu APIWebsitePhrases
    private Map<String[], APIWebsiteStats> resultsWebsiteStat; //mapa <serwis, data> i obiekt typu APIWebsiteStats
    private Map<String[], APIWebsiteCompetitors> resultsWebsiteCompetitors; //mapa <konkurent, poziom konkur.> i obiekt APIWebsiteCompetitors
    private Boolean isLive; 
    private String lang;
 
    public APIData() {
        this.apiKey = "";
        this.apiCode = -1;
        this.isLive = true;
        this.resultsWebsitePhrases = new HashMap<String[], APIWebsitePhrases>();  
        this.resultsWebsiteStat = new HashMap<String[], APIWebsiteStats>();  
        this.resultsWebsiteCompetitors = new HashMap<String[], APIWebsiteCompetitors>();
        this.date = new SimpleDateFormat("yyyyMM").format(new Date()); //domyślnie ustawianmy na Live data
    }
    
   
    public void addAPIWebsitePhrase(APIWebsitePhrases apiDE){
        //dane sa pobierane albo live - dany dzien albo zadany miesiac (historczne, jeden mc)
        //analiza wskazuje ze wartosci klucza nie muszą byc unikalne - wybieramy "nowsze" z danego miesiaca
        String[] phrase_url = new String[2];
        phrase_url[0] = apiDE.getPhrase();
        phrase_url[1] = apiDE.getUrl();
        
        APIWebsitePhrases valid;
        valid = resultsWebsitePhrases.get(apiDE);
        if (valid == null || (valid.getTimestamp() < apiDE.getTimestamp() ))
            this.resultsWebsitePhrases.put(phrase_url, new APIWebsitePhrases(apiDE));
        //jeśl nie ma takiego obiektu lub timestamp jest nowszy niz timestamp dla znalezonego [phrase, url] 
                        
    }
    
    public void addAPIWebsiteStat(String website, APIWebsiteStats apiStats){   
       //analiza wskazuje ze wartosci klucza zawsze sa unikalne
        apiStats.setWebsite(website);
        String[] domain_date = new String[2];
        domain_date[0] = website;
        domain_date[1] = apiStats.getDataYYYYMM();
        this.resultsWebsiteStat.put(domain_date, apiStats);
    }

    public void addAPIWebsiteCompetitor(APIWebsiteCompetitors apiWComp){
        //analiza wskazuje ze wartosci klucza zawsze sa unikalne
        String[] comp_relev = new String[2];
        comp_relev[0] = apiWComp.getCompetitor();
        comp_relev[1] = Float.toString(apiWComp.getRelevance());
        this.resultsWebsiteCompetitors.put(comp_relev, apiWComp);
    }

    //wydruk wartości w mapach
    public String printAPIWebsitePhrases(){
       Integer stop = 0;
       if (this.resultsWebsitePhrases != null) {
            this.resultsWebsitePhrases.entrySet().forEach((entry) -> {
                
                String[] key = entry.getKey();
                APIWebsitePhrases value = entry.getValue();
                System.out.print("key: "+Arrays.toString(key) + " ");
                System.out.println("value: "+value.EntityToString());
            });
       }
       return "printed API results";
    }
 
     public String printAPIWebsitePhrases(Integer offset){
       Integer cc = 1;  
       String res = "";
       if (this.resultsWebsiteStat != null) {
           for (String[] key : this.resultsWebsitePhrases.keySet()) {
                res += this.resultsWebsitePhrases.get(key).EntityToString() + "\r\n";  
                //System.out.print("key: "+Arrays.toString(key) + " ");
                //System.out.println("value: "+ this.resultsWebsitePhrases.get(key).EntityToString());
                if (cc >= offset) break;
                cc++;    
           }
       }
       return "printed stats, with limit: " + cc + "\r\n" + res;
    }
    
     public String printAPIWebsiteStats(){
       if (this.resultsWebsiteStat != null) {
            this.resultsWebsiteStat.entrySet().forEach((entry) -> {
                String[] key = entry.getKey();
                APIWebsiteStats value = entry.getValue();
                System.out.print("key: "+Arrays.toString(key) + " ");
                System.out.println("value: "+ value.EntityToString());
            });
       }
       return "printed stats";
    }
    
      public String printAPIWebsiteCompetitors(){
        //if map.get("keyword_lp")..
       if (this.resultsWebsiteCompetitors != null) {
            this.resultsWebsiteCompetitors.entrySet().forEach((entry) -> {
                String[] key = entry.getKey();
                APIWebsiteCompetitors value = entry.getValue();
                System.out.print("key: "+Arrays.toString(key) + " ");
                System.out.println("value: "+value.EntityToString());
            });
       }
       return "printed competitors";
    }
         
    //getters and setters

    public String getApiKey() {
        return this.apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public int getApiCode() {
        return this.apiCode;
    }

    public void setApiCode(int apiCode) {
        this.apiCode = apiCode;
    }

    public Boolean getIsLive() {
        return this.isLive;
    }

    public void setIsLive(Boolean isLive) {
        this.isLive = isLive;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
        
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Map<String[], APIWebsitePhrases> getResultsWebsitePhrases() {
        return resultsWebsitePhrases;
    }
    
    public void clearWebsitePhrases() {
        this.resultsWebsitePhrases.clear();
    }

    public void setResultsWebsitePhrases(Map<String[], APIWebsitePhrases> resultsWebsitePhrases) {
        this.resultsWebsitePhrases = resultsWebsitePhrases;
        
    }   

    public Map<String[], APIWebsiteStats> getResultsWebsiteStats() {
        return resultsWebsiteStat;
    }

    public void setResultsWebsiteStats(Map<String[], APIWebsiteStats> resultsWebsiteStat) {
        this.resultsWebsiteStat = resultsWebsiteStat;
    }

    public Map<String[], APIWebsiteCompetitors> getResultsWebsiteCompetitors() {
        return resultsWebsiteCompetitors;
    }

    public void setResultsWebsiteCompetitors(Map<String[], APIWebsiteCompetitors> resultsWebsiteCompetitors) {
        this.resultsWebsiteCompetitors = resultsWebsiteCompetitors;
    }


}
