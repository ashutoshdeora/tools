package com.ibm.utils.defmng.model;

import java.io.Serializable;

public class DefectBean implements Serializable{
	
	private String HPQCID;

	/**
	 * @return the hPQCID
	 */
	public String getHPQCID() {
		return HPQCID;
	}

	/**
	 * @param hPQCID the hPQCID to set
	 */
	public void setHPQCID(String hPQCID) {
		HPQCID = hPQCID;
	}

}
