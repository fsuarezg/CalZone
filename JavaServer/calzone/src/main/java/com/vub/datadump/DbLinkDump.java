package com.vub.datadump;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import com.vub.model.Globals;

public class DbLinkDump {
	
	// private static DbConfigFile dbconfig = new DbConfigFile("/Wilmadbconfig.txt");
	
   // static ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-module.xml");
	//private static DbConfigFile dbconfig = (DbConfigFile) context.getBean("dbConfigDump");
	private static String db_user = "se2_1314";
	private static String db_password = "Bean59Cabal";
	private static String url = "jdbc:mysql://wilma.vub.ac.be/se2_1314";
	
	private static Connection db_connection;
	
	public static Statement stmt = null;
	public static ResultSet rs = null;
	public static ResultSetMetaData rsmd = null;
	
	public static ResultSet executeSqlQuery(String sql) {
		try {
			return stmt.executeQuery(sql);
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			if (Globals.DEBUG == 1) {
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
            return null;
		}
	}
	
	public static void executeSql(String sql) {
		try {
			stmt.execute(sql);
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			if (Globals.DEBUG == 1) {
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		}
	}
	
	public static void closeConnection() {
		try {
			//rs.close();
			//stmt.close();
			db_connection.close();
		} catch (SQLException ex) {
			// handle the error
			if (Globals.DEBUG == 1) {
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		}
	}
	
	public static void openConnection() {
        try {
        	
        	db_connection = DriverManager.getConnection(url,db_user,db_password);
        	stmt = db_connection.createStatement();
        	
        	// rs = executeSqlQuery("SELECT * FROM Persons;");
        	// test ( bij elk nieuw gebruik van stmt wordt rs geclosed !! )
        	// while(rs.next()){
        	//  	System.out.println(rs.getString(2)+" "+rs.getString(3));
        	// }
        	// rs.beforeFirst();
        	// rsmd = rs.getMetaData();
        	// int nrOfCol = rsmd.getColumnCount();
        	// System.out.println("Column count of table: "+nrOfCol);

        	
        } catch (Exception ex) {
            // handle the error
        	if (Globals.DEBUG == 1) {
        		System.out.println("SQLException: " + ex.getMessage());
        		System.out.println("SQLState: " + ((SQLException) ex).getSQLState());
        		System.out.println("VendorError: " + ((SQLException) ex).getErrorCode());
        	}
        }
    }
	
}