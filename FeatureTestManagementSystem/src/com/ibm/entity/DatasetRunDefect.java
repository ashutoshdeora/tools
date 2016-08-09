package com.ibm.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the DATASETRUNDEFECT database table.
 * 
 */
@Entity
@Table(name="DATASETRUNDEFECT")
@NamedQuery(name="DatasetRunDefect.findAll", query="SELECT d FROM DatasetRunDefect d")
public class DatasetRunDefect implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DatasetRunDefectPK id;

	@Column(nullable=false, length=20)
	private String defectsevrity;

	@Column(nullable=false, length=20)
	private String defectstatus;

	@Column(nullable=false, precision=22)
	private BigDecimal hpqcdefectid;

	//bi-directional many-to-one association to DatasetRun
	@ManyToOne
	@JoinColumn(name="DATASETRUNID", nullable=false, insertable=false, updatable=false)
	private DatasetRun datasetrun;

	//bi-directional many-to-one association to FeatureRun
	@ManyToOne
	@JoinColumn(name="FEATURERUNID")
	private FeatureRun featurerun;

	public DatasetRunDefect() {
	}

	public DatasetRunDefectPK getId() {
		return this.id;
	}

	public void setId(DatasetRunDefectPK id) {
		this.id = id;
	}

	public String getDefectsevrity() {
		return this.defectsevrity;
	}

	public void setDefectsevrity(String defectsevrity) {
		this.defectsevrity = defectsevrity;
	}

	public String getDefectstatus() {
		return this.defectstatus;
	}

	public void setDefectstatus(String defectstatus) {
		this.defectstatus = defectstatus;
	}

	public BigDecimal getHpqcdefectid() {
		return this.hpqcdefectid;
	}

	public void setHpqcdefectid(BigDecimal hpqcdefectid) {
		this.hpqcdefectid = hpqcdefectid;
	}

	public DatasetRun getDatasetrun() {
		return this.datasetrun;
	}

	public void setDatasetrun(DatasetRun datasetrun) {
		this.datasetrun = datasetrun;
	}

	public FeatureRun getFeaturerun() {
		return this.featurerun;
	}

	public void setFeaturerun(FeatureRun featurerun) {
		this.featurerun = featurerun;
	}

}