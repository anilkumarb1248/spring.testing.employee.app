package com.app.emp.util;

import java.io.Serializable;

/**
 * @author anilb
 *
 */
public class ResponseStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	private String statusCode;
	private String message;
	private String errorMessage;

	public ResponseStatus() {

	}

	public ResponseStatus(String statusCode, String message, String errorMessage) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
