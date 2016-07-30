package com.ibm.asset.ILCTimesheet;

import java.util.List;

public class DropDownView {
	
	private List<WorkStream> workStreams;
	private List<Activity> activities;
	private String workstream;
	private String activity;
	public List<WorkStream> getWorkStreams() {
		return workStreams;
	}
	public void setWorkStreams(List<WorkStream> workStreams) {
		this.workStreams = workStreams;
	}
	public List<Activity> getActivities() {
		return activities;
	}
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	public String getWorkstream() {
		return workstream;
	}
	public void setWorkstream(String workstream) {
		this.workstream = workstream;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	
	
	

}
