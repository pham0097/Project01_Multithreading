
/*
 * Course Info: CST8227 Enterprise Application Programming 
 * Student Name: Phuong Pham
 */

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This Class implements Runnable,Consumer with a run method that loops,
 * reading 1000 fishsticks objects from buffer every time.
 * @author Phuong Pham
 *
 */
public class Consumer implements Runnable
{ 
   /**
    * Buffer reference to shared object.
    * @author Phuong Pham
    */
   private final Buffer sharedLocation;
   /**
    * Int sum shows total number of records consumers.
    * @author Phuong Pham
    */
   private static int sum = 0;
   /**
    * Connection object con to connect to database.
    * @author Phuong Pham
    */
   private Connection con = null;
   /**
    * String connectionString to connect to database of assignment 1.
    * @author Phuong Pham
    */
   private final String connectionString = "jdbc:mysql://localhost/assignment1";
   /**
    * String username, the username to connect database of assignment 1.
    * @author Phuong Pham
    */
   private final String username = "assignment1";
   /**
    * String password, the password to connect database.
    * @author Phuong Pham
    */
   private final String password = "password";
   /**
    * Constructor Consumer
    * @author Phuong Pham
    * @param sharedLocation
    */
   public Consumer(Buffer sharedLocation)
   {
      this.sharedLocation = sharedLocation;
   }
   /**
    * Open the connection, get connect to database.
    * @author Phuong Pham 
    */
   private void openConnection(){
		try{
			if(con != null){
				System.out.println("Can't create new connection, one exists already");
			}
			else{
				con = DriverManager.getConnection(connectionString, username, password);
			}
			con.setAutoCommit(false);
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
		catch(SQLException ex){System.out.println(ex.getMessage());
		}
	}
   /**
    * Get the total number of records inserted to the database.
    * @author Phuong Pham
    */
   public int getRecordsInserted(){
	   return sum;
   }
   /**
    * Insert objects fishsticks into database.
    * @author Phuong Pham
    */
   public void insertFishStick(FishStick fishstick) {
		PreparedStatement pstmt = null;
		int commit = 0;
		try{
			if(con == null || con.isClosed()) {
				System.out.println("Cannot insert records, no connection or connection closed");
			}
			pstmt = con.prepareStatement(
					"INSERT INTO FishSticks (recordnumber, omega, lambda, uuid) " +
					"VALUES(?, ?, ?, ?)");
			pstmt.setInt(   1, fishstick.getRecordNumber());
			pstmt.setString(2, fishstick.getOmega());
			pstmt.setString(3, fishstick.getLambda());	
			pstmt.setString(4, fishstick.getUUID());
			pstmt.executeUpdate();
			con.commit();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{ if(pstmt != null){ pstmt.close(); }}
			catch(SQLException ex){System.out.println(ex.getMessage());}
		}
	}
   
	/**
	 * Run the process of insert fishsticks objects into database,
	 * when reach the object with UUID = "EOF",terminate the process.
	 * Read sharedLocation's objects and print message every 1000 times and sum the values.
	 * @author Phuong Pham
	 */
   public void run()                                           
   {
	  openConnection();
	  try{
		  while (true)		
		  {  
			  FishStick fishStick = sharedLocation.blockingGet();
			  if (fishStick.getUUID().equals("EOF") 
					&& fishStick.getId() == 0 
					&& fishStick.getLambda().equals("") 
					&& fishStick.getOmega().equals(""))
				break;
			  insertFishStick(fishStick);
			  sum++;
				if (sum % 1000 == 0)
					System.out.printf("%d records inserted by consumer%n", sum);
		  }
	  }catch (InterruptedException exception) 
      {
          Thread.currentThread().interrupt(); 
       } 
	  closeConnection();
	  System.out.println(sum + " records inserted, task completed.");
	  System.out.println("Consumer done consuming Terminating Consumer");
   } 
} 
// end class Consumer