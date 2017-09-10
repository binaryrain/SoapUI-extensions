package com.md.soapui.custom.util.datasource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataSource extends AbstractDataSource {
	
	//Default values
	private static final int DEFAULT_SHEET_INDEX = 0;
	private static final String DEFAULT_SHEET_NAME="Sheet1";
	
	private static final boolean DEFAULT_SELECT_SHEET_BY_NAME = false;
	private static final boolean DEFAULT_INITIALIZE_FIRST_CELL_NUMBER = true;
	private static final boolean DEFAULT_INITIALIZE_LAST_CELL_NUMBER = true;
	private static final boolean DEFAULT_INITIALIZE_LAST_DATAROW_NUMBER = true;
	
	//Private fields - access with getters / setters
	private int firstCellNumber;
	private int lastCellNumber;
	private int firstHeaderCell;
	private int lastHeaderCell;
	private int sheetIndex;
	
	private String sheetName;
	
	private boolean selectSheetByName;
	private boolean initializeFirstCellNumber;
	private boolean initializeLastCellNumber;
	private boolean initializeLastDatarowNumber;
	
	private File file;
	private XSSFWorkbook wb;
	private XSSFSheet sheet;
	private XSSFRow headerRow;
	
	/**
	 * <p>Creates object ExcelDataDriver.</p>
	 * <p>Sets File file to input 'file' in parameters.</p>
	 * <p>Initializes standard values</p>
	 * <p>Header row: index 0</p>
	 * <p>First data row: index 1</p>
	 * <p>Return data with headers: false</p>
	 * 
	 * The following values are initialized:
	 * <li>
	 * Default sheet index (0)
	 * </li>
	 * <li>
	 * Default sheet name (Sheet1)
	 * </li>
	 * <li>
	 * Default select sheet by name boolean (false)
	 * </li>
	 * <li>
	 * Default first cell number (identified by looking for the first cell in the first line found)
	 * </li>
	 * <li>
	 * Default last cell number (identified by looking for the last cell in the range of rows defined)
	 * </li>
	 * <li>
	 * Default last data row (identified by looking for the last row with a non-null cell.
	 * </li>
	 * @param file
	 */
	public ExcelDataSource(File file) {
		this.file = file;
		setWorkbook();
		initializeDefaultValues();
		this.sheetIndex = DEFAULT_SHEET_INDEX;
		this.sheetName = DEFAULT_SHEET_NAME;
		this.selectSheetByName = DEFAULT_SELECT_SHEET_BY_NAME;
		this.initializeFirstCellNumber = DEFAULT_INITIALIZE_FIRST_CELL_NUMBER;
		this.initializeLastCellNumber = DEFAULT_INITIALIZE_LAST_CELL_NUMBER;
		this.initializeLastDatarowNumber = DEFAULT_INITIALIZE_LAST_DATAROW_NUMBER;
	}
	
	private void setWorkbook() {
		try {
			this.wb = new XSSFWorkbook(this.file);
		} catch (InvalidFormatException e) {
			new DataSourceException("This is not a valid format");
			e.printStackTrace();
		} catch (IOException e) {
			new DataSourceException("IO Exception");
			e.printStackTrace();
		}
	}
	
	/**
	 * Load workbook at file set in constructor.
	 * When selectSheetByName equals true, the sheet name will be used.
	 * When selectSheetByName equals false, the sheet index will be used.
	 * 
	 * Default sheet index: 0
	 * Default sheet name: Sheet1
	 */
	@Override
	public void loadDataset() {
		if(!selectSheetByName) {
			sheet = wb.getSheetAt(this.sheetIndex);
			System.out.println(sheet.getSheetName());
		} else {
			sheet = wb.getSheet(sheetName);
			System.out.println(sheet.getSheetName());
		}
		setDatasetCountValues();
		setHeaderRow();
	}
	
	@Override
	protected void setDatasetCountValues() {
		if(initializeFirstCellNumber) {
			initializeFirstExistingCellNumber();
			initializeFirstHeaderCellNumber();
		}
		if(initializeLastCellNumber) {
			initializeLastExistingCellNumber();
			initializeLastHeaderCellNumber();
		}
		if(initializeLastDatarowNumber) {
			initializeLastDatarowNumber();
		}
	}
	
	private void setHeaderRow() {
		this.headerRow = sheet.getRow(getHeaderRowNumber());
	}
	
	@Override
	public ExcelDataSource getTheSheetWithName(String sheetName) {
		this.sheetName = sheetName;
		this.selectSheetByName = true;
		return this;
	}
	@Override
	public ExcelDataSource getTheSheetWithIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
		this.selectSheetByName = false;
		return this;
	}
	@Override
	public ExcelDataSource startAtRow(int rowIndex) {
		setFirstDataRowNumber(rowIndex);
		return this;
	}
	@Override
	public ExcelDataSource stopAtRow(int rowIndex) {
		this.lastDataRowIndex = rowIndex;
		this.initializeLastDatarowNumber = false;
		return this;
	}
	@Override
	public ExcelDataSource startAtColumn(int cellIndex) {
		this.firstCellNumber = cellIndex;
		this.initializeFirstCellNumber = false;
		return this;
	}
	@Override
	public ExcelDataSource stopAtColumn(int cellIndex) {
		this.lastCellNumber = cellIndex;
		this.initializeLastCellNumber = false;
		return this;
	}
	@Override
	public ExcelDataSource useHeadersAtRow(int rowIndex) {
		this.setHeaderRowNumber(rowIndex);
		this.setBasedOnHeaders(true);
		return this;
	}
	
	
