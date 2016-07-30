package com.ibm.utils.defmng.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity implementation class for Entity: Account
 *
 */
@Entity
@Table(name="account")
public class Account implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Account() {
		super();
	}

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="account_id")
	private int id;
	@Column(nullable=false)
	private String accountName;
	@Column(nullable=false)
	private String accountNumber;
	
	@ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(name = "feature_accounts", 
            joinColumns = @JoinColumn(name = "feature_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id"))
	private List<Feature> features;
	
	@OneToOne(mappedBy="accountRun")
	private TestExecution testExecution;
	
	@ManyToMany(mappedBy="accountsList")
	private List<Defect> defectsList; 
	
	@Embedded
	RecordCreation recordCreation;
	
	@Transient
	private String featureID;


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}
	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	/**
	 * @return the features
	 */
	public List<Feature> getFeatures() {
		return features;
	}
	/**
	 * @param features the features to set
	 */
	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
	/**
	 * @return the testExecution
	 */
	public TestExecution getTestExecution() {
		return testExecution;
	}
	/**
	 * @param testExecution the testExecution to set
	 */
	public void setTestExecution(TestExecution testExecution) {
		this.testExecution = testExecution;
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
	 * @return the featureID
	 */
	public String getFeatureID() {
		return featureID;
	}
	/**
	 * @param featureID the featureID to set
	 */
	public void setFeatureID(String featureID) {
		this.featureID = featureID;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Account [id=" + id + ", accountName=" + accountName + ", accountNumber=" + accountNumber + ", features=" + features + ", testExecution="
				+ testExecution + ", recordCreation=" + recordCreation + "]";
	}
	
   
}
