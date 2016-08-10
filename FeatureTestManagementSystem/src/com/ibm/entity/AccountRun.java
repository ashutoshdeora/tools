package com.ibm.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ACCOUNTRUN database table.
 * 
 */
@Entity
@Table(name="ACCOUNTRUN")
@NamedQuery(name="AccountRun.findAll", query="SELECT a FROM AccountRun a")
public class AccountRun implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision=22)
	private long accountrunid;

	//bi-directional many-to-one association to AccountMaster
	@ManyToOne
	@JoinColumn(name="ACCOUNTMASTERID", nullable=false)
	private AccountMaster accountmaster;

	//bi-directional many-to-one association to DatasetRun
	@ManyToOne
	@JoinColumn(name="DATASETRUNID", nullable=false)
	private DatasetRun datasetrun;

	public AccountRun() {
	}

	public long getAccountrunid() {
		return this.accountrunid;
	}

	public void setAccountrunid(long accountrunid) {
		this.accountrunid = accountrunid;
	}

	public AccountMaster getAccountmaster() {
		return this.accountmaster;
	}

	public void setAccountmaster(AccountMaster accountmaster) {
		this.accountmaster = accountmaster;
	}

	public DatasetRun getDatasetrun() {
		return this.datasetrun;
	}

	public void setDatasetrun(DatasetRun datasetrun) {
		this.datasetrun = datasetrun;
	}

}