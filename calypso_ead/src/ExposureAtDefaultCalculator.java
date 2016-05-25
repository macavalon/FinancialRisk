
abstract class ExposureAtDefaultCalculator {

	ExposureAtDefaultCalculator()
	{
		//all integral types are default initialised to 0 or 0.0
		
		//for unmargined transactions
		margined = false;
		
		
		// 1yr
		//minimumTimeRiskHorizon = 1.0;
		
		alpha = 1.4;
	}
	
	public double CalculateEad()
	{
		double ead = 0.0;
		
		//marketValue 
		//simple algebraic sum of the derivatives’ market values at the reference date
		
		//haircutValue
		haircutValue = 0.0;
		
		//calc RC
		double interimValue = marketValue - haircutValue;
		replacementCost = Math.max(interimValue, 0); 
		
		
		//initial subclass specific values
		SetFloor();
		
		SetSupervisoryFactor();
		
		double timeNow = 0;
		CalculateMinimumTimeRiskHorizon(timeNow);
		
		//calculate adjusted notional
		CalculateAdjustedNotional();
		
		CalculateSupervisoryDelta();
		
		CalculateEffectiveNotional();
		
		CalculateAddonAggregate();
		
		//
		//When the collateral held is less than the net market value of the derivative
		//contracts (“under-collateralisation”), the current replacement cost is positive and the multiplier is equal
		//to one (ie the PFE component is equal to the full value of the aggregate add-on).
		
		multiplier = 1;
		
		// otherwise
		//CalculateMultiplier();
		
		// this assumes unmargined netting sets
		
		ead = alpha * (replacementCost + (multiplier*addOnAggregate));
		
		
		return ead;
	}
	
	private void CalculateMinimumTimeRiskHorizon(Double timeFromNow)
	{
		if(!margined)
		{
			Double remainingMaturity = maturity - timeFromNow;
			Double step1 = Math.min(remainingMaturity, 1); //1yr
			
			minimumTimeRiskHorizon = Math.sqrt(step1/1);
		}
	}
	
	private void CalculateMultiplier()
	{
		double step1 = marketValue - haircutValue;
		double step2 = 2*(1-Floor)*addOnAggregate;
		
		double step3 = Math.exp(step1/step2);
		
		multiplier = Floor + ((1-Floor)*step3);
		
		multiplier = Math.min(1, multiplier);
	}
	
	abstract void CalculateAddonAggregate();
	
	abstract void CalculateEffectiveNotional();
	
	abstract void CalculateAdjustedNotional();
	
	abstract void CalculateSupervisoryDelta();
	
	abstract void SetFloor();
	abstract void SetSupervisoryFactor();
	
	protected double maturity;	// in whole years i.g. 1 = 1yr, 0.5 = 6mths 
	protected double startDate;
	protected double endDate;
	
	protected double notional;
	protected double marketValue;
	protected double haircutValue;
	
	protected double adjustedNotional;
	
	protected double effectiveNotional;
	
	protected double supervisoryDelta;
	
	private   double alpha;
	protected double replacementCost;
	protected double potentialFutureExposure;
	
	protected double multiplier;
	protected double addOnAggregate;
	
	protected double supervisoryDuration;
	
	protected double minimumTimeRiskHorizon;
	
	
	protected double supervisoryFactor;
	
	protected double Floor;
	
	protected Boolean margined;
	
	
}
