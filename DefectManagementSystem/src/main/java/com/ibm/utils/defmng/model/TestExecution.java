package com.ibm.utils.defmng.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: TestExecution
 *
 */
@Entity
@Table(name = "testExecution")
public class TestExecution implements Serializable {

	private static final long serialVersionUID = 1L;

	public TestExecution() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int testExecutionID;
	@Column(length = 4000)
	private String testScenario;

	@Column(length = 4000)
	private String testScenarioDescription;

	@Column(length = 4000) // Comments
	private String testScriptComments;
	@Column(length = 4000)
	private String clarifications;
	@Column // test script COl - C
	private String testScriptName;
	@Column // Status - K
	private String testStatus;// pass/fail/not executable.
	@Column // Col E
	private String testExecutionPhase; // Feature /1 E2E / 2E2E
	@Column
	private String testExecutionStatus;

	@Column(length = 2, nullable = false) // "ac/dc"
	private String activeData;

	@Transient
	private String featureID;

	@Transient
	private String dataset;

	@Transient
	private String accountName;

	@Transient
	private String siteName;

	@Transient
	private String RouteName;

	@Column
	private String testGroup;

	@Column
	private String testOwner;

	@Column
	private String rolloutStatus;

	@Column
	private int totalTestScripts;

	@Column
	private String featureTestRun;

	@Column
	private String accountTestRun;

	@ManyToOne
	@JoinColumn(name = "feature_id")
	private Feature featureRun;

	@OneToOne
	@JoinColumn(name = "account_id")
	private Account accountRun;

	@OneToOne
	@JoinColumn(name = "sites_id")
	private Sites siteRun;

	@OneToOne
	@JoinColumn(name = "route_id")
	private Route routeRun;

	@OneToOne
	@JoinColumn(name = "dataset_id")
	private DataSet dataSetRun;

	@OneToMany(targetEntity = Defect.class, fetch = FetchType.EAGER)
	private List<Defect> defectslist;

	@Embedded
	RecordCreation recordCreation;

	

	/**
	 * @return the testExecutionID
	 */
	public int getTestExecutionID() {
		return testExecutionID;
	}

	/**
	 * @param testExecutionID
	 *            the testExecutionID to set
	 */
	public void setTestExecutionID(int testExecutionID) {
		this.testExecutionID = testExecutionID;
	}

	/**
	 * @return the testScenario
	 */
	public String getTestScenario() {
		return testScenario;
	}

	/**
	 * @param testScenario
	 *            the testScenario to set
	 */
	public void setTestScenario(String testScenario) {
		this.testScenario = testScenario;
	}

	/**
	 * @return the testScenarioDescription
	 */
	public String getTestScenarioDescription() {
		return testScenarioDescription;
	}

	/**
	 * @param testScenarioDescription
	 *            the testScenarioDescription to set
	 */
	public void setTestScenarioDescription(String testScenarioDescription) {
		this.testScenarioDescription = testScenarioDescription;
	}

	/**
	 * @return the testScriptComments
	 */
	public String getTestScriptComments() {
		return testScriptComments;
	}

	/**
	 * @param testScriptComments
	 *            the testScriptComments to set
	 */
	public void setTestScriptComments(String testScriptComments) {
		this.testScriptComments = testScriptComments;
	}

	/**
	 * @return the clarifications
	 */
	public String getClarifications() {
		return clarifications;
	}

	/**
	 * @param clarifications
	 *            the clarifications to set
	 */
	public void setClarifications(String clarifications) {
		this.clarifications = clarifications;
	}

	/**
	 * @return the testScriptName
	 */
	public String getTestScriptName() {
		return testScriptName;
	}

	/**
	 * @param testScriptName
	 *            the testScriptName to set
	 */
	public void setTestScriptName(String testScriptName) {
		this.testScriptName = testScriptName;
	}

	/**
	 * @return the testStatus
	 */
	public String getTestStatus() {
		return testStatus;
	}

