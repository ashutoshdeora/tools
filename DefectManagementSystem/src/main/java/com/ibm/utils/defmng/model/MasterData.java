package com.ibm.utils.defmng.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: MasterData
 *
 */
@Entity
@Table(name="masterdata")
public class MasterData implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public MasterData() {
		super();
	}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;
   
	@Column
	private String catagory;
	@Column
	private String catdata;
	
	
	@Embedded
	RecordCreation recordCreation;

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}
	/**
	 * @return the catagory
	 */
	public String getCatagory() {
		return catagory;
	}
	/**
	 * @param catagory the catagory to set
	 */
	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}
	/**
	 * @return the catdata
	 */
	public String getCatdata() {
		return catdata;
	}
	/**
	 * @param catdata the catdata to set
	 */
	public void setCatdata(String catdata) {
		this.catdata = catdata;
	}
	/**
	 * @return the recordCreation
	 */
	public RecordCreation getRecordCreation() {
		return recordCreation;
	}
	/**
	 * @param recordCreation the recordCreation to set
	 */
	public void setRecordCreation(RecordCreation recordCreation) {
		this.recordCreation = recordCreation;
	}
	
	
	
}
