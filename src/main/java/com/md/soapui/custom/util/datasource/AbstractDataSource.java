package com.md.soapui.custom.util.datasource;

import java.util.ArrayList;
import java.util.Map;

abstract public class AbstractDataSource {
	private static int DEFAULT_HEADERS_ROW = 0;
	private static int DEFAULT_FIRST_DATA_ROW = 1;
	private static boolean DEFAULT_BASED_ON_HEADERS = false;
	
	protected int headerRowNumber;
	protected int firstDataRowIndex;
	protected int lastDataRowIndex;
	protected boolean basedOnHeaders;
	
	public void setBasedOnHeaders(boolean basedOnHeaders) {
		this.basedOnHeaders = basedOnHeaders;
	}
	public boolean getBasedOnHeaders() {
		return this.basedOnHeaders;
	}
	public void setHeaderRowNumber(int headerRowNumber) {
		this.headerRowNumber = headerRowNumber;
	}
	public int getHeaderRowNumber() {
		return this.headerRowNumber;
	}
	public int getFirstDataRowNumber() {
		return this.firstDataRowIndex;
	}
	public void setFirstDataRowNumber(int firstDataRowNumber) {
		this.firstDataRowIndex = firstDataRowNumber;
	}
	public void initializeDefaultValues() {
		setBasedOnHeaders(DEFAULT_BASED_ON_HEADERS);
		setHeaderRowNumber(DEFAULT_HEADERS_ROW);
		setFirstDataRowNumber(DEFAULT_FIRST_DATA_ROW);
	}
	
	public Object getLine(int rowNumber) {
		Object o = null;
		if(basedOnHeaders) {
		    o = getLineWithHeaders(rowNumber);
		} else {
			o = getLineWithoutHeaders(rowNumber);
		}
		return o;
	}
	
	public abstract AbstractDataSource getTheSheetWithName(String sheetName);
	public abstract AbstractDataSource getTheSheetWithIndex(int sheetIndex);
	public abstract AbstractDataSource startAtRow(int rowIndex);
	public abstract AbstractDataSource stopAtRow(int rowIndex);
	public abstract AbstractDataSource startAtColumn(int cellIndex);
	public abstract AbstractDataSource stopAtColumn(int cellIndex);
	public abstract AbstractDataSource useHeadersAtRow(int rowIndex);
	
	abstract public void loadDataset();
	abstract protected ArrayList<String> getLineWithoutHeaders(int rowNumber);
	abstract protected Map<String,String> getLineWithHeaders(int rowNumber);
	abstract protected void setDatasetCountValues();
}