/*############################################################*
 * 	Private methods for the automatic initialization          *
 *############################################################*/
	
	private void initializeFirstExistingCellNumber() {
		int firstCellNumber = sheet.getRow(0).getFirstCellNum();
		if(firstCellNumber != 0) {
			for(int i = 1; i < sheet.getLastRowNum(); i++) {
				int testInt = sheet.getRow(i).getFirstCellNum();
				if(testInt < firstCellNumber) {
					firstCellNumber = testInt;
				}
				if(testInt == 0) {
					break;
				}
			}
		}
		this.firstCellNumber = firstCellNumber;
	}
	
	private void initializeLastExistingCellNumber() {
		int lastCellNumber = 0;
		for(int i = 0; i < sheet.getLastRowNum(); i++) {
			if(sheet.getRow(i) != null) {
				int testInt = sheet.getRow(i).getLastCellNum();
				if(testInt > lastCellNumber) {
					lastCellNumber = testInt;
				}
			}
		}
		this.lastCellNumber = lastCellNumber;
	}
	
	private void initializeFirstHeaderCellNumber() {
		firstHeaderCell = sheet.getRow(this.getHeaderRowNumber())
				               .getFirstCellNum();
	}
	
	private void initializeLastHeaderCellNumber() {
		lastHeaderCell = sheet.getRow(this.getHeaderRowNumber())
				              .getLastCellNum();
	}
	
	private void initializeLastDatarowNumber() {
		lastDataRowIndex = sheet.getLastRowNum();
	}
	
	
	
	/*############################################################*
	 * 	Return line of data methods. With / Without headers.      *
	 *############################################################*/
	
	@Override
	protected ArrayList<String> getLineWithoutHeaders (int rowNumber) {
		ArrayList<String> row = new ArrayList<>();
		XSSFRow dataRow = sheet.getRow(rowNumber);
		if(dataRow != null) {
			for(int cellNum = firstCellNumber; cellNum < lastCellNumber; cellNum++) {
				if(dataRow.getCell(cellNum)==null) {
					row.add("");
				} else {
					row.add(dataRow.getCell(cellNum).getStringCellValue());
				}
		    }
		}
		return row;
	}
	
	@Override
	protected HashMap<String,String> getLineWithHeaders(int rowNumber) {
		HashMap<String, String> row = new HashMap<>();
		XSSFRow dataRow = sheet.getRow(rowNumber);
		
		for(int cellNum = firstHeaderCell; cellNum < lastHeaderCell; cellNum++) {
			row.put(headerRow.getCell(cellNum).getStringCellValue()
				   ,dataRow.getCell(cellNum).getStringCellValue());
		}
		return row;
	}
}