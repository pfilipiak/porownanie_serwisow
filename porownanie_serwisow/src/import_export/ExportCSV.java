/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package import_export;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 *
 * @author pfilipiak
 */
public class ExportCSV {
    public static void exportDoCSV(String zrodlo, String delimeter, String nazwaTabeli){
        Statement stmt = null;
        Connection conn = null;
        
        
        String url = "jdbc:postgresql://93.--.233.149:5432/zpi_project";
        String user = "postgres";
        String password = "--";

        try {

            conn = DriverManager.getConnection(url, user, password);
       conn.setAutoCommit(false);
             stmt = conn.createStatement();
            ResultSet rset = stmt
                    .executeQuery("select * from "+nazwaTabeli);
            ResultSetMetaData rsmd = rset.getMetaData();
            rset.next();
            FileWriter cname = new FileWriter(zrodlo);
            System.out.println("No of columns in the table:"
                    + rsmd.getColumnCount());
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.print(rsmd.getColumnName(i) + " ");
                   cname.append("\""); 
                   cname.append(rsmd.getColumnName(i)); 
                   cname.append("\""); 
                   if (i<rsmd.getColumnCount())
                   cname.append(delimeter);
                   cname.flush();
                   cname.write(System.getProperty( "line.separator" ));
                 
            }
 
            System.out.println();
            while (rset.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.print(rset.getString(i) + " ");
                   cname.append("\""); 
                   cname.append(rset.getString(i)); 
                   cname.append("\""); 
                   if (i<rsmd.getColumnCount())
                   cname.append(delimeter);
                   cname.flush();
                   
                 
                }
                cname.write(System.getProperty( "line.separator" ));                
            }
            stmt.close();
 
        }
 
        catch (Exception e) {
            System.err.println("Unable to connect to database: " + e);
 
        }
    }
//    skopiujPlikCSV("/home/pfilipiak/ZPI_dane/ceneo.pl-domain_organic-pl.csv",";","nazwa_tabeli");
}
