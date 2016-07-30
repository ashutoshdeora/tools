/**
 * 
 */
package com.ibm.defectMan.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.gson.Gson;
import com.ibm.utils.defmng.model.Feature;

/**
 * @author ibm
 *
 */

@ManagedBean
@ViewScoped
public class FeatureDataManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;
	private int number2 = 0;
	private int number3 = 60;
	private String[] rolloutOption;

	public FeatureDataManagedBean() {

	}

	private static final String PERSISTENCE_UNIT_NAME = "dbpersistence";

	private List<Feature> featurDataList;

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
	public void populateFeatureAllList() {
		rolloutOption = new String[2];
		rolloutOption[0] = "O";
		rolloutOption[1] = "Y";
		try {
			if (loginManagedBean != null) {
				FacesContext context = FacesContext.getCurrentInstance();
				String objectId = context.getExternalContext().getRequestParameterMap().get("selectedFeatureId");
				if (objectId != null) {
					retriveFeatureListFromDBForFeatureId(objectId);
				} else {
					retriveFeatureListFromDB();
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	String toJSON(List<Feature> featurDataList2) {
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		for (Feature d : featurDataList2) {
			sb.append(gson.toJson(d));
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	private List<Feature> retriveFeatureListFromDB() {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		featurDataList = new ArrayList<Feature>();
		featurDataList = entityManager.createQuery("select fe from Feature fe").getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();

		return featurDataList;

	}

	@SuppressWarnings("unchecked")
	private List<Feature> retriveFeatureListFromDBForFeatureId(String featureID) {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		featurDataList = new ArrayList<Feature>();
		featurDataList = entityManager.createQuery("select fe from Feature fe where fe.featureNumber=:featureNumber")
				.setParameter("featureNumber", featureID).getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();

		return featurDataList;

	}

	/**
	 * @return the featurDataList
	 */
	public List<Feature> getFeaturDataList() {
		return featurDataList;
	}

	/**
	 * @param featurDataList
	 *            the featurDataList to set
	 */
	public void setFeaturDataList(List<Feature> featurDataList) {
		this.featurDataList = featurDataList;
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

	public List<String> getRolloutOption() {
		return Arrays.asList(rolloutOption);
	}

	/**
	 * @return the number2
	 */
	public int getNumber2() {
		return number2;
	}

	/**
	 * @param number2
	 *            the number2 to set
	 */
	public void setNumber2(int number2) {
		this.number2 = number2;
	}

	/**
	 * @return the number3
	 */
	public int getNumber3() {
		return number3;
	}

	/**
	 * @param number3
	 *            the number3 to set
	 */
	public void setNumber3(int number3) {
		this.number3 = number3;
	}

}
