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
    private String apiPath = "";
    private String apiCreditsPath = "https://www.semrush.com/users/countapiunits.html?key=";
    private String [] inputParams;
    private String lang = "pl";
    private String currDate;
    private Boolean isLive = false;
    private String mainWebsite;
    private final Map<String, String> APIDictionary = new HashMap<String, String>();
    //do testów, jesli masz zapisany output API
    public Boolean testMode = true;
    private String apiTestOrganicLiveData = "http://localhost:10001/semrush/semrush_live_demo"; 
    private String apiTestOrganicHistData = "http://localhost:10001/semrush/semrush_201610_demo"; 
    
    
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
        String apiTest = apiCreditsPath + apiKey; 
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
                            credits = Long.parseLong(readStr.trim());
                        } catch(NumberFormatException er) {
                            credits = -1L; //bład klucza API
                            //System.out.println("credits err:" + er);
                        }                        
                    }    
            }
            
         } catch (IOException e){
            System.err.println("Błąd połączenia: " + e);
        }
        
        return credits; 
    }
        
    public boolean buildQuery(String website, String lang, String report_type, String[] params, String YYYYMM, int rows)    {
        //jesli testy api
        String testAPIPath = "";
        //walidacja daty, konwersja live
        Boolean qresult = false;
        Date today = new Date();
        String localDate = new SimpleDateFormat("yyyyMM").format(today);
        if (YYYYMM.equals("live") || YYYYMM.isEmpty()) YYYYMM = localDate;
        
        try {
            Date checkdate = new SimpleDateFormat("yyyyMM").parse(YYYYMM);
            if (today.before(checkdate)) return false;
            
        } catch (ParseException ex) {
            System.err.println("Bład formatu daty" + ex);
        }
        
        
        //Raport fraz
        String sort = "tr_desc"; //sortuj wg udziału fraz w ruchu
        String diplay_date = ""; //dane live - brak filtru
        if (!localDate.equals(YYYYMM) && report_type.equals("domain_organic")) { //dane historyczne dla fraz
            diplay_date = "&display_date="+YYYYMM+"15"; //15 = bazy danych
            this.currDate = YYYYMM;
            this.isLive = false;
            testAPIPath = this.apiTestOrganicHistData;
            qresult = true;
        } else {
            this.currDate = localDate; 
            this.isLive = true;
            testAPIPath = this.apiTestOrganicLiveData;
            qresult = true;
        }
        
        //Raport statysytk
        if (report_type.equals("domain_rank_history")) {
            sort = "dt_desc";
            qresult = true;
        } //sortowanie staty historyczne wg daty
        
        //Raport konkurncji
        if (report_type.equals("domain_organic_organic")){
            sort = "np_desc";
            qresult = true;
        }//sortowanie konkurencja
        
        //Buduj zapytanie do API
        this.apiPath = "http://api.semrush.com/"
                        + "?type=" + report_type//np. domain_organic
                        + "&key=" + this.apiKey
                        + "&export_escape=1&display_limit=" + rows
                        + "&export_columns=" + String.join(",", params)
                        + "&domain="+ website
                        + "&database=" + lang
                        + diplay_date
                        + "&display_sort="+sort;
        
        if (this.testMode == true) this.apiPath = testAPIPath;
        
        
        //pozostałe setter'y - na potrzeby monitororwania stanu obiektu
        this.inputParams = params;
        this.mainWebsite = website;
        this.lang = lang;
        
        return true;
    }
    
     public APIData fetchData(String[] input_param_labels, Integer rep_type){
        APIData responseApi = new APIData();
        String content = "";    
                
        try {
            int statusCode = 0;
            URL semRushUrl = new URL(apiPath);
            //HttpURLConnection http = (HttpURLConnection)semRushUrl.openConnection();
            //http.getResponseCode();
            
            Long testapi = TestAPIKey();
            if (testapi > 0L) 
                statusCode = 200; 
            else statusCode = testapi.intValue();
            if (this.testMode == true) statusCode = 200; 
            //System.err.println("hghgh: "+ TestAPIKey() + ", " + this.apiKey + ", " + statusCode);
            
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
                                
                                if (output != null){                                    
                                    //linia po linii budujemy mape z obiektami
                                    switch (rep_type) {
                                        case 0:
                                            //frazy, urle i pozycje
                                            APIWebsitePhrases apiDE = new APIWebsitePhrases();
                                            for (int k = 0; k < output.length; k++){
                                                if (input_param_labels[k] != null)
                                                apiDE.setAttribute(input_param_labels[k], output[k]);
                                                //System.out.println(input_param_labels[k] + " : " + output[k]);
                                            } responseApi.addAPIWebsitePhrase(apiDE);
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
                                            //dane o konkurencji
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
     
    public boolean getWebsitePhrasesReport(APIData api_data, String website, String lang, String YYYYMM, int rows) {
        
               
        String[] raportFraz = {"phrase", "url", "position", "volumen", "trafficShare", "timestamp"};
        //String[] raportFraz = {"Ph","Ur","Po","Nq","Tr","Ts"};
        List<String> param_labels = new ArrayList<>();
        List<String> params = new ArrayList<>();
        if (YYYYMM.isEmpty()) YYYYMM = "live";
        
        for (int i=0; i < raportFraz.length; i++) {
            if (APIDictionary.get(raportFraz[i].toLowerCase()) != null) {
               params.add(APIDictionary.get(raportFraz[i].toLowerCase()));
               param_labels.add(raportFraz[i]);
               }
            }
        //System.err.println( String.join(",", params) + " : " + String.join(",", param_labels));
        String type = "domain_organic";
        if (params.size()>0) {
            Boolean query = buildQuery(website, lang, type, params.toArray(new String[params.size()]), YYYYMM, rows);
            if (query == false) return false;
            APIData temp_data = fetchData( param_labels.toArray(new String[param_labels.size()]), 0);
            if (temp_data.getApiCode() == 200) {
                api_data.setApiCode(200);
                api_data.setApiKey(this.getAPIKey());
                api_data.setLang(this.getLang());
                api_data.setDate(getCurrDate());
                api_data.setIsLive(this.isLive);
                api_data.setResultsWebsitePhrases(temp_data.getResultsWebsitePhrases());
                return true;
            }
        }
        
        return false;
    
    }
    
    
    public boolean getWebsiteStatsReport(APIData api_data, String website, String lang, Boolean live) {
        /* 
        live = false -> dane historyczne.
        Nie sa sie filtrować poprzez API, biorą X wierszy od danej daty (musi być sortowanie w zapytaniu)
        */ 
        String type = "domain_rank_history"; //domyślnie raport historyczny, tzn do miesiąca poprzedzajacego biezacy
        String[] raportFraz = {"hist_date", "traffic", "keywords"};
                
        //live
        String localDate = new SimpleDateFormat("yyyyMM").format(new Date()); 
        if (live) {
            type = "domain_rank"; //jeśli data jak bieżący miesiąc to SemRush zwraca dane "live"
            raportFraz = new String[] {"traffic", "keywords"};
            
        } else {
          Calendar c = Calendar.getInstance(); 
          c.setTime( new Date()); 
          c.add(Calendar.MONTH, -1);
          localDate = new SimpleDateFormat("yyyyMM").format(c.getTime());
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
            Boolean query = buildQuery(website, lang, type, params.toArray(new String[params.size()]), localDate, 12);  
            if (query == false) return false;
            APIData temp_data = fetchData( param_labels.toArray(new String[param_labels.size()]), 1);
            if (temp_data.getApiCode() == 200) {
                api_data.setApiCode(200);
                api_data.setApiKey(this.getAPIKey());
                api_data.setLang(this.getLang());
                api_data.setDate(localDate);
                api_data.setIsLive(live);
                api_data.setResultsWebsiteStats(temp_data.getResultsWebsiteStats());
                return true;
            }
        }
        
        return false;
    }

    public boolean getWebsiteCompetitorsReport(APIData api_data, String website, String lang, Integer rows) {
        /*
        API nie oferuje histronycznych konurentów. 
        Jezeli Klient ma abonament to my z czasem zbudujemy listę historycznych konkurentów 
        */
        
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
            Boolean query = buildQuery(website, lang, type, params.toArray(new String[params.size()]), "live", rows); 
            if (query == false) return false;
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
