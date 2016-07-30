package com.ibm.utils.defmng.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Route
 *
 */
@Entity
@Table(name="route")
public class Route implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Route() {
		super();
	}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="route_id")
	private int id;
	@Column
	private String routeName;
	
	@ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(name = "feature_routes", 
            joinColumns = @JoinColumn(name = "feature_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id"))
	private List<Feature> features;
	
	@Embedded
	RecordCreation recordCreation;
	
	@OneToOne(mappedBy="routeRun")
	private TestExecution testExecution;
	
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
	 * @return the routeName
	 */
	public String getRouteName() {
		return routeName;
	}
	/**
	 * @param routeName the routeName to set
	 */
	public void setRouteName(String routeName) {
		this.routeName = routeName;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Route [id=" + id + ", routeName=" + routeName + ", recordCreation=" + recordCreation + "]";
	}
   
}
