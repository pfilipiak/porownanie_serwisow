/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porownanie_serwisow;
import porownanie_serwisow.api.*;

 /*
 * @author pfilipiak
 */
public class DemoMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Kod demo do Main.java
        //String apiKey = "-nie-zapisywac-api-na-githubie-";
        String apiKey = "2fc1906300ddc89289961a1c3642a273";
        Boolean isdone;
        Long credits;
              SemrushAPIConnector sem = new SemrushAPIConnector(apiKey);  
        credits = sem.TestAPIKey();
        System.out.println("credits:" + credits);
        Boolean testMode = true;
        // private Boolean testMode = true; w SemRushAPIConnector + urle do danych temp
        
        if (testMode){
            /* 
            w testcie mamy dane skopiowane z api dla
               - raport fraz live (24.11.2016), ceneo, 111 tys rekodrów
               - raport fraz historycznych (październik 2016), ceneo, 47 tys rekrodów
            */
            APIData onlyTest = new APIData();           
            sem.getWebsitePhrasesReport(onlyTest, "pwr.wroc.pl", "pl", "live", 8); //top 5 fraz           
            System.out.println("Czy dane live: " + onlyTest.getIsLive() + ", data " + onlyTest.getDate());
            System.out.println("Ile wierszy:" + onlyTest.getResultsWebsitePhrases().size());
            System.out.println(onlyTest.printAPIWebsitePhrases(5));
            
            sem.getWebsitePhrasesReport(onlyTest, "pwr.wroc.pl", "pl", "201610", 8); //top 5 fraz           
            System.out.println(onlyTest.printAPIWebsitePhrases(5));
            System.out.println("Czy dane live: " + onlyTest.getIsLive() + ", data " + onlyTest.getDate());
        }
        
        if (testMode==false && credits > 0L) {
        APIData test = new APIData();
                
        //live demo
        System.out.println("DEMO DANYCH LIVE:");
        
        sem.getWebsiteStatsReport(test, "pwr.wroc.pl", "pl", true); //statystyki bieżący miesiąc
        sem.getWebsitePhrasesReport(test, "pwr.wroc.pl", "pl", "201611", 5); //top 5 fraz
        sem.getWebsiteCompetitorsReport(test, "pwr.wroc.pl", "pl", 5); //top 5 konkurentów
               
        //System.out.println(sem.getApiPath());  //wyswietla zapytanie do ostatnio wykonanego raportu
        System.out.println(test.printAPIWebsiteStats());
        System.out.println(test.printAPIWebsitePhrases());
        System.out.println(test.printAPIWebsiteCompetitors());
        System.out.println("Czy dane live: " + test.getIsLive() + ", data " + test.getDate());

        //dane histryczne - brak konkurentów historycznych (bezposrednio przez API, tylko nasza DB)
        System.out.println("\r\nDEMO DANYCH HISTORYCZNYCH:");
        
        sem.getWebsitePhrasesReport(test, "pwr.wroc.pl", "pl", "201606", 5); //top 5 fraz czerwiec 2016
        sem.getWebsiteStatsReport(test, "pwr.wroc.pl", "pl", false); //statystyki 12 mc wstecz
        
        System.out.println(test.printAPIWebsiteStats());
        System.out.println(test.printAPIWebsitePhrases());
        System.out.println(test.getIsLive() + " " + test.getDate());
        
        }
    }

        

    public DemoMain() {
 
    }
    
    
}
