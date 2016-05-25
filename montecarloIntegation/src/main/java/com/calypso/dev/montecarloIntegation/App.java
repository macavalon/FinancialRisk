package com.calypso.dev.montecarloIntegation;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        int numberOfSamples = 100;
        
        MonteCarloIntegration m = new MonteCarloIntegration(numberOfSamples);
        
        double integral = m.calcIntegral();
        
    }
}
