/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import computation.Computation;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import logger.MyLogger;

/**
 *
 * @author chandra
 */
public class MultiThreadedTCPServer implements Runnable{
    
    private int          serverPort   = 8080;
    private ServerSocket serverSocket = null;
    private boolean      isStopped    = false;
    private Thread       runningThread= null;
    private ExecutorService threadPool ;// Executors.newFixedThreadPool(30);
    //private ExecutorService threadPoolFIbo; 
    private int sizeOfThreadPool = 10;
    private static final Logger LOGGER =
        Logger.getLogger(MultiThreadedTCPServer.class.getName());
    
    
    
    public MultiThreadedTCPServer(int port, int sizeOfThreadPool){
        this.serverPort = port;
        this.sizeOfThreadPool = sizeOfThreadPool;
        this.threadPool= Executors.newFixedThreadPool(sizeOfThreadPool);
        //this.threadPool= Executors.newCachedThreadPool();
        //this.threadPoolFIbo =  Executors.newFixedThreadPool(2*sizeOfThreadPool);
    }

    public void run(){
        LOGGER.entering(getClass().getName(), "run() method");
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        //Computation computation = new Computation();
        //new Thread(computation).start();
        
        openServerSocket();
        LOGGER.log(Level.INFO, "Server started at port{0}", serverPort);
        
        
        while(! isStopped()){
            //try {
            //    Thread.sleep(100);
            //} catch (InterruptedException ex) {
            //    Logger.getLogger(MultiThreadedTCPServer.class.getName()).log(Level.SEVERE, null, ex);
            //}
            //final Socket clientSocket;
            try {
                //clientSocket = this.serverSocket.accept();
                //new Thread(new ClientThread(this.serverSocket.accept(), threadPool)).start();
                this.threadPool.execute(new ClientThread(
                    this.serverSocket.accept()));
            } catch (IOException e) {
                if(isStopped()) {
                    LOGGER.log(Level.INFO, "Server Stopped");
                    return;
                }
                LOGGER.log(Level.SEVERE, "Error accepting client connection", e);
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
            //this.threadPool.execute(new ClientThread(
            //        clientSocket));
        }
        this.threadPool.shutdown();
        //this.threadPoolFIbo.shutdown();
        //System.out.println("Server Stopped.") ;
        LOGGER.log(Level.INFO, "Server Stopped");
        LOGGER.exiting(getClass().getName(), "Server thread stopped");
        
    }
    
    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Cannon Open Port", new RuntimeException("Error"));
            throw new RuntimeException("Error closing server", e);
            
        }
    }
    
     private void openServerSocket() {
        LOGGER.entering(getClass().getName(), "openServerSocket()");
        try {
            this.serverSocket = new ServerSocket();
            this.serverSocket.setReuseAddress(true);
            this.serverSocket.bind(new InetSocketAddress(this.serverPort));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Cannon Open Port", new RuntimeException("Error"));
            throw new RuntimeException("Cannot open port "+serverPort, e);
        }
        LOGGER.exiting(getClass().getName(), "openServerSocket");
    }

    public static void main(String[] args){
        try {
              MyLogger.setup();
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Cannot Open file", ex);
        }
        
        int sizeOfTPool=10;
        if (args.length == 0){
            throw new IllegalArgumentException("Must specify a port!");
	}else if (args.length>=2){
            String level = args[1];
            MyLogger.setLevel(Integer.decode(level));
            sizeOfTPool = Integer.decode(args[2]);
        }
        
	int port = Integer.parseInt(args[0]);
        MultiThreadedTCPServer server = new MultiThreadedTCPServer(8181,1000);
        new Thread(server).start();
        
    
                
    }
    
}
