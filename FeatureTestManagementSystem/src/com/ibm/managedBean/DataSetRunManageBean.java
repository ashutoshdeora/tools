package com.ibm.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.ibm.entity.AccountMaster;

import com.ibm.entity.DatasetMaster;
import com.ibm.entity.DatasetRun;
import com.ibm.entity.DatasetRunDefect;

import com.ibm.entity.FeatureMaster;
import com.ibm.entity.MasterData;

@ManagedBean
@ViewScoped
public class DataSetRunManageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataSetRunManageBean() {
		// TODO Auto-generated constructor stub
	}

	private static final String PERSISTENCE_UNIT_NAME = "tmorcl";

	private List<DatasetMaster> datasetmastersList;
	private List<AccountMaster> accountmastersList;
	private List<FeatureMaster> featuremastersList;
	private List<DatasetRunDefect> datasetrundefectsList;
	private List<MasterData> masterDatasDropDown;
	private List<DatasetRun> datasetRunsList;
	
	private FeatureMaster selecetdFeature;
	private String selectedfeature;
	private String selectedAccount;
	private String selectedDataSet;
	private String testScriptComments;
	private String selectedDataSetphase;

	private boolean tablePermission;
	private boolean panelPermission;

	@PostConstruct
	private void init() {
		try{
		datasetmastersList = new ArrayList<DatasetMaster>();
		datasetmastersList = populateDataSet();
		masterDatasDropDown = new ArrayList<MasterData>(); 
		masterDatasDropDown = populateMasterData();
		}catch(Exception exception)
		{
			exception.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Application/Data Error",
					exception.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
	}

	@SuppressWarnings("unchecked")
	private List<DatasetMaster> populateDataSet() {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();

		List<DatasetMaster> temp = new ArrayList<DatasetMaster>();
		temp = entityManager.createNamedQuery("DatasetMaster.findAll").getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();

		List<DatasetMaster> tempList = new ArrayList<DatasetMaster>();
		for (int i = 1; i < temp.size(); i++) {
			
			if (temp.get(i).getDatasetid() <18) {
				tempList.add(temp.get(i));
			}
		}

		return tempList;
	}
	
	private List<MasterData> populateMasterData(){
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		
		List<MasterData> masterDatas = new ArrayList<MasterData>();
		masterDatas = entityManager.createQuery("select ms from MasterData ms").getResultList();
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return masterDatas;
	}
	
	private void populateDataSetRunData(){
		datasetRunsList.get(0).getFeatureruns().get(0).getFeaturemaster();
	}

	public void onDataSetChange() {
		accountmastersList = new ArrayList<AccountMaster>();
		featuremastersList = new ArrayList<FeatureMaster>();
		for (DatasetMaster datasetMaster : datasetmastersList) {
			if (selectedDataSet.equalsIgnoreCase(String.valueOf(datasetMaster.getDatasetid()))) {
				accountmastersList = datasetMaster.getAccountmasters();
				featuremastersList = datasetMaster.getFeaturemasters();
				break;

			}
		}
		
	}

	
	public void saveDataSetRun(){
		
	}
	
	
	public void addDefects(){
		
	}
	/**
	 * 
	 * @return entityManager
	 */
	private EntityManager getEntitymanagerFromCurrent() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		return em;
	}

	/**
	 * @return the datasetmastersList
	 */
	public List<DatasetMaster> getDatasetmastersList() {
		return datasetmastersList;
	}

	/**
	 * @param datasetmastersList
	 *            the datasetmastersList to set
	 */
	public void setDatasetmastersList(List<DatasetMaster> datasetmastersList) {
		this.datasetmastersList = datasetmastersList;
	}

	/**
	 * @return the accountmastersList
	 */
	public List<AccountMaster> getAccountmastersList() {
		return accountmastersList;
	}

	/**
	 * @param accountmastersList
	 *            the accountmastersList to set
	 */
	public void setAccountmastersList(List<AccountMaster> accountmastersList) {
		this.accountmastersList = accountmastersList;
	}

	/**
	 * @return the featuremastersList
	 */
	public List<FeatureMaster> getFeaturemastersList() {
		return featuremastersList;
	}

	/**
	 * @param featuremastersList
	 *            the featuremastersList to set
	 */
	public void setFeaturemastersList(List<FeatureMaster> featuremastersList) {
		this.featuremastersList = featuremastersList;
	}

	/**
	 * @return the datasetrundefectsList
	 */
	public List<DatasetRunDefect> getDatasetrundefectsList() {
		return datasetrundefectsList;
	}

	/**
	 * @param datasetrundefectsList
	 *            the datasetrundefectsList to set
	 */
	public void setDatasetrundefectsList(List<DatasetRunDefect> datasetrundefectsList) {
		this.datasetrundefectsList = datasetrundefectsList;
	}

	/**
	 * @return the selectedfeature
	 */
	public String getSelectedfeature() {
		return selectedfeature;
	}

	/**
	 * @param selectedfeature
	 *            the selectedfeature to set
	 */
	public void setSelectedfeature(String selectedfeature) {
		this.selectedfeature = selectedfeature;
	}

	/**
	 * @return the selectedAccount
	 */
	public String getSelectedAccount() {
		return selectedAccount;
	}

	/**
	 * @param selectedAccount
	 *            the selectedAccount to set
	 */
	public void setSelectedAccount(String selectedAccount) {
		this.selectedAccount = selectedAccount;
	}

	/**
	 * @return the selectedDataSet
	 */
	public String getSelectedDataSet() {
		return selectedDataSet;
	}

	/**
	 * @param selectedDataSet
	 *            the selectedDataSet to set
	 */
	public void setSelectedDataSet(String selectedDataSet) {
		this.selectedDataSet = selectedDataSet;
	}

	/**
	 * @return the tablePermission
	 */
	public boolean isTablePermission() {
		return tablePermission;
	}

	/**
	 * @param tablePermission
	 *            the tablePermission to set
	 */
	public void setTablePermission(boolean tablePermission) {
		this.tablePermission = tablePermission;
	}

	/**
	 * @return the panelPermission
	 */
	public boolean isPanelPermission() {
		return panelPermission;
	}

	/**
	 * @param panelPermission
	 *            the panelPermission to set
	 */
	public void setPanelPermission(boolean panelPermission) {
		this.panelPermission = panelPermission;
	}

	/**
	 * @return the testScriptComments
	 */
	public String getTestScriptComments() {
		return testScriptComments;
	}

	/**
	 * @param testScriptComments the testScriptComments to set
	 */
	public void setTestScriptComments(String testScriptComments) {
		this.testScriptComments = testScriptComments;
	}

	/**
	 * @return the masterDatasDropDown
	 */
	public List<MasterData> getMasterDatasDropDown() {
		return masterDatasDropDown;
	}

	/**
	 * @param masterDatasDropDown the masterDatasDropDown to set
	 */
	public void setMasterDatasDropDown(List<MasterData> masterDatasDropDown) {
		this.masterDatasDropDown = masterDatasDropDown;
	}

	/**
	 * @return the selectedDataSetphase
	 */
	public String getSelectedDataSetphase() {
		return selectedDataSetphase;
	}

	/**
	 * @param selectedDataSetphase the selectedDataSetphase to set
	 */
	public void setSelectedDataSetphase(String selectedDataSetphase) {
		this.selectedDataSetphase = selectedDataSetphase;
	}

	/**
	 * @return the datasetRunsList
	 */
	public List<DatasetRun> getDatasetRunsList() {
		return datasetRunsList;
	}

	/**
	 * @param datasetRunsList the datasetRunsList to set
	 */
	public void setDatasetRunsList(List<DatasetRun> datasetRunsList) {
		this.datasetRunsList = datasetRunsList;
	}

	/**
	 * @return the selecetdFeature
	 */
	public FeatureMaster getSelecetdFeature() {
		return selecetdFeature;
	}

	/**
	 * @param selecetdFeature the selecetdFeature to set
	 */
	public void setSelecetdFeature(FeatureMaster selecetdFeature) {
		this.selecetdFeature = selecetdFeature;
	}

}
