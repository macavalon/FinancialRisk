package com.calypso.dev.montecarloIntegation;

import java.util.ArrayList;
import java.util.Random;

public class MonteCarloIntegration {
	
	public double functionToIntegrate(double x)
	{
		double xsquared = Math.pow(x, 2);
		double y = Math.exp(-xsquared);
		
		return y;
	}
	
	public MonteCarloIntegration(int _numberOfSamples)
	{
		numberOfSamples = _numberOfSamples;
	
	}
	
	public double calcIntegral()
	{
		double sum   = 0.0;
        
        double sum2  = 0.0;
        
        //integrate e(-x^2)
        //over range 0 <= x <= 1
        
        double xStart = 0;
        double xEnd = 1;
        double xRange = xEnd - xStart;
        
        double yStart = functionToIntegrate(xStart);
        double yEnd = functionToIntegrate(xEnd);
        
        double yMax = Math.max(yEnd, yStart);
        
        double yMin = Math.min(yEnd, yStart);
        double yRandStart = Math.min(0, yMin);
        
        double area = xRange * yMax;    // area of enclosing region
		
		for (int i = 0; i < numberOfSamples; i++) 
		{
			// calculate random samples
			// accept those that are inside the area under the function
			double x = xStart + xEnd * Math.random();
            double y = yRandStart + yMax * Math.random();
		
            //are these coordinates inside the function
            //i.e. inside the area detemined by function curve
            
            if( y <= functionToIntegrate(x))
            {
            	sum   += 1.0;
                sum2  += 1.0;
            }
		}
		
		// estimate integral
        double integral   = area * sum  / numberOfSamples;

        // estimate the error
        double error  = area * Math.sqrt((sum2 /numberOfSamples - (sum /numberOfSamples)*(sum /numberOfSamples)) / numberOfSamples);

        System.out.printf("Integral = %.9f +- %.9f", integral, error);
	
        return integral;
	}
	
	private int numberOfSamples;
}
