package threadtestapplication;

import java.util.concurrent.Callable;

/**
 *
 * @author pgouvas
 */
public class InternalTask implements Callable {
    
    private int delay = 1000;
    
    public InternalTask(int delay){
        this.delay = delay;
    }
    
    public Object call() throws Exception {
        try {
            Thread.sleep(delay);
        } catch (Exception ex){
            
        }
        return null;
    }//EoM
    
}//EoC
