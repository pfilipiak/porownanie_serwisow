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
        
        connection = DriverManager.getConnection("jdbc:postgresql://--.158.233.149:5432/zpi_project", "postgres", "-----" );
        Statement stmt = connection.createStatement();
           
        ResultSet rs = stmt.executeQuery("select * from bp_produkt_oleole order by traffic_share desc limit 10");
           
        ResultSetMetaData rsMD = rs.getMetaData();
        int columnsNumber = rsMD.getColumnCount();
         while (rs.next()) {
                APIWebsitePhrases apiDE = new APIWebsitePhrases(); //frazy, urle i pozycje
                for (int i = 1; i <= columnsNumber; i++) {
                    
                    String apiAttr = rsMD.getColumnName(i);
                    String columnValue = rs.getString(i);
                    System.out.println(columnValue + "\t" + "\t"+ apiAttr);   
                    }
                    
                }

        rs.close();
        stmt.close();
        connection.close();
           
    }
}