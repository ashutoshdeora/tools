package com.ibm.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the DATASETRUN database table.
 * 
 */
@Entity
@Table(name="DATASETRUN")
@NamedQuery(name="DatasetRun.findAll", query="SELECT d FROM DatasetRun d")
public class DatasetRun implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, precision=22)
	private long datasetrunid;

	@Column(nullable=false, precision=22)
	private BigDecimal parentdatasetrunid;

	@Column(nullable=false, length=2)
	private String readyforrun;

	@Column(nullable=false, length=40)
	private String runby;

	@Column(nullable=false, length=20)
	private String runphase;

	@Column(nullable=false, length=20)
	private String runstatus;

	@Column(nullable=false)
	private Timestamp runtime;

	//bi-directional many-to-one association to AccountRun
	@OneToMany(mappedBy="datasetrun")
	private List<AccountRun> accountruns;

	//bi-directional many-to-one association to DatasetMaster
	@ManyToOne
	@JoinColumn(name="DATASETID", nullable=false)
	private DatasetMaster datasetmaster;

	//bi-directional many-to-one association to DatasetRunDefect
	@OneToMany(mappedBy="datasetrun")
	private List<DatasetRunDefect> datasetrundefects;

	//bi-directional many-to-one association to FeatureRun
	@OneToMany(mappedBy="datasetrun")
	private List<FeatureRun> featureruns;

	public DatasetRun() {
	}

	public long getDatasetrunid() {
		return this.datasetrunid;
	}

	public void setDatasetrunid(long datasetrunid) {
		this.datasetrunid = datasetrunid;
	}

	public BigDecimal getParentdatasetrunid() {
		return this.parentdatasetrunid;
	}

	public void setParentdatasetrunid(BigDecimal parentdatasetrunid) {
		this.parentdatasetrunid = parentdatasetrunid;
	}

	public String getReadyforrun() {
		return this.readyforrun;
	}

	public void setReadyforrun(String readyforrun) {
		this.readyforrun = readyforrun;
	}

	public String getRunby() {
		return this.runby;
	}

	public void setRunby(String runby) {
		this.runby = runby;
	}

	public String getRunphase() {
		return this.runphase;
	}

	public void setRunphase(String runphase) {
		this.runphase = runphase;
	}

	public String getRunstatus() {
		return this.runstatus;
	}

	public void setRunstatus(String runstatus) {
		this.runstatus = runstatus;
	}

	public Timestamp getRuntime() {
		return this.runtime;
	}

	public void setRuntime(Timestamp runtime) {
		this.runtime = runtime;
	}

	public List<AccountRun> getAccountruns() {
		return this.accountruns;
	}

	public void setAccountruns(List<AccountRun> accountruns) {
		this.accountruns = accountruns;
	}

	public AccountRun addAccountrun(AccountRun accountrun) {
		getAccountruns().add(accountrun);
		accountrun.setDatasetrun(this);

		return accountrun;
	}

	public AccountRun removeAccountrun(AccountRun accountrun) {
		getAccountruns().remove(accountrun);
		accountrun.setDatasetrun(null);

		return accountrun;
	}

	public DatasetMaster getDatasetmaster() {
		return this.datasetmaster;
	}

	public void setDatasetmaster(DatasetMaster datasetmaster) {
		this.datasetmaster = datasetmaster;
	}

	public List<DatasetRunDefect> getDatasetrundefects() {
		return this.datasetrundefects;
	}

	public void setDatasetrundefects(List<DatasetRunDefect> datasetrundefects) {
		this.datasetrundefects = datasetrundefects;
	}

	public DatasetRunDefect addDatasetrundefect(DatasetRunDefect datasetrundefect) {
		getDatasetrundefects().add(datasetrundefect);
		datasetrundefect.setDatasetrun(this);

		return datasetrundefect;
	}

	public DatasetRunDefect removeDatasetrundefect(DatasetRunDefect datasetrundefect) {
		getDatasetrundefects().remove(datasetrundefect);
		datasetrundefect.setDatasetrun(null);

		return datasetrundefect;
	}

	public List<FeatureRun> getFeatureruns() {
		return this.featureruns;
	}

	public void setFeatureruns(List<FeatureRun> featureruns) {
		this.featureruns = featureruns;
	}

	public FeatureRun addFeaturerun(FeatureRun featurerun) {
		getFeatureruns().add(featurerun);
		featurerun.setDatasetrun(this);

		return featurerun;
	}

	public FeatureRun removeFeaturerun(FeatureRun featurerun) {
		getFeatureruns().remove(featurerun);
		featurerun.setDatasetrun(null);

		return featurerun;
	}

}