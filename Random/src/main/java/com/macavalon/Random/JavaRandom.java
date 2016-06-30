package com.macavalon.Random;

import java.util.ArrayList;

//encapsulate java random inside randombase

public class JavaRandom extends RandomBase
{
	public JavaRandom(long _dimensionality, long seed, String name)
	{
		super(_dimensionality,name);
		InitialSeed = seed;
		
		//InnerGenerator = new ParkMiller(seed);
		
		//Reciprocal = 1.0/(1.0+InnerGenerator.Max());
		
	}
	
	public double random()
	{
		return Math.random();
	}
	
	//RandomBase clone
	
	public void GetUniforms(ArrayList<Double> variates)
	{
		for(int j=0; j < GetDimensionality(); j++)
		{
			variates.add(Math.random());
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
		//InnerGenerator.SetSeed(seed);
		
	}
	
	public void Reset()
	{
		//InnerGenerator.SetSeed(InitialSeed);
	}
	
	public void ResetDimensionality(long _dimensionality)
	{
		super.ResetDimensionality(_dimensionality);
		//InnerGenerator.SetSeed(InitialSeed);
	}
	
	//private ParkMiller InnerGenerator;
	
	private long InitialSeed;
	
	//private double Reciprocal;
}
