package com.ibm.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


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

	@Column(nullable=false, precision=22)
	private BigDecimal datasetrunid;

	//bi-directional many-to-one association to AccountMaster
	@ManyToOne
	@JoinColumn(name="ACCOUNTMASTERID", nullable=false)
	private AccountMaster accountmaster;

	public AccountRun() {
	}

	public long getAccountrunid() {
		return this.accountrunid;
	}

	public void setAccountrunid(long accountrunid) {
		this.accountrunid = accountrunid;
	}

	public BigDecimal getDatasetrunid() {
		return this.datasetrunid;
	}

	public void setDatasetrunid(BigDecimal datasetrunid) {
		this.datasetrunid = datasetrunid;
	}

	public AccountMaster getAccountmaster() {
		return this.accountmaster;
	}

	public void setAccountmaster(AccountMaster accountmaster) {
		this.accountmaster = accountmaster;
	}

}