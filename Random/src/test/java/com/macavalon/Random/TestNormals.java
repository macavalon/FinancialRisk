package com.macavalon.Random;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestNormals extends TestCase {
	public TestNormals( String testName )
    {
        super( testName );
    }
	
	/**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestNormals.class );
    }

    /**
     * initial test of normal distribution
     */
    public void testNormal()
    {
    	Normals instance = Normals.getInstance();
    	
    	double val0 = instance.NormalDensity(0);
    	double testVal = round(val0,1);
    	assertEquals(testVal,0.4);
    	
    	double val0pt5 = instance.NormalDensity(0.5);
    	testVal = round(val0pt5,2);
    	assertEquals(testVal,0.35);
    	
    	double val1 = instance.NormalDensity(1);
    	testVal = round(val1,2);
    	assertEquals(testVal,0.24);
    	
        //assertTrue( true );
    }
    
    // test values from
    // https://en.wikipedia.org/wiki/Normal_distribution
    // for mean=0, variance = 1
    
    public void testCumulativeNormal()
    {
    	Normals instance = Normals.getInstance();
    	
    	double val0 = instance.CumulativeNormal(0);
    	double testVal = round(val0,1);
    	assertEquals(testVal,0.5);
    	
    	double val0pt5 = instance.CumulativeNormal(0.5);
    	testVal = round(val0pt5,1);
    	assertEquals(testVal,0.7);
    	
    	double val1 = instance.CumulativeNormal(1);
    	testVal = round(val1,2);
    	assertEquals(testVal,0.84);
    
    }
    
    public double round(double value, int decimalPlaces)
    {
    	StringBuilder precisionString = new StringBuilder();
    	precisionString.append("#.");

    	for(int i=0; i < decimalPlaces; i++)
    	{
    		precisionString.append("#");
    	}
    	DecimalFormat df = new DecimalFormat(precisionString.toString()); 
    	df.setRoundingMode(RoundingMode.HALF_UP);
    	String result = df.format(value);
    	
    	Double roundedNum = new Double(result);
    	
    	return roundedNum;
    }
    
    // test values from
    // https://en.wikipedia.org/wiki/Normal_distribution
    
    public void testInverseCumulativeNormal()
    {
    	Normals instance = Normals.getInstance();
    	
    	double val0 = instance.InverseCumulativeNormal(0);
    	
    	assertEquals(val0,Double.NEGATIVE_INFINITY);
    	
    	double val0pt5 = instance.InverseCumulativeNormal(0.5);
    	double testVal = Math.abs(val0pt5);
    	assertEquals(testVal, 0.0);
    	
    	double val1 = instance.InverseCumulativeNormal(1);
    	
    	assertEquals(val1, Double.POSITIVE_INFINITY);
    
    }
	
}
