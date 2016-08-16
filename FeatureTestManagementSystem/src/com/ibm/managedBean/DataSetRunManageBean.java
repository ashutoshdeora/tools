package com.ibm.managedBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
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

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import com.ibm.entity.AccountMaster;
import com.ibm.entity.AccountRun;
import com.ibm.entity.DatasetMaster;
import com.ibm.entity.DatasetRun;
import com.ibm.entity.DatasetRunDefect;
import com.ibm.entity.DatasetRunDefectPK;
import com.ibm.entity.FeatureMaster;
import com.ibm.entity.FeatureRun;
import com.ibm.entity.MasterData;
import com.ibm.model.DataSetRunBean;
import com.ibm.model.DefectBean;
import com.ibm.model.FeatureForDataSetRunBean;
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
	private static final String DATASETRUNREADYFORRERUN = "Ready";

	private static final String DATASETSTATUSCOLORPASSED = "row_status_style_passed";
	private static final String DATASETSTATUSCOLORRRDY = "row_status_style_rrdy";
	private static final String DATASETSTATUSCOLORFAILED = "row_status_style_failed";

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
	private List<DataSetRunBean> dataSetRunBeansList;

	private boolean showDefectGroup;
	private FeatureMaster selecetdFeature;
	private String selectedfeature;
	private String selectedAccount;
	// private String selectedDataSet;
	private String testScriptComments;
	private String selectedDataSetphase;
	private String selectedFeatureResult;
	private String selectedTestPhase;
	private String selectedFeatureID;
	private String selectedFeaturePhase;
	private boolean tablePermission;
	private boolean panelPermission;
	private boolean showfeatureDefectPanel;
	private DatasetMaster masterRecordFromsuggestion;
	private DataSetRunBean selectedDataSetRunBean;

	@PostConstruct
	private void init() {
		try {
			datasetmastersList = new ArrayList<DatasetMaster>();
			datasetmastersList = populateDataSet();
			populateMasterData();
			defectaddingList = new ArrayList<DefectBean>();
			showfeatureDefectPanel = false;
			featureRunModelBeansList = new ArrayList<FeatureRunModelBean>();
			populateDataSetRunData();
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

	public void onDataSetChange(SelectEvent event) {
		masterRecordFromsuggestion = (DatasetMaster) event.getObject();
		accountmastersList = new ArrayList<AccountMaster>();
		featuremastersList = new ArrayList<FeatureMaster>();
		for (DatasetMaster datasetMaster : datasetmastersList) {
			if (getMasterRecordFromsuggestion().getSelectedDataSetEvent().equalsIgnoreCase(datasetMaster.getDatasetname())) {
				setMasterRecordFromsuggestion(datasetMaster);
				accountmastersList = datasetMaster.getAccountmasters();
				featuremastersList = datasetMaster.getFeaturemasters();
				FeatureRunModelBean bean = null;
				featureRunModelBeansList = new ArrayList<FeatureRunModelBean>();
				/*
				 * for (FeatureMaster master :
				 * datasetMaster.getFeaturemasters()) { bean = new
				 * FeatureRunModelBean();
				 * bean.setFeatureSetId(master.getFeatureset());
				 * bean.setDefectBeansList(defectaddingList);
				 * bean.setFeaturemasterID(master.getFeatureid());
				 * bean.setFeatureMaster(master);
				 * featureRunModelBeansList.add(bean); }
				 */
				// this is temp solution
				for (int i = 1; i < 4; i++) {
					bean = new FeatureRunModelBean();
					bean.setFeatureSetId(datasetMaster.getFeaturemasters().get(i).getFeatureset());
					bean.setDefectBeansList(defectaddingList);
					bean.setFeaturemasterID(datasetMaster.getFeaturemasters().get(i).getFeatureid());
					bean.setFeatureMaster(datasetMaster.getFeaturemasters().get(i));
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

	public void onRowEdit(RowEditEvent event) {
		FeatureRunModelBean featureRunModelBean = (FeatureRunModelBean) event.getObject();

		// set all required data and reset all drop down fields in else

		featureRunModelBean.setFeatureRunPhase(selectedFeaturePhase);
		featureRunModelBean.setFeatureRunResult(selectedFeatureResult);
		featureRunModelBean.setFeatureTestPhase(selectedTestPhase);
		String defectString = featureRunModelBean.getInputDefects().trim();
		if (selectedFeatureResult.equalsIgnoreCase(FAILED) || selectedFeatureResult.equalsIgnoreCase(PASSEDWWA)) {
			ArrayList<String> defList = new ArrayList<String>(Arrays.asList(defectString.split(",")));
			if (validateWithRest(defList)) {
				featureRunModelBean.setDefectsData(defectString);
				featureRunModelBean.setDefectList(defList);
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "One of defect not present in HPQC", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);

			}
		}
		selectedFeaturePhase = new String();
		selectedFeatureResult = new String();
		selectedTestPhase = new String();
		FacesMessage msg = new FacesMessage("Row Edited", null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<String> completeText(String query) {
		List<String> results = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			results.add(query + i);
		}
		return results;
	}

	public List<DatasetMaster> completeDataset(String query) {
		List<DatasetMaster> tempList = new ArrayList<DatasetMaster>();
		for (int i = 0; i < datasetmastersList.size(); i++) {
			String dataSetname = datasetmastersList.get(i).getDatasetname();
			if (dataSetname.toLowerCase().contains(query)) {
				tempList.add(datasetmastersList.get(i));
			}
		}
		return tempList;
	}

	public void reExecuteDataSet(ActionEvent event) {
		DataSetRunBean myattr = (DataSetRunBean) event.getComponent().getAttributes().get("selDsrb");
		System.out.println(myattr.getDatasetRun().getDatasetrunid());

	}

	private boolean validateWithRest(ArrayList<String> defList) {
		// TODO Auto-generated method stub
		return true;
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled", null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void saveDataSetRun() {
		try {
			EntityManager entityManager = getEntitymanagerFromCurrent();
			showfeatureDefectPanel = false;
			if (selectedDataSetphase.length() > 0 && testScriptComments.trim().length() > 0) {
				// insert data in data set run
				boolean datasetRunstatusfailed = false;
				DatasetRun datasetRun = new DatasetRun();

				datasetRun.setRuntime(new Timestamp(new Date().getTime()));
				datasetRun.setRunby("user");
				datasetRun.setRunphase(selectedDataSetphase);
				datasetRun.setReadyforrun(READYFORRERUNYES);
				datasetRun.setDatasetmaster(getMasterRecordFromsuggestion());

				// calculate run status for dataset.
				if (featureRunModelBeansList != null && featureRunModelBeansList.size() > 0) {
					for (FeatureRunModelBean bean : featureRunModelBeansList) {
						if (bean.getDefectList().size() > 0) {
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
				entityManager.getTransaction().begin();
				runmerged = entityManager.merge(datasetRun);
				entityManager.getTransaction().commit();
				DatasetRun remerge = new DatasetRun();
				remerge = runmerged;
				remerge.setParentdatasetrunid(BigDecimal.valueOf(runmerged.getDatasetrunid()));
				entityManager.getTransaction().begin();
				entityManager.merge(remerge);
				entityManager.getTransaction().commit();

				FeatureRun run = null;
				FeatureRun featureRunMerged = null;
				for (FeatureRunModelBean bean : featureRunModelBeansList) {
					run = new FeatureRun();
					featureRunMerged = new FeatureRun();
					run.setDatasetrunid(BigDecimal.valueOf(runmerged.getDatasetrunid()));
					run.setFeaturemasterid(BigDecimal.valueOf(bean.getFeaturemasterID()));
					run.setStatus(bean.getFeatureRunResult());
					entityManager.getTransaction().begin();
					featureRunMerged = entityManager.merge(run);
					entityManager.getTransaction().commit();

					DatasetRunDefect defect = null;

					if (bean.getDefectList() != null && bean.getDefectList().size() > 0) {
						for (String defectBean : bean.getDefectList()) {
							defect = new DatasetRunDefect();
							defect.setFeaturerunid(BigDecimal.valueOf(featureRunMerged.getFeaturerunid()));
							defect.setDefectsevrity("High");
							defect.setDefectstatus(FAILED);
							defect.setHpqcdefectid(new BigDecimal(defectBean));
							DatasetRunDefectPK pk = new DatasetRunDefectPK();
							pk.setDatasetrunid(runmerged.getDatasetrunid());
							defect.setId(pk);
							entityManager.getTransaction().begin();
							entityManager.persist(defect);
							entityManager.getTransaction().commit();

						}

					}
				}

				// now we have datasetRun ID

				AccountRun accountRun = null;
				for (AccountMaster accountMaster : accountmastersList) {
					accountRun = new AccountRun();
					accountRun.setAccountmasterid(BigDecimal.valueOf(accountMaster.getAccountid()));
					accountRun.setDatasetrunid(BigDecimal.valueOf(runmerged.getDatasetrunid()));
					entityManager.getTransaction().begin();
					entityManager.persist(accountRun);
					entityManager.getTransaction().commit();

				}

				entityManager.close();
				populateDataSetRunData();

			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, null, "Cannot Save Data without required fields");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
		} catch (Exception exception) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Some Error");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			exception.printStackTrace();
		}
	}

	public void addFeatureDefects(ActionEvent event) {
		FeatureRunModelBean featureMaster = new FeatureRunModelBean();
		featureMaster = (FeatureRunModelBean) event.getComponent().getAttributes().get("feature");
		setShowfeatureDefectPanel(true);
		System.out.println(featureMaster);
		selectedFeatureID = featureMaster.getFeatureSetId();
	}

	@SuppressWarnings("unchecked")
	private void populateDataSetRunData() {
		EntityManager entityManager = getEntitymanagerFromCurrent();

		dataSetRunBeansList = new ArrayList<DataSetRunBean>();
		List<DatasetRun> datasetRunsList = new ArrayList<DatasetRun>();
		List<DatasetMaster> datasetMastersList = new ArrayList<DatasetMaster>();
		List<FeatureRun> featureRuns = new ArrayList<FeatureRun>();

		List<FeatureForDataSetRunBean> featureForDataSetRunBeans = null;
		List<DatasetRunDefect> datasetRunDefectsList = null;
		FeatureForDataSetRunBean bean = null;
		entityManager.getTransaction().begin();

		datasetRunsList = entityManager.createNamedQuery("DatasetRun.findAll").getResultList();
		DataSetRunBean dataSetRunBean = null;
		for (DatasetRun datasetRun : datasetRunsList) {
			dataSetRunBean = new DataSetRunBean();
			datasetMastersList.add(datasetRun.getDatasetmaster());
			featureRuns = entityManager.createQuery("select fr from FeatureRun fr where fr.datasetrunid = :datasetrunid")
					.setParameter("datasetrunid", BigDecimal.valueOf(datasetRun.getDatasetrunid())).getResultList();
			featureForDataSetRunBeans = new ArrayList<FeatureForDataSetRunBean>();
			for (FeatureRun featureRun : featureRuns) {
				datasetRunDefectsList = new ArrayList<DatasetRunDefect>();
				datasetRunDefectsList = entityManager.createQuery("select df from DatasetRunDefect df where df.featurerunid = :featurerunid")
						.setParameter("featurerunid", BigDecimal.valueOf(featureRun.getFeaturerunid())).getResultList();
				FeatureMaster featureMaster = (FeatureMaster) entityManager.createQuery("select fm from FeatureMaster fm where fm.featureid =:featureid")
						.setParameter("featureid", featureRun.getFeaturemasterid().longValue()).getSingleResult();
				bean = new FeatureForDataSetRunBean();
				bean.setDatasetRunDefectsList(datasetRunDefectsList);
				bean.setFeatureMaster(featureMaster);
				bean.setFeaturerunid(featureRun.getFeaturerunid());
				bean.setStatus(featureRun.getStatus());

				featureForDataSetRunBeans.add(bean);
			}
			dataSetRunBean.setDatasetMaster(datasetRun.getDatasetmaster());
			dataSetRunBean.setDatasetRun(datasetRun);
			dataSetRunBean.setFeatureForDataSetRunBeansList(featureForDataSetRunBeans);
			if (dataSetRunBean.getDatasetRun().getRunstatus().equalsIgnoreCase(DATASETRUNFAILED)) {
				dataSetRunBean.setStyleClassForDataSetStatus(DATASETSTATUSCOLORFAILED);
			} else if (dataSetRunBean.getDatasetRun().getRunstatus().equalsIgnoreCase(DATASETRUNPASS)) {
				dataSetRunBean.setStyleClassForDataSetStatus(DATASETSTATUSCOLORPASSED);
			} else {
				dataSetRunBean.setStyleClassForDataSetStatus(DATASETSTATUSCOLORRRDY);
			}
			if (datasetRun.getReadyforrun().equalsIgnoreCase(READYFORRERUNYES)) {
				dataSetRunBean.setReadyForReExecute(true);
			} else {
				dataSetRunBean.setReadyForReExecute(false);
			}
			dataSetRunBeansList.add(dataSetRunBean);
		}
		entityManager.getTransaction().commit();
		entityManager.close();
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

	/**
	 * @return the dataSetRunBeansList
	 */
	public List<DataSetRunBean> getDataSetRunBeansList() {
		return dataSetRunBeansList;
	}

	/**
	 * @param dataSetRunBeansList
	 *            the dataSetRunBeansList to set
	 */
	public void setDataSetRunBeansList(List<DataSetRunBean> dataSetRunBeansList) {
		this.dataSetRunBeansList = dataSetRunBeansList;
	}

	/**
	 * @return the masterRecordFromsuggestion
	 */
	public DatasetMaster getMasterRecordFromsuggestion() {
		return masterRecordFromsuggestion;
	}

	/**
	 * @param masterRecordFromsuggestion
	 *            the masterRecordFromsuggestion to set
	 */
	public void setMasterRecordFromsuggestion(DatasetMaster masterRecordFromsuggestion) {
		this.masterRecordFromsuggestion = masterRecordFromsuggestion;
	}

	/**
	 * @return the selectedDataSetRunBean
	 */
	public DataSetRunBean getSelectedDataSetRunBean() {
		return selectedDataSetRunBean;
	}

	/**
	 * @param selectedDataSetRunBean
	 *            the selectedDataSetRunBean to set
	 */
	public void setSelectedDataSetRunBean(DataSetRunBean selectedDataSetRunBean) {
		this.selectedDataSetRunBean = selectedDataSetRunBean;
	}

}
