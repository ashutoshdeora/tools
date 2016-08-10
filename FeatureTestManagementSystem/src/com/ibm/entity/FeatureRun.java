package com.ibm.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the FEATURERUN database table.
 * 
 */
@Entity
@Table(name="FEATURERUN")
@NamedQuery(name="FeatureRun.findAll", query="SELECT f FROM FeatureRun f")
public class FeatureRun implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision=22)
	private long featurerunid;

	@Column(nullable=false, length=20)
	private String status;

	//bi-directional many-to-one association to DatasetRunDefect
	@OneToMany(mappedBy="featurerun")
	private List<DatasetRunDefect> datasetrundefects;

	//bi-directional many-to-one association to DatasetRun
	@ManyToOne
	@JoinColumn(name="DATASETRUNID", nullable=false)
	private DatasetRun datasetrun;

	//bi-directional many-to-one association to FeatureMaster
	@ManyToOne
	@JoinColumn(name="FEATUREMASTERID", nullable=false)
	private FeatureMaster featuremaster;

	public FeatureRun() {
	}

	public long getFeaturerunid() {
		return this.featurerunid;
	}

	public void setFeaturerunid(long featurerunid) {
		this.featurerunid = featurerunid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<DatasetRunDefect> getDatasetrundefects() {
		return this.datasetrundefects;
	}

	public void setDatasetrundefects(List<DatasetRunDefect> datasetrundefects) {
		this.datasetrundefects = datasetrundefects;
	}

	public DatasetRunDefect addDatasetrundefect(DatasetRunDefect datasetrundefect) {
		getDatasetrundefects().add(datasetrundefect);
		datasetrundefect.setFeaturerun(this);

		return datasetrundefect;
	}

	public DatasetRunDefect removeDatasetrundefect(DatasetRunDefect datasetrundefect) {
		getDatasetrundefects().remove(datasetrundefect);
		datasetrundefect.setFeaturerun(null);

		return datasetrundefect;
	}

	public DatasetRun getDatasetrun() {
		return this.datasetrun;
	}

	public void setDatasetrun(DatasetRun datasetrun) {
		this.datasetrun = datasetrun;
	}

	public FeatureMaster getFeaturemaster() {
		return this.featuremaster;
	}

	public void setFeaturemaster(FeatureMaster featuremaster) {
		this.featuremaster = featuremaster;
	}

}