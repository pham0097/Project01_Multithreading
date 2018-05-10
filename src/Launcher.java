/*
 * Course Info: CST8227 Enterprise Application Programming 
 * Student Name: Phuong Pham
 */
// Application with two threads manipulating an unsynchronized buffer.
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * Application with two threads manipulating buffer.
 * @author Phuong Pham
 */
public class Launcher
{
   public static void main(String[] args) throws InterruptedException
   {         
         DataLoader loader = new DataLoader();
         loader.processRecords();   
   } 
   // end main
} 
// end class SharedBufferTest