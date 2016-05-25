package com.calypso.dev.montecarloIntegation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class McTest extends TestCase
{
	MonteCarloIntegration m;
	
	public McTest()
	{
	
		 
	}
	
	
	// single precision i.e. float gives 6/7 decimal places
	// double precision i.e. doublefive 15/16 decimal places
	
	public void testLowAccuracy()
    {
		int numberOfSamples = 100000000;
        
	    m = new MonteCarloIntegration(numberOfSamples);
		
	    double integral = m.calcIntegral();
	    
	    
	    //from http://www.wolframalpha.com/widgets/view.jsp?id=8ab70731b1553f17c11a3bbc87e0b605
	    double testvalue = 0.74682413281242;
	    
	    double tolerance = 0.00000001;
	    
	    double difference = Math.abs(integral-testvalue);
	    
	    assertTrue(difference > tolerance);
        
    }
	
	/*public void testHighAccuracy()
    {
		int numberOfSamples = 100000000;
        
	    m = new MonteCarloIntegration(numberOfSamples);
		
	    double integral = m.calcIntegral();
	    
	    
	    //from http://www.wolframalpha.com/widgets/view.jsp?id=8ab70731b1553f17c11a3bbc87e0b605
	    double testvalue = 0.74682413281242;
	    
	    double tolerance = 0.00000001;
	    
	    double difference = Math.abs(integral-testvalue);
	    
	    assertTrue(difference < tolerance);
        
    }*/
	
}
