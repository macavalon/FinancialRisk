package com.macavalon.Random;

import java.util.ArrayList;
import java.util.Random;

public class MonteCarloIntegration {
	
	public MonteCarloIntegration(int _numberOfSamples)
	{
		numberOfSamples = _numberOfSamples;
		
		functionToIntegrate = null;
		
		rnd = null;
	}
	
	public void setFunctionToIntegrate(Function _functionToIntegrate)
	{
		functionToIntegrate = _functionToIntegrate;
	}
	
	public void setRandomGenerator(RandomBase _rnd)
	{
		rnd = _rnd;
	}
	
	public double calcIntegral()
	{
		double integral = 0;
		
		if(functionToIntegrate == null)
		{
		
		}
		else
		{
			double sum   = 0.0;
	        
	        double sum2  = 0.0;
	        
	        //integrate e(-x^2)
	        //over range 0 <= x <= 1
	        
	        double xStart = 0;
	        double xEnd = 1;
	        double xRange = xEnd - xStart;
	        
	        double yStart = functionToIntegrate.fn(xStart);
	        double yEnd = functionToIntegrate.fn(xEnd);
	        
	        double yMax = Math.max(yEnd, yStart);
	        
	        double yMin = Math.min(yEnd, yStart);
	        double yRandStart = Math.min(0, yMin);
	        
	        double area = xRange * yMax;    // area of enclosing region
			
	        //two dimensions to get random numbers for
	        rnd.ResetDimensionality(2);
	        ArrayList<Double> samples = new ArrayList<Double>();
	        
			for (int i = 0; i < numberOfSamples; i++) 
			{
				// calculate random samples
				// accept those that are inside the area under the function
				
				
				samples.clear();
				
				rnd.GetUniforms(samples);
				
				double x = xStart + xEnd * samples.get(0);
	            double y = yRandStart + yMax * samples.get(1);
			
	            //are these coordinates inside the function
	            //i.e. inside the area detemined by function curve
	            
	            if( y <= functionToIntegrate.fn(x))
	            {
	            	sum   += 1.0;
	                sum2  += 1.0;
	            }
			}
			
			// estimate integral
	        integral   = area * sum  / numberOfSamples;
	
	        // estimate the error
	        double error  = area * Math.sqrt((sum2 /numberOfSamples - (sum /numberOfSamples)*(sum /numberOfSamples)) / numberOfSamples);
	
	        System.out.printf("Using rnd : %s, Integral = %.9f +- %.9f\n",rnd.GetName(), integral, error);
		}
        return integral;
	}
	
	private int numberOfSamples;
	
	Function functionToIntegrate;
	
	RandomBase rnd;
}
