package automationExcersie;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class read {
    @Test
	public void reads() throws IOException
		{
			String fileLocation ="./Data/CardDetails.xlsx";
			XSSFWorkbook wbook =new XSSFWorkbook(fileLocation);
			XSSFSheet sheet = wbook.getSheetAt(0);
			XSSFRow row= sheet.getRow(1);
			for (int i = 0; i < 2; i++) {
				XSSFCell cell = row.getCell(0);
				String value = cell.getStringCellValue();
				System.out.println(value);
			}
}
