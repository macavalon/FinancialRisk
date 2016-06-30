package com.macavalon.Random;

public class ParkMiller
{
	
	final long a = 16807;
	final long m = 2147483647;
	final long q = 127773;
	final long r = 2836;
	
	public ParkMiller(long _seed)
	{
		seed = _seed;
		if(seed == 0)
		{
			seed = 1;
		}
	}
	
	public void SetSeed(long _seed)
	{
		seed = _seed;
		if(seed == 0)
		{
			seed = 1;
		}
	}

	public long Max()
	{
		return m-1;
	}
	
	public long Min()
	{
		return 1;
	}
	
	public long GetOneRandomInteger()
	{
		long k;
		
		k = seed/q;
		
		seed = a*(seed-(k*q))-(r*k);
		
		if(seed < 0)
		{
			seed += m;
		}
		
		return seed;
	}
	
	private long seed;
}
