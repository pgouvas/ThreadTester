package threadtestapplication;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Task implements Callable {
   private int seq;
   private Random rand = new Random();
   private int maxdelay = 1000;
   private int randomdelay = 1000;
   private int maxnumoftasks=100;   
   private int randomnumoftasks=100;
   private boolean parallelexecution = false;
   public Task() {
   }
   public Task(int i) { 
       seq = i; 
       this.randomdelay = rand.nextInt(maxdelay);
   }
   public Task(int i,int maxdelay) { 
       seq = i; 
       this.maxdelay=maxdelay; 
       this.randomdelay = rand.nextInt(maxdelay);
   }
   public Task(int i,int maxdelay,int maxnomoftasks, boolean parallelinternalexecution) { 
       seq = i; 
       this.maxdelay=maxdelay; 
       this.randomdelay = rand.nextInt(maxdelay);
       this.maxnumoftasks = maxnomoftasks;
       this.randomnumoftasks = rand.nextInt(maxnomoftasks);
       this.parallelexecution = parallelinternalexecution;
   }
   
   
   public Object call() {
      String str = "";
      long begTest = new java.util.Date().getTime();
      //System.out.println("start - Task "+seq);
      try {
         if (parallelexecution){
            ExecutorService eservice = Executors.newCachedThreadPool();           
            CompletionService<Object> cservice = new ExecutorCompletionService<> (eservice);
            for (int index = 0; index < randomnumoftasks; index++)
                cservice.submit(new InternalTask(randomdelay) );
            
                Object taskResult;
                for(int index = 0; index < randomnumoftasks; index++) {
                   try {
                      taskResult = cservice.take().get();
                      //System.out.println("result "+taskResult);
                   } catch (InterruptedException | ExecutionException e) {}
                }//for            
            
         } else { //Serial Execution
            // sleep for randomdelay seconds to simulate a remote call
            for(int i = 0; i < randomnumoftasks; i++)
               Thread.sleep(randomdelay);             
         }
         
      } catch (InterruptedException e) {}
      Double secs = new Double((new java.util.Date().getTime() - begTest)*0.001);
      //System.out.println("run time " + secs + " secs");
      return seq;
    }
}