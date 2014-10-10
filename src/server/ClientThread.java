/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import computation.Actions;
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
    protected String serverText   = null;
    
    private static final Logger LOGGER =
        Logger.getLogger(MultiThreadedTCPServer.class.getName());
    
    
    public ClientThread(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }
    
    public void run() {
        LOGGER.entering(getClass().getName(), "run()");
        try {
            InputStreamReader inputStreamReader  = new InputStreamReader(clientSocket.getInputStream());
            final BufferedReader input = new BufferedReader(inputStreamReader);
            final PrintStream printStream = new PrintStream(clientSocket.getOutputStream(),true);
            
            //System.out.println("Connection from " + clientSocket.getInetAddress().getHostAddress());
            LOGGER.log(Level.INFO, "Connection from " + clientSocket.getInetAddress().getHostAddress());
            String inputLine;        
            while ((inputLine = input.readLine()) != null) {
                final String commands = inputLine;
                
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int result = Actions.fibonacciLoop(Integer.decode(commands));
                        //printStream.println(result);
                        LOGGER.log(Level.INFO, "Fibo result:" + result);
                    }
                }).start();                
            } 
            LOGGER.log(Level.INFO, "Client has left"); 
            
            input.close();
            printStream.close();
            
            
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    
        LOGGER.exiting(getClass().getName(), "run()");
    }
    
}
