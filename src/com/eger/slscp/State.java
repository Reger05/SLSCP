package com.eger.slscp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Defines a State object
 * @author Robert
 *
 */
public class State {

	String stateName;
	HashMap<Integer, RateArea> rateAreas = new HashMap<Integer,RateArea>();//stores rate area to see if we have a rate area by this number in this state
	HashMap<Integer, ArrayList<RateArea>> zipCodeRateMap = new HashMap<Integer, ArrayList<RateArea>>();//used to easily access rate area plans by zipcode
	
	State(String stateName){
		this.stateName = stateName;
	}
	
	public void updateRateArea(int zipcode, int rateArea){
		RateArea ra = rateAreas.get(rateArea);
		if(ra == null){
			ra = new RateArea(rateArea, stateName);//only create the rate area once per state. The same rate area can appear in multiple counties but it's still the same rate area. 
			rateAreas.put(rateArea, ra);
		}
		ArrayList<RateArea> raList = zipCodeRateMap.get(zipcode);
		if(raList == null){
			raList = new ArrayList<RateArea>();
			zipCodeRateMap.put(zipcode, raList);
		}
		if(!raList.contains(ra))//Make sure list doesn't already contain this object
			raList.add(ra);//add RateArea to the list
	}

	public void addPlan(Plan p) {
		int area = p.getRateArea();
		RateArea ra = rateAreas.get(area);
		if(ra != null)
			ra.addPlan(p);
		
	}

	public String getSLSCP(int zipcode) {
		String ret = "";
		ArrayList<RateArea> raList = zipCodeRateMap.get(zipcode);
		if(raList != null && (raList.size() == 1))//if raList is greating than 1 we have more than one rate Area for this zip code
			ret = raList.get(0).getSLSCP();
		
		return ret;
	}
	
}
