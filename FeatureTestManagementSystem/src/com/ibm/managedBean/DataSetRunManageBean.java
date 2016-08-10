package com.ibm.managedBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.ibm.entity.AccountMaster;
import com.ibm.entity.AccountRun;
import com.ibm.entity.DatasetMaster;
import com.ibm.entity.DatasetRun;
import com.ibm.entity.DatasetRunDefect;
import com.ibm.entity.DatasetRunDefectPK;
import com.ibm.entity.FeatureMaster;
import com.ibm.entity.FeatureRun;
import com.ibm.entity.MasterData;
import com.ibm.model.DefectBean;
import com.ibm.model.FeatureRunModelBean;

@ManagedBean
@ViewScoped
public class DataSetRunManageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataSetRunManageBean() {

	}

	private static final String PERSISTENCE_UNIT_NAME = "tmorcl";
	private static final String FAILED = "Failed";
	private static final String PASSEDWWA = "Passed with W/O";
	private static final String MANAGER = "manager";
	private static final String TESTER = "tester";
	private static final String FEATURECYCLE = "featurecycle";
	private static final String FEATURETESTRESULT = "featureTestRunResult";
	private static final String TESTPHASE = "testPhase";
	private static final String DATASETPHASE = "datasetphase";
	private static final String READYFORRERUNYES = "Y";
	private static final String READYFORRERUNNO = "N";
	private static final String READYFORRERUNREADY = "R";
	private static final String DATASETRUNPASS = "Passed";
	private static final String DATASETRUNFAILED = "Failed";

	private List<DatasetMaster> datasetmastersList;
	private List<AccountMaster> accountmastersList;
	private List<FeatureMaster> featuremastersList;
	private List<DatasetRunDefect> datasetrundefectsList;
	private List<MasterData> testPhaseDropDown;
	private List<DatasetRun> datasetRunsList;
	private List<MasterData> featurePhaseDropDown;
	private List<MasterData> featureStatusDropDown;
	private List<MasterData> datasetPhaseDropDown;
	private List<DefectBean> defectaddingList;
	private List<FeatureRunModelBean> featureRunModelBeansList;

	private boolean showDefectGroup;
	private FeatureMaster selecetdFeature;
	private String selectedfeature;
	private String selectedAccount;
	private String selectedDataSet;
	private String testScriptComments;
	private String selectedDataSetphase;
	private String selectedFeatureResult;
	private String selectedTestPhase;
	private String selectedFeatureID;
	private String selectedFeaturePhase;
	private boolean tablePermission;
	private boolean panelPermission;
	private boolean showfeatureDefectPanel;

	@PostConstruct
	private void init() {
		try {
			datasetmastersList = new ArrayList<DatasetMaster>();
			datasetmastersList = populateDataSet();
			populateMasterData();
			defectaddingList = new ArrayList<DefectBean>();
			showfeatureDefectPanel = false;
			featureRunModelBeansList = new ArrayList<FeatureRunModelBean>();
		} catch (Exception exception) {
			exception.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Application/Data Error", exception.getLocalizedMessage());
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

			if (temp.get(i).getDatasetid() < 18) {
				tempList.add(temp.get(i));
			}
		}

		return tempList;
	}

	@SuppressWarnings("unchecked")
	private void populateMasterData() {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();

		List<MasterData> masterDatas = new ArrayList<MasterData>();
		masterDatas = entityManager.createQuery("select ms from MasterData ms").getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		featurePhaseDropDown = new ArrayList<MasterData>();
		featureStatusDropDown = new ArrayList<MasterData>();
		datasetPhaseDropDown = new ArrayList<MasterData>();
		testPhaseDropDown = new ArrayList<MasterData>();
		for (MasterData masterData : masterDatas) {

			if (masterData.getCatagory().equalsIgnoreCase(FEATURECYCLE)) {
				featurePhaseDropDown.add(masterData);
			} else if (masterData.getCatagory().equalsIgnoreCase(FEATURETESTRESULT)) {
				featureStatusDropDown.add(masterData);
			} else if (masterData.getCatagory().equalsIgnoreCase(TESTPHASE)) {
				testPhaseDropDown.add(masterData);
			} else if (masterData.getCatagory().equalsIgnoreCase(DATASETPHASE)) {
				datasetPhaseDropDown.add(masterData);
			}
		}

	}

	private void populateDataSetRunData() {
		datasetRunsList.get(0).getFeatureruns().get(0).getFeaturemaster();
	}

	public void onResultChange() {
		if ((selectedFeatureResult.equalsIgnoreCase(FAILED)) || (selectedFeatureResult.equalsIgnoreCase(PASSEDWWA))) {
			showDefectGroup = true;

		} else {
			showDefectGroup = false;
		}
	}

	public void addMoreDefects() {
		System.out.println("I am called");
		if (showDefectGroup) {
			defectaddingList.add(new DefectBean());
			showDefectGroup = true;
		}
		System.out.println(defectaddingList.size());
	}

	public void onDataSetChange() {
		accountmastersList = new ArrayList<AccountMaster>();
		featuremastersList = new ArrayList<FeatureMaster>();
		for (DatasetMaster datasetMaster : datasetmastersList) {
			if (selectedDataSet.equalsIgnoreCase(String.valueOf(datasetMaster.getDatasetid()))) {
				accountmastersList = datasetMaster.getAccountmasters();
				featuremastersList = datasetMaster.getFeaturemasters();
				FeatureRunModelBean bean = null;
				featureRunModelBeansList = new ArrayList<FeatureRunModelBean>();
				for (FeatureMaster master : datasetMaster.getFeaturemasters()) {
					bean = new FeatureRunModelBean();
					bean.setFeatureSetId(master.getFeatureset());
					bean.setDefectBeansList(defectaddingList);
					bean.setFeaturemasterID(master.getFeatureid());
					bean.setFeatureMaster(master);
					featureRunModelBeansList.add(bean);
				}
				break;

			}
		}

	}

	public void saveFeatureData() {
		// insert data in feature run object level
		for (FeatureRunModelBean bean : featureRunModelBeansList) {
			if (bean.getFeatureSetId().equalsIgnoreCase(selectedFeatureID)) {
				if (selectedFeaturePhase.length() > 0 && selectedFeatureResult.length() > 0 && selectedTestPhase.length() > 0) {
					bean.setFeatureSetId(selectedFeatureID);
					bean.setFeatureRunPhase(selectedFeaturePhase);
					bean.setFeatureRunResult(selectedFeatureResult);
					bean.setFeatureTestPhase(selectedTestPhase);
					bean.setReadyForinsert(true);
					// check for the feature result status
					if ((selectedFeatureResult.equalsIgnoreCase(PASSEDWWA) || selectedFeatureResult.equalsIgnoreCase(FAILED)) && defectaddingList.size() > 0) {
						bean.setDefectBeansList(defectaddingList);
					}
					// reset all drop downs and data as we have updated the bean

				} else {
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, null, "Cannot Save Data without required fields");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					// error cannot save data without required field
				}
				defectaddingList = new ArrayList<DefectBean>();
				selectedFeatureID = new String();
				selectedFeaturePhase = new String();
				selectedFeatureResult = new String();
				selectedTestPhase = new String();
				showDefectGroup = false;
				break;
			}
		}
		showDefectGroup = false;
		showfeatureDefectPanel = false;
	}

	public void saveDataSetRun() {
		showfeatureDefectPanel = false;
		if (selectedDataSetphase.length() > 0 && testScriptComments.trim().length() > 0) {
			// insert data in data set run
			boolean datasetRunstatusfailed = false;
			DatasetRun datasetRun = new DatasetRun();

			datasetRun.setRuntime(new Timestamp(new Date().getTime()));
			datasetRun.setRunby("user");
			datasetRun.setRunphase(selectedDataSetphase);
			datasetRun.setReadyforrun(READYFORRERUNYES);

			// calculate run status for dataset.
			if (featureRunModelBeansList != null && featureRunModelBeansList.size() > 0) {
				for (FeatureRunModelBean bean : featureRunModelBeansList) {
					if (bean.getDefectBeansList().size() > 0) {
						datasetRunstatusfailed = true;
						break;
					}
				}
			}
			if (datasetRunstatusfailed) {
				datasetRun.setRunstatus(DATASETRUNFAILED);
			} else {
				datasetRun.setRunstatus(DATASETRUNPASS);
			}
			DatasetRun runmerged = new DatasetRun();
			EntityManager entityManager = getEntitymanagerFromCurrent();
			entityManager.getTransaction().begin();
			runmerged = entityManager.merge(datasetRun);
			entityManager.getTransaction().commit();
			entityManager.close();

			List<FeatureRun> featureRuns = new ArrayList<FeatureRun>();
			FeatureRun run = null;
			for (FeatureRunModelBean bean : featureRunModelBeansList) {
				run = new FeatureRun();
				run.setDatasetrun(runmerged);
				run.setFeaturemaster(bean.getFeatureMaster());
				run.setStatus(bean.getFeatureRunResult());
				entityManager = getEntitymanagerFromCurrent();
				entityManager.getTransaction().begin();
				entityManager.persist(run);
				entityManager.getTransaction().commit();
				entityManager.close();

			}

			// now we have datasetRun ID
			List<AccountRun> accountRuns = new ArrayList<AccountRun>();
			AccountRun accountRun = null;
			for (AccountMaster accountMaster : accountmastersList) {
				accountRun = new AccountRun();
				accountRun.setAccountmaster(accountMaster);
				accountRun.setDatasetrun(runmerged);
				entityManager = getEntitymanagerFromCurrent();
				entityManager.getTransaction().begin();
				entityManager.persist(accountRun);
				entityManager.getTransaction().commit();
				entityManager.close();

			}

			DatasetRunDefect defect = new DatasetRunDefect();
			DatasetRunDefectPK pk = new DatasetRunDefectPK();
			pk.setDatasetrunid(runmerged.getDatasetrunid());
			if (featureRunModelBeansList != null && featureRunModelBeansList.size() > 0) {
				for (FeatureRunModelBean bean : featureRunModelBeansList) {
					if (bean.getDefectBeansList().size() > 0) {
						for (DefectBean defectBean : bean.getDefectBeansList()) {
							defect.setDatasetrun(runmerged);
							defect.setDefectsevrity("High");
							defect.setDefectstatus(FAILED);
							defect.setHpqcdefectid(new BigDecimal(defectBean.getHPQCID()));
							defect.setId(pk);
						}
					}
				}
			}

		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, null, "Cannot Save Data without required fields");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
	}

	public void addFeatureDefects(ActionEvent event) {
		FeatureRunModelBean featureMaster = new FeatureRunModelBean();
		featureMaster = (FeatureRunModelBean) event.getComponent().getAttributes().get("feature");
		setShowfeatureDefectPanel(true);
		System.out.println(featureMaster);
		selectedFeatureID = featureMaster.getFeatureSetId();
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
	 * @param testScriptComments
	 *            the testScriptComments to set
	 */
	public void setTestScriptComments(String testScriptComments) {
		this.testScriptComments = testScriptComments;
	}

	/**
	 * @return the selectedDataSetphase
	 */
	public String getSelectedDataSetphase() {
		return selectedDataSetphase;
	}

	/**
	 * @param selectedDataSetphase
	 *            the selectedDataSetphase to set
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
	 * @param datasetRunsList
	 *            the datasetRunsList to set
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
	 * @param selecetdFeature
	 *            the selecetdFeature to set
	 */
	public void setSelecetdFeature(FeatureMaster selecetdFeature) {
		this.selecetdFeature = selecetdFeature;
	}

	/**
	 * @return the featurePhaseDropDown
	 */
	public List<MasterData> getFeaturePhaseDropDown() {
		return featurePhaseDropDown;
	}

	/**
	 * @param featurePhaseDropDown
	 *            the featurePhaseDropDown to set
	 */
	public void setFeaturePhaseDropDown(List<MasterData> featurePhaseDropDown) {
		this.featurePhaseDropDown = featurePhaseDropDown;
	}

	/**
	 * @return the featureStatusDropDown
	 */
	public List<MasterData> getFeatureStatusDropDown() {
		return featureStatusDropDown;
	}

	/**
	 * @param featureStatusDropDown
	 *            the featureStatusDropDown to set
	 */
	public void setFeatureStatusDropDown(List<MasterData> featureStatusDropDown) {
		this.featureStatusDropDown = featureStatusDropDown;
	}

	/**
	 * @return the datasetPhaseDropDown
	 */
	public List<MasterData> getDatasetPhaseDropDown() {
		return datasetPhaseDropDown;
	}

	/**
	 * @param datasetPhaseDropDown
	 *            the datasetPhaseDropDown to set
	 */
	public void setDatasetPhaseDropDown(List<MasterData> datasetPhaseDropDown) {
		this.datasetPhaseDropDown = datasetPhaseDropDown;
	}

	/**
	 * @return the testPhaseDropDown
	 */
	public List<MasterData> getTestPhaseDropDown() {
		return testPhaseDropDown;
	}

	/**
	 * @param testPhaseDropDown
	 *            the testPhaseDropDown to set
	 */
	public void setTestPhaseDropDown(List<MasterData> testPhaseDropDown) {
		this.testPhaseDropDown = testPhaseDropDown;
	}

	/**
	 * @return the defectaddingList
	 */
	public List<DefectBean> getDefectaddingList() {
		return defectaddingList;
	}

	/**
	 * @param defectaddingList
	 *            the defectaddingList to set
	 */
	public void setDefectaddingList(List<DefectBean> defectaddingList) {
		this.defectaddingList = defectaddingList;
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
	 * @return the selectedFeatureResult
	 */
	public String getSelectedFeatureResult() {
		return selectedFeatureResult;
	}

	/**
	 * @param selectedFeatureResult
	 *            the selectedFeatureResult to set
	 */
	public void setSelectedFeatureResult(String selectedFeatureResult) {
		this.selectedFeatureResult = selectedFeatureResult;
	}

	/**
	 * @return the selectedTestPhase
	 */
	public String getSelectedTestPhase() {
		return selectedTestPhase;
	}

	/**
	 * @param selectedTestPhase
	 *            the selectedTestPhase to set
	 */
	public void setSelectedTestPhase(String selectedTestPhase) {
		this.selectedTestPhase = selectedTestPhase;
	}

	/**
	 * @return the showfeatureDefectPanel
	 */
	public boolean isShowfeatureDefectPanel() {
		return showfeatureDefectPanel;
	}

	/**
	 * @param showfeatureDefectPanel
	 *            the showfeatureDefectPanel to set
	 */
	public void setShowfeatureDefectPanel(boolean showfeatureDefectPanel) {
		this.showfeatureDefectPanel = showfeatureDefectPanel;
	}

	/**
	 * @return the selectedFeatureID
	 */
	public String getSelectedFeatureID() {
		return selectedFeatureID;
	}

	/**
	 * @param selectedFeatureID
	 *            the selectedFeatureID to set
	 */
	public void setSelectedFeatureID(String selectedFeatureID) {
		this.selectedFeatureID = selectedFeatureID;
	}

	/**
	 * @return the featureRunModelBeansList
	 */
	public List<FeatureRunModelBean> getFeatureRunModelBeansList() {
		return featureRunModelBeansList;
	}

	/**
	 * @param featureRunModelBeansList
	 *            the featureRunModelBeansList to set
	 */
	public void setFeatureRunModelBeansList(List<FeatureRunModelBean> featureRunModelBeansList) {
		this.featureRunModelBeansList = featureRunModelBeansList;
	}

}
