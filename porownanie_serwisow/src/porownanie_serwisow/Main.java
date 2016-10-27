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
        //String apiKey = "-nie-zapisywac-api-na-githubie-";
        
        String apiKey = "----";
        SemrushAPIConnector sm = new SemrushAPIConnector(apiKey);  
        sm.buildQuery("wp.pl", "", "", "", 3);
        APIData test = sm.runQuery();
        System.out.println(test.getAPIKey());
        System.out.println(test.getHTTPResCode());
        System.out.println(test.getContent()); 
        
        System.out.println(test.printAPIDataEntity()); 
        //int count[] = {34, 22,10,60,30,22};
        /*Map<String[], String[]> map = new HashMap<String[], String[]>();
        String [] s1 = {"kupa", "dupa"};
        String [] s2 = {"kupa", "dupa", "lupa"};
        map.put (s1,s1);
        map.put(s2,s2);
              
        System.out.println(Arrays.toString(map.get(s2)));
        */
       // mapa.put("pierwszy", 1);
       // mapa.put("drugi", 2);
       // System.out.println(mapa.get("pierwszy")); //wypisze "1"
        
    }

    public Main() {
 
    }
    
    
}
