package com.ibm.utils.defmng.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Feature
 *
 */
@Entity
@Table(name="feature")
public class Feature implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Feature() {
		super();
	}
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="feature_id")
	private int featureID;
	@Column
	private String featureName;
	@Column
	private String owner;
	@Column
	private String ba;
	
	@Column
	private String featureGrouping;
	
	@Column(length=4000) // test script COl - C
	private String featureTestScriptName;
	
	@Column(length=4000) // Comments
	private String featureTestScriptComments;
	
	@Column // Col E
	private String featureTestExecutionPhase; // Feature /1 E2E / 2E2E
	
	@Column
	private String featureOwner;
	
	@Column
	private String featureDataSetCatagoery;
	
	@Column(length=1)
	private String featureRollOut;
	
	@Column
	private String featureStatus;
	
	private String featureTestExecution;
	
	@Column
	private int recordStatus;
	
	@Column(nullable=false)
	private String featureNumber;
	
	
	@ManyToMany(mappedBy="features")
    private List<Account> accounts;
	
	@ManyToMany(mappedBy="features")
	private List<DataSet> dataSets;
	
	@ManyToMany(mappedBy="features")
	private List<Defect> defectsList; 
	
	@ManyToMany(mappedBy="features")
	private List<Sites> SitesList;
	
	@ManyToMany(mappedBy="features")
	private List<Route> routes ;

	
	@OneToMany(targetEntity=TestExecution.class)
    private List<TestExecution> testExecution;
	
	@Embedded
	RecordCreation recordCreation;

	/**
	 * @return the featureID
	 */
	public int getFeatureID() {
		return featureID;
	}

	/**
	 * @param featureID the featureID to set
	 */
	public void setFeatureID(int featureID) {
		this.featureID = featureID;
	}

	/**
	 * @return the featureName
	 */
	public String getFeatureName() {
		return featureName;
	}

	/**
	 * @param featureName the featureName to set
	 */
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the ba
	 */
	public String getBa() {
		return ba;
	}

	/**
	 * @param ba the ba to set
	 */
	public void setBa(String ba) {
		this.ba = ba;
	}

	/**
	 * @return the accounts
	 */
	public List<Account> getAccounts() {
		return accounts;
	}

	/**
	 * @param accounts the accounts to set
	 */
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	/**
	 * @return the dataSets
	 */
	public List<DataSet> getDataSets() {
		return dataSets;
	}

	/**
	 * @param dataSets the dataSets to set
	 */
	public void setDataSets(List<DataSet> dataSets) {
		this.dataSets = dataSets;
	}

	/**
	 * @return the defectsList
	 */
	public List<Defect> getDefectsList() {
		return defectsList;
	}

	/**
	 * @param defectsList the defectsList to set
	 */
	public void setDefectsList(List<Defect> defectsList) {
		this.defectsList = defectsList;
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
	 * @return the featureNumber
	 */
	public String getFeatureNumber() {
		return featureNumber;
	}

	/**
	 * @param featureNumber the featureNumber to set
	 */
	public void setFeatureNumber(String featureNumber) {
		this.featureNumber = featureNumber;
	}

	/**
	 * @return the featureGrouping
	 */
	public String getFeatureGrouping() {
		return featureGrouping;
	}

	/**
	 * @param featureGrouping the featureGrouping to set
	 */
	public void setFeatureGrouping(String featureGrouping) {
		this.featureGrouping = featureGrouping;
	}

	/**
	 * @return the featureTestScriptName
	 */
	public String getFeatureTestScriptName() {
		return featureTestScriptName;
	}

	/**
	 * @param featureTestScriptName the featureTestScriptName to set
	 */
	public void setFeatureTestScriptName(String featureTestScriptName) {
		this.featureTestScriptName = featureTestScriptName;
	}

	/**
	 * @return the featureTestScriptComments
	 */
	public String getFeatureTestScriptComments() {
		return featureTestScriptComments;
	}

	/**
	 * @param featureTestScriptComments the featureTestScriptComments to set
	 */
	public void setFeatureTestScriptComments(String featureTestScriptComments) {
		this.featureTestScriptComments = featureTestScriptComments;
	}

	/**
	 * @return the featureTestExecutionPhase
	 */
	public String getFeatureTestExecutionPhase() {
		return featureTestExecutionPhase;
	}

	/**
	 * @param featureTestExecutionPhase the featureTestExecutionPhase to set
	 */
	public void setFeatureTestExecutionPhase(String featureTestExecutionPhase) {
		this.featureTestExecutionPhase = featureTestExecutionPhase;
	}

	/**
	 * @return the featureOwner
	 */
	public String getFeatureOwner() {
		return featureOwner;
	}

	/**
	 * @param featureOwner the featureOwner to set
	 */
	public void setFeatureOwner(String featureOwner) {
		this.featureOwner = featureOwner;
	}

	/**
	 * @return the featureDataSetCatagoery
	 */
	public String getFeatureDataSetCatagoery() {
		return featureDataSetCatagoery;
	}

	/**
	 * @param featureDataSetCatagoery the featureDataSetCatagoery to set
	 */
	public void setFeatureDataSetCatagoery(String featureDataSetCatagoery) {
		this.featureDataSetCatagoery = featureDataSetCatagoery;
	}

	/**
	 * @return the featureRollOut
	 */
	public String getFeatureRollOut() {
		return featureRollOut;
	}

	/**
	 * @param featureRollOut the featureRollOut to set
	 */
	public void setFeatureRollOut(String featureRollOut) {
		this.featureRollOut = featureRollOut;
	}

	/**
	 * @return the featureStatus
	 */
	public String getFeatureStatus() {
		return featureStatus;
	}

	/**
	 * @param featureStatus the featureStatus to set
	 */
	public void setFeatureStatus(String featureStatus) {
		this.featureStatus = featureStatus;
	}

	/**
	 * @return the sitesList
	 */
	public List<Sites> getSitesList() {
		return SitesList;
	}

	/**
	 * @param sitesList the sitesList to set
	 */
	public void setSitesList(List<Sites> sitesList) {
		SitesList = sitesList;
	}

	/**
	 * @return the routes
	 */
	public List<Route> getRoutes() {
		return routes;
	}

	/**
	 * @param routes the routes to set
	 */
	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	/**
	 * @return the testExecution
	 */
	public List<TestExecution> getTestExecution() {
		return testExecution;
	}

	/**
	 * @param testExecution the testExecution to set
	 */
	public void setTestExecution(List<TestExecution> testExecution) {
		this.testExecution = testExecution;
	}

	/**
	 * @return the featureTestExecution
	 */
	public String getFeatureTestExecution() {
		return featureTestExecution;
	}

	/**
	 * @param featureTestExecution the featureTestExecution to set
	 */
	public void setFeatureTestExecution(String featureTestExecution) {
		this.featureTestExecution = featureTestExecution;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Feature [featureID=" + featureID + ", featureName=" + featureName + ", owner=" + owner + ", ba=" + ba
				+ ", featureGrouping=" + featureGrouping + ", featureTestScriptName=" + featureTestScriptName
				+ ", featureTestScriptComments=" + featureTestScriptComments + ", featureTestExecutionPhase="
				+ featureTestExecutionPhase + ", featureOwner=" + featureOwner + ", featureDataSetCatagoery="
				+ featureDataSetCatagoery + ", featureRollOut=" + featureRollOut + ", featureStatus=" + featureStatus
				+ ", featureNumber=" + featureNumber + ", accounts=" + accounts + ", dataSets=" + dataSets
				+ ", defectsList=" + defectsList + ", SitesList=" + SitesList + ", routes=" + routes
				+ ", testExecution=" + testExecution + ", recordCreation=" + recordCreation + "]";
	}

}
