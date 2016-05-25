

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EadCalcMessageHandler implements HttpHandler
{
	EadCalcMessageHandler()
	{
	}
	
	private HashMap<String, String> queryToMap(String query){
		
		HashMap<String, String> result = new HashMap<String, String>();
		if(query!=null)
		{
		    for (String param : query.split("&")) {
		        String pair[] = param.split("=");
		        if (pair.length>1) {
		            result.put(pair[0], pair[1]);
		        }else{
		            result.put(pair[0], "");
		        }
		    }
		}
	    return result;
	}
	
	@Override
    public void handle(HttpExchange t) throws IOException {
		String httpmethod = t.getRequestMethod();
		
		if(httpmethod.compareTo("GET")!=0)
		{
			return;
		}
		
		
		String query = t.getRequestURI().getQuery();
		String path = t.getRequestURI().getPath();
		
		HashMap<String, String> queryMap = queryToMap(query);
	
		//input parameters for EAD calculation
		String asset = "";
		double notional = 0.0;
		double maturity = 0.0;
		double spotrate = 0.0;
		String currency = "";
		String currencyPair = ""; //i.e. USDGBP i.e. $1 exchanged for £0.68
		
		String response =  "";
		
		if(	query==null )
		{
			response = "Unknown Request";
		}
		else
		{
			
			//get the search query 
			if(queryMap.containsKey("asset"))
			{
				asset = queryMap.get("asset");
			}
			
			
			//need to pull out all arguments...as required for each type of request
			
			//get the maturity
			if(queryMap.containsKey("maturity"))
			{
				String maturityStr = queryMap.get("maturity");
				maturity = Double.parseDouble(maturityStr);
			}
			
			//get the notional
			if(queryMap.containsKey("notional"))
			{
				String notionalStr = queryMap.get("notional");
				Double.parseDouble(notionalStr);
				notional = Double.parseDouble(notionalStr);
			}
			
			//get the spotrate
			if(queryMap.containsKey("spotrate"))
			{
				String spotrateStr = queryMap.get("spotrate");
				spotrate = Double.parseDouble(spotrateStr);
			}
			
			//get the currency
			if(queryMap.containsKey("currency"))
			{
				currency = queryMap.get("currency");
			}
			
			//get the currency
			if(queryMap.containsKey("currencyPair"))
			{
				currencyPair = queryMap.get("currencyPair");
			}
			
			ExposureAtDefaultCalculator eadCalculator = null;
			
			if(asset.equals("InterestRateSwap"))
			{
				eadCalculator = new InterestRateSwapEADCalc(notional,maturity);
			}
			else if(asset.equals("FxForward"))
			{
				eadCalculator = new FxForwardEADCalc(notional,maturity,spotrate);
			}
			
			double calculatedEad = 0.0;
			JSONObject resultObj = new JSONObject();
			
			if(eadCalculator!=null)
			{
				
				
				//calculate the exposure at default
				calculatedEad = eadCalculator.CalculateEad();
				
				// output the result as JSON formatted string
				JSONObject inputObj  = new JSONObject();
				inputObj.put("asset", asset);
				inputObj.put("notional", notional);
				inputObj.put("maturity", maturity);
				inputObj.put("currency", currency);
				
				if(asset.equals("FxForward"))
				{
					inputObj.put("spotrate", spotrate);
					inputObj.put("currencyPair", currencyPair);
				}
				
				JSONObject outputObj  = new JSONObject();
				outputObj.put("calculatedEad", calculatedEad);
				
				resultObj.put("input", inputObj);
				resultObj.put("output", outputObj);
			}
			
			response =  resultObj.toString();
			
		}
		
		t.sendResponseHeaders(200, response.getBytes().length);
		
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
	

}
