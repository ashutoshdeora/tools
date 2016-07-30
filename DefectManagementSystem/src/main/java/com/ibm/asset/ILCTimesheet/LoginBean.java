package com.ibm.asset.ILCTimesheet;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@RequestScoped
@ManagedBean
public class LoginBean implements Serializable{
	
	public LoginBean() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String loginAction(){
		// DB call 
		// if true next page 
		if(true){
			return ""; 
		}else{
			return "timeEntry.xhtml";
		}
		// else same page 
	}

}
