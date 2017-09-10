package com.md.soapui.custom.util.datasource;

import java.io.File;
import java.util.Arrays;

public class DataSource {
	
	private DataSourceType type;
	private AbstractDataSource dataSource;
	private File file;
	
	/**
	 * This constructor initializes a DataSource object.
	 * The data source can be configured with the datasource.config() command.
	 * 
	 * @param type
	 * @param file
	 * @throws DataSourceException
	 */
	public DataSource(DataSourceType type, File file) throws DataSourceException {
		//Add check when extra implementation for abstract data driver that do not fit type and file constructor.
		this.type = type;
		this.file = file;
		initiateDriver();
	}
	
	public void setDataDriverType(DataSourceType type) {
		this.type = type;
	}
	public DataSourceType getDataDriverType() {
		return this.type;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public File getFile() {
		return this.file;
	}
	
	private void initiateDriver() throws DataSourceException {
		switch(type) {
		    case EXCEL_XLSX : dataSource = new ExcelDataSource(file);
		    break;
		    case CSV : dataSource = new CsvDataSource(file);
		    break;
		    default: throw new DataSourceException("Type not known - must be one of DataDriverType Enum " + Arrays.asList(DataSourceType.values()));
		}
	}
	
	/**
	 * Access point for all configuration as well as the command to load the set with the loadDataset() method.
	 * @return AbstractDataDriver
	 */
	public AbstractDataSource config() {
		return dataSource;
	}
	
	/**
	 * Get line of data within the boundaries of the configuration.
	 * <br/>
	 * It either returns a map or a list of strings.
	 * <br/>
	 * <p>The Map of strings is defined as:<br/>
	 * <li>key: header name</li>
	 * <li>value: cell value as String</li>
	 * </p>
	 * <p>
	 * The list of Strings contains the values for the range defined in the configuration in the order they are retrieved.<br/>
	 * 
	 * </p>
	 * 
	 * @param int rowIndex
	 * @return Map&lt;String, String&gt; or ArrayList&lt;String&gt;
	 */
	public Object getDataLine(int rowIndex) {
		return dataSource.getLine(rowIndex);
	}
}