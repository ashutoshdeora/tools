package com.ibm.utils.defmng.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: DataSet
 *
 */
@Entity
@Table(name="dataset")
public class DataSet implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public DataSet() {
		super();
	}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dataset_id")
	private int Id;
	@Column(nullable=false)
	private String dataSetName;
	@Column(nullable=false)
	private String dataSetLocation;
	
	@Transient
	private String featureID;
	
	
	@ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(name = "feature_datasets",
            joinColumns = @JoinColumn(name = "feature_id"),
            inverseJoinColumns = @JoinColumn(name = "dataset_id"))
	private List<Feature> features;
	@Embedded
	RecordCreation recordCreation;
	
	@OneToOne(mappedBy="dataSetRun")
	private TestExecution testExecution;

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
	 * @return the dataSetName
	 */
	public String getDataSetName() {
		return dataSetName;
	}
	/**
	 * @param dataSetName the dataSetName to set
	 */
	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}
	/**
	 * @return the dataSetLocation
	 */
	public String getDataSetLocation() {
		return dataSetLocation;
	}
	/**
	 * @param dataSetLocation the dataSetLocation to set
	 */
	public void setDataSetLocation(String dataSetLocation) {
		this.dataSetLocation = dataSetLocation;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DataSet [Id=" + Id + ", dataSetName=" + dataSetName + ", dataSetLocation=" + dataSetLocation + ", features=" + features + ", recordCreation="
				+ recordCreation + "]";
	}
   
}
