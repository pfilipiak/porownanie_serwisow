package porownanie_serwisow.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.ResultSetMetaData;
/**
 *
 * @author pfilipiak
 */
public class przyklad {
    public static void main(String[] args) throws SQLException{
        Connection connection = null;
        try {
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://93.158.233.149:5432/zpi_project",
                        "postgres",
                        "1@3$qWeR");
        } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
                return;
        }
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery( "select * from bp_produkt_media_markt limit 10" );
        ResultSetMetaData rsMD = rs.getMetaData();
        int columnsNumber = rsMD.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                System.out.print(columnValue + " " + rsMD.getColumnName(i));
            }
            System.out.println("");
        }
        /*
        while ( rs.next() ) {
            String keyword = rs.getString("keyword");
            int position = rs.getInt("position");
            long searchVolume = rs.getLong("search_volume");
            String url = rs.getString("url");
            BigDecimal trafficShare = rs.getBigDecimal("traffic_share");
            String trends = rs.getString("trends");
            Timestamp timestamp = rs.getTimestamp("timestamp");
            int year = rs.getInt("year");
            int month = rs.getInt("month");
            System.out.print( "keyword = "+keyword+ "@@");
            System.out.print( "position = " + position + "@@");
            System.out.print( "search_volume = " + searchVolume+ "@@" );
            System.out.print( "url = " + url + "@@");
            System.out.print( "traffic_share = " + trafficShare + "@@");
            System.out.print( "trends = " + trends + "@@");
            System.out.print( "timestamp = " + timestamp + "@@");
            System.out.print( "year = " + year + "@@");
            System.out.print( "month = " + month+ "@@" );
            System.out.println();
         }

        */
         rs.close();
         stmt.close();
         connection.close();
    }
}