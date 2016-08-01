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

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.ibm.utils.defmng.model.Account;
import com.ibm.utils.defmng.model.Defect;
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
	private TreeNode root;

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
					createTree();
				}
				
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			
		}
	}

	
	private void createTree(){
		root = new DefaultTreeNode("Account", null);
		
		for(Account account:accounts){
			TreeNode accountTreeNode = new DefaultTreeNode(account.getAccountName(),root);
			for(Feature feature :account.getFeatures()){
				TreeNode featureNode = new DefaultTreeNode(feature.getFeatureNumber(),accountTreeNode);
				for(Defect defect:feature.getDefectsList()){
					TreeNode defectNode = new DefaultTreeNode(defect.getHpqcDefectID(),featureNode);
					featureNode.getChildren().add(defectNode);
				}
				accountTreeNode.getChildren().add(featureNode);
			}
			root.getChildren().add(accountTreeNode);
		}
		
        TreeNode node0 = new DefaultTreeNode("Node 0", root);
        TreeNode node1 = new DefaultTreeNode("Node 1", root);
         
        TreeNode node00 = new DefaultTreeNode("Node 0.0", node0);
        TreeNode node01 = new DefaultTreeNode("Node 0.1", node0);
         
        TreeNode node10 = new DefaultTreeNode("Node 1.0", node1);
         
        node1.getChildren().add(new DefaultTreeNode("Node 1.1"));
        node00.getChildren().add(new DefaultTreeNode("Node 0.0.0"));
        node00.getChildren().add(new DefaultTreeNode("Node 0.0.1"));
        node01.getChildren().add(new DefaultTreeNode("Node 0.1.0"));
        node10.getChildren().add(new DefaultTreeNode("Node 1.0.0"));
        root.getChildren().add(new DefaultTreeNode("Node 2"));
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

	/**
	 * @return the root
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(TreeNode root) {
		this.root = root;
	}

}
