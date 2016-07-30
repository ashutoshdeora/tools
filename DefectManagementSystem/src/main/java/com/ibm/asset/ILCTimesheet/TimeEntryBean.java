package com.ibm.asset.ILCTimesheet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class TimeEntryBean implements Serializable{
	
	
	public TimeEntryBean() {
	}
	
	@PostConstruct
	void populatePreRenderData(){
		userDataLists = new ArrayList<UserData>();
		UserData userData = new UserData();
		userData.setUserName("Ashutosh Deora");
		userData.setEmpId("0147B0");
		userData.setSelectedDate(new Date());
		userData.setWorkHrs("0");
		userData.setComments("Some Activity");
		userData.setBillableToClient("No");
		userData.setDropDownView(null);
		userDataLists.add(userData);
		userDataLists.add(userData);
		userDataLists.add(userData);
		userDataLists.add(userData);
		userDataLists.add(userData);
		
	}
	private List<UserData> userDataLists ;
	
	public List<UserData> getUserDataLists() {
		return userDataLists;
	}

	public void setUserDataLists(List<UserData> userDataLists) {
		this.userDataLists = userDataLists;
	}
	
	public void addNewRow(){
		UserData userData = new UserData();
		userData.setUserName("Ashutosh Deora");
		userData.setEmpId("0147B0");
		userData.setSelectedDate(new Date());
		userData.setWorkHrs("0");
		userData.setComments("Some Activity");
		userData.setBillableToClient("No");
		userData.setDropDownView(new DropDownView());
		userDataLists.add(userData);
		
	}
	
	public void populateSelectedWeek(){
		
	}


}
