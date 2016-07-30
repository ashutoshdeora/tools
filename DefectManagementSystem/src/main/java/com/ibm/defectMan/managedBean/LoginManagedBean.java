package com.ibm.defectMan.managedBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.ibm.asset.defectMang.persist.data.RetriveUserDetails;
import com.ibm.utils.defmng.model.UserDetails;

@SessionScoped
@ManagedBean(name = "loginManagedBean")
public class LoginManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginManagedBean() {

	}

	private String userName;
	private String password;
	private String userRole;

	public String checkuserloginWithRole() {
		try {
			List<UserDetails> userDetails = new RetriveUserDetails().fetchUserData(userName);
			if (userDetails != null && userDetails.size() > 0) {
				userName = userDetails.get(0).getUserName();
				userRole = userDetails.get(0).getAccesslevel();
				return "loggedIn";
			} else {
				// no record found
				// set messages
			}

		} catch (Exception exception) {
			exception.printStackTrace();

		}
		return null;
	}

	public void logout() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.invalidateSession();
		ec.redirect(ec.getRequestContextPath() + "/pages/login.xhtml");
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole
	 *            the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
}
