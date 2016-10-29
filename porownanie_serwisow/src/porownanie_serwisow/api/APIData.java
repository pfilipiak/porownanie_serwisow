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
    private Map<String[], APIDataEntity> resultsMapApiED; //mapa <keyword, landinPage> i obiekt typu APIDataEntity
    private Boolean isLive; 
    
    public APIData() {
        this.apiKey = "";
        this.apiCode = -1;
        this.isLive = true;
        this.resultsMapApiED = new HashMap<String[], APIDataEntity>();  
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); //domyślnie ustawianmy na Live data
    }
    
   
    public void setAPIDataEntity(APIDataEntity apiDE){
        String[] keyword_lp = new String[2];
        keyword_lp[0] = apiDE.getKeyword();
        keyword_lp[1] = apiDE.getLandingPage();
        //if map.get("keyword_lp")..
        this.resultsMapApiED.put(keyword_lp, new APIDataEntity(apiDE));
    }
    
    public String printAPIDataEntity(){

        //if map.get("keyword_lp")..
       if (this.resultsMapApiED != null)
        this.resultsMapApiED.entrySet().forEach((entry) -> {
            String[] key = entry.getKey();
            APIDataEntity value = entry.getValue();
            System.out.print("key: "+Arrays.toString(key) + " ");
            System.out.println("value: "+value.EntityToString());
        });
        
        return "printed";
        
    }

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
    
    

}
