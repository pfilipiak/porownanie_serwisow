/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porownanie_serwisow;
import porownanie_serwisow.api.*;
import java.util.*;


 /*
 * @author pfilipiak
 */
public class DemoMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //String apiKey = "-nie-zapisywac-api-na-githubie-";
        String apiKey = "---";
        Boolean isdone;
        Long credits;
        
        SemrushAPIConnector sem = new SemrushAPIConnector(apiKey);  
        credits = sem.TestAPIKey();
        System.out.println("credits:" + credits);
        
        if (credits > 0) {
        APIData test = new APIData();
        
        isdone = sem.getPhrasesReport(test, "pwr.wroc.pl", "pl", "201611", 3);
        System.out.println("czy dziala:" + isdone);
               
        System.out.println(sem.getApiPath()); 
        System.out.println(test.printAPIWebsitePhrases());
        System.out.println(test.getIsLive() + " " + test.getDate());
        
        
        sem.getDomainStatsReport(test, "pwr.wroc.pl", "pl", "201611");
        System.out.println(sem.getApiPath()); 
        System.out.println(test.printAPIWebsiteStats());
        System.out.println(test.getIsLive() + " " + test.getDate());

        
        sem.getCompetitorsReport(test, "pwr.wroc.pl", "pl", 4);
        System.out.println(sem.getApiPath()); 
        System.out.println(test.printAPIWebsiteCompetitors());
        System.out.println(test.getIsLive() + " " + test.getDate());
        }
    }

    public DemoMain() {
 
    }
    
    
}
