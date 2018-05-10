
/*
 * Course Info: CST8227 Enterprise Application Programming 
 * Student Name: Phuong Pham
 */

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Scanner;
/**
 * This Class implements Runnable,Producer with a run method 
 * that inserts the values into the buffer.
 * @author Phuong Pham
 *
 */
public class Producer implements Runnable
{
   /**
    * Buffer reference to shared object
    * @author Phuong Pham
    */
   private final Buffer sharedLocation; 
   /**
    * Int sum shows total number of records produces.
    * @author Phuong Pham
    */
   private static int sum = 0;
   /**
    * File reader scanner object.
    * @author Phuong Pham
    */
   private Scanner fishstickScanner = null;
   /**
    * Constructor Producer
    * @param sharedLocation
    * @author Phuong Pham
    */
   public Producer(Buffer sharedLocation)
   {
      this.sharedLocation = sharedLocation;
   } 
   /**
    * Get the total number of records read from file.
    * @return int sum
    * @author Phuong Pham
    */
   public int getRecordsRead(){
	   return sum;
   }
   /**
    * Open a csv file from system.
    * @author Phuong Pham
    */
	private void openFile() {
		try {
			fishstickScanner = new Scanner(new FileReader(new File("DataSet18W_100000.csv")));
		}
		catch(IOException ex){
			System.out.println("Problem opening file: "
					+ ex.getMessage());
		}
	}
	/**
	 * Close the open file.
	 * @author Phuong Pham
	 */
	private void closeFile() {
		try {
			if(fishstickScanner != null) {fishstickScanner.close();}
		}
		catch(Exception ex) {
			System.out.println("Problem closing file: "
					+ ex.getMessage());
		}
	}
	
   /**
    * Run the process of read file and print message after every 1000 records has been read from file,
    * store values in sharedLocation buffer. After finish read file, create a new fishstick object with
    * UUID "EOF" in order to give a sign to consumer that there is no more fishstick in buffer sharedLocation.
    * @author Phuong Pham
    */
   public void run()                             
   {
	  openFile();
	  while(fishstickScanner.hasNext()){
		  try{
				String line = fishstickScanner.nextLine(); // read raw data
				String[] fields = line.split(","); // split on delimiter
				FishStick fishstick = new FishStick();
				fishstick.setRecordNumber(Integer.parseInt(fields[0]));
				fishstick.setOmega(fields[1]);
				fishstick.setLambda(fields[2]);
				fishstick.setUUID(fields[3]);
				sharedLocation.blockingPut(fishstick);
				sum++;
				if (sum % 1000 == 0)
					System.out.printf("%d records read%n", sum);
		  	}catch (InterruptedException exception) 
	         {
	            Thread.currentThread().interrupt(); 
	            closeFile();
	         } 

			}

      closeFile();
      System.out.println(sum + " records read, task completed.");
      System.out.println("Producer done producing Terminating Producer");
      
		FishStick fishstick = new FishStick();
		fishstick.setRecordNumber(0);
		fishstick.setOmega("");
		fishstick.setLambda("");
		fishstick.setUUID("EOF");
		try {
			sharedLocation.blockingPut(fishstick);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		} 
   } 
} 
// end class Producer