package com.ibm.asset.ILCTimesheet;

import java.io.Serializable;
import java.util.Date;

public class UserData implements Serializable{
	
	private String userName;
	private String empId;
	private Date selectedDate;
	private String workHrs;
	private String comments;
	private DropDownView dropDownView;
	private String billableToClient;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public Date getSelectedDate() {
		return selectedDate;
	}
	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}
	public String getWorkHrs() {
		return workHrs;
	}
	public void setWorkHrs(String workHrs) {
		this.workHrs = workHrs;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public DropDownView getDropDownView() {
		return dropDownView;
	}
	public void setDropDownView(DropDownView dropDownView) {
		this.dropDownView = dropDownView;
	}
	public String getBillableToClient() {
		return billableToClient;
	}
	public void setBillableToClient(String billableToClient) {
		this.billableToClient = billableToClient;
	}
	
	

}
