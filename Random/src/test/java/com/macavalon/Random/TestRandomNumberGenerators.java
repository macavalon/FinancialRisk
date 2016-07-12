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

        numberOfIntegrationSamples = 100000;
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
	    
	    double toleranceUpper = 0.0012; //predictable, not as good as halton sequence
	    double toleranceLower = 0.0011;
	    
	    double difference = Math.abs(integral-testvalue);
	    
	    assertTrue(difference < toleranceUpper);
	    assertTrue(difference > toleranceLower);
    	
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
	    
	    double toleranceUpper = 0.0019;	//predictable, not as good as std parkmiller
	    double toleranceLower = 0.0018;
	    
	    double difference = Math.abs(integral-testvalue);
	    
	    assertTrue(difference < toleranceUpper);
	    assertTrue(difference > toleranceLower);
    	
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
	    
	    double toleranceUpper = 0.0025;	//java random shows wide range of convergence
	    double toleranceLower = 0.0005;
	    
	    double difference = Math.abs(integral-testvalue);
	    
	    assertTrue(difference < toleranceUpper);
	    assertTrue(difference > toleranceLower);
    	
    }
    
    /**
     * test functionality provided by halton low discrepancy sequence generator
     * which implements the randombase interface
     */
    public void testHaltonSequence()
    {
    	int dimension = 1; // return 1 random number
    	int seed = 1;
    	
    	
    	RandomBase JavaRandomInst = new HaltonSequence(dimension,seed, "Halton");
    	
    	
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
	    
	    double toleranceUpper = 0.000065; //predictable, and much higher accuracy than other random generators
	    double toleranceLower = 0.000064; //for this use with montecarlo
	    
	    double difference = Math.abs(integral-testvalue);
	    
	    assertTrue(difference < toleranceUpper);
	    assertTrue(difference > toleranceLower);
    	
    	
    	
    }
    
}
