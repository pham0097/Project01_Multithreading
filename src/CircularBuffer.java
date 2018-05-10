/*
 * Course Info: CST8227 Enterprise Application Programming 
 * Student Name: Phuong Pham
 */
/**
 * This is CircularBuffer class which implements Buffer interface,
 * synchronizing access to a shared three-element bounded buffer.
 * @author Phuong Pham
 */
public class CircularBuffer implements Buffer{
   /**
    * FishStick objects array shared buffer. 
    * @author Phuong Pham
    */
   private final FishStick[] buffer = new FishStick[1000];
   /**
    * Int occupiedCells to count the number of buffers used.
    * @author Phuong Pham
    */
   private int occupiedCells = 0;
   /**
    * Int writeIndex to index of next element to write to.
    * @author Phuong Pham
    */
   public int writeIndex = 0;
   /**
    * Int readIndex to index of next element to read.
    * @author Phuong Pham
    */
   public int readIndex = 0;
   /**
    * Method blockingPut take FishStick object value into buffer.
    * @author Phuong Pham
    */
   public synchronized void blockingPut(FishStick value) throws InterruptedException
   {
      // wait until buffer has space avaialble, then write value;
      // while no empty locations, place thread in waiting state
      while (occupiedCells == buffer.length) 
      {
         //System.out.printf("Buffer is full. Producer waits.%n");
         wait(); // wait until a buffer cell is free
      } 

      buffer[writeIndex] = value; // set new buffer value

      // update circular write index
      writeIndex = (writeIndex + 1) % buffer.length;

      ++occupiedCells; // one more buffer cell is full
      //displayState("Producer writes " + value);
      notifyAll(); // notify threads waiting to read from buffer
   } 
    
   /**
    * Method blockingGet remove value from buffer.
    * Return FishStick object value from buffer
    * @author Phuong Pham
    */
   public synchronized FishStick blockingGet() throws InterruptedException
   {
      // wait until buffer has data, then read value;
      // while no data to read, place thread in waiting state
      while (occupiedCells <= 0) 
      {
         System.out.printf("Buffer is empty. Consumer waits.%n");
         wait(); // wait until a buffer cell is filled
      } 

      FishStick readValue = buffer[readIndex]; // read value from buffer

      // update circular read index
      readIndex = (readIndex + 1) % buffer.length;
      
      --occupiedCells; // one fewer buffer cells are occupied
      //displayState("Consumer reads " + readValue);
      notifyAll(); // notify threads waiting to write to buffer

      return readValue;
   }
} // end class CircularBuffer