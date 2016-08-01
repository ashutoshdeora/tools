/**
 * 
 */
package com.ibm.utils.defmng.model;

import java.io.Serializable;

/**
 * @author ibm
 *
 */
public class AccountDataBean implements Serializable {
	
	private String accountName;

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

}
