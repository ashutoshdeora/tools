package com.ibm.model;

import java.io.Serializable;
import java.util.List;

public class FeatureRunModelBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String featureSetId;
	private List<DefectBean> defectBeansList;
	private String featureRunPhase;
	private String featureRunResult;
	private String featureTestPhase;
	private boolean readyForinsert;
	/**
	 * @return the featureSetId
	 */
	public String getFeatureSetId() {
		return featureSetId;
	}
	/**
	 * @param featureSetId the featureSetId to set
	 */
	public void setFeatureSetId(String featureSetId) {
		this.featureSetId = featureSetId;
	}
	/**
	 * @return the defectBeansList
	 */
	public List<DefectBean> getDefectBeansList() {
		return defectBeansList;
	}
	/**
	 * @param defectBeansList the defectBeansList to set
	 */
	public void setDefectBeansList(List<DefectBean> defectBeansList) {
		this.defectBeansList = defectBeansList;
	}
	/**
	 * @return the featureRunPhase
	 */
	public String getFeatureRunPhase() {
		return featureRunPhase;
	}
	/**
	 * @param featureRunPhase the featureRunPhase to set
	 */
	public void setFeatureRunPhase(String featureRunPhase) {
		this.featureRunPhase = featureRunPhase;
	}
	/**
	 * @return the featureRunResult
	 */
	public String getFeatureRunResult() {
		return featureRunResult;
	}
	/**
	 * @param featureRunResult the featureRunResult to set
	 */
	public void setFeatureRunResult(String featureRunResult) {
		this.featureRunResult = featureRunResult;
	}
	/**
	 * @return the featureTestPhase
	 */
	public String getFeatureTestPhase() {
		return featureTestPhase;
	}
	/**
	 * @param featureTestPhase the featureTestPhase to set
	 */
	public void setFeatureTestPhase(String featureTestPhase) {
		this.featureTestPhase = featureTestPhase;
	}
	/**
	 * @return the readyForinsert
	 */
	public boolean isReadyForinsert() {
		return readyForinsert;
	}
	/**
	 * @param readyForinsert the readyForinsert to set
	 */
	public void setReadyForinsert(boolean readyForinsert) {
		this.readyForinsert = readyForinsert;
	}
	

}
