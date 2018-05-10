/*
 * Course Info: CST8227 Enterprise Application Programming 
 * Student Name: Phuong Pham
 */
import java.util.concurrent.ArrayBlockingQueue;
/**
 * This is BlockingBuffer class which implements Buffer interface,
 * Creating a synchronized buffer using the ArrayBlockingQueue class.
 * @author Phuong Pham
 */
public class BlockingBuffer implements Buffer
{
   /**
    * ArrayBlockingQueue automatically handles synchronization for us, shared buffer.
    * @author Phuong Pham 	
    */
   private final ArrayBlockingQueue<FishStick> buffer; // shared buffer
   /**
    * Constructor of BlockingBuffer take a int of parameter size.
    * @author Phuong Pham
    */
   public BlockingBuffer(int size)
   {
      buffer = new ArrayBlockingQueue<FishStick>(size);
   }
   /**
    * Method blockingPut take FishStick object value into buffer.
    * @author Phuong Pham
    */
   public void blockingPut(FishStick value) throws InterruptedException
   {
      buffer.put(value); // place value in buffer
   } 
   /**
    * Return FishStick object value from buffer
    * @author Phuong Pham
    */
   public FishStick blockingGet() throws InterruptedException
   {
	  FishStick readValue = buffer.take();// remove value from buffer
      return readValue;
   } 
} // end class BlockingBuffer

