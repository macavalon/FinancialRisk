
public class InterestRateSwapEADCalc extends ExposureAtDefaultCalculator
{

	InterestRateSwapEADCalc(double _notional, double _maturity)
	{
		maturity = _maturity;
		notional = _notional;
		endDate = maturity;
		
		domesticCurrencyRateFactor = 1.0;
		
		//NPV of swap is initially 0
		// also the swap is a zero instrument
		// so any positive value increase to one party, is a loss to the other
		marketValue = 0.0;
		
		// no haircut collateral held by the bank !
		haircutValue = 0.0;
	}
	
	@Override
	void CalculateAddonAggregate() {
		addOnAggregate = supervisoryFactor * effectiveNotional;
		
	}
	
	//include aggregation across maturity buckets
	//time bucket 1 (t < 1yr)
	//time bucket 2  (1 <= t <= 5yr)
	//time bucket 3  (t > 5yr)
	@Override
	 void CalculateEffectiveNotional()
	{
		//example is currently only in one time bucket
		
		effectiveNotional = Math.abs(supervisoryDelta * adjustedNotional * minimumTimeRiskHorizon);
		
	}

	@Override
	void CalculateAdjustedNotional() {

		double step1 = Math.exp(-0.05*startDate) - Math.exp(-0.05*endDate);
		supervisoryDuration = step1/0.05;
		
		adjustedNotional = notional * domesticCurrencyRateFactor * supervisoryDuration;
	}
	
	@Override
	void CalculateSupervisoryDelta() {

		Boolean longPosition = true;
		
		if(longPosition)
		{
			supervisoryDelta = 1.0;
		}
		else
		{
			supervisoryDelta = -1.0;
		}
	}

	@Override
	void SetFloor() {
		Floor = 5d/100d; //5%
		
	}

	@Override
	void SetSupervisoryFactor() {
		supervisoryFactor = 0.5d/100d; //0.5%
		
	}
	
	protected double supervisoryDuration;
	
	protected double domesticCurrencyRateFactor;

}
