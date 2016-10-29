/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porownanie_serwisow.api;
import java.net.*;
import java.io.*;
import java.util.*;

/**
 **
 * @author Adrian
 */

public class SemrushAPIConnector {
    private String apiKey;
    private String apiPath = "https://www.semrush.com/"; //zmienic
    
    public SemrushAPIConnector(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public void setAPIKey(String apiKey){
        this.apiKey = apiKey;
    }
    
    public String getAPIKey(){
        return this.apiKey;
    }
        
    public void buildQuery(String website, String input_param, String filter, String output_param, int rows) {
        //
        
        this.apiPath = "http://api.semrush.com/?type=domain_organic&key="
                        + this.apiKey
                        + "&export_escape=1&display_limit=" + rows
                        + "&export_columns=Ph,Ur,Po,Nq,Tr,Ts&domain="
                        + website
                        + "&display_sort=tr_desc&database=pl";
    }
    
    
    public APIData runQuery(){
        
        APIData respApi = new APIData();
        respApi.setApiKey(this.apiKey);
        String content = "";
        
            
        try {
            int statusCode;
            URL semRushUrl = new URL(apiPath);
            HttpURLConnection http = (HttpURLConnection)semRushUrl.openConnection();
            statusCode = http.getResponseCode();
            respApi.setApiCode(statusCode);
            Integer i = 0;
            if (statusCode == 200) {
                try {
                    BufferedReader input = new BufferedReader(new InputStreamReader(semRushUrl.openStream()));
                    String readStr;
                    while ((readStr = input.readLine()) != null) {
                        i++;
                        if (!readStr.isEmpty()) {
                            content += readStr + "\r\n";
                            String cc[] = readStr.split("[;]+");
                            if (cc.length>2 && i>1){
                                APIDataEntity apiDE = new APIDataEntity();
                                apiDE.setKeyword(cc[0].replace("\"", "")); //poprawic, malo eleg
                                apiDE.setLandingPage(cc[1].replace("\"", ""));
                                //System.out.println("cc:"+cc[2].replace("\"", "").trim());
                                apiDE.setPosition(cc[2].replace("\"", "").trim());
                                apiDE.setVolumen(Integer.parseInt(cc[3].replace("\"", "").trim()));
                                apiDE.setTrafficShare(Float.parseFloat(cc[4].replace("\"", "").trim()));
                                apiDE.setTimestamp(Long.parseLong(cc[5].replace("\"", "").trim()));
                                respApi.setAPIDataEntity(apiDE);
                             }
                        }
                    }
                    System.out.println(content);
                    input.close();
                    
                } catch (IOException ex) {
                    System.err.println("Błąd w trakcie połączenia: " + ex);
                }

            } else 
                System.err.println("Błąd zapytania");
        
        } catch (IOException e){
            System.err.println("Błąd połączenia: " + e);
        }

         return respApi;
    }
 //
    
     public APIData fetchData(){
        
        APIData respApi = new APIData();
        respApi.setApiKey(this.apiKey);
        String content = "";
            
        try {
            int statusCode;
            URL semRushUrl = new URL(apiPath);
            HttpURLConnection http = (HttpURLConnection)semRushUrl.openConnection();
            statusCode = http.getResponseCode();
            respApi.setApiCode(statusCode);

            if (statusCode == 200) {
                try {
                    BufferedReader input = new BufferedReader(new InputStreamReader(semRushUrl.openStream()));
                    String readStr;
                    while ((readStr = input.readLine()) != null) {
                        if (!readStr.isEmpty()) {
                            content += readStr + "\r\n";
                            String cc[] = readStr.split("[;]+");
                            if (cc.length>2){
                                APIDataEntity apiDE = new APIDataEntity();
                                apiDE.setKeyword(cc[0].replace("\"", "")); //poprawic, malo eleg
                                apiDE.setLandingPage(cc[1].replace("\"", ""));
                                //apiDE.setPosition(cc[2].replace("\"", ""));
                                //apiDE.setVolumen(Integer.parseInt(cc[3].replace("\"", "")));
                                //apiDE.setTrafficShare(Float.parseFloat(cc[4].replace("\"", "")));
                                //apiDE.setTimestamp(Long.parseLong(cc[5].replace("\"", "")));
                                respApi.setAPIDataEntity(apiDE);
                             }
                        }
                    }
                    System.out.println(content);
                    input.close();
                    
                } catch (IOException ex) {
                    System.err.println("Błąd w trakcie połączenia: " + ex);
                }

            } else 
                System.err.println("Błąd zapytania");
        
        } catch (IOException e){
            System.err.println("Błąd połączenia: " + e);
        }

         return respApi;
    }
    
}
