/**
 * 
 */
package com.gcp.bq.view.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author Shaikh Ahmed Reza
 *
 */
public class BqViewRequest {

	@NotEmpty(message = "Account cannot be Null or Empty")
	String account;

	@NotEmpty(message = "Email cannot be Null or Empty")
	@Email(message = "Email should be valid")
	String email;

	@NotEmpty(message = "Source cannot be Null or Empty")
	String source;

	@NotEmpty(message = "Action cannot be Null or Empty")
	String action;

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @param account
	 * @param email
	 * @param source
	 * @param action
	 */
	public BqViewRequest(@NotEmpty(message = "Account cannot be Null or Empty") String account,
			@NotEmpty(message = "Email cannot be Null or Empty") @Email(message = "Email should be valid") String email,
			@NotEmpty(message = "Source cannot be Null or Empty") String source,
			@NotEmpty(message = "Action cannot be Null or Empty") String action) {
		super();
		this.account = account;
		this.email = email;
		this.source = source;
		this.action = action;
	}

	/**
	 * 
	 */
	public BqViewRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

}
