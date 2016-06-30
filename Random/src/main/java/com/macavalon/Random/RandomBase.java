package com.macavalon.Random;

import java.util.ArrayList;

//dimensionality : how many random numbers to return at once

abstract class RandomBase {
	RandomBase(long _dimensionality, String _name)
	{
		dimensionality = _dimensionality;
		name = _name;
	}
	
	public long GetDimensionality()
	{
		return dimensionality;
	}
	
	abstract void GetUniforms(ArrayList<Double> variates);
	abstract void Skip(long numberOfPaths);
	abstract void SetSeed(long seed);
	abstract void Reset();
	
	abstract double random(); //simply return random double
	
	
	public void GetGaussians(ArrayList<Double> variates)
	{
		GetUniforms(variates);
		
		for(int i=0; i < dimensionality; i++)
		{
			double x = variates.get(i);
			double val = Normals.getInstance().InverseCumulativeNormal(x);
			variates.set(i, val);
		}
	}
	
	public void ResetDimensionality(long _dimensionality)
	{
		dimensionality = _dimensionality;
	}
	
	public String GetName()
	{
		return name;
	}
	
	private long dimensionality;
	
	private String name;
	
}
