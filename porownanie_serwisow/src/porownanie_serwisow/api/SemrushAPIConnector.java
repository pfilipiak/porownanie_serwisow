/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porownanie_serwisow.api;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

/**
 *
 * @author Adrian
 */

public class SemrushAPIConnector {
    private String apiKey;
    private String apiPath = "https://www.semrush.com/"; //zmienic
    private String [] inputParams;
    private String lang = "pl";
    private String currDate;
    private String mainWebsite;
    private final Map<String, String> APIDictionary = new HashMap<String, String>();

    private int dictIndexOf(String key){
        String[] arr = this.getInputParams();
        String value = APIDictionary.get(key.toLowerCase());
        
        if (arr != null && value != null) {
            for (int i = 0; i < arr.length; i++)
                if (value.equals(arr[i])) return i;
        }       
        return -1;
    }
      
    
    public SemrushAPIConnector(String apiKey) {
        this.apiKey = apiKey;
        //https://www.semrush.com/api-analytics/#filters
        this.APIDictionary.put("phrase", "Ph");
        this.APIDictionary.put("url", "Ur");
        this.APIDictionary.put("position", "Po");
        this.APIDictionary.put("volumen", "Nq");
        this.APIDictionary.put("trafficshare", "Tr");
        this.APIDictionary.put("timestamp", "Ts");
        
        this.APIDictionary.put("traffic", "Ot");
        this.APIDictionary.put("keywords", "Or");
        this.APIDictionary.put("hist_date", "Dt");
        
        this.APIDictionary.put("competitor", "Dn");
        this.APIDictionary.put("relevance", "Cr");
        this.APIDictionary.put("commonkeywords", "Np");
        
    }
    
    public Long TestAPIKey(){
        
        Long credits = -2L; //wartośc gdy bład przy połączeniu do SemRush
        String apiTest = this.apiPath + "users/countapiunits.html?key=" + this.apiKey; 
        try {
            int statusCode;
            URL semRushUrl = new URL(apiTest);
            HttpURLConnection http = (HttpURLConnection)semRushUrl.openConnection();
            statusCode = http.getResponseCode();
            if (statusCode == 200) {
                BufferedReader input = new BufferedReader(new InputStreamReader(semRushUrl.openStream()));
                String readStr;
                while ((readStr = input.readLine()) != null)
                   if (!readStr.isEmpty()) { //jesli jest o
                        try {
                            credits = (Long) NumberFormat.getInstance().parse(readStr.trim());
                        } catch(ParseException e) {
                            credits = -1L; //bład klucza API
                        }                        
                    }    
            }
            
         } catch (IOException e){
            System.err.println("Błąd połączenia: " + e);
        }
        return credits; 
    }
        
    public void buildQuery(String website, String lang, String report_type, String[] params, String YYYYMM, int rows) {
        //   
        String localDate = new SimpleDateFormat("yyyyMM").format(new Date());
        if (localDate.equals(YYYYMM)) YYYYMM = "live";
        
        //
        String diplay_date = "";
        if (!YYYYMM.equals("live") && report_type.equals("domain_organic")) {
            diplay_date = "&display_date="+YYYYMM+"15"; //15 = bazy danych
            this.currDate = YYYYMM;
        } else this.currDate = localDate;
        
        //
        String sort = "tr_desc";
        if (report_type.equals("domain_rank_history")) sort = "dt_desc";
        if (report_type.equals("domain_organic_organic")) sort = "np_desc";
        
        //
        this.apiPath = "http://api.semrush.com/"
                        + "?type=" + report_type//"domain_organic"
                        + "&key=" + this.apiKey
                        + "&export_escape=1&display_limit=" + rows
                        + "&export_columns=" + String.join(",", params)
                        + "&domain="+ website
                        + "&database=" + lang
                        + diplay_date
                        + "&display_sort="+sort;
        this.inputParams = params;
        this.mainWebsite = website;
        this.lang = lang;
    }
    
