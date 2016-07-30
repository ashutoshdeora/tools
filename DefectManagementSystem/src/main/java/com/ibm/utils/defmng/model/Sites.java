package com.ibm.utils.defmng.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Sites
 *
 */
@Entity
@Table(name="sites")
public class Sites implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Sites() {
		super();
	}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="sites_id")
	private int id;
	@Column
	private String siteName;
	@Embedded
	RecordCreation recordCreation;
	
	@Transient
	private String featureID;
	
	@ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(name = "feature_sites", 
            joinColumns = @JoinColumn(name = "feature_id"),
            inverseJoinColumns = @JoinColumn(name = "sites_id"))
	private List<Feature> features;
	
	@OneToOne(mappedBy="siteRun")
	private TestExecution testExecution;

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
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}
	/**
	 * @param siteName the siteName to set
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Sites [id=" + id + ", siteName=" + siteName + ", recordCreation=" + recordCreation + ", featureID=" + featureID + ", features=" + features
				+ "]";
	}
	
}
