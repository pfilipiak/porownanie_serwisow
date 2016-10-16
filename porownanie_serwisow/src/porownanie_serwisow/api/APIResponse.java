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
public class APIResponse {
    private String apiKey;
    private String content;
    private int code;
     
    public APIResponse(String apiKey) {
        this.apiKey = "";
        this.content = "";
        code = 0;
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
    

}
