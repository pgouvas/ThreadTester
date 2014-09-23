package threadtestapplication;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author pgouvas
 */
public class ThreadTestApplication {
    private static final int NUM_OF_EXTERNAL_TASKS = 100;
    private static final int MAX_NUM_OF_INTERNAL_TASKS = 100;    
    private static final int MAX_TASK_DELAY = 4000; //ms
    private static final boolean PARALLEL_EXTERNAL_EXECUTION = true;    
    private static final boolean PARALLEL_INTERNAL_EXECUTION = true;
    
    public static void main(String[] args) {
    }
    
   public ThreadTestApplication() {}

   public void run() {
      long begTest = new java.util.Date().getTime();
      
      if (PARALLEL_EXTERNAL_EXECUTION){          
    //    http://java.dzone.com/articles/java-concurrency-%E2%80%93-part-7      
    //    ExecutorService eservice = Executors.newFixedThreadPool(nrOfProcessors);      
          ExecutorService eservice = Executors.newCachedThreadPool();           
          CompletionService<Object> cservice = new ExecutorCompletionService<> (eservice);
    //    int nrOfProcessors = Runtime.getRuntime().availableProcessors();      
    //    System.out.println("max:"+ ((ThreadPoolExecutor)eservice).getMaximumPoolSize() );

          for (int index = 0; index < NUM_OF_EXTERNAL_TASKS; index++)
             cservice.submit(new Task(index,MAX_TASK_DELAY,MAX_NUM_OF_INTERNAL_TASKS,PARALLEL_INTERNAL_EXECUTION));

          Object taskResult;
          for(int index = 0; index < NUM_OF_EXTERNAL_TASKS; index++) {
             try {
                taskResult = cservice.take().get();
                //OLA ta internal teleiosan gia to docid
                //System.out.println("result "+taskResult);
             } catch (InterruptedException | ExecutionException e) {}
          }//for
      }  else { //end parallel execution
          for (int index = 0; index < NUM_OF_EXTERNAL_TASKS; index++){
              Task task= new Task(index,MAX_TASK_DELAY,MAX_NUM_OF_INTERNAL_TASKS,PARALLEL_INTERNAL_EXECUTION);
              task.call();
          }//for
      }//else
      Double secs = new Double((new java.util.Date().getTime() - begTest)*0.001);
      System.out.println("#########run time " + secs + " secs");
   }//run    
    
}//EoC