     public APIData fetchData(String[] input_param_labels, Integer rep_type){
        APIData responseApi = new APIData();
        String content = "";    
                
        try {
            int statusCode;
            URL semRushUrl = new URL(apiPath);
            HttpURLConnection http = (HttpURLConnection)semRushUrl.openConnection();
            statusCode = http.getResponseCode();
            
            responseApi.setApiCode(statusCode);
            if (statusCode == 200) {
                try (BufferedReader input = new BufferedReader(new InputStreamReader(semRushUrl.openStream()))) {
                        String readStr;
                        Integer line = 0;
                        while ((readStr = input.readLine()) != null) {
                            if (!readStr.isEmpty() && line > 0) { //jesli jest okey, pomijamy pierszwy wiersz - nagłowki
                                readStr = readStr.replace("\";\"", "#");
                                readStr = readStr.replace("\"", "").trim();
                                String output[] = readStr.split("[#]+"); //czyscimy wynik, rozbijamy do tablicy
                                //System.out.println(readStr);
                                if (output != null){                                    
                                    //linia po linii budujemy mape z obiektami
                                    switch (rep_type) {
                                        case 0:
                                            //frazy, urle i pozycje
                                            APIWebsitePhrases apiDE = new APIWebsitePhrases();
                                            for (int k = 0; k < output.length; k++){
                                                if (input_param_labels[k] != null)
                                                    apiDE.setAttribute(input_param_labels[k], output[k]);
                                                // System.out.println(input_param_labels[k] + " : " + output[k]);
                                            }   responseApi.addAPIWebsitePhrase(apiDE);
                                            break;
                                        case 1:
                                            //statystyki serwisu
                                            APIWebsiteStats apiWS = new APIWebsiteStats();
                                            for (int k = 0; k < output.length; k++){
                                                if (input_param_labels[k] != null)
                                                    apiWS.setAttribute(input_param_labels[k], output[k]);
                                            }   responseApi.addAPIWebsiteStat(this.mainWebsite, apiWS);
                                            break;
                                        default:
                                            //konkurenci
                                            APIWebsiteCompetitors apiComp = new APIWebsiteCompetitors();
                                            for (int k = 0; k < output.length; k++){
                                                if (input_param_labels[k] != null)
                                                    apiComp.setAttribute(input_param_labels[k], output[k]);
                                            }   responseApi.addAPIWebsiteCompetitor(apiComp);
                                            break;
                                    }
                                    
                                } //end if (output != null)
                            } //end if (!readStr.isEmpty()
                            line++;
                        } //end while
                        System.out.println(content);
                        
                    } catch (IOException e) {
                        System.err.println("Błąd w czasie pobierania danych: " + e);
                    }

                
            } else 
                System.err.println("Błąd zapytania");
        
        } catch (IOException e){
            System.err.println("Błąd połączenia: " + e);
        }

        return responseApi;        

    }
    //raporty
     
    public boolean getPhrasesReport(APIData api_data, String website, String lang, String YYYYMM, int rows) {
        
        String[] raportFraz = {"phrase", "url", "position", "volumen", "trafficShare", "timestamp"};
        //String[] raportFraz = {"Ph","Ur","Po","Nq","Tr","Ts"};
        List<String> param_labels = new ArrayList<>();
        List<String> params = new ArrayList<>();

        for (int i=0; i < raportFraz.length; i++) {
            if (APIDictionary.get(raportFraz[i].toLowerCase()) != null) {
               params.add(APIDictionary.get(raportFraz[i].toLowerCase()));
               param_labels.add(raportFraz[i]);
               }
            }
        //System.err.println( String.join(",", params) + " : " + String.join(",", param_labels));
        String type = "domain_organic";
        if (params.size()>0) {
            buildQuery(website, lang, type, params.toArray(new String[params.size()]), YYYYMM, rows);   
            APIData temp_data = fetchData( param_labels.toArray(new String[param_labels.size()]), 0);
            if (temp_data.getApiCode() == 200) {
                api_data.setApiCode(200);
                api_data.setApiKey(this.getAPIKey());
                api_data.setLang(this.getLang());
                api_data.setDate(getCurrDate());
                api_data.setIsLive(YYYYMM.equals("live"));
                api_data.setResultsWebsitePhrases(temp_data.getResultsWebsitePhrases());
                return true;
            }
        }
        
        return false;
    
    }
    
    
    public boolean getDomainStatsReport(APIData api_data, String website, String lang, String YYYYMM) {
        /* 
        obecnie pobieramy albo dane live tj biezacy miesiac, 1 wynik
        albo 12 miesiacy wstecz (do biezacego). Mozna wiecej - max 36.
        czyli albo live albo 12 mc historycznych. 
        Nie ma znaczenia czy czy podasz datę przed 2 mc czy 10 lat.
        Attrybut tak zostawiam bo w razie czego mozemy uwzglednic dokladnie żądany zakres dat
        */
        String type = "domain_rank_history"; //domyślnie raport historyczny, tzn do miesiąca poprzedzajacego biezacy
        String localDate = new SimpleDateFormat("yyyyMM").format(new Date());
        String[] raportFraz = {"hist_date", "traffic", "keywords"};
                
        if (localDate.equals(YYYYMM) || YYYYMM.equals("live")) {
            type = "domain_rank"; //jeśli data jak bieżący miesiąc to SemRush zwraca dane "live"
            YYYYMM = "live";
            raportFraz = new String[] {"traffic", "keywords"};
        }
        
        List<String> param_labels = new ArrayList<>();
        List<String> params = new ArrayList<>();

        for (int i=0; i < raportFraz.length; i++) {
            if (APIDictionary.get(raportFraz[i].toLowerCase()) != null) {
               params.add(APIDictionary.get(raportFraz[i].toLowerCase()));
               param_labels.add(raportFraz[i]);
               }
            }
        //System.err.println( String.join(",", params) + " : " + String.join(",", param_labels));

        if (params.size()>0) {
            buildQuery(website, lang, type, params.toArray(new String[params.size()]), YYYYMM, 12);   
            APIData temp_data = fetchData( param_labels.toArray(new String[param_labels.size()]), 1);
            if (temp_data.getApiCode() == 200) {
                api_data.setApiCode(200);
                api_data.setApiKey(this.getAPIKey());
                api_data.setLang(this.getLang());
                api_data.setDate(YYYYMM.equals("live")?localDate:YYYYMM);
                api_data.setIsLive(YYYYMM.equals("live"));
                api_data.setResultsWebsiteStats(temp_data.getResultsWebsiteStats());
                return true;
            }
        }
        
        return false;
    }

    public boolean getCompetitorsReport(APIData api_data, String website, String lang, Integer rows) {

        String type = "domain_organic_organic"; //domyślnie raport historyczny, tzn do miesiąca poprzedzajacego biezacy
        String[] raportFraz = {"competitor", "relevance", "commonKeywords" ,"traffic", "keywords"};
        String localDate =  new SimpleDateFormat("yyyyMM").format(new Date());
        List<String> param_labels = new ArrayList<>();
        List<String> params = new ArrayList<>();

        for (int i=0; i < raportFraz.length; i++) {
            if (APIDictionary.get(raportFraz[i].toLowerCase()) != null) {
               params.add(APIDictionary.get(raportFraz[i].toLowerCase()));
               param_labels.add(raportFraz[i]);
               }
            }
        //System.err.println( String.join(",", params) + " : " + String.join(",", param_labels));

        if (params.size()>0) {
            buildQuery(website, lang, type, params.toArray(new String[params.size()]), "live", rows);   
            APIData temp_data = fetchData( param_labels.toArray(new String[param_labels.size()]), 2);
            if (temp_data.getApiCode() == 200) {
                api_data.setApiCode(200);
                api_data.setApiKey(this.getAPIKey());
                api_data.setLang(this.getLang());
                api_data.setDate(localDate);
                api_data.setIsLive(true);
                api_data.setResultsWebsiteCompetitors(temp_data.getResultsWebsiteCompetitors());
                return true;
            }
        }
        
        return false;
    }    
    
       
    //getters and setters
    
        
    public void setAPIKey(String apiKey){
        this.apiKey = apiKey;
    }
    
    public String getAPIKey(){
        return apiKey;
    }
    

    public String[] getInputParams() {
        return inputParams;
    }

    public String getLang() {
        return lang;
    }

    public String getApiPath() {
        return apiPath;
    }
    
    public String getCurrDate() {
        return currDate;
    }
    
    
}
