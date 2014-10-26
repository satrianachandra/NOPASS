/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import computation.Actions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chandra
 */
public class ClientThread implements Runnable{
    
    private Socket clientSocket = null;
    private String inputLine;
    //private ExecutorService myThreadPool;
    
    private static final Logger LOGGER =
        Logger.getLogger(ClientThread.class.getName());
    
    
    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        //this.myThreadPool = tPool;
        //try {
        //    this.clientSocket.setSoTimeout(5000);
        //}catch (SocketException ex) {
        //    Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
       // }
    }
        
    @Override
    public void run() {
        LOGGER.entering(getClass().getName(), "run()");
        
        try {
            BufferedReader in = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));
        
            //String inputLine;
            while ((inputLine = in.readLine()) != null) {
                //long result = Actions.fibonacciLoop(Long.decode(inputLine));
                //LOGGER.log(Level.INFO, "Fibo result:{0}", result);
                //Computation.queue.offer(Long.decode(inputLine));
                /*
                myThreadPool.execute(new Runnable() {

                    @Override
                    public void run() {
                        long result = Actions.fibonacciLoop(Long.decode(inputLine));
                        LOGGER.log(Level.INFO, "Fibo result:{0}", result);
                    }
                });
                */
                long result = Actions.fibonacciLoop(Long.decode(inputLine));
                LOGGER.log(Level.INFO, "Fibo result:{0}", result);
                /*
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        long result = Actions.fibonacciLoop(Long.decode(inputLine));
                        LOGGER.log(Level.INFO, "Fibo result:{0}", result);
                    }
                }).start();
                */
                
            }
            
        }catch(IOException ex){
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                //in.close();
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /*
        new Thread(new Runnable() {

            @Override
            public void run() {
                long result = Actions.fibonacciLoop(1000000000);
                LOGGER.log(Level.INFO, "Thread-s"+threadNumber+"Fibo result:{0}", result);
            }
        }).start();*/
        //computation.processNumber(1000000000L);
        //Computation.queue.offer(1000000000L);
        LOGGER.exiting(getClass().getName(), "run()");
    }
    
    //testing git upstart
    //testing again
}
