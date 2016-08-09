package com.ibm.utils.defmng.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Defects
 *
 */
@Entity
@Table(name="defect")
public class Defect implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Defect() {
		super();
	}
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="defect_id")
	private int Id;
	
	@Column
	private String defectDescription;
	
	@ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(name = "feature_defects",
            joinColumns = @JoinColumn(name = "feature_id"),
            inverseJoinColumns = @JoinColumn(name = "defect_id"))
	private List<Feature> features;
	
	@ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(name = "account_defects",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "defect_id"))
	private List<Account> accountsList;
	
	@ManyToOne
	@JoinColumn(name="testExecutionID")
    private TestExecution testExecution;
	
	@Embedded
	RecordCreation recordCreation;
	
	
	@Transient
	private String featureID;
	
	@Transient
	private String accountId;
	
	@Column(nullable=false)
    private String hpqcDefectID;	
	@Column
	private String assignedTo;	
	@Column
	private String closingDate;	
	@Column(length=4000)
	private String summary;	
	@Column
	private String status;	
	@Column
	private String project;	
	@Column
	private String priority;	
	@Column
	private String severity;	
	@Column
	private String detectedinCycle;	
	@Column
	private String detectedBy;	
	@Column
	private String functionality;	
	@Column
	private String rootCause;	
	@Column
	private String detectedonDate;	
	@Column(length=10000)
	private String statusChangeComments;	
	@Column(length=10000)
	private String description	;
	@Column
	private String modified;	
	@Column
	private String targetCycle	;
	@Column
	private String route;	
	@Column
	private String actualFixTime;	
	@Column
	private String closedinVersion;	
	@Column
	private String detectedinRelease;	
	@Column
	private String detectedinVersion	;
	@Column
	private String estimatedFixTime;	
	@Column
	private String plannedClosingVersion;	
	@Column
	private String reproducible;	
	@Column
	private String reviewStatus;	
	@Column
	private String subject;	
	@Column
	private String targetRelease;	
	@Column
	private String testDay;	
	@Column
	private String transactionWeek;
	
	@Transient 
	// have to be a column will be having style class mentioned 
	// code to be written for for changing severity 
	private String defectSeveraityColor;

	/**
	 * @return the id
	 */
	public int getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		Id = id;
	}
	/**
	 * @return the defectDescription
	 */
	public String getDefectDescription() {
		return defectDescription;
	}
	/**
	 * @param defectDescription the defectDescription to set
	 */
	public void setDefectDescription(String defectDescription) {
		this.defectDescription = defectDescription;
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
	 * @return the hpqcDefectID
	 */
	public String getHpqcDefectID() {
		return hpqcDefectID;
	}
	/**
	 * @param hpqcDefectID the hpqcDefectID to set
	 */
	public void setHpqcDefectID(String hpqcDefectID) {
		this.hpqcDefectID = hpqcDefectID;
	}
	/**
	 * @return the assignedTo
	 */
	public String getAssignedTo() {
		return assignedTo;
	}
	/**
	 * @param assignedTo the assignedTo to set
	 */
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	/**
	 * @return the closingDate
	 */
	public String getClosingDate() {
		return closingDate;
	}
	/**
	 * @param closingDate the closingDate to set
	 */
	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the project
	 */
	public String getProject() {
		return project;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(String project) {
		this.project = project;
	}
	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}
	/**
	 * @return the severity
	 */
	public String getSeverity() {
		return severity;
	}
	/**
	 * @param severity the severity to set
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	/**
	 * @return the detectedinCycle
	 */
	public String getDetectedinCycle() {
		return detectedinCycle;
	}
	/**
	 * @param detectedinCycle the detectedinCycle to set
	 */
	public void setDetectedinCycle(String detectedinCycle) {
		this.detectedinCycle = detectedinCycle;
	}
	/**
	 * @return the detectedBy
	 */
	public String getDetectedBy() {
		return detectedBy;
	}
	/**
	 * @param detectedBy the detectedBy to set
	 */
	public void setDetectedBy(String detectedBy) {
		this.detectedBy = detectedBy;
	}
	/**
	 * @return the functionality
	 */
	public String getFunctionality() {
		return functionality;
	}
	/**
	 * @param functionality the functionality to set
	 */
	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}
	/**
	 * @return the rootCause
	 */
	public String getRootCause() {
		return rootCause;
	}
	/**
	 * @param rootCause the rootCause to set
	 */
	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}
	/**
	 * @return the detectedonDate
	 */
	public String getDetectedonDate() {
		return detectedonDate;
	}
	/**
	 * @param detectedonDate the detectedonDate to set
	 */
	public void setDetectedonDate(String detectedonDate) {
		this.detectedonDate = detectedonDate;
	}
	/**
	 * @return the statusChangeComments
	 */
	public String getStatusChangeComments() {
		return statusChangeComments;
	}
	/**
	 * @param statusChangeComments the statusChangeComments to set
	 */
	public void setStatusChangeComments(String statusChangeComments) {
		this.statusChangeComments = statusChangeComments;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the modified
	 */
	public String getModified() {
		return modified;
	}
	/**
	 * @param modified the modified to set
	 */
	public void setModified(String modified) {
		this.modified = modified;
	}
	/**
	 * @return the targetCycle
	 */
	public String getTargetCycle() {
		return targetCycle;
	}
	/**
	 * @param targetCycle the targetCycle to set
	 */
	public void setTargetCycle(String targetCycle) {
		this.targetCycle = targetCycle;
	}
	/**
	 * @return the route
	 */
	public String getRoute() {
		return route;
	}
	/**
	 * @param route the route to set
	 */
	public void setRoute(String route) {
		this.route = route;
	}
	/**
	 * @return the actualFixTime
	 */
	public String getActualFixTime() {
		return actualFixTime;
	}
	/**
	 * @param actualFixTime the actualFixTime to set
	 */
	public void setActualFixTime(String actualFixTime) {
		this.actualFixTime = actualFixTime;
	}
	/**
	 * @return the closedinVersion
	 */
	public String getClosedinVersion() {
		return closedinVersion;
	}
	/**
	 * @param closedinVersion the closedinVersion to set
	 */
	public void setClosedinVersion(String closedinVersion) {
		this.closedinVersion = closedinVersion;
	}
	/**
	 * @return the detectedinRelease
	 */
	public String getDetectedinRelease() {
		return detectedinRelease;
	}
	/**
	 * @param detectedinRelease the detectedinRelease to set
	 */
	public void setDetectedinRelease(String detectedinRelease) {
		this.detectedinRelease = detectedinRelease;
	}
	/**
	 * @return the detectedinVersion
	 */
	public String getDetectedinVersion() {
		return detectedinVersion;
	}
	/**
	 * @param detectedinVersion the detectedinVersion to set
	 */
	public void setDetectedinVersion(String detectedinVersion) {
		this.detectedinVersion = detectedinVersion;
	}
	/**
	 * @return the estimatedFixTime
	 */
	public String getEstimatedFixTime() {
		return estimatedFixTime;
	}
	/**
	 * @param estimatedFixTime the estimatedFixTime to set
	 */
	public void setEstimatedFixTime(String estimatedFixTime) {
		this.estimatedFixTime = estimatedFixTime;
	}
	/**
	 * @return the plannedClosingVersion
	 */
	public String getPlannedClosingVersion() {
		return plannedClosingVersion;
	}
	/**
	 * @param plannedClosingVersion the plannedClosingVersion to set
	 */
	public void setPlannedClosingVersion(String plannedClosingVersion) {
		this.plannedClosingVersion = plannedClosingVersion;
	}
	/**
	 * @return the reproducible
	 */
	public String getReproducible() {
		return reproducible;
	}
	/**
	 * @param reproducible the reproducible to set
	 */
	public void setReproducible(String reproducible) {
		this.reproducible = reproducible;
	}
	/**
	 * @return the reviewStatus
	 */
	public String getReviewStatus() {
		return reviewStatus;
	}
	/**
	 * @param reviewStatus the reviewStatus to set
	 */
	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the targetRelease
	 */
	public String getTargetRelease() {
		return targetRelease;
	}
	/**
	 * @param targetRelease the targetRelease to set
	 */
	public void setTargetRelease(String targetRelease) {
		this.targetRelease = targetRelease;
	}
	/**
	 * @return the testDay
	 */
	public String getTestDay() {
		return testDay;
	}
	/**
	 * @param testDay the testDay to set
	 */
	public void setTestDay(String testDay) {
		this.testDay = testDay;
	}
	/**
	 * @return the transactionWeek
	 */
	public String getTransactionWeek() {
		return transactionWeek;
	}
	/**
	 * @param transactionWeek the transactionWeek to set
	 */
	public void setTransactionWeek(String transactionWeek) {
		this.transactionWeek = transactionWeek;
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
	 * @return the accountsList
	 */
	public List<Account> getAccountsList() {
		return accountsList;
	}
	/**
	 * @param accountsList the accountsList to set
	 */
	public void setAccountsList(List<Account> accountsList) {
		this.accountsList = accountsList;
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
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Defect [Id=" + Id + ", defectDescription=" + defectDescription + ", features=" + features + ", recordCreation=" + recordCreation
				+ ", hpqcDefectID=" + hpqcDefectID + ", assignedTo=" + assignedTo + ", closingDate=" + closingDate + ", summary=" + summary + ", status="
				+ status + ", project=" + project + ", priority=" + priority + ", severity=" + severity + ", detectedinCycle=" + detectedinCycle
				+ ", detectedBy=" + detectedBy + ", functionality=" + functionality + ", rootCause=" + rootCause + ", detectedonDate=" + detectedonDate
				+ ", statusChangeComments=" + statusChangeComments + ", description=" + description + ", modified=" + modified + ", targetCycle=" + targetCycle
				+ ", route=" + route + ", actualFixTime=" + actualFixTime + ", closedinVersion=" + closedinVersion + ", detectedinRelease=" + detectedinRelease
				+ ", detectedinVersion=" + detectedinVersion + ", estimatedFixTime=" + estimatedFixTime + ", plannedClosingVersion=" + plannedClosingVersion
				+ ", reproducible=" + reproducible + ", reviewStatus=" + reviewStatus + ", subject=" + subject + ", targetRelease=" + targetRelease
				+ ", testDay=" + testDay + ", transactionWeek=" + transactionWeek + "]";
	}

    
}
