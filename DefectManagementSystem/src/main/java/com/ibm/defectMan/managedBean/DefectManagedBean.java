package com.ibm.defectMan.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.utils.defmng.model.Defect;

@ManagedBean
@ViewScoped
public class DefectManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DefectManagedBean() {

	}

	private static final String PERSISTENCE_UNIT_NAME = "dbpersistence";
	private List<Defect> defectsList;
	
	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;
	
	@ManagedProperty(value="#{chartView}")
	private ChartView chartView;

	@PostConstruct
	public void populateDefects() {
		try {
			if (loginManagedBean != null) {
				FacesContext context = FacesContext.getCurrentInstance();
				String objectId = context.getExternalContext().getRequestParameterMap().get("selectedDefect");
				if (objectId != null) {
					defectsList = new ArrayList<Defect>();
					defectsList = retriveSpecificDefects(objectId);
					chartView.createBarModels();
					
				} else {
					defectsList = new ArrayList<Defect>();
					defectsList = retriveAllDefects();
					
				}
				
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			
		}
	}

	private List<Defect> retriveAllDefects() {

		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();

		List<Defect> tempList = new ArrayList<Defect>();
		tempList = entityManager.createQuery("select df from Defect df").getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		return tempList;

	}

	private List<Defect> retriveSpecificDefects(String defectId) {

		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		List<Defect> tempList = new ArrayList<Defect>();
		tempList = entityManager.createQuery("select df from Defect df where df.hpqcDefectID=:hpqcDefectID").setParameter("hpqcDefectID", defectId)
				.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return tempList;

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
	 * @return the chartView
	 */
	public ChartView getChartView() {
		return chartView;
	}

	/**
	 * @param chartView the chartView to set
	 */
	public void setChartView(ChartView chartView) {
		this.chartView = chartView;
	}
}
