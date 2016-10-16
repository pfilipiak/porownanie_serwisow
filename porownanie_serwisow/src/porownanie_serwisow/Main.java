/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porownanie_serwisow;
import porownanie_serwisow.api.*;
/**
 *
 * @author pfilipiak
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //bobobobobobobo
        //System.err.println("dududu");
        //Goodbye gb = new Goodbye("fackup");
        //gb= new Goodbye();
        //Hello he = new Hello();
        String apiKey = "-nie-zapisywac-api-na-githubie-";
        semrushAPI sm = new semrushAPI(apiKey);  
        sm.buildQuery("wp.pl", "", "", "", 3);
        APIResponse test = sm.runQuery();
        System.out.println(test.getAPIKey());
        System.out.println(test.getHTTPResCode());
        System.out.println(test.getContent());
    }

    public Main() {
 
    }
    
    
}
