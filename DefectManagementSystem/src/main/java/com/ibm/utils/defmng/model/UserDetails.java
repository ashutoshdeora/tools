package com.ibm.utils.defmng.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: UserDetails
 *
 */
@Entity
@Table(name="userdetails")
public class UserDetails implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public UserDetails() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private int id;
	
	@Column
	private String userName;
	
	@Column
	private String password;
	
	@Column
	private String empId;
	
	@Column
	private String accesslevel;
	
	@Embedded
	RecordCreation recordCreation;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
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
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the empId
	 */
	public String getEmpId() {
		return empId;
	}

	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(String empId) {
		this.empId = empId;
	}

	/**
	 * @return the recordCreation
	 */
	public RecordCreation getRecordCreation() {
		return recordCreation;
	}

	/**
	 * @param recordCreation the recordCreation to set
	 */
	public void setRecordCreation(RecordCreation recordCreation) {
		this.recordCreation = recordCreation;
	}

	/**
	 * @return the accesslevel
	 */
	public String getAccesslevel() {
		return accesslevel;
	}

	/**
	 * @param accesslevel the accesslevel to set
	 */
	public void setAccesslevel(String accesslevel) {
		this.accesslevel = accesslevel;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", userName=" + userName + ", password=" + password + ", empId=" + empId + ", accesslevel=" + accesslevel
				+ ", recordCreation=" + recordCreation + "]";
	}
	
	
	
}
