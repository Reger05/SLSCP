package com.eger.slscp;

import java.util.ArrayList;
import java.util.Collections;

import com.eger.slscp.Plan.PlanLevel;

/**
 * Defines a Rate Area
 * Geographical area that sets defines the rate of different plans.
 * @author Robert
 *
 */
public class RateArea {
	
	String state;
	int rateAreaNumber;
	private ArrayList<Plan> plans = new ArrayList<Plan>();
	
	RateArea(int areaNumber, String stateName){
		state = stateName;
		rateAreaNumber = areaNumber;
	}
	
	void addPlan(Plan p){
		//for simplicity I will only add plans that are silver to reduce memory and processing footprint. Easily extendable to other plan types later
		if(p.getpLevel() == PlanLevel.SILVER)
			plans.add(p);
	}

	public String getSLSCP() {
		String ret = "";
		if(plans.size() > 0){
			Collections.sort(plans);//sort based on rate. 
			ret = String.valueOf(plans.get(0).getRate());//after sorting, spot [1] is the 2nd cheapest
		}
		return ret;
	}

	
	
}
