
public class FxForwardEADCalc extends ExposureAtDefaultCalculator
{

	FxForwardEADCalc(double _notional, double _maturity, double _spotRate)
	{
		maturity = _maturity;
		notional = _notional;
		spotRate = _spotRate;
	}
	
	@Override
	void CalculateAddonAggregate() {
		addOnAggregate = supervisoryFactor * effectiveNotional;
		
	}
	
	@Override
	 void CalculateEffectiveNotional()
	{
		//no time buckets for fx
		effectiveNotional = Math.abs(supervisoryDelta * adjustedNotional * minimumTimeRiskHorizon);
	
	}
	
	@Override
	void CalculateAdjustedNotional() {
		// For foreign exchange derivatives, the adjusted notional is defined as the notional of the foreign
		// currency leg of the contract, converted to the domestic currency. If both legs of a foreign 
		// exchange derivative are denominated in currencies other than the domestic currency, the
		// notional amount of each leg is converted to the domestic currency and the leg with the larger
		// domestic currency value is the adjusted notional amount.
		//
		// For USDGBP... USE denomination is greater so 
		if(spotRate <= 1)
		{
			adjustedNotional = notional;
		}
		else
		{
			adjustedNotional = spotRate * notional;
		}
		
	}
	
	@Override
	void CalculateSupervisoryDelta() {
		Boolean longPosition = true;
		
		if(longPosition)
		{
			//“Long in the primary risk factor” means that the market value of the instrument increases when the value of the primary risk
			//factor increases
			supervisoryDelta = 1.0;
		}
		else
		{
			//“Short in the primary risk factor” means that the market value of the instrument decreases when the value of the primary risk
			//factor increases.
			supervisoryDelta = -1.0;
		}
		
	}

	@Override
	void SetFloor() {
		Floor = 5d/100d; //5%
		
	}

	@Override
	void SetSupervisoryFactor() {
		supervisoryFactor = 4d/100d; //4%
		
	}

	private double spotRate;
}
