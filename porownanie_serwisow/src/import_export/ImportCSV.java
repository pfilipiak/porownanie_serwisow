/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package import_export;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImportCSV {

    public static void skopiujPlikCSV(String zrodlo, String delimeter, String nazwaTabeli){
        Statement stmt = null;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        

        String url = "jdbc:postgresql://93.---.233.149:5432/zpi_project";
        String user = "postgres";
        String password = "---";

        try {

            con = DriverManager.getConnection(url, user, password);
             
            BufferedReader fr = new BufferedReader(new FileReader(zrodlo));            
            
            String sql ="create table tmp_"+ nazwaTabeli+"( ";
            String line =  fr.readLine();
            String[] nazwyKolumn = line.split(delimeter);
            for (int i =0; i<nazwyKolumn.length;i++){
                sql = sql + " \"" + nazwyKolumn[i] +"\" text";
                if (i!=nazwyKolumn.length -1 ){
                    sql = sql + ", ";
                }
            }
            sql = sql + ")";
            stmt = con.createStatement();
            
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println(sql);
            while ((line = fr.readLine())!= null){
                line=line.replace("\""+delimeter+"\"", "#");
                line= line.replace("\"", "").trim();
                String[] insertyKolumn = line.split("#");               
                
                sql = "insert into tmp_"+ nazwaTabeli+ " values(";
                for (int i =0; i<insertyKolumn.length;i++){
                sql = sql + " '" + insertyKolumn[i] +"' ";
                    if (i!=nazwyKolumn.length -1 ){
                        sql = sql + ", ";
                    }                
                }
                sql = sql + ")";
                System.out.println(sql);
                stmt = con.createStatement();
                
                stmt.executeUpdate(sql);
                stmt.close();
                System.out.println("");
            
            }
                      
        } catch (SQLException | IOException ex) {
            Logger lgr = Logger.getLogger(ImportCSV.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
               

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(ImportCSV.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
            
        }
    }
    
    
    
       //skopiujPlikCSV("/home/pfilipiak/ZPI_dane/ceneo.pl-domain_organic-pl.csv",";","nazwa_tabeli");
    
}
