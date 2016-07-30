/**
 * 
 */
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

import com.ibm.utils.defmng.model.DataSet;
import com.sun.faces.facelets.tag.jsf.core.SetPropertyActionListenerHandler;

/**
 * @author ibm
 *
 */

@ManagedBean
@ViewScoped
public class DataSetManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataSetManagedBean() {

	}

	private static final String PERSISTENCE_UNIT_NAME = "dbpersistence";
	private List<DataSet> dataSetsList;
	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;

	/**
	 * 
	 * @return entityManager
	 */
	private EntityManager getEntitymanagerFromCurrent() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		return em;
	}

	@PostConstruct
	public void populateDataSet() {
		try {
			if (loginManagedBean != null) {
				FacesContext context = FacesContext.getCurrentInstance();
				String objectId = context.getExternalContext().getRequestParameterMap().get("selectedDataSetLocation");
				if (objectId != null) {
					dataSetsList = new ArrayList<DataSet>();
					dataSetsList = retriveSelectedData(objectId);
				} else {
					dataSetsList = new ArrayList<DataSet>();
					dataSetsList = retriveAllData();
				}
				
			} 
		} catch (Exception exception) {
			exception.printStackTrace();
			
		}

	}

	private List<DataSet> retriveAllData() {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		List<DataSet> tempList = entityManager.createQuery("select ds from DataSet ds").getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();

		return tempList;
	}

	private List<DataSet> retriveSelectedData(String dataSetLocation) {

		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		List<DataSet> tempList = entityManager.createQuery("select ds from DataSet ds where ds.dataSetLocation=:dataSetLocation")
				.setParameter("dataSetLocation", dataSetLocation).getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();

		return tempList;

	}

	/**
	 * @return the dataSetsList
	 */
	public List<DataSet> getDataSetsList() {
		return dataSetsList;
	}

	/**
	 * @param dataSetsList
	 *            the dataSetsList to set
	 */
	public void setDataSetsList(List<DataSet> dataSetsList) {
		this.dataSetsList = dataSetsList;
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

}
