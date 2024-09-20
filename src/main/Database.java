package main;

import java.sql.*;

/**
* <strong>
* --------------------------------------------------------------- <br>
* | ISYS6197 - BUSINESS APPLICATION DEVELOPMENT | <br>
* --------------------------------------------------------------- <br>
* </strong>
* <br>
* Connect.java | This class is used for connection to MySQL database
* <br>
* Copyright 2023 - Bina Nusantara University
* <br>
* Richelle Widyananda (2602058636), All rights reserved.
* <br>
*/
public final class Database {
	
	private final String USERNAME = "root"; 
	private final String PASSWORD = ""; 
	private final String DATABASE = "lecbad_projectk4"; 
	private final String HOST = "localhost:3306"; 
	private final String CONECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	private Connection con;
	private Statement st1;
	private Statement st2;
	private static Database connect;

    private Database() {
    	try {  
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(CONECTION, USERNAME, PASSWORD);  
            st1 = con.createStatement(); 
            st2 = con.createStatement();
        } catch(Exception e) {
        	e.printStackTrace();
        	System.out.println("Failed to connect the database, the system is terminated!");
        	System.exit(0);
        }  
    }    
	
    public static synchronized Database getConnection() {		
		return connect = (connect == null) ? new Database() : connect;
    }

    public ResultSet executeQuery(String query) {
        ResultSet rs = null;
    	try {
            rs = st1.executeQuery(query);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet executeQuery2(String query) {
        ResultSet rs = null;
    	try {
            rs = st2.executeQuery(query);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return rs;
    }

    public void executeUpdate(String query) {
    	try {
			st1.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
 
    public PreparedStatement prepareStatement(String query) {
    	PreparedStatement ps = null;
    	try {
			ps = con.prepareStatement(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return ps;
    }
}
