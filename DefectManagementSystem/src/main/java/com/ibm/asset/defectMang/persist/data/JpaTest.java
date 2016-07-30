package com.ibm.asset.defectMang.persist.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaTest {

	private static final String PERSISTENCE_UNIT_NAME = "dbpersistence";
	private EntityManagerFactory factory;

	public static void main(String[] args) {
		new JpaTest().createPersistence();
	}

	private void createPersistence() {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	private EntityManager getEntitymanagerFromCurrent() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		return em;
	}


}
