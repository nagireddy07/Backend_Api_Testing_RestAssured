package backendTesting.genericUtilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 
 * This Class consists of generic methods related to File Operations Like Property file , Excel File
 * @author Bobbala Venkata Nagi
 */

public class FileUtility {
	
	/**
	 * This Method will read data from property file.
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String readDataFromPropertyFile(String key) throws Exception {
		FileInputStream fis = new FileInputStream(".\\src\\test\\resources\\configEnvData.properties");
		Properties p = new Properties();
		p.load(fis);
		return p.getProperty(key);
	}
	
	
	/**
	 * This method will read data from excel file and return the value to caller
	 * @param sheet
	 * @param row
	 * @param cell
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public String readDataFromExcel(String sheet,int row,int cell) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(".\\src\\test\\resources\\TestData.xlsx");
		return WorkbookFactory.create(fis).getSheet(sheet).getRow(row).getCell(cell).toString();
	}
	
	
	/**
	 * This method return multiple data present in a row
	 * @param sheet
	 * @param row
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public List<String> readRowDataFromExcel(String sheet,int row) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(".\\src\\test\\resources\\TestData.xlsx");
		Row row1 = WorkbookFactory.create(fis).getSheet(sheet).getRow(row);
		List<String> list = new ArrayList<String>();
		for(int i=0;i<row1.getLastCellNum();i++) {
			list.add(row1.getCell(i).toString());
		}
		return list;
	}
	
	public static Object[][] getMultipleDataFromExcel(String sheetName) throws IOException {
	    FileInputStream fis = new FileInputStream(".\\src\\test\\resources\\TestData.xlsx");
	    Sheet sheet = WorkbookFactory.create(fis).getSheet(sheetName);

	    int lastRow = sheet.getLastRowNum();               // e.g., 10 means 11 rows (0–10)
	    int lastCol = sheet.getRow(0).getLastCellNum();    // total number of columns

	    Object[][] details = new Object[lastRow][lastCol]; // skipping header

	    for (int i = 1; i <= lastRow; i++) {  // start from row 1 (skip header)
	        Row row = sheet.getRow(i);
	        for (int j = 0; j < lastCol; j++) {
	            if (row == null || row.getCell(j) == null)
	                details[i - 1][j] = "";
	            else
	                details[i - 1][j] = row.getCell(j).toString();
	        }
	    }
	    fis.close();
	    return details;
	}
	
}
