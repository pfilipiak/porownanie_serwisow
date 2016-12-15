package porownanie_serwisow.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.ResultSetMetaData;
import java.util.Arrays;
import java.util.Map;
import porownanie_serwisow.api.APIData;
import porownanie_serwisow.api.APIWebsitePhrases;
/**
 *
 * @author pfilipiak
 */
public class przyklad {
    public static void main(String[] args) throws SQLException{
        APIData ap = new  APIData();
        dbConnector db = new dbConnector();
        db.getWebsitePhrasesReport(ap,"media.pl", "pl", "live", 10 );
        Map<String[], APIWebsitePhrases> websiteKWs = ap.getResultsWebsitePhrases();
        String table[] = new String[6];
        
                
            if (websiteKWs != null) {
                for (String[] key : websiteKWs.keySet()) {
                    //map.get(key)....
                    table[5] += "['" + websiteKWs.get(key).getPhrase() + "', " + 
                                "'" + websiteKWs.get(key).getUrl() + "', " + 
                                "{v:" + websiteKWs.get(key).getPosition() + "}, " + 
                                "{v:" + websiteKWs.get(key).getVolumen()+ "}, " + 
                                "{v:" + websiteKWs.get(key).getTrafficShare() + "}],\r\n";
                    System.out.println("semdb:" + websiteKWs.get(key).getPhrase());
                }
            }

            table[5] = table[5].trim();
            table[5] = table[5].substring(0, table[5].length()-1);   
            System.out.println(Arrays.toString(table));
    }
}