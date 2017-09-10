package com.md.soapui.custom.util.datasource;

public class DataSourceException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DataSourceException() {
	}
	
	public DataSourceException(String message) {
		super(message);
	}
	
	public DataSourceException(Throwable cause) {
		super(cause);
	}
	
	public DataSourceException(String message, Throwable cause) {
		super(message,cause);
	}

}
