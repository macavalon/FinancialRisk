package com.macavalon.Random;

import java.util.ArrayList;

//the antithetic class is a decorator
// i.e. you pass in another randombase derived
// class and it wraps with the same interface
// but now does antithetic sampling
// from the composed base
public class AntiThetic extends RandomBase
{
	public AntiThetic( RandomBase _innerGenerator)
	{
		super(_innerGenerator.GetDimensionality(), _innerGenerator.GetName());
		innerGenerator = _innerGenerator;
		innerGenerator.Reset();
		
		OddEven = true;
		
		nextVariates = new ArrayList<Double>();
	}
	
	public double random()
	{
		double val = 0;
		if(OddEven)
		{
			val = innerGenerator.random();
			nextVal = 1-val;
			
			OddEven = false;
		}
		else
		{
			
			val = nextVal;
			OddEven = true;
		}
		
		return val;
	}
	
	public void GetUniforms(ArrayList<Double> variates)
	{
		if(OddEven)
		{
			innerGenerator.GetUniforms(variates);
			
			nextVariates.clear();
			for(double val : variates)
			{
				nextVariates.add(1.0-val);
			}
			OddEven = false;
		}
		else
		{
			variates.clear();
			for(double val : nextVariates)
			{
				variates.add(1.0-val);
			}
			OddEven = true;
		}
	}
	
	public void Skip(long numberOfPaths)
	{
		if(numberOfPaths == 0)
		{
			return;
		}
		
		if(OddEven)
		{
			OddEven = false;
			numberOfPaths--;
		}
		
		innerGenerator.Skip(numberOfPaths/2);
		
		if((numberOfPaths % 2) ==0)
		{
			ArrayList<Double> tmp = new ArrayList<Double>();
			GetUniforms(tmp);
		}
		
	}
	
	public void SetSeed(long seed)
	{
		innerGenerator.SetSeed(seed);
		OddEven = true;
	}
	
	public void Reset()
	{
		innerGenerator.Reset();
		OddEven = true;
	}
	
	public void ResetDimensionality(long _dimensionality)
	{
		innerGenerator.ResetDimensionality(_dimensionality);
	}
	
	private RandomBase innerGenerator;
	private boolean OddEven;
	private ArrayList<Double> nextVariates;
	private double nextVal;
}
