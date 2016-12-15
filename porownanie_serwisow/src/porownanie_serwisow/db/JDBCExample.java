package porownanie_serwisow.db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author samsung
 */
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCExample {

	public static void main(String[] argv) throws SQLException {
                Statement stmt = null;
                
                
		System.out.println("-------- PostgreSQL "
				+ "JDBC Connection Testing ------------");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection c = null;

		try {

			c = DriverManager.getConnection(
				"jdbc:postgresql://--.---.233.149:5432/zpi_project", "postgres",
                                "----");

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}

		if (c != null) {
			System.out.println("You made it, take control your database now!");
                        
                        
                        stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery( "SELECT * from d_bp_produkt;" );
                        while ( rs.next() ) {
//                           int id = rs.getInt("id");
//                           String  name = rs.getString("name");
//                           int age  = rs.getInt("age");
//                           String  address = rs.getString("address");
//                           float salary = rs.getFloat("salary");
//                           System.out.println( "a = " + id );
//                           System.out.println( "b = " + name );
//                           System.out.println( "c = " + age );
//                           System.out.println( "d = " + address );
//                           System.out.println( "e = " + salary );
//                           System.out.println();
         }
		} else {
			System.out.println("Failed to make connection!");
		}
	}

}
