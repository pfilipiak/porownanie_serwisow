package porownanie_serwisow.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.ResultSetMetaData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import porownanie_serwisow.api.APIData;
import porownanie_serwisow.api.APIWebsitePhrases;
/**
 *
 * @author pfilipiak
 */
public class dbConnector {
    
    private String dbsource =  "jdbc:postgresql://--.158.233.149:5432/zpi_project";
    private String dbname =  "postgres";
    private String dbpass =  "---";
    private final Map<String, String> DBtoAPIDictionary = new HashMap<String, String>();
    
    
    public dbConnector() {
        
        //przykadowe kolumy w db: keyword,  position,  search_volume,  url, traffic_share, trends,  timestamp,  year,  month
        //naglowki DB do API (obiekt)
        this.DBtoAPIDictionary.put("keyword", "phrase");
        this.DBtoAPIDictionary.put("url", "url");
        this.DBtoAPIDictionary.put("position", "position");
        this.DBtoAPIDictionary.put("search_volume", "volumen");
        this.DBtoAPIDictionary.put("traffic_share", "trafficshare");
        this.DBtoAPIDictionary.put("timestamp", "timestamp");
    }
    
    public String checkDate(String YYYYMM) {
        Date today = new Date();
        String localDate = new SimpleDateFormat("yyyyMM").format(today);
        if (YYYYMM.equals("live") || YYYYMM.isEmpty() || YYYYMM.equals(localDate)) {
            return localDate;
        }
        
        try {
            Date checkdate = new SimpleDateFormat("yyyyMM").parse(YYYYMM);
            if (today.before(checkdate)) {
              return localDate;
            } else localDate = new SimpleDateFormat("yyyyMM").format(checkdate);
            
        } catch (ParseException ex) {
            System.err.println("Bład formatu daty" + ex);
        }

        return localDate;
    }
    
    
    public boolean getWebsitePhrasesReport(APIData api_data, String website, String lang, String YYYYMM, int rows) throws SQLException{

        /*
            case "phrase" : this.setPhrase(attr_value); break;
            case "url": this.setUrl(attr_value); break;
            case "position": this.setPosition(attr_value); break;
            case "volumen": this.setVolumen(attr_value); break;
            case "trafficshare": this.setTrafficShare(attr_value); break;
            case "timestamp": this.setTimestamp(attr_value); break;
        */
        website = website.toLowerCase().trim();
        String sqltable = "";
        switch (website) {
            case "saturn.pl" : sqltable = "bp_produkt_saturn"; break;
            case "mediamarkt.pl" : sqltable = "bp_produkt_media_markt"; break;
            case "mediaexpert.pl" : sqltable = "bp_produkt_media_expert"; break;
            case "euro.com.pl" : sqltable = "bp_produkt_euro"; break;
            case "oleole.pl" : sqltable = "bp_produkt_oleole"; break;
            case "morele.net" : sqltable = "bp_produkt_morele"; break;
            //case "ceneo.pl" : sqltable = "bp_produkt_ceneo"; break;
            default: break;
        }
        
        if (sqltable.isEmpty()) return false;
        
        Date today = new Date();
        YYYYMM = checkDate(YYYYMM);
        String localDate = new SimpleDateFormat("yyyyMM").format(today);
        if (!YYYYMM.equals(localDate)) sqltable += "_history";
        
        String order = " order by traffic_share desc ";
        String limit = " limit " + rows;
        if (rows < 1) limit = ""; //brak limitu jesli rows < 1
        
        String query = "select * from " + sqltable  + order + limit;
        System.out.println(query);
        
        Connection connection = null;
        try {
           connection = DriverManager.getConnection(dbsource, dbname, dbpass);
           Statement stmt = connection.createStatement();
           
           ResultSet rs = stmt.executeQuery(query);
           ResultSetMetaData rsMD = rs.getMetaData();
           int columnsNumber = rsMD.getColumnCount();
           
           api_data.clearWebsitePhrases();
            while (rs.next()) {
                APIWebsitePhrases apiDE = new APIWebsitePhrases(); //frazy, urle i pozycje
                for (int i = 1; i <= columnsNumber; i++) {
                    
                    String apiAttr = DBtoAPIDictionary.get(rsMD.getColumnName(i));
                    String columnValue = rs.getString(i);
                    if (apiAttr != null && columnValue != null) {
                        apiDE.setAttribute(apiAttr, columnValue);
                        //System.out.println("d:" + columnValue + "\t" + rsMD.getColumnName(i) + "\t"+ apiAttr);   
                    }
                    
                }
               api_data.addAPIWebsitePhrase(apiDE);
            }

           rs.close();
           stmt.close();
           connection.close();
        
        } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
                return false;
        }
                 
        return true;
    
    }
   
    
    
}