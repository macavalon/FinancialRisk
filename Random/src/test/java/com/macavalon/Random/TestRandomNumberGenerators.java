package com.macavalon.Random;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestRandomNumberGenerators extends TestCase {
	
	int numberOfIntegrationSamples;
	
	public TestRandomNumberGenerators( String testName )
	{
        super( testName );
        numberOfIntegrationSamples = 10000000;
    }
	
	/**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestRandomNumberGenerators.class );
    }

    /**
     * test functionality provided by random park miller generator
     * which implements the randombase interface
     */
    public void testRandomParkMiller()
    {
    	int dimension = 1; // return 1 random number
    	int seed = 1;

    	
    	RandomBase ParkMillerRndInst = new RandomParkMiller(dimension,seed,"ParkMiller");
    	
    	// test using montecarlo integration
    	
    	Function expFunction = new Function() {
    		public double fn(double x)
    		{
    			double xsquared = Math.pow(x, 2);
    			double y = Math.exp(-xsquared);
    			
    			return y;
    		}
    		
    	};
    	
    	int numberOfSamples = numberOfIntegrationSamples;
        
    	MonteCarloIntegration m = new MonteCarloIntegration(numberOfSamples);
		m.setFunctionToIntegrate(expFunction);
		m.setRandomGenerator(ParkMillerRndInst);
	    
	    
	    double integral = m.calcIntegral();
	    
	    
	    //from http://www.wolframalpha.com/widgets/view.jsp?id=8ab70731b1553f17c11a3bbc87e0b605
	    double testvalue = 0.74682413281242;
	    
	    double tolerance = 0.00000001;
	    
	    double difference = Math.abs(integral-testvalue);
	    
	    assertTrue(difference > tolerance);
    	
    }
    
    /**
     * test functionality provided by random park miller generator
     * which implements the randombase interface
     */
    public void testAntiTheticRandomParkMiller()
    {
    	int dimension = 1; // return 1 random number
    	int seed = 1;

    	
    	RandomBase ParkMillerRndInst = new RandomParkMiller(dimension,seed,"AntiTheticParkMiller");
    	RandomBase AntiTheticParkMiller = new AntiThetic(ParkMillerRndInst);
    	
    	// test using montecarlo integration
    	
    	Function expFunction = new Function() {
    		public double fn(double x)
    		{
    			double xsquared = Math.pow(x, 2);
    			double y = Math.exp(-xsquared);
    			
    			return y;
    		}
    		
    	};
    	
    	int numberOfSamples = numberOfIntegrationSamples;
        
    	MonteCarloIntegration m = new MonteCarloIntegration(numberOfSamples);
		m.setFunctionToIntegrate(expFunction);
		m.setRandomGenerator(AntiTheticParkMiller);
	    
	    
	    double integral = m.calcIntegral();
	    
	    
	    //from http://www.wolframalpha.com/widgets/view.jsp?id=8ab70731b1553f17c11a3bbc87e0b605
	    double testvalue = 0.74682413281242;
	    
	    double tolerance = 0.00000001;
	    
	    double difference = Math.abs(integral-testvalue);
	    
	    assertTrue(difference > tolerance);
    	
    }
    
    /**
     * test functionality provided by random from java, but
     * which implements the randombase interface
     */
    public void testJavaRandom()
    {
    	int dimension = 1; // return 1 random number
    	int seed = 1;
    	
    	
    	RandomBase JavaRandomInst = new JavaRandom(dimension,seed, "JavaRandom");
    	
    	
    	// test using montecarlo integration
    	
    	Function expFunction = new Function() {
    		public double fn(double x)
    		{
    			double xsquared = Math.pow(x, 2);
    			double y = Math.exp(-xsquared);
    			
    			return y;
    		}
    		
    	};
    	
    	int numberOfSamples = numberOfIntegrationSamples;
        
    	MonteCarloIntegration m = new MonteCarloIntegration(numberOfSamples);
		m.setFunctionToIntegrate(expFunction);
		m.setRandomGenerator(JavaRandomInst);
	    
	    
	    double integral = m.calcIntegral();
	    
	    
	    //from http://www.wolframalpha.com/widgets/view.jsp?id=8ab70731b1553f17c11a3bbc87e0b605
	    double testvalue = 0.74682413281242;
	    
	    double tolerance = 0.00000001;
	    
	    double difference = Math.abs(integral-testvalue);
	    
	    assertTrue(difference > tolerance);
    	
    	
    	
    }
}
