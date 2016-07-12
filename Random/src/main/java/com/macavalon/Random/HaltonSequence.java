package com.macavalon.Random;

import java.util.ArrayList;

//encapsulate java random inside randombase

public class HaltonSequence extends RandomBase
{
	public HaltonSequence(long _dimensionality, long seed, String name)
	{
		super(_dimensionality,name);
		InitialSeed = seed;
		
		sequenceOddStart = 1;
		sequenceEvenStart = 1;
		odd = true;
		
	}
	
	private double GenerateNextInSequence()
	{
		//we don't use the seed
		
		// use halton generator with base 2
		double value = 0.0;
		
		if(odd)
		{
			//even sequence generate from base 2
			value = Halton(sequenceOddStart,2);
			sequenceOddStart++;
		}
		else
		{
			//even sequence generate from base 3
			// otherwise values will be orthogonal (well sum to 1.0)
			value = Halton(sequenceEvenStart,3);
			sequenceEvenStart++;
			
		}
		odd = !odd;
		
		
		
		return value;
	}
	
	private double Halton(long nthTerm, int base)
	{
		long n0, n1, r;
		double h,f;
		n0 = nthTerm;
		h = 0.0;
		f = 1.0 / base;
		
		while(n0 > 0)
		{
			n1 = (int)(n0 / base);
			r = n0 - (n1 * base);
			h = h + (f * r);
			f = f / base;
			n0 = n1;
		}
		return h;
	}
	
	public double random()
	{
		return GenerateNextInSequence();
	}
	
	//RandomBase clone
	
	public void GetUniforms(ArrayList<Double> variates)
	{
		for(int j=0; j < GetDimensionality(); j++)
		{
			variates.add(GenerateNextInSequence());
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
		sequenceOddStart = 1;
		sequenceEvenStart = 1;
	}
	
	public void ResetDimensionality(long _dimensionality)
	{
		super.ResetDimensionality(_dimensionality);
		//InnerGenerator.SetSeed(InitialSeed);
	}

	
	private long InitialSeed;
	
	private long sequenceOddStart;
	private long sequenceEvenStart;
	
	private Boolean odd;
}
