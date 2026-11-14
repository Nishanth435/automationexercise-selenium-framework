package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Readexcel {
	    public static Map<String, String> readPaymentDetails()
	            throws IOException {

	        Map<String, String> paymentDetails = new HashMap<>();

	        String filePath = "src/test/resources/data/CardDetails.xlsx";

	        FileInputStream fis = new FileInputStream(filePath);

	        Workbook workbook = new XSSFWorkbook(fis);

	        Sheet sheet = workbook.getSheetAt(0);

	        Row headerRow = sheet.getRow(0);
	        Row dataRow = sheet.getRow(1);

	        DataFormatter formatter = new DataFormatter();

	        for (int i = 0; i < headerRow.getLastCellNum(); i++) {

	            String columnName =
	                    formatter.formatCellValue(headerRow.getCell(i));

	            String cellValue =
	                    formatter.formatCellValue(dataRow.getCell(i));

	            paymentDetails.put(columnName, cellValue);
	        }

	        workbook.close();
	        fis.close();

	        return paymentDetails;
	    }

	    public static void main(String[] args) throws IOException {

	        Map<String, String> details = readPaymentDetails();

	        System.out.println("Name: " + details.get("NameOnCard"));
	        System.out.println("Card Number: " + details.get("CardNumber"));
	        System.out.println("CVC: " + details.get("CVC"));
	        System.out.println("Expiry Month: " + details.get("ExpiryMonth"));
	        System.out.println("Expiry Year: " + details.get("ExpiryYear"));
	    }
	}
