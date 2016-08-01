package com.ibm.asset.ILCTimesheet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.ibm.utils.defmng.model.Account;
import com.ibm.utils.defmng.model.DataSet;
import com.ibm.utils.defmng.model.Defect;
import com.ibm.utils.defmng.model.Feature;
import com.ibm.utils.defmng.model.MasterData;
import com.ibm.utils.defmng.model.RecordCreation;
import com.ibm.utils.defmng.model.Route;
import com.ibm.utils.defmng.model.Sites;
import com.ibm.utils.defmng.model.TestExecution;

@ManagedBean
@ViewScoped
public class FileUploadView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String PERSISTENCE_UNIT_NAME = "dbpersistence";

	public FileUploadView() {

	}

	private UploadedFile file;
	// private Defect defect;
	private List<Defect> defectsList;
	private List<Feature> features;
	// private Feature feature;
	private List<DataSet> dataSets;
	// private DataSet dataSet;
	// private Account account;
	private List<Account> accounts;

	private List<Sites> sitesList;

	private List<Route> routes;

	private List<TestExecution> testExecutionsList;

	private EntityManager getEntitymanagerFromCurrent() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		return em;
	}

	/**
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void fileUploadListener(FileUploadEvent e) {
		try {
			// Get uploaded file from the FileUploadEvent
			this.file = e.getFile();
			// Print out the information of the file
			System.out.println("Uploaded File Name Is :: " + file.getFileName() + " :: Uploaded File Size :: " + file.getSize());

			// read full file all at once and prepare all data and insert
			List<String[]> dataFromExcel;
			int sheetNumberToRead = 0;

			// for feature data reading and insert

			sheetNumberToRead = 0;
			dataFromExcel = new ArrayList<String[]>();
			dataFromExcel = readSheet(file.getInputstream(), sheetNumberToRead);
			populateFeatureBeanList(dataFromExcel);
			insertfeatureData(features);

			// for account data reading and insert 
			sheetNumberToRead = 1;
			dataFromExcel = new ArrayList<String[]>();
			dataFromExcel = readSheet(file.getInputstream(), sheetNumberToRead);
			populateCustomerDataList(dataFromExcel);
			insertCustomerDataAndRelation(accounts);

			sheetNumberToRead = 2;
			dataFromExcel = new ArrayList<String[]>();
			dataFromExcel = readSheet(file.getInputstream(), sheetNumberToRead);
			populateDataSetList(dataFromExcel);
			insertDataSet(dataSets);

			sheetNumberToRead = 3;
			dataFromExcel = new ArrayList<String[]>();
			dataFromExcel = readSheet(file.getInputstream(), sheetNumberToRead);
			populateSiteList(dataFromExcel);
			insertSiteDataAndRelation(sitesList);

			sheetNumberToRead = 4;
			dataFromExcel = new ArrayList<String[]>();
			dataFromExcel = readSheet(file.getInputstream(), sheetNumberToRead);
			populateRouteList(dataFromExcel);
			insertRouteList(routes);

			sheetNumberToRead = 6;
			dataFromExcel = new ArrayList<String[]>();
			dataFromExcel = readSheet(file.getInputstream(), sheetNumberToRead);
			populateTestExecution(dataFromExcel);
			insertTestExecutionData(testExecutionsList);
			
			sheetNumberToRead = 7;
			dataFromExcel = new ArrayList<String[]>();
			dataFromExcel = readSheet(file.getInputstream(), sheetNumberToRead);
			populateDefectBean(dataFromExcel);
			insertDefectdataInDB(defectsList);
			
			

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Step1
	 * 
	 * @param inputStream
	 * @param sheetNumberToRead
	 */
	private List<String[]> readSheet(InputStream inputStream, int sheetNumberToRead) {
		List<String[]> dataArray = null;
		try {

			// Get the workbook instance for XLS file
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			// HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

			// Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(sheetNumberToRead);

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			System.out.println(sheet.getPhysicalNumberOfRows() + "---- no of rows");
			int numberofRows = sheet.getPhysicalNumberOfRows();

			System.out.println(sheet.getRow(0).getLastCellNum() + "----- no of columns");

			int numberofColumns = sheet.getRow(0).getLastCellNum();

			// prepare header
			String[] headers = new String[numberofColumns];
			Iterator<Cell> cellIterators = sheet.getRow(0).cellIterator();
			List<String> cellColumns = new ArrayList<String>();
			while (cellIterators.hasNext()) {
				Cell cell = cellIterators.next();

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_BOOLEAN:
					System.out.print(cell.getBooleanCellValue() + "\t\t");
					break;
				case Cell.CELL_TYPE_NUMERIC:
					cellColumns.add(String.valueOf(cell.getNumericCellValue()));
					break;
				case Cell.CELL_TYPE_STRING:
					cellColumns.add(cell.getStringCellValue());
					break;
				}
			}
			headers = cellColumns.toArray(new String[numberofColumns]);
			System.out.println(headers[0].toString());
			// now put body in list of arrays.
			dataArray = new ArrayList<String[]>();
			for (int i = 1; i < numberofRows; i++) {
				Iterator<Cell> cellIteratorsforData = sheet.getRow(i).cellIterator();
				List<String> celldataColumns = new ArrayList<String>();
				String[] data = new String[numberofColumns];
				while (cellIteratorsforData.hasNext()) {
					Cell cell = cellIteratorsforData.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						// System.out.print(cell.getBooleanCellValue() +
						// "\t\t");
						break;
					case Cell.CELL_TYPE_NUMERIC:
						int value = (int) cell.getNumericCellValue();
						celldataColumns.add(String.valueOf(value));
						break;
					case Cell.CELL_TYPE_STRING:
						celldataColumns.add(cell.getStringCellValue());
						System.out.print(cell.getStringCellValue() + "\t\t");
						;
						break;
					case Cell.CELL_TYPE_BLANK:
						 celldataColumns.add(cell.getStringCellValue());
					}
				}
				System.out.println();
				data = celldataColumns.toArray(new String[numberofColumns]);
				// System.out.println(data);
				dataArray.add(data);
			}

			inputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dataArray;
	}

	private void populateDefectBean(List<String[]> dataFromExcel) {
		defectsList = new ArrayList<Defect>();
		for (int i = 0; i < dataFromExcel.size(); i++) {
			// read one row each time
			String[] columns = dataFromExcel.get(i);
			Defect defect = new Defect();
			defect.setHpqcDefectID(columns[0]);
			defect.setAssignedTo(columns[1]);
			defect.setClosingDate(columns[2]);
			defect.setSummary(columns[3]);
			defect.setStatus(columns[4]);
			defect.setProject(columns[5]);
			defect.setPriority(columns[6]);
			defect.setSeverity(columns[7]);
			defect.setDetectedinCycle(columns[8]);
			defect.setDetectedBy(columns[9]);
			defect.setFunctionality(columns[10]);
			defect.setRootCause(columns[11]);
			defect.setDetectedonDate(columns[12]);
			defect.setStatusChangeComments(columns[13]);
			defect.setDescription(columns[14]);
			defect.setModified(columns[15]);
			defect.setTargetCycle(columns[16]);
			defect.setRoute(columns[17]);
			defect.setActualFixTime(columns[18]);
			defect.setClosedinVersion(columns[19]);
			defect.setDetectedinRelease(columns[20]);
			defect.setDetectedinVersion(columns[21]);
			defect.setEstimatedFixTime(columns[22]);
			defect.setPlannedClosingVersion(columns[23]);
			defect.setReproducible(columns[24]);
			defect.setReviewStatus(columns[25]);
			defect.setSubject(columns[26]);
			defect.setTargetRelease(columns[27]);
			defect.setTestDay(columns[28]);
			defect.setTransactionWeek(columns[29]);
			defect.setFeatureID(columns[30]);
			defect.setAccountId(columns[31]);
			
			defectsList.add(defect);
		}
		System.out.println(defectsList.size());
	}

	private void populateFeatureBeanList(List<String[]> dataFromExcel) {
		features = new ArrayList<Feature>();
		for (int i = 0; i < dataFromExcel.size(); i++) {
			// read one row each time
			String[] columns = dataFromExcel.get(i);
			Feature feature = new Feature();
			feature.setFeatureNumber(columns[0]);
			feature.setFeatureGrouping((columns[1] != null) ? columns[1] : "No Data");
			feature.setFeatureTestScriptName((columns[2]!= null) ? columns[2] : "No Data");
			feature.setFeatureTestScriptComments((columns[3]!= null) ? columns[3] : "No Data");
			feature.setFeatureTestExecutionPhase((columns[4]!= null) ? columns[4] : "No Data");
			feature.setFeatureOwner((columns[5]!= null) ? columns[5] : "No Data");
			feature.setFeatureDataSetCatagoery((columns[6]!= null) ? columns[6] : "No Data");
			feature.setFeatureRollOut((columns[7]!= null) ? columns[7] : "No Data");
			feature.setFeatureStatus((columns[8]!= null) ? columns[8] : "No Data");
			feature.setFeatureTestExecution((columns[9]!= null) ? columns[9] : "No Data");
			feature.setOwner((columns[10]!= null) ? columns[10] : "No Data");
			feature.setBa((columns[11]!= null) ? columns[11] : "No Data");
			
			
			feature.setRecordCreation(createRecord());
			features.add(feature);
		}
		System.out.println(features.size());
	}

	private void populateDataSetList(List<String[]> dataFromExcel) {
		dataSets = new ArrayList<DataSet>();
		for (int i = 0; i < dataFromExcel.size(); i++) {
			// read one row each time
			String[] columns = dataFromExcel.get(i);
			if (columns[1] != null && columns[1].length() > 0) {
				DataSet dataSet = new DataSet();
				dataSet.setDataSetLocation(columns[1]);
				dataSet.setFeatureID(columns[0]);
				dataSet.setRecordCreation(createRecord());
				dataSets.add(dataSet);
			}
		}

		System.out.println(dataSets.size());
	}

	private void populateCustomerDataList(List<String[]> dataFromExcel) {
		accounts = new ArrayList<Account>();
		for (int i = 0; i < dataFromExcel.size(); i++) {
			String[] columns = dataFromExcel.get(i);
			if (columns[1] != null && columns[1].length() > 0) {
				Account account = new Account();
				account.setAccountName(columns[1]);
				account.setFeatureID(columns[0]);
				accounts.add(account);
			}
		}

	}

	private void populateSiteList(List<String[]> dataFromExcel) {
		sitesList = new ArrayList<Sites>();
		for (int i = 0; i < dataFromExcel.size(); i++) {
			String[] columns = dataFromExcel.get(i);
			if (columns[1] != null && columns[1].length() > 0) {
				Sites sites = new Sites();
				sites.setFeatureID(columns[0]);
				sites.setSiteName(columns[1]);
				sitesList.add(sites);
			}
		}

	}

	private void populateRouteList(List<String[]> dataFromExcel) {
		routes = new ArrayList<Route>();
		for (int i = 0; i < dataFromExcel.size(); i++) {
			String[] columns = dataFromExcel.get(i);
			if (columns[1] != null && columns[1].length() > 0) {
				Route route = new Route();
				route.setFeatureID(columns[0]);
				route.setRouteName(columns[1]);
				routes.add(route);
			}
		}

	}

	private void populateTestExecution(List<String[]> dataFromExcel) {
		testExecutionsList = new ArrayList<TestExecution>();
		for (int i = 0; i < dataFromExcel.size(); i++) {
			String[] columns = dataFromExcel.get(i);
			TestExecution execution = new TestExecution();
			System.out.println(columns[0]);
			execution.setFeatureID(columns[0]);
			execution.setTestGroup((columns[1] != null) ? columns[1] : "No Data");
			execution.setTestScriptName((columns[2] != null) ? columns[2] : "No Data");
			execution.setTestScriptComments((columns[3] != null) ? columns[3] : "No Data");
			execution.setTestExecutionPhase((columns[4] != null) ? columns[4] : "No Data");
			execution.setTestOwner((columns[5] != null) ? columns[5] : "No Data");
			execution.setRolloutStatus((columns[6] != null) ? columns[6] : "No Data");
			execution.setTotalTestScripts((columns[1] != null) ? Integer.valueOf(columns[7]) : 0);
			execution.setTestExecutionStatus((columns[8] != null) ? columns[8] : "No Data");
			execution.setTestStatus((columns[9] != null) ? columns[9] : "No Data");
			execution.setDataset(columns[11]);
			execution.setAccountName(columns[13]);
			execution.setSiteName(columns[15]);
			execution.setRouteName(columns[16]);
			testExecutionsList.add(execution);
		}

	}

	private RecordCreation createRecord() {
		RecordCreation creation = new RecordCreation();
		creation.setCreatedBy("Ashutosh");
		creation.setUpdatedBy("Ashutosh");
		creation.setCreationDate(new Date());
		creation.setUpdationDate(new Date());
		return creation;
	}

	private RecordCreation updateRecord() {
		RecordCreation creation = new RecordCreation();
		creation.setUpdatedBy("Ashutosh");
		creation.setUpdationDate(new Date());
		return creation;
	}

	

	/**
	 * 
	 * @param featuresList
	 */
	private void insertfeatureData(List<Feature> featuresList) {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		for (Feature feature : featuresList) {
			
			if (feature.getOwner() != null && feature.getOwner().trim().length() > 0) {

			} else {
				feature.setOwner("NA");
			}

			if (feature.getBa() != null && feature.getBa().trim().length() > 0) {

			} else {
				feature.setBa("NA");
			}
			@SuppressWarnings("unchecked")
			List<Feature> tempList = entityManager.createQuery("select fe from Feature fe where fe.featureNumber=:featureNumber ")
					.setParameter("featureNumber", feature.getFeatureNumber()).getResultList();
			if (tempList != null && tempList.size() > 0) {
				Feature featureMerge = tempList.get(0);
				featureMerge.setFeatureID(feature.getFeatureID());
				featureMerge.setBa(feature.getBa());
				featureMerge.setFeatureName(feature.getFeatureName());
				featureMerge.setRecordCreation(updateRecord());
				entityManager.merge(featureMerge);
			} else {
				entityManager.persist(feature);
			}
		}
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@SuppressWarnings("unchecked")
	private void insertDataSet(List<DataSet> dataSetsList) {

		Set<String> uniqueData = new HashSet<String>();
		for (DataSet dataSet : dataSetsList) {
			uniqueData.add(dataSet.getDataSetLocation());
		}

		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();

		for (String data : uniqueData) {
			List<String> featureIds = new ArrayList<String>();
			for (DataSet tempdataSet : dataSetsList) {
				if (data.equalsIgnoreCase(tempdataSet.getDataSetLocation())) {
					featureIds.add(tempdataSet.getFeatureID());
				}
			}
			// now query from feature table to get list of features ids
			List<Feature> tempfeatureListForDataSet = new ArrayList<Feature>();
			tempfeatureListForDataSet = entityManager.createQuery("select fe from Feature fe where fe.featureNumber IN :featureNumber ")
					.setParameter("featureNumber", featureIds).getResultList();
			if (tempfeatureListForDataSet != null && tempfeatureListForDataSet.size() > 0) {
				DataSet dataSetData = new DataSet();
				dataSetData.setDataSetLocation(data);
				dataSetData.setDataSetName("No Data");
				dataSetData.setFeatures(tempfeatureListForDataSet);
				dataSetData.setRecordCreation(createRecord());
				entityManager.persist(dataSetData);

			}
		}
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	// steps for data insert

	// 1.extract second column from excel sheet
	// 2.create a unique list from that data .
	// 3.insert that data
	// 4.then insert many to many relation with feature of that data.
	@SuppressWarnings("unchecked")
	private void insertCustomerDataAndRelation(List<Account> accountsList) {

		Set<String> uniqueData = new HashSet<String>();
		for (Account accountData : accountsList) {
			uniqueData.add(accountData.getAccountName());
		}

		// insert account data in accounts table
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();

		for (String data : uniqueData) {
			List<String> featureIds = new ArrayList<String>();
			for (Account tempAccount : accountsList) {
				if (data.equalsIgnoreCase(tempAccount.getAccountName())) {
					featureIds.add(tempAccount.getFeatureID());
				}
			}
			// now query from feature table to get list of features ids
			List<Feature> tempfeatureListForDataSet = new ArrayList<Feature>();
			tempfeatureListForDataSet = entityManager.createQuery("select fe from Feature fe where fe.featureNumber IN :featureNumber ")
					.setParameter("featureNumber", featureIds).getResultList();
			if (tempfeatureListForDataSet != null && tempfeatureListForDataSet.size() > 0) {
				Account accountInsertData = new Account();
				accountInsertData.setAccountName(data);
				accountInsertData.setAccountNumber("No Data");
				accountInsertData.setRecordCreation(createRecord());
				accountInsertData.setFeatures(tempfeatureListForDataSet);
				entityManager.persist(accountInsertData);
			}
		}

		entityManager.getTransaction().commit();
		entityManager.close();

	}

	@SuppressWarnings("unchecked")
	private void insertSiteDataAndRelation(List<Sites> sitesDataList) {

		Set<String> uniqueData = new HashSet<String>();
		for (Sites sites : sitesDataList) {
			uniqueData.add(sites.getSiteName());
		}

		// insert account data in sites table
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();

		for (String data : uniqueData) {
			List<String> featureIds = new ArrayList<String>();
			for (Sites sites : sitesDataList) {
				if (data.equalsIgnoreCase(sites.getSiteName())) {
					featureIds.add(sites.getFeatureID());
				}
			}

			// now query from feature table to get list of features ids
			List<Feature> tempfeatureListForDataSet = new ArrayList<Feature>();
			tempfeatureListForDataSet = entityManager.createQuery("select fe from Feature fe where fe.featureNumber IN :featureNumber ")
					.setParameter("featureNumber", featureIds).getResultList();
			if (tempfeatureListForDataSet != null && tempfeatureListForDataSet.size() > 0) {
				Sites tempSites = new Sites();
				tempSites.setSiteName(data);
				tempSites.setRecordCreation(createRecord());
				tempSites.setFeatures(tempfeatureListForDataSet);
				entityManager.persist(tempSites);
			}
		}
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@SuppressWarnings("unchecked")
	private void insertRouteList(List<Route> routesDataList) {
		Set<String> uniqueData = new HashSet<String>();
		for (Route route : routesDataList) {
			uniqueData.add(route.getRouteName());
		}
		// insert account data in sites table
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();

		for (String data : uniqueData) {
			List<String> featureIds = new ArrayList<String>();
			for (Route route : routesDataList) {
				if (data.equalsIgnoreCase(route.getRouteName())) {
					featureIds.add(route.getFeatureID());
				}
			}
			// now query from feature table to get list of features ids
			List<Feature> tempfeatureListForDataSet = new ArrayList<Feature>();
			tempfeatureListForDataSet = entityManager.createQuery("select fe from Feature fe where fe.featureNumber IN :featureNumber ")
					.setParameter("featureNumber", featureIds).getResultList();
			if (tempfeatureListForDataSet != null && tempfeatureListForDataSet.size() > 0) {
				Route tempRoute = new Route();
				tempRoute.setRouteName(data);
				tempRoute.setFeatures(tempfeatureListForDataSet);
				tempRoute.setRecordCreation(createRecord());
				entityManager.persist(tempRoute);
			}
		}
		entityManager.getTransaction().commit();
		entityManager.close();

	}
	
	// correction required for merge
		@SuppressWarnings("unchecked")
		private void insertDefectdataInDB(List<Defect> defectsListForDB) {
			Set<String> uniqueData = new HashSet<String>();
			for (Defect defect : defectsListForDB) {
				uniqueData.add(defect.getHpqcDefectID());
			}
			// insert account data in sites table
			EntityManager entityManager = getEntitymanagerFromCurrent();
			entityManager.getTransaction().begin();

			for (String data : uniqueData) {
				List<String> featureIds = new ArrayList<String>();
				List<String> accountNames = new ArrayList<String>();
				Defect tempDefectData = new Defect();
				for (Defect defect : defectsListForDB) {
					if (data.equalsIgnoreCase(defect.getHpqcDefectID())) {
						featureIds.add(defect.getFeatureID());
						accountNames.add(defect.getAccountId());
						tempDefectData = defect;
					}
				}
				// now query from feature table to get list of features ids
				List<Feature> tempfeatureListForDataSet = new ArrayList<Feature>();
				tempfeatureListForDataSet = entityManager.createQuery("select fe from Feature fe where fe.featureNumber IN :featureNumber ")
						.setParameter("featureNumber", featureIds).getResultList();
				List<Account> tempAccountListForDB = new ArrayList<Account>();
				tempAccountListForDB = entityManager.createQuery("select ac from Account ac where ac.accountName IN :accountNames ")
										.setParameter("accountNames", accountNames).getResultList();
				
				if ((tempfeatureListForDataSet != null && tempfeatureListForDataSet.size() > 0) && (tempAccountListForDB!=null && tempAccountListForDB.size()>0)) {
					
					tempDefectData.setFeatures(tempfeatureListForDataSet);
					tempDefectData.setAccountsList(tempAccountListForDB);
					tempDefectData.setRecordCreation(createRecord());
					tempDefectData.setHpqcDefectID(data);
					entityManager.persist(tempDefectData);
				}
			}
			entityManager.getTransaction().commit();
			entityManager.close();
		}

	@SuppressWarnings("unchecked")
	private void insertTestExecutionData(List<TestExecution> testExecutionsDataList) {
		// insert account data in sites table
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		for (TestExecution testExecution : testExecutionsDataList) {
			Feature feature = (Feature) entityManager.createQuery("select fe from Feature fe where fe.featureNumber = :featureNumber ")
					.setParameter("featureNumber", testExecution.getFeatureID()).getSingleResult();
			Account account = (Account) entityManager.createQuery("select ac from Account ac where ac.accountName=:accountName")
					.setParameter("accountName", testExecution.getAccountName()).getSingleResult();
			Sites site = (Sites) entityManager.createQuery("select ss from Sites ss where ss.siteName=:siteName")
					.setParameter("siteName", testExecution.getSiteName()).getSingleResult();
			Route route = (Route) entityManager.createQuery("select rs from Route rs where rs.routeName=:routeName")
					.setParameter("routeName", testExecution.getRouteName()).getSingleResult();
			List<DataSet> dataSets =  entityManager.createQuery("select ds from DataSet ds where ds.dataSetLocation=:dataSetLocation")
					.setParameter("dataSetLocation", testExecution.getDataset()).getResultList();
			TestExecution execution = new TestExecution();
			execution = testExecution;
			execution.setFeatureRun(feature);
			if(dataSets!=null && dataSets.size()>0){
				execution.setDataSetRun(dataSets.get(0));
			}
			execution.setAccountRun(account);
			execution.setSiteRun(site);
			execution.setRouteRun(route);
			execution.setRecordCreation(createRecord());
			execution.setActiveData("ac");
			entityManager.persist(execution);
			

		}
		entityManager.getTransaction().commit();
		entityManager.close();

	}
	
	

	private Map<String, String> createUniqueMap(Map<String, String> tempMap) {
		Map<String, String> map = new HashMap<String, String>();
		map = tempMap;
		map.remove(null);
		System.out.println("Initial Map : " + map);
		for (String s : new ConcurrentHashMap<String, String>(map).keySet()) {
			String value = map.get(s);
			for (Map.Entry<String, String> ss : new ConcurrentHashMap<String, String>(map).entrySet()) {
				if (s != ss.getKey() && value == ss.getValue()) {
					map.remove(ss.getKey());
				}
			}
		}
		System.out.println("Final Map : " + map);
		return map;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	/**
	 * @return the defectsList
	 */
	public List<Defect> getDefectsList() {
		return defectsList;
	}

	/**
	 * @param defectsList
	 *            the defectsList to set
	 */
	public void setDefectsList(List<Defect> defectsList) {
		this.defectsList = defectsList;
	}

	/**
	 * @return the features
	 */
	public List<Feature> getFeatures() {
		return features;
	}

	/**
	 * @param features
	 *            the features to set
	 */
	public void setFeatures(List<Feature> features) {
		this.features = features;
	}

	/**
	 * @return the dataSets
	 */
	public List<DataSet> getDataSets() {
		return dataSets;
	}

	/**
	 * @param dataSets
	 *            the dataSets to set
	 */
	public void setDataSets(List<DataSet> dataSets) {
		this.dataSets = dataSets;
	}

	/**
	 * @return the accounts
	 */
	public List<Account> getAccounts() {
		return accounts;
	}

	/**
	 * @param accounts
	 *            the accounts to set
	 */
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	/**
	 * @return the sites
	 */
	public List<Sites> getSitesList() {
		return sitesList;
	}

	/**
	 * @param sites
	 *            the sites to set
	 */
	public void setSitesList(List<Sites> sitesList) {
		this.sitesList = sitesList;
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
	 * @return the routes
	 */
	public List<Route> getRoutes() {
		return routes;
	}

	/**
	 * @param routes
	 *            the routes to set
	 */
	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
}
