package com.md.soapui.custom.util.datasource;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class CsvDataSource extends AbstractDataSource {
	
	public CsvDataSource(File file) {
		
	}

	@Override
	public void loadDataset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ArrayList<String> getLineWithoutHeaders(int rowNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<String, String> getLineWithHeaders(int rowNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setDatasetCountValues() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AbstractDataSource getTheSheetWithName(String sheetName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractDataSource getTheSheetWithIndex(int sheetIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractDataSource startAtRow(int rowIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractDataSource stopAtRow(int rowIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractDataSource startAtColumn(int cellIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractDataSource stopAtColumn(int cellIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractDataSource useHeadersAtRow(int rowIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
