package com.ibm.defectMan.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.primefaces.event.RowEditEvent;

import com.ibm.utils.defmng.model.Account;
import com.ibm.utils.defmng.model.DataSet;
import com.ibm.utils.defmng.model.Defect;
import com.ibm.utils.defmng.model.Feature;
import com.ibm.utils.defmng.model.MasterData;
import com.ibm.utils.defmng.model.RecordCreation;
import com.ibm.utils.defmng.model.TestExecution;

@ManagedBean
@ViewScoped
public class HomeManagedBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HomeManagedBean() {

	}

	private static final String PERSISTENCE_UNIT_NAME = "dbpersistence";
	List<TestExecution> testExecutionsList;
	private List<Feature> featurDataListForDropDown;
	private List<Account> accountsForDropDown;
	private List<DataSet> dataSetsListForDropDown;
	private List<MasterData> masterDatasList;
	private List<String> defectaddingList;
	private List<MasterData> listForFeatureTestExecutionResult;
	private List<MasterData> listFeaturePhaseExecutionForDropDown;

	private String selectedfeature;
	private String selectedAccount;
	private String selectedDataSet;
	private String featurerunExecutionResult;
	private String testScriptComments;
	private String defect;
	private String selectedFeaturePhase;


	private boolean showDefectGroup;
	private boolean tablePermission;
	private boolean panelPermission;

	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;

	@PostConstruct
	public void populateTestExecutionList() {
		try {
			if (loginManagedBean != null) {
				if (loginManagedBean.getUserRole().equalsIgnoreCase("manager")) {
					panelPermission = false;
					tablePermission = true;
					testExecutionsList = populateAllDataList();
				} else if (loginManagedBean.getUserRole().equalsIgnoreCase("tester")) {
					panelPermission = true;
					tablePermission = true;
					testExecutionsList = populateUserSpecificData();
					retriveAllListForPage();
					retriveMasterData();
					retriveMasterDataForFeatureResult();
					showDefectGroup = false;
					defectaddingList = new ArrayList<String>();

				}
				
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			
		}
	}

	public void onFeatureChange() {
		accountsForDropDown = new ArrayList<Account>();
		dataSetsListForDropDown = new ArrayList<DataSet>();
		for (Feature feature : featurDataListForDropDown) {
			if (feature.getFeatureNumber().equalsIgnoreCase(selectedfeature)) {
				accountsForDropDown.addAll(feature.getAccounts());
				dataSetsListForDropDown.addAll(feature.getDataSets());
				break;
			}
		}

		System.out.println(accountsForDropDown.size());
	}

	public void onResultChange() {
		if ((featurerunExecutionResult.equalsIgnoreCase("Failed")) || (featurerunExecutionResult.equalsIgnoreCase("Passed W"))) {
			showDefectGroup = true;
			defectaddingList.add(new String());
		} else {
			showDefectGroup = false;
		}
	}

	public void addMoreDefects() {
		System.out.println("I am called");
		if (showDefectGroup) {
			defectaddingList.add(new String());
			showDefectGroup = true;
		}
		System.out.println(defectaddingList.size());
	}

	@SuppressWarnings("unchecked")
	public void saveAllData() {
		TestExecution testExecution = new TestExecution();
		Feature featureRun = new Feature();
		Account accountRun = new Account();
		DataSet dataSetRun = new DataSet();
		for (Feature feature : featurDataListForDropDown) {
			if (feature.getFeatureNumber().equalsIgnoreCase(selectedfeature)) {
				featureRun = feature;
				break;
			}
		}

		for (Account account : featureRun.getAccounts()) {
			if (account.getAccountName().equalsIgnoreCase(selectedAccount)) {
				accountRun = account;
				break;
			}
		}

		for (DataSet dataSet : featureRun.getDataSets()) {
			if (dataSet.getDataSetLocation().equalsIgnoreCase(selectedDataSet)) {
				dataSetRun = dataSet;
				break;
			}
		}
		
		

		testExecution.setFeatureRun(featureRun);
		testExecution.setAccountRun(accountRun);
		testExecution.setDataSetRun(dataSetRun);
		testExecution.setTestScriptComments(testScriptComments);
		testExecution.setTestExecutionPhase(selectedFeaturePhase);
		testExecution.setTestStatus(featurerunExecutionResult);
		testExecution.setRecordCreation(createRecord());

		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();

		List<Defect> tempDefList = entityManager.createQuery("select df from Defect df where hpqcDefectID IN :defects")
				.setParameter("defects", defectaddingList).getResultList();
		testExecution.setDefectslist(tempDefList);
		entityManager.persist(testExecution);
		entityManager.getTransaction().commit();
		entityManager.close();
		testExecutionsList = populateUserSpecificData();
		// not adding defects to list as data not proper , will add defects once
		// data is there.

		// now reset all data

		showDefectGroup = false;
		defectaddingList = new ArrayList<String>();

	}
	
	public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", ((TestExecution) event.getObject()).getTestExecutionPhase());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((TestExecution) event.getObject()).getTestExecutionPhase());
        FacesContext.getCurrentInstance().addMessage(null, msg);
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

	@SuppressWarnings("unchecked")
	private List<TestExecution> populateAllDataList() {

		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();

		List<TestExecution> tempExecutionlist = new ArrayList<TestExecution>();

		tempExecutionlist = entityManager.createQuery("Select tx from TestExecution tx").getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();

		return tempExecutionlist;
	}

	@SuppressWarnings("unchecked")
	private List<TestExecution> populateUserSpecificData() {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();

		List<TestExecution> tempExecutionlist = new ArrayList<TestExecution>();

		tempExecutionlist = entityManager.createQuery("Select tx from TestExecution tx where tx.recordCreation.updatedBy = :userName")
				.setParameter("userName", loginManagedBean.getUserName()).getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();

		return tempExecutionlist;

	}
	
	private void retriveAllListForPage(){
		featurDataListForDropDown = new ArrayList<Feature>();
		featurDataListForDropDown = retriveFeatureListFromDB();
		accountsForDropDown = new ArrayList<Account>();
		accountsForDropDown = retriveAllAccountListFromDB(); 
		dataSetsListForDropDown = new ArrayList<DataSet>();
		dataSetsListForDropDown = retriveAllDataSetListFromDB();
		
	}

	@SuppressWarnings("unchecked")
	private List<Feature> retriveFeatureListFromDB() {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		//List<Feature> tempList = new ArrayList<Feature>();
		List<Feature> tempList = new ArrayList<Feature>();
		tempList = entityManager.createQuery("select fe from Feature fe").getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();

		return tempList;

	}
	
	@SuppressWarnings("unchecked")
	private List<Account> retriveAllAccountListFromDB() {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		List<Account> tempList = new ArrayList<Account>();
		tempList = entityManager.createQuery("Select ac from Account ac ").getResultList();
		for (Account account : tempList) {
			StringBuilder builder = new StringBuilder();
			for (Feature feature : account.getFeatures()) {
				builder.append(feature.getFeatureNumber());
			}
			System.out.println(builder.toString());
		}

		entityManager.getTransaction().commit();
		entityManager.close();
		return tempList;

	}
	
	@SuppressWarnings("unchecked")
	private List<DataSet> retriveAllDataSetListFromDB() {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		List<DataSet> tempList = entityManager.createQuery("select ds from DataSet ds").getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();

		return tempList;
	}

	@SuppressWarnings("unchecked")
	private List<Feature> retriveFeatureListFromDBForFeatureId(String featureID) {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		featurDataListForDropDown = new ArrayList<Feature>();
		featurDataListForDropDown = entityManager.createQuery("select fe from Feature fe where fe.featureNumber=:featureNumber")
				.setParameter("featureNumber", featureID).getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();

		return featurDataListForDropDown;

	}

	@SuppressWarnings("unchecked")
	private List<Account> retriveSelectedAccountList(String accountName) {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		List<Account> tempList = new ArrayList<Account>();
		tempList = entityManager.createQuery("Select ac from Account ac where ac.accountName=:accountName").setParameter("accountName", accountName)
				.getResultList();
		for (Account account : tempList) {
			StringBuilder builder = new StringBuilder();
			for (Feature feature : account.getFeatures()) {
				builder.append(feature.getFeatureNumber());
			}
			System.out.println(builder.toString());
		}

		entityManager.getTransaction().commit();
		entityManager.close();
		return tempList;

	}

	@SuppressWarnings("unchecked")
	private List<DataSet> retriveSelectedData(String dataSetLocation) {

		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		List<DataSet> tempList = entityManager.createQuery("select ds from DataSet ds where ds.dataSetLocation=:dataSetLocation")
				.setParameter("dataSetLocation", dataSetLocation).getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();

		return tempList;

	}

	private void retriveMasterData() {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		masterDatasList = new ArrayList<MasterData>();
		List<MasterData> masterDatas = entityManager.createQuery("select ms from MasterData ms ").getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		masterDatasList = masterDatas;
	}

	private void retriveMasterDataForFeatureResult() {
		List<MasterData> tempList = new ArrayList<MasterData>();
		List<MasterData> tempListPhase = new ArrayList<MasterData>();
		for (MasterData data : masterDatasList) {
			if (data.getCatagory().equalsIgnoreCase("featureTestRunResult")) {
				tempList.add(data);
			} else if (data.getCatagory().equalsIgnoreCase("featurecycle")) {
				tempListPhase.add(data);
			}
		}
		listForFeatureTestExecutionResult = tempList;
		listFeaturePhaseExecutionForDropDown = tempListPhase;
	}

	private RecordCreation createRecord() {
		RecordCreation creation = new RecordCreation();
		creation.setCreatedBy(loginManagedBean.getUserName());
		creation.setUpdatedBy(loginManagedBean.getUserName());
		creation.setCreationDate(new Date());
		creation.setUpdationDate(new Date());
		return creation;
	}

	public String featurepage() {
		return "featurePages";
	}

	/**
	 * @return the testExecutionsList
	 */
	public List<TestExecution> getTestExecutionsList() {
		return testExecutionsList;
	}

	/**
	 * @param testExecutionsList
	 *            the testExecutionsList to set
	 */
	public void setTestExecutionsList(List<TestExecution> testExecutionsList) {
		this.testExecutionsList = testExecutionsList;
	}

	/**
	 * @return the featurDataListForDropDown
	 */
	public List<Feature> getFeaturDataListForDropDown() {
		return featurDataListForDropDown;
	}

	/**
	 * @param featurDataListForDropDown
	 *            the featurDataListForDropDown to set
	 */
	public void setFeaturDataListForDropDown(List<Feature> featurDataListForDropDown) {
		this.featurDataListForDropDown = featurDataListForDropDown;
	}

	/**
	 * @return the accountsForDropDown
	 */
	public List<Account> getAccountsForDropDown() {
		return accountsForDropDown;
	}

	/**
	 * @param accountsForDropDown
	 *            the accountsForDropDown to set
	 */
	public void setAccountsForDropDown(List<Account> accountsForDropDown) {
		this.accountsForDropDown = accountsForDropDown;
	}

	/**
	 * @return the dataSetsListForDropDown
	 */
	public List<DataSet> getDataSetsListForDropDown() {
		return dataSetsListForDropDown;
	}

	/**
	 * @param dataSetsListForDropDown
	 *            the dataSetsListForDropDown to set
	 */
	public void setDataSetsListForDropDown(List<DataSet> dataSetsListForDropDown) {
		this.dataSetsListForDropDown = dataSetsListForDropDown;
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
	 * @return the masterDatasList
	 */
	public List<MasterData> getMasterDatasList() {
		return masterDatasList;
	}

	/**
	 * @param masterDatasList
	 *            the masterDatasList to set
	 */
	public void setMasterDatasList(List<MasterData> masterDatasList) {
		this.masterDatasList = masterDatasList;
	}

	/**
	 * @return the listForFeatureTestExecutionResult
	 */
	public List<MasterData> getListForFeatureTestExecutionResult() {
		return listForFeatureTestExecutionResult;
	}

	/**
	 * @param listForFeatureTestExecutionResult
	 *            the listForFeatureTestExecutionResult to set
	 */
	public void setListForFeatureTestExecutionResult(List<MasterData> listForFeatureTestExecutionResult) {
		this.listForFeatureTestExecutionResult = listForFeatureTestExecutionResult;
	}

	/**
	 * @return the featurerunExecutionResult
	 */
	public String getFeaturerunExecutionResult() {
		return featurerunExecutionResult;
	}

	/**
	 * @param featurerunExecutionResult
	 *            the featurerunExecutionResult to set
	 */
	public void setFeaturerunExecutionResult(String featurerunExecutionResult) {
		this.featurerunExecutionResult = featurerunExecutionResult;
	}

	/**
	 * @return the testScriptComments
	 */
	public String getTestScriptComments() {
		return testScriptComments;
	}

	/**
	 * @param testScriptComments
	 *            the testScriptComments to set
	 */
	public void setTestScriptComments(String testScriptComments) {
		this.testScriptComments = testScriptComments;
	}

	/**
	 * @return the defect
	 */
	public String getDefect() {
		return defect;
	}

	/**
	 * @param defect
	 *            the defect to set
	 */
	public void setDefect(String defect) {
		this.defect = defect;
	}

	/**
	 * @return the showDefectGroup
	 */
	public boolean isShowDefectGroup() {
		return showDefectGroup;
	}

	/**
	 * @param showDefectGroup
	 *            the showDefectGroup to set
	 */
	public void setShowDefectGroup(boolean showDefectGroup) {
		this.showDefectGroup = showDefectGroup;
	}

	/**
	 * @return the defectaddingList
	 */
	public List<String> getDefectaddingList() {
		return defectaddingList;
	}

	/**
	 * @param defectaddingList
	 *            the defectaddingList to set
	 */
	public void setDefectaddingList(List<String> defectaddingList) {
		this.defectaddingList = defectaddingList;
	}

	/**
	 * @return the listFeaturePhaseExecutionForDropDown
	 */
	public List<MasterData> getListFeaturePhaseExecutionForDropDown() {
		return listFeaturePhaseExecutionForDropDown;
	}

	/**
	 * @param listFeaturePhaseExecutionForDropDown
	 *            the listFeaturePhaseExecutionForDropDown to set
	 */
	public void setListFeaturePhaseExecutionForDropDown(List<MasterData> listFeaturePhaseExecutionForDropDown) {
		this.listFeaturePhaseExecutionForDropDown = listFeaturePhaseExecutionForDropDown;
	}

	/**
	 * @return the selectedFeaturePhase
	 */
	public String getSelectedFeaturePhase() {
		return selectedFeaturePhase;
	}

	/**
	 * @param selectedFeaturePhase
	 *            the selectedFeaturePhase to set
	 */
	public void setSelectedFeaturePhase(String selectedFeaturePhase) {
		this.selectedFeaturePhase = selectedFeaturePhase;
	}

	/**
	 * @return the loginManagedBean
	 */
	public LoginManagedBean getLoginManagedBean() {
		return loginManagedBean;
	}

	/**
	 * @param loginManagedBean
	 *            the loginManagedBean to set
	 */
	public void setLoginManagedBean(LoginManagedBean loginManagedBean) {
		this.loginManagedBean = loginManagedBean;
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

	

}
