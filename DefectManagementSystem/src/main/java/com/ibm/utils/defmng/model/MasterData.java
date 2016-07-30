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
	private String testPhase;
	@Column
	private String featureCycle;//FT,E2E,
	@Column
	private String sitCycle;
	@Column
	private String testgroup;// which group
	@Column
	private String featureRelease;// release 1, release 2
	@Column
	private String featureGroup; // 
	@Column
	private String rollout;
	
	@Column
	private String featureTestResult;
	
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
	 * @return the testPhase
	 */
	public String getTestPhase() {
		return testPhase;
	}
	/**
	 * @param testPhase the testPhase to set
	 */
	public void setTestPhase(String testPhase) {
		this.testPhase = testPhase;
	}
	/**
	 * @return the featureCycle
	 */
	public String getFeatureCycle() {
		return featureCycle;
	}
	/**
	 * @param featureCycle the featureCycle to set
	 */
	public void setFeatureCycle(String featureCycle) {
		this.featureCycle = featureCycle;
	}
	/**
	 * @return the sitCycle
	 */
	public String getSitCycle() {
		return sitCycle;
	}
	/**
	 * @param sitCycle the sitCycle to set
	 */
	public void setSitCycle(String sitCycle) {
		this.sitCycle = sitCycle;
	}

	/**
	 * @return the featureRelease
	 */
	public String getFeatureRelease() {
		return featureRelease;
	}
	/**
	 * @param featureRelease the featureRelease to set
	 */
	public void setFeatureRelease(String featureRelease) {
		this.featureRelease = featureRelease;
	}
	/**
	 * @return the featureGroup
	 */
	public String getFeatureGroup() {
		return featureGroup;
	}
	/**
	 * @param featureGroup the featureGroup to set
	 */
	public void setFeatureGroup(String featureGroup) {
		this.featureGroup = featureGroup;
	}
	/**
	 * @return the rollout
	 */
	public String getRollout() {
		return rollout;
	}
	/**
	 * @param rollout the rollout to set
	 */
	public void setRollout(String rollout) {
		this.rollout = rollout;
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
	/**
	 * @return the testgroup
	 */
	public String getTestgroup() {
		return testgroup;
	}
	/**
	 * @param testgroup the testgroup to set
	 */
	public void setTestgroup(String testgroup) {
		this.testgroup = testgroup;
	}
	/**
	 * @return the featureTestResult
	 */
	public String getFeatureTestResult() {
		return featureTestResult;
	}
	/**
	 * @param featureTestResult the featureTestResult to set
	 */
	public void setFeatureTestResult(String featureTestResult) {
		this.featureTestResult = featureTestResult;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MasterData [ID=" + ID + ", catagory=" + catagory + ", testPhase=" + testPhase + ", featureCycle=" + featureCycle + ", sitCycle=" + sitCycle
				+ ", testgroup=" + testgroup + ", featureRelease=" + featureRelease + ", featureGroup=" + featureGroup + ", rollout=" + rollout + ", recordCreation="
				+ recordCreation + "]";
	}
	
	
}
