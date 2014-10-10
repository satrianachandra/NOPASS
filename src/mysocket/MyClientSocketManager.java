/*
 * Class For sending String through socket and get the response.
 * by Chandra Satriana
 * 
 */
package mysocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chandra
 */
public class MyClientSocketManager {
    
    private String hostName;
    private int port = 8000;
    private Socket theSocket;
    
    public MyClientSocketManager(String hostName,int port, String commands){
        this.hostName = hostName;
        this.port = port;
    }
    
    public void openSocket() throws IOException{
        this.theSocket = new Socket(hostName, port);
    }
    
    public String sendData(String data){
        try (
                final PrintStream out = new PrintStream(theSocket.getOutputStream(),true);
                final BufferedReader in = new BufferedReader( new InputStreamReader(theSocket.getInputStream()));
            ) {

               // BufferedReader stdIn =
                //    new BufferedReader(new InputStreamReader(System.in));

                //String theCommands = commandsTextField.getText();
                out.println(data);
                String fromServer;
                //String fromUser;
                in.readLine();
                StringBuilder sbOutput = new StringBuilder();
                while ((fromServer = in.readLine()) != null) { 
                       if (fromServer.equalsIgnoreCase("done")){
                           //outputTextArea.setText(sbOutput.toString());
                           return sbOutput.toString();
                           //sbOutput = new StringBuilder();
                           //break;
                       }else{
                           sbOutput.append(fromServer+"\n");
                       }

                }                
                out.close();
                in.close();

            } catch (UnknownHostException e) {
                System.err.println("Don't know about host " + hostName);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
                System.exit(1);
            }
        return null;
    }
    
    public void closeSocket() throws IOException{
        theSocket.close();
    }
    
}
