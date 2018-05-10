/*
 * Course Info: CST8227 Enterprise Application Programming 
 * Student Name: Phuong Pham
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class resets the database table by truncating it to help with testing.
 * @author Phuong Pham
 *
 */
public class FishStickCleaner {
	/**
	 * Connection object con to connect to database.
	 * @author Phuong Pham
	 */
	private Connection con = null;
	/**
	 * String connectionString to connect to database.
	 * @author Phuong Pham
	 */
	private final String connectionString = "jdbc:mysql://localhost/assignment1";
	/**
	 * String username, the username to connect database.
	 * @author Phuong Pham
	 */	
	private final String username = "assignment1";
	/**
	 * String password, the password to connect database.
	 * @author Phuong Pham
	 */
	private final String password = "password";
	/**
	 * Open the connection, get connect to database.
	 * @author Phuong Pham 
	 */
	private void openConnection(){
		try{
			if(con != null){
				System.out.println("Cannot create new connection, one exists already");
			}
			else{
				con = DriverManager.getConnection(connectionString, username, password);
			}
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	/**
	 * Close connection to database.
	 * @author Phuong Pham
	 */
	private void closeConnection() {
		try{ if(con != null){ con.close(); }}
		catch(SQLException ex){System.out.println(ex.getMessage());}
	}
	/**
	 * Delete all tables from database.
	 * @author Phuong Pham
	 */
	public void deleteAllFishSticks() {
		openConnection();
		PreparedStatement pstmt = null;
		try{
			if(con == null || con.isClosed()) {
				System.out.println("Cannot delete records, no connection or connection closed");
			}

			pstmt = con.prepareStatement(
					"TRUNCATE TABLE fishsticks");
			pstmt.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{ if(pstmt != null){ pstmt.close(); closeConnection();}}
			catch(SQLException ex){System.out.println(ex.getMessage());}
		}
	}
}
//end class FishStickCleaner