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
import java.util.Arrays;
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
    
    private String dbsource =  "jdbc:postgresql://93.158.233.149:5432/zpi_project";
    private String dbname =  "postgres";
    private String dbpass =  "1@3$qWeR";
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
    
    
    // lista fraz i prarametów - basic stats
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
           
           //----
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
            //-----

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
    //end basic stats
    
    
    
    //competitors
    
    //wyciagnij liste
       public boolean getDBQueryResults(ArrayList responseStr, String query) throws SQLException{
        
        if (query.isEmpty() ) return false;
        Connection connection = null;
        try {
           connection = DriverManager.getConnection(dbsource, dbname, dbpass);
           Statement stmt = connection.createStatement();
           //System.out.println("polaczono sie");
           //System.out.println(query);
           
           ResultSet rs = stmt.executeQuery(query);
           ResultSetMetaData rsMD = rs.getMetaData();
           int columnsNumber = rsMD.getColumnCount();
           String columnValue = "";
           //----
             while (rs.next()) {
                  columnValue = "";
                  for (int i = 1; i <= columnsNumber; i++) {                  
                    //String apiAttr = DBtoAPIDictionary.get(rsMD.getColumnName(i));
                    columnValue += rs.getString(i) + "\t";
                    //System.out.println(rs.getString(i));
                 }
                  if (!columnValue.trim().isEmpty())
                        responseStr.add(columnValue.trim());
                  //System.out.println("wynik query " + columnValue.trim());
             }
 
           //----
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
    
    //wyciagnij konkurentow
    
    public boolean getWebsiteCompetitorsReport(ArrayList responseList, String website, String subQuery, String whereQuery) throws SQLException{
  
       Boolean qres = false;
        String sqltable = "d_bp_produkt";
        String query = "select distinct source_table from " + sqltable + " where recognize_text='" + website + "'";
        ArrayList resList = new ArrayList();
        
        qres = getDBQueryResults(resList, query);             
        if (qres == true && resList.size() > 0) { 
        sqltable = resList.get(0) + "_konkurencja";
        //responseList.add(sqltable);//tylko nazwa tabeli konkurencji
      
        //---
        resList.clear();
        query = "select distinct id from " + sqltable;
        qres = getDBQueryResults(resList, query);   
        if (qres==true && resList.size()>0) {
            Map<String, String> monthRes = new HashMap<String, String>();
            String domena = "miesiac";
            for (int k=0; k<resList.size(); k++)
            {
               ArrayList<String> templist = new ArrayList();
               query = "select recognize_text from d_bp_produkt where id="+resList.get(k);
               qres = getDBQueryResults(templist, query);
               domena = domena + "," + templist.get(0);
               templist.clear();
               
               query = "select source_table from d_bp_produkt where id="+resList.get(k);
               qres = getDBQueryResults(templist, query);
               if (qres==true) {
                   sqltable = templist.get(0);
                   templist.clear();
                   //qres = getDBQueryResults(templist, "select year, month, count(*) from "+sqltable+" group by year, month");
                   qres = getDBQueryResults(templist, "select year, month, "+subQuery+" from "+sqltable+" "+whereQuery+" group by year, month");
                   //qres = getDBQueryResults(templist, "select year, month, count(*) from "+sqltable+"_history group by year, month order by month desc limit 11");
                   qres = getDBQueryResults(templist, "select year, month, "+subQuery+" from "+sqltable+"_history"+" "+whereQuery+" group by year, month order by month desc limit 11");
                   
                   for (int x=0; x<templist.size(); x++) {
                       String month = templist.get(x).split("\t")[1];
                       if (month.length()==1) month = "0" + month;
                       month = "'"+ templist.get(x).split("\t")[0]+"-"+month + "'"; //'rok + mth'
                       String res = templist.get(x).split("\t")[2];
                       String tryMonth = monthRes.get(month);
                       if (tryMonth != null) {
                            res = tryMonth + ", " + res;
                       }
                       monthRes.put(month, res);
                       //responseList.add(domena + "\t" + templist.get(x));
                   }
                   templist.clear();
                   
                 }
            }
            //
            responseList.add(domena);
            //System.out.println("mapa:");
            if (monthRes != null) {
                monthRes.entrySet().forEach((entry) -> {
                responseList.add(entry.getKey() + "," + entry.getValue());
                //System.out.println(key + "," + value);
            });
       }
            
        }
        
        
        } else return false;
        
        return true;
    }
   
    //end comp
   
    //organic stat
    public boolean getWebsiteTrendsReport(ArrayList responseList, String website, String query) throws SQLException{
  
        Boolean qres = false;
        ArrayList resList = new ArrayList();

        qres = getDBQueryResults(resList, query);   
        
        if (qres==true && resList.size()>0) {
            for (int i=0; i<resList.size();i++) {
                responseList.add(resList.get(i));
                //System.err.println("dodano "+resList.get(i));
                }
            } else return false;
        
        return true;
    }
    //end organic
    
    
}