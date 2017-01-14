package porownanie_serwisow.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import porownanie_serwisow.api.APIWebsitePhrases;
/**
 *
 * @author pfilipiak
 */
public class przykladDB {
    public static void main(String[] args) throws SQLException{
        
        Connection connection = null;
        
        connection = DriverManager.getConnection("jdbc:postgresql://93.158.233.149:5432/zpi_project", "postgres", "1@3$qWeR" );
        Statement stmt = connection.createStatement();
        //ResultSet rs = stmt.executeQuery("select distinct id, domain from bp_produkt_saturn_konkurencja");
        ResultSet rs = stmt.executeQuery(
                "select * from bp_produkt_saturn_zestawienie"
       );
        
        ResultSetMetaData rsMD = rs.getMetaData();
        int columnsNumber = rsMD.getColumnCount();
         while (rs.next()) {
                APIWebsitePhrases apiDE = new APIWebsitePhrases(); //frazy, urle i pozycje
                for (int i = 1; i <= columnsNumber; i++) {
                    
                    String apiAttr = rsMD.getColumnName(i);
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue + ",");   
                    //System.out.println(apiAttr + "\t" + columnValue);   
                    }
                    System.out.println("");
                }

        rs.close();
        stmt.close();
        connection.close();
           
    }
}