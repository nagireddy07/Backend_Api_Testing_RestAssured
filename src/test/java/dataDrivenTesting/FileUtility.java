package dataDrivenTesting;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 
 * This Class consists of generic methods related to File Operations Like Property file , Excel File
 * @author Bobbala Venkata Nagi
 */

public class FileUtility {
	
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
