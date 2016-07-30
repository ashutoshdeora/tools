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

import com.ibm.utils.defmng.model.Account;
import com.ibm.utils.defmng.model.Feature;

@ManagedBean
@ViewScoped
public class AccountsManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountsManagedBean() {

	}

	private static final String PERSISTENCE_UNIT_NAME = "dbpersistence";
	private List<Account> accounts;
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
	public void showAccountData() {
		try {
			if (loginManagedBean != null) {
				FacesContext context = FacesContext.getCurrentInstance();
				String objectId = context.getExternalContext().getRequestParameterMap().get("selectedAccountId");
				if (objectId != null) {
					accounts = new ArrayList<Account>();
					accounts = retriveSelectedAccountList(objectId);
				} else {
					// currently all records
					// it will change on for user access
					accounts = new ArrayList<Account>();
					accounts = retriveAllAccountList();

				}
				
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			
		}
	}

	@SuppressWarnings("unchecked")
	private List<Account> retriveAllAccountList() {
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
