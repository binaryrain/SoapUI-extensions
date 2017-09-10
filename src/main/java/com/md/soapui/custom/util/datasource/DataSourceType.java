package com.md.soapui.custom.util.datasource;

public enum DataSourceType {
	CSV(null),
	EXCEL_XLSX(ExcelDataSource.class),
	EXCEL_XLS(null);
	
	private Class claz;
	
	DataSourceType(Class claz) {
		this.claz = claz;
	}
	
	public Class getTypeClass() {
		return claz;
	}
}
