/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computation;

//import interfaces.ShellExecutionCallback;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Ethan_Hunt
 */
public class ShellExecution {
    
    private String commands;
    
    
    
    //public ShellExecution(String commands, ShellExecutionCallback theCallback){
    //    this.commands = commands;
    //}
    
    public String executeShell(String commands){
        StringBuffer output = new StringBuffer();
        
        String[] cmd = { "/bin/sh", "-c",commands};
        Process p;
        try {
                p = Runtime.getRuntime().exec(cmd);
                //p.waitFor();
                BufferedReader stdInput = new BufferedReader(new
                 InputStreamReader(p.getInputStream()));
 
                BufferedReader stdError = new BufferedReader(new
                 InputStreamReader(p.getErrorStream()));
                                
                String line = "";			
                while ((line = stdInput.readLine())!= null) {
                        output.append(line + "\n");
                }

                while ((line = stdError.readLine())!= null) {
                        output.append(line + "\n");
                }
                
        } catch (IOException e) {
                e.printStackTrace();
        }
        
        return output.toString();
        
    }

}
