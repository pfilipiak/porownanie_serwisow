/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porownanie_serwisow;
import porownanie_serwisow.api.*;
import java.util.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;

 /*
 * @author pfilipiak
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
  
        // TODO code application logic here
        //String apiKey = "-nie-zapisywac-api-na-githubie-";
        
        String apiKey = "...................";

        Long credits;
        SemrushAPIConnector sem = new SemrushAPIConnector(apiKey);  
        sem.testMode = true;
        credits = sem.TestAPIKey();
        System.out.println("credits:" + credits);
        ///
        System.out.println("\r\nDEMO DANYCH HISTORYCZNYCH:");
        APIData test1 = new APIData();    
        sem.getWebsitePhrasesReport(test1,"test.pl","pl", "201610", 10);
        System.out.println(test1.printAPIWebsitePhrases(10));
        sem.getWebsiteCompetitorsReport(test1, "test.pl", "pl", 10);
        System.out.println(test1.printAPIWebsiteCompetitors());
        sem.getWebsiteStatsReport(test1, "test.pl", "pl", false);
        System.out.println(test1.printAPIWebsiteStats());
        
        ////
        sem.testMode = true;
        System.out.println("credits:" + credits);
        Boolean testMode = true;
        // private Boolean testMode = true; w SemRushAPIConnector + urle do danych temp
    }


    public Main() {
 
    }
    
    
}
