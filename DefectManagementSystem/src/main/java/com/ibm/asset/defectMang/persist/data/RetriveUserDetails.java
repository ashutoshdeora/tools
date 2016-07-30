package com.ibm.asset.defectMang.persist.data;

import java.util.List;

import javax.persistence.EntityManager;

import com.ibm.utils.defmng.model.UserDetails;

public class RetriveUserDetails {
	
	private EntityManager entityManager = new PersistenceDetails().getEntitymanagerFromCurrent();
	
	
	public List<UserDetails> fetchUserData(String username){
		 return entityManager.createQuery("select un from UserDetails un where un.userName=:username")
		.setParameter("username", username).getResultList();
	}

}
