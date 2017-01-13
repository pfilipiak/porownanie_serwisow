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
        ResultSet rs = stmt.executeQuery("Select (select name from d_bp_produkt where id = 1) as name, \n" +
"sum(month_1_current_ctr) as \"m-12\", sum(month_2_current_ctr) as \"m-11\", sum(month_3_current_ctr) as \"m-10\", sum(month_4_current_ctr) as \"m-9\", sum(month_5_current_ctr) as \"m-8\", sum(month_6_current_ctr) as \"m-7\", sum(month_7_current_ctr) as \"m-6\", sum(month_8_current_ctr) as \"m-5\", sum(month_9_current_ctr) as \"m-4\", sum(month_10_current_ctr) as \"m-3\", sum(month_11_current_ctr) as \"m-2\", sum(month_12_current_ctr) as \"m-1\" from bp_produkt_saturn\n" +
"union\n" +
"Select (select name from d_bp_produkt where id = 2) as name, \n" +
"sum(month_1_current_ctr) as \"m-12\", sum(month_2_current_ctr) as \"m-11\", sum(month_3_current_ctr) as \"m-10\", sum(month_4_current_ctr) as \"m-9\", sum(month_5_current_ctr) as \"m-8\", sum(month_6_current_ctr) as \"m-7\", sum(month_7_current_ctr) as \"m-6\", sum(month_8_current_ctr) as \"m-5\", sum(month_9_current_ctr) as \"m-4\", sum(month_10_current_ctr) as \"m-3\", sum(month_11_current_ctr) as \"m-2\", sum(month_12_current_ctr) as \"m-1\" from bp_produkt_euro\n" +
"union\n" +
"Select (select name from d_bp_produkt where id = 3) as name, \n" +
"sum(month_1_current_ctr) as \"m-12\", sum(month_2_current_ctr) as \"m-11\", sum(month_3_current_ctr) as \"m-10\", sum(month_4_current_ctr) as \"m-9\", sum(month_5_current_ctr) as \"m-8\", sum(month_6_current_ctr) as \"m-7\", sum(month_7_current_ctr) as \"m-6\", sum(month_8_current_ctr) as \"m-5\", sum(month_9_current_ctr) as \"m-4\", sum(month_10_current_ctr) as \"m-3\", sum(month_11_current_ctr) as \"m-2\", sum(month_12_current_ctr) as \"m-1\" from bp_produkt_media_expert\n" +
"union\n" +
"Select (select name from d_bp_produkt where id = 4) as name, \n" +
"sum(month_1_current_ctr) as \"m-12\", sum(month_2_current_ctr) as \"m-11\", sum(month_3_current_ctr) as \"m-10\", sum(month_4_current_ctr) as \"m-9\", sum(month_5_current_ctr) as \"m-8\", sum(month_6_current_ctr) as \"m-7\", sum(month_7_current_ctr) as \"m-6\", sum(month_8_current_ctr) as \"m-5\", sum(month_9_current_ctr) as \"m-4\", sum(month_10_current_ctr) as \"m-3\", sum(month_11_current_ctr) as \"m-2\", sum(month_12_current_ctr) as \"m-1\" from bp_produkt_media_markt\n" +
"union\n" +
"Select (select name from d_bp_produkt where id = 5) as name, \n" +
"sum(month_1_current_ctr) as \"m-12\", sum(month_2_current_ctr) as \"m-11\", sum(month_3_current_ctr) as \"m-10\", sum(month_4_current_ctr) as \"m-9\", sum(month_5_current_ctr) as \"m-8\", sum(month_6_current_ctr) as \"m-7\", sum(month_7_current_ctr) as \"m-6\", sum(month_8_current_ctr) as \"m-5\", sum(month_9_current_ctr) as \"m-4\", sum(month_10_current_ctr) as \"m-3\", sum(month_11_current_ctr) as \"m-2\", sum(month_12_current_ctr) as \"m-1\" from bp_produkt_morele\n" +
"union\n" +
"Select (select name from d_bp_produkt where id = 6) as name, \n" +
"sum(month_1_current_ctr) as \"m-12\", sum(month_2_current_ctr) as \"m-11\", sum(month_3_current_ctr) as \"m-10\", sum(month_4_current_ctr) as \"m-9\", sum(month_5_current_ctr) as \"m-8\", sum(month_6_current_ctr) as \"m-7\", sum(month_7_current_ctr) as \"m-6\", sum(month_8_current_ctr) as \"m-5\", sum(month_9_current_ctr) as \"m-4\", sum(month_10_current_ctr) as \"m-3\", sum(month_11_current_ctr) as \"m-2\", sum(month_12_current_ctr) as \"m-1\" from bp_produkt_oleole;\n" +
"");                                                  
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