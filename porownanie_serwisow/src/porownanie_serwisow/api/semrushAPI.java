/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porownanie_serwisow.api;
import java.net.*;
import java.io.*;
/**
 *
 * @author Adrian
 */

public class semrushAPI {
    private String apiKey="";
    private String apiPath = "https://www.semrush.com/"; //zmienic
    
    public semrushAPI(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public void setAPIKey(String apiKey){
        this.apiKey = apiKey;
    }
        
    public void buildQuery(String website, String input_param, String filter, String output_param, int rows) {
        //
        
        this.apiPath = "http://api.semrush.com/?type=domain_organic&key="
                        + this.apiKey
                        + "&display_limit=" + rows
                        + "&export_columns=Ph,Po,Nq,Ur&domain="
                        + website
                        + "&display_sort=tr_desc&database=pl";
    }
    
    public APIResponse runQuery(){
        
        APIResponse resp = new APIResponse(apiKey);
        
        String content = "";
        int statusCode = 0;
            
        try {
            
            URL semRushUrl = new URL(apiPath);
            HttpURLConnection http = (HttpURLConnection)semRushUrl.openConnection();
            statusCode = http.getResponseCode();
            resp.setHTTPResCode(statusCode);

            if (statusCode == 200) {
                try {
                    BufferedReader input = new BufferedReader(new InputStreamReader(semRushUrl.openStream()));
                    String readStr;
                    while ((readStr = input.readLine()) != null) {
                        content += readStr + "\r\n";
                    }
                    input.close();
                    resp.setContent(content);       
                } catch (IOException ex) {
                    System.err.println("Błąd w trakcie połączenia: " + ex);
                }

            } else 
                System.err.println("Błąd zapytania");
        
        } catch (IOException e){
            System.err.println("Błąd połączenia: " + e);
        }

         return resp;
    }
    
    
}
