/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computation;

/**
 *
 * @author Ethan_Hunt
 */
public class Actions {
    
    public static void runLoop(int n){
        for (int i=0;i<n;i++){
            
        }
    }
    
    // Java program for Fibonacci number using Loop.
    public static long fibonacciLoop(long number){
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
    
}
