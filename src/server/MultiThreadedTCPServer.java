/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import logger.MyLogger;

/**
 *
 * @author chandra
 */
public class MultiThreadedTCPServer implements Runnable{
    
    protected int          serverPort   = 8080;
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    private static final Logger LOGGER =
        Logger.getLogger(MultiThreadedTCPServer.class.getName());
    
    public MultiThreadedTCPServer(int port){
        this.serverPort = port;
    }

    public void run(){
        LOGGER.entering(getClass().getName(), "run() method");
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        LOGGER.log(Level.INFO, "Server started at port{0}", serverPort);
        
        while(! isStopped()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    LOGGER.log(Level.INFO, "Server Stopped");
                    return;
                }
                LOGGER.log(Level.SEVERE, "Error accepting client connection", e);
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
            new Thread(
                new ClientThread(
                    clientSocket, "Multithreaded Server")
            ).start();
        }
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
            this.serverSocket = new ServerSocket(this.serverPort);
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
        if (args.length == 0){
            throw new IllegalArgumentException("Must specify a port!");
	}else if (args.length==2){
            String level = args[1];
            MyLogger.setLevel(Integer.decode(level));
        }
        
	int port = Integer.parseInt(args[0]);
        MultiThreadedTCPServer server = new MultiThreadedTCPServer(port);
        new Thread(server).start();
    
                
    }
    
}
