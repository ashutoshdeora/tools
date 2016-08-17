package com.ibm.managedBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.ibm.entity.DatasetRun;
import com.ibm.entity.DatasetRunDefect;

@ManagedBean
@ViewScoped
public class DefectDataManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<DatasetRunDefect> datasetRunDefectsList;
	private static final String PERSISTENCE_UNIT_NAME = "tmorcl";

	@PostConstruct
	private void populateAllDefects() {

		try {

			FacesContext context = FacesContext.getCurrentInstance();
			String objectId = context.getExternalContext().getRequestParameterMap().get("selectedDefect");
			if (objectId != null) {
				datasetRunDefectsList = retriveAllDefectSetForDefectId(objectId);
			} else {
				datasetRunDefectsList = retriveAllDefectSet();
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private List<DatasetRunDefect> retriveAllDefectSet() {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		List<DatasetRunDefect> tempList = new ArrayList<DatasetRunDefect>();
		tempList = entityManager.createNamedQuery("DatasetRunDefect.findAll").getResultList();
		List<DatasetRunDefect> temp = new ArrayList<DatasetRunDefect>();
		List<DatasetRun> runList = null;
		for (DatasetRunDefect defect : tempList) {
			runList = new ArrayList<DatasetRun>();
			runList = entityManager.createQuery("select dr from DatasetRun dr where dr.datasetrunid =:datasetrunid")
					.setParameter("datasetrunid", defect.getId().getDatasetrunid()).getResultList();

			defect.setDatasetRuns(runList);
			temp.add(defect);
		}

		entityManager.getTransaction().commit();
		entityManager.close();
		return temp;
	}

	@SuppressWarnings("unchecked")
	private List<DatasetRunDefect> retriveAllDefectSetForDefectId(String objectId) {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		List<DatasetRunDefect> tempList = new ArrayList<DatasetRunDefect>();
		tempList = entityManager.createQuery("select dfr from DatasetRunDefect dfr where dfr.hpqcdefectid =:hpqcdefectid")
				.setParameter("hpqcdefectid", BigDecimal.valueOf(Long.valueOf(objectId))).getResultList();
		List<DatasetRunDefect> temp = new ArrayList<DatasetRunDefect>();
		List<DatasetRun> runList = null;
		for (DatasetRunDefect defect : tempList) {
			runList = new ArrayList<DatasetRun>();
			runList = entityManager.createQuery("select dr from DatasetRun dr where dr.datasetrunid =:datasetrunid")
					.setParameter("datasetrunid", defect.getId().getDatasetrunid()).getResultList();

			defect.setDatasetRuns(runList);
			temp.add(defect);
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		return temp;
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
	 * @return the datasetRunDefectsList
	 */
	public List<DatasetRunDefect> getDatasetRunDefectsList() {
		return datasetRunDefectsList;
	}

	/**
	 * @param datasetRunDefectsList
	 *            the datasetRunDefectsList to set
	 */
	public void setDatasetRunDefectsList(List<DatasetRunDefect> datasetRunDefectsList) {
		this.datasetRunDefectsList = datasetRunDefectsList;
	}

}