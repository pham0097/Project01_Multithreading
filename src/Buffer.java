/*
 * Course Info: CST8227 Enterprise Application Programming 
 * Student Name: Phuong Pham
 */

public interface Buffer{// This Class is a Buffer interface which specifies methods called by Producer and Consumer
   /**
    * Place FishStick value into Buffer
    * @author Phuong Pham
    */
   public void blockingPut(FishStick value) throws InterruptedException; 
   
   /**
    * Obtain FishStick value from Buffer
    * @throws InterruptedException
    * @author Phuong Pham
    */
   public FishStick blockingGet() throws InterruptedException; 
   
} 
// end interface Buffer