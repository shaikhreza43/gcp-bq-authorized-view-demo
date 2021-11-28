/**
 * 
 */
package com.gcp.bq.view.exception;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * @author Shaikh Ahmed Reza
 *
 */
public class ErrorDetails {

	private Timestamp timestamp;
	private String code;
	private HttpStatus status;
	private List errors;

	/**
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	/**
	 * @return the errors
	 */
	public List getErrors() {
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List errors) {
		this.errors = errors;
	}

	/**
	 * @param timestamp
	 * @param code
	 * @param status
	 * @param errors
	 */
	public ErrorDetails(Timestamp timestamp, String code, HttpStatus status, List errors) {
		super();
		this.timestamp = timestamp;
		this.code = code;
		this.status = status;
		this.errors = errors;
	}

	/**
	 * 
	 */
	public ErrorDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
}
