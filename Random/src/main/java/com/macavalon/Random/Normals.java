package com.macavalon.Random;

//normal distribution functions

public class Normals
{
	private final static Normals instance = new Normals();
	
	final double OneOverRootTwoPi = 0.398942280401433;
	
	private Normals()
	{

	}
	
	public static Normals getInstance()
	{
		return instance;
	}
	
	public double NormalDensity(double x)
	{
		return OneOverRootTwoPi*Math.exp(-x*x/2);
	}
	
	public double CumulativeNormal(double x)
	{
		double a[] = { 	0.319381530,
						-0.356563782,
						1.781477937,
						-1.821255978,
						1.330274429 };
		
		double result = 0.0;
		
		if(x < -7.0)
		{
			result = NormalDensity(x) / Math.sqrt(1+(x*x));
		}
		else
		{
			if(x > 7.0)
			{
				result = 1.0 - CumulativeNormal(-x);
			}
			else
			{
				double temp = 1.0/(1.0 + 0.2316419 * Math.abs(x));
				
				double step4 = temp * a[4];
				double step3 = temp * (a[3] +  step4);
				double step2 = temp * (a[2] +  step3);
				double step1 = temp * (a[1] +  step2);
				double step0 = temp * (a[0] +  step1);
				
				result = 1 - NormalDensity(x) * step0;
				
				if(x <= 0.0)
				{
					result = 1.0-result;
				}
			}
		}
		
		return result;
		
	}
	
	public double InverseCumulativeNormal(double quantile)
	{
		double a[] = { 2.50662823884,
						-18.61500062529,
						41.39119773534,
						-25.44106049637 };
						
		double b[] = { -8.47351093090,
						23.08336743743,
						-21.06224101826,
						3.13082909833 };
		
		double c[] = {	0.3374754822726147,
						0.9761690190917186,
						0.1607979714918209,
						0.0276438810333863,
						0.0038405729373609,
						0.0003951896511919,
						0.0000321767881768,
						0.0000002888167364,
						0.0000003960315187 };
		
		//this code from https://www.quantstart.com/articles/Statistical-Distributions-in-C
		if (quantile >= 0.5 && quantile <= 0.92) 
		{
		    double num = 0.0;
		    double denom = 1.0;

		    for (int i=0; i<4; i++) {
		      num += a[i] * Math.pow((quantile - 0.5), 2*i + 1);
		      denom += b[i] * Math.pow((quantile - 0.5), 2*i);
		    }
		    return num/denom;

		  } else if (quantile > 0.92 && quantile <= 1.0) {
		    double num = 0.0;

		    for (int i=0; i<9; i++) {
		      num += c[i] * Math.pow((Math.log10(-Math.log10(1-quantile))), i);
		    }
		    return num;

		  } else {
		    return -1.0*InverseCumulativeNormal(1-quantile);
		  }
	}

}
