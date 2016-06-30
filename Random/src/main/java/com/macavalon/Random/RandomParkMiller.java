package com.macavalon.Random;

import java.util.ArrayList;

public class RandomParkMiller extends RandomBase
{
	public RandomParkMiller(long _dimensionality, long seed,String name)
	{
		super(_dimensionality,name);
		InitialSeed = seed;
		
		InnerGenerator = new ParkMiller(seed);
		
		Reciprocal = 1.0/(1.0+InnerGenerator.Max());
		
	}
	
	//RandomBase clone
	
	public double random()
	{
		return InnerGenerator.GetOneRandomInteger()*Reciprocal;
	}
	
	public void GetUniforms(ArrayList<Double> variates)
	{
		for(int j=0; j < GetDimensionality(); j++)
		{
			variates.add(InnerGenerator.GetOneRandomInteger()*Reciprocal);
		}
	}
	
	public void Skip(long numberOfPaths)
	{
		ArrayList<Double> tmp = new ArrayList<Double>();
		for(int i =0; i < numberOfPaths; i++)
		{
			GetUniforms(tmp);
		}
	}
	
	public void SetSeed(long seed)
	{
		InitialSeed = seed;
		InnerGenerator.SetSeed(seed);
		
	}
	
	public void Reset()
	{
		InnerGenerator.SetSeed(InitialSeed);
	}
	
	public void ResetDimensionality(long _dimensionality)
	{
		super.ResetDimensionality(_dimensionality);
		InnerGenerator.SetSeed(InitialSeed);
	}
	
	private ParkMiller InnerGenerator;
	
	private long InitialSeed;
	
	private double Reciprocal;
}
