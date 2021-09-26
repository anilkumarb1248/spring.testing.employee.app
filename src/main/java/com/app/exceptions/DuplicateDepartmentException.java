package com.app.exceptions;

public class DuplicateDepartmentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateDepartmentException(String msg) {
		super(msg);
	}
}
