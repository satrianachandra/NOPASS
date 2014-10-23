/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ethan_Hunt
 */
public class Computation implements Runnable{
    
    private static int sizeOfThreadPool=4000;
    private static ExecutorService threadPool ;
    
    //public static BlockingQueue queue = new LinkedBlockingQueue();
    public static Queue<Long> queue = new ConcurrentLinkedQueue<Long>();
    
    private boolean stop = false;
    private final Logger LOGGER =
        Logger.getLogger(Computation.class.getName());
    
    
    public Computation(){
        Computation.threadPool = Executors.newFixedThreadPool(Computation.sizeOfThreadPool);
        //Computation.threadPool = Executors.newCachedThreadPool();
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
            final Long number = queue.poll();
            if (number!=null){
                Computation.threadPool.execute(new Runnable() {

                    @Override
                    public void run() {
                        Long result = getFibonacci(number);
                        LOGGER.log(Level.INFO, "Fibo result:{0}", result);
                    }
                });
                
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
