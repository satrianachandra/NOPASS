/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ethan_Hunt
 */
public class Computation implements Runnable{
    
    public static BlockingQueue queue = new ArrayBlockingQueue(4096);
    
    private boolean stop = false;
    private static final Logger LOGGER =
        Logger.getLogger(Computation.class.getName());
    
    private final Object lock = new Object();
    
    public Computation(){

    }
    
    
    private long getFibonacci(long number){
        if(number == 1 || number == 2){
            return 1;
        }
        long fibo1=1, fibo2=1, fibonacci=1;
        for(int i= 3; i<= number; i++){
            fibonacci = fibo1 + fibo2; //Fibonacci number is sum of previous two Fibonacci number
            fibo1 = fibo2;
            fibo2 = fibonacci;
 
        }
        return fibonacci; //Fibonacci number
    }

    @Override
    public void run() {
        while (!stop){          
            Long number = null;
            try {
                number = (Long)queue.take();
                Long result = getFibonacci(number);
                LOGGER.log(Level.INFO, "Fibo result:{0}", result);
            } catch (InterruptedException ex) {
                Logger.getLogger(Computation.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            /*
            Iterator<Long> iter = numbersToProcess.iterator();
            while (iter.hasNext()) {
                Long element = iter.next();
                Long result = getFibonacci(element);
                LOGGER.log(Level.INFO, "Fibo result:{0}", result);
                iter.remove();
            }*/
            
            /*
            List<Long> removed = new ArrayList<>();
            for(Long number : numbersToProcess) {
                getFibonacci(number);
                removed.add(number);
            }
            numbersToProcess.removeAll(removed);
            */
        }
        
    }
    
    public void stop(){
        stop = true;
    }
}