	/**
	 * @param testStatus
	 *            the testStatus to set
	 */
	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}

	/**
	 * @return the testExecutionPhase
	 */
	public String getTestExecutionPhase() {
		return testExecutionPhase;
	}

	/**
	 * @param testExecutionPhase
	 *            the testExecutionPhase to set
	 */
	public void setTestExecutionPhase(String testExecutionPhase) {
		this.testExecutionPhase = testExecutionPhase;
	}

	/**
	 * @return the featureTestRun
	 */
	public String getFeatureTestRun() {
		return featureTestRun;
	}

	/**
	 * @param featureTestRun
	 *            the featureTestRun to set
	 */
	public void setFeatureTestRun(String featureTestRun) {
		this.featureTestRun = featureTestRun;
	}

	/**
	 * @return the accountTestRun
	 */
	public String getAccountTestRun() {
		return accountTestRun;
	}

	/**
	 * @param accountTestRun
	 *            the accountTestRun to set
	 */
	public void setAccountTestRun(String accountTestRun) {
		this.accountTestRun = accountTestRun;
	}

	/**
	 * @return the featureRun
	 */
	public Feature getFeatureRun() {
		return featureRun;
	}

	/**
	 * @param featureRun
	 *            the featureRun to set
	 */
	public void setFeatureRun(Feature featureRun) {
		this.featureRun = featureRun;
	}

	/**
	 * @return the accountRun
	 */
	public Account getAccountRun() {
		return accountRun;
	}

	/**
	 * @param accountRun
	 *            the accountRun to set
	 */
	public void setAccountRun(Account accountRun) {
		this.accountRun = accountRun;
	}

	/**
	 * @return the recordCreation
	 */
	public RecordCreation getRecordCreation() {
		return recordCreation;
	}

	/**
	 * @param recordCreation
	 *            the recordCreation to set
	 */
	public void setRecordCreation(RecordCreation recordCreation) {
		this.recordCreation = recordCreation;
	}

	/**
	 * @return the testGroup
	 */
	public String getTestGroup() {
		return testGroup;
	}

	/**
	 * @param testGroup
	 *            the testGroup to set
	 */
	public void setTestGroup(String testGroup) {
		this.testGroup = testGroup;
	}

	/**
	 * @return the testOwner
	 */
	public String getTestOwner() {
		return testOwner;
	}

	/**
	 * @param testOwner
	 *            the testOwner to set
	 */
	public void setTestOwner(String testOwner) {
		this.testOwner = testOwner;
	}

	/**
	 * @return the rolloutStatus
	 */
	public String getRolloutStatus() {
		return rolloutStatus;
	}

	/**
	 * @param rolloutStatus
	 *            the rolloutStatus to set
	 */
	public void setRolloutStatus(String rolloutStatus) {
		this.rolloutStatus = rolloutStatus;
	}

	/**
	 * @return the totalTestScripts
	 */
	public int getTotalTestScripts() {
		return totalTestScripts;
	}

	/**
	 * @param totalTestScripts
	 *            the totalTestScripts to set
	 */
	public void setTotalTestScripts(int totalTestScripts) {
		this.totalTestScripts = totalTestScripts;
	}

	/**
	 * @return the testExecutionStatus
	 */
	public String getTestExecutionStatus() {
		return testExecutionStatus;
	}

	/**
	 * @param testExecutionStatus
	 *            the testExecutionStatus to set
	 */
	public void setTestExecutionStatus(String testExecutionStatus) {
		this.testExecutionStatus = testExecutionStatus;
	}

	/**
	 * @return the featureID
	 */
	public String getFeatureID() {
		return featureID;
	}

	/**
	 * @param featureID
	 *            the featureID to set
	 */
	public void setFeatureID(String featureID) {
		this.featureID = featureID;
	}

	/**
	 * @return the dataset
	 */
	public String getDataset() {
		return dataset;
	}

	/**
	 * @param dataset
	 *            the dataset to set
	 */
	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}

	/**
	 * @param siteName
	 *            the siteName to set
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	/**
	 * @return the routeName
	 */
	public String getRouteName() {
		return RouteName;
	}

	/**
	 * @param routeName
	 *            the routeName to set
	 */
	public void setRouteName(String routeName) {
		RouteName = routeName;
	}

	/**
	 * @return the siteRun
	 */
	public Sites getSiteRun() {
		return siteRun;
	}

	/**
	 * @param siteRun
	 *            the siteRun to set
	 */
	public void setSiteRun(Sites siteRun) {
		this.siteRun = siteRun;
	}

	/**
	 * @return the routeRun
	 */
	public Route getRouteRun() {
		return routeRun;
	}

	/**
	 * @param routeRun
	 *            the routeRun to set
	 */
	public void setRouteRun(Route routeRun) {
		this.routeRun = routeRun;
	}

	/**
	 * @return the dataSetRun
	 */
	public DataSet getDataSetRun() {
		return dataSetRun;
	}

	/**
	 * @param dataSetRun
	 *            the dataSetRun to set
	 */
	public void setDataSetRun(DataSet dataSetRun) {
		this.dataSetRun = dataSetRun;
	}

	/**
	 * @return the defectslist
	 */
	public List<Defect> getDefectslist() {
		return defectslist;
	}

	/**
	 * @param defectslist
	 *            the defectslist to set
	 */
	public void setDefectslist(List<Defect> defectslist) {
		this.defectslist = defectslist;
	}

	/**
	 * @return the activeData
	 */
	public String getActiveData() {
		return activeData;
	}

	/**
	 * @param activeData
	 *            the activeData to set
	 */
	public void setActiveData(String activeData) {
		this.activeData = activeData;
	}

	
}
