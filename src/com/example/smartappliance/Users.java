package com.example.smartappliance;

public class Users {

	int id;
	String ApplianceName;
	String StartTime;
	String EndTime;
	String Cost;
	
	public String getApplianceName() {
		return ApplianceName;
	}
	public void setApplianceName(String appname) {
		this.ApplianceName = appname;
	}
	
	
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String strtime) {
		this.StartTime = strtime;
	}
	
	
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endtime) {
		this.EndTime = endtime;
	}
	
	
	public String getCost() {
		return Cost;
	}
	public void setCost(String cost) {
		this.Cost = cost;
	}
	
	
}