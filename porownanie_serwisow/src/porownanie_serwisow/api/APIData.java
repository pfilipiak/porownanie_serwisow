/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porownanie_serwisow.api;

import java.util.*;

/**
 *
 * @author Adrian
 */
public class APIData {
    private String apiKey;
    private String content;
    private int code;
    private String data;
    private Map<String[], APIDataEntity> resultsMapApiED;
    
    public APIData(String apiKey) {
        this.apiKey = "";
        this.content = "";
        code = 0;
        this.resultsMapApiED = new HashMap<String[], APIDataEntity>();
    }
    public void setAPIKey(String apiKey){
           this.apiKey = apiKey;  
    }
    
        
    public void setContent(String content){
           this.content = content;  
    }
    
    public void setHTTPResCode(int code){
           this.code = code;  
    }
    
    public String getContent(){
           return this.content;  
    }
    
    public int getHTTPResCode(){
           return this.code;  
    }
   
    public String getAPIKey(){
           return this.apiKey;
    }
    
    public void setAPIDataEntity(APIDataEntity apiDE){
        String[] keyword_lp = new String[2];
        keyword_lp[0] = apiDE.getKeyword();
        keyword_lp[1] = apiDE.getLandingPage();
        //if map.get("keyword_lp")..
        this.resultsMapApiED.put(keyword_lp, new APIDataEntity(apiDE));
        //new APIDataEntity(apiDE)
    }
    
    public String printAPIDataEntity(){

        //if map.get("keyword_lp")..
       if (this.resultsMapApiED != null)
        this.resultsMapApiED.entrySet().forEach((entry) -> {
            String[] key = entry.getKey();
            APIDataEntity value = entry.getValue();
            System.out.println("mapa:");
            System.out.print("key: "+Arrays.toString(key) + " ");
            System.out.println("value: "+value.getKeyword() + ", " + value.getLandingPage());
        });
        
        return "printed";
        
    }

}
