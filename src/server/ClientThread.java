/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import computation.Actions;
import computation.Computation;
import computation.ShellExecution;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chandra
 */
public class ClientThread implements Runnable{
    
    protected Socket clientSocket = null;
    private Computation computation;
    
    private static final Logger LOGGER =
        Logger.getLogger(MultiThreadedTCPServer.class.getName());
    
    
    public ClientThread(Socket clientSocket, Computation computation) {
        this.clientSocket = clientSocket;
        this.computation = computation;
    }
    
    @Override
    public void run() {
        LOGGER.entering(getClass().getName(), "run()");
        try {
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
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
        Computation.queue.offer(1000000000L);
        LOGGER.exiting(getClass().getName(), "run()");
    }
    
}
