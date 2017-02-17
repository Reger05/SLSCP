package com.eger.slscp;

/**
 * Defines a health care plan.
 * Implements compareable for sorting array list to get the SLSCP
 * @author Robert
 *
 */
public class Plan implements Comparable<Plan>{

	private String planId;
	private String state;
	enum PlanLevel {BRONZE, SILVER, GOLD, PLATINUM, CATASTROPHIC};
	private PlanLevel pLevel;
	private Double rate;
	private int rateArea;
	
	Plan(String planId, String state, String pLevel, Double rate, int rateArea){
		//could also call the other constructor here
		this.planId = planId;
		this.state = state;
		this.pLevel =  convertPlanLevelStringToEnum(pLevel);
		this.rate = rate;
		this.rateArea = rateArea;
	}
	
	Plan(String planId, String state, PlanLevel pLevel, Double rate, int rateArea){
		this.planId = planId;
		this.state = state;
		this.pLevel = pLevel;
		this.rate = rate;
		this.rateArea = rateArea;
	}
	
	
	private PlanLevel convertPlanLevelStringToEnum(String planLevel){
		PlanLevel ret;
		switch(planLevel){
		case "Bronze":
			ret = PlanLevel.BRONZE;
			break;
		case "Silver":
			ret = PlanLevel.SILVER;
			break;
		case "Gold":
			ret = PlanLevel.GOLD;
			break;
		case "Platinum":
			ret = PlanLevel.PLATINUM;
			break;
		case "CATASTROPHIC":
			ret = PlanLevel.CATASTROPHIC;
			break;
		default:
			ret = PlanLevel.BRONZE;
		}
		return ret;
	}

	public String getPlanId() {
		return planId;
	}

	public String getState() {
		return state;
	}
	
	public PlanLevel getpLevel() {
		return pLevel;
	}

	public Double getRate() {
		return rate;
	}

	public int getRateArea() {
		return rateArea;
	}

	@Override
	public int compareTo(Plan that) {
		int ret = 0;
		if(this.rate < that.rate){
			ret = -1;
		}
		else if(this.rate > that.rate){
			ret = 1;
		}
		
		return ret;
	}
	
}
