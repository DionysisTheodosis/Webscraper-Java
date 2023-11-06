package com.Webscraper;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Hyperlink;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {
	private Workbook workbook;
	private Sheet sheet;
	private int currentRow;

	public ExcelWriter(String fileName, String sheetName) {
		// Open an existing workbook if it exists, or create a new one
		File file = new File(fileName);
		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				workbook = new XSSFWorkbook(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			workbook = new XSSFWorkbook();
		}

		sheet = workbook.getSheet(sheetName);
		if (sheet == null) {
			sheet = workbook.createSheet(sheetName);
		}

		currentRow = sheet.getPhysicalNumberOfRows();
	}

	public void addRow(Enterprise enterprise) {
		Row dataRow = sheet.createRow(currentRow++);
		Cell cell;

		// Write data to cells
		cell = dataRow.createCell(0);
		cell.setCellValue(enterprise.getCompany_name());

		cell = dataRow.createCell(1);
		cell.setCellValue(enterprise.getCountry());

		cell = dataRow.createCell(2);
		cell.setCellValue(enterprise.getStreet_number().toString());

		cell = dataRow.createCell(3);
		cell.setCellValue(enterprise.getPostal_code());

		cell = dataRow.createCell(4);
		cell.setCellValue(enterprise.getCity());

		cell = dataRow.createCell(5);
		if (enterprise.getWebsite() != null) {
			setHyperlink(cell, enterprise.getWebsite(),"URL");
		}
		cell = dataRow.createCell(6);
		if (enterprise.getLinkedin() !=null) {
			setHyperlink(cell, enterprise.getLinkedin(),"URL");
		}

		cell = dataRow.createCell(7);
		if (enterprise.getFacebook() !=null) {
			setHyperlink(cell, enterprise.getFacebook(),"URL");
		}

		cell = dataRow.createCell(8);
		if (enterprise.getInstagram() != null) {
			setHyperlink(cell, enterprise.getInstagram(),"URL");
		}

		cell = dataRow.createCell(9);
		if (enterprise.getTwitter() != null) {
			setHyperlink(cell, enterprise.getTwitter(),"URL");
		}

		cell = dataRow.createCell(10);
		if (enterprise.getEmail() !=null) {
			setHyperlink(cell, enterprise.getEmail().toString(),"EMAIL");
		}

	}

	private void setHyperlink(Cell cell, String url,String type) {
		CreationHelper createHelper = workbook.getCreationHelper();
		Hyperlink hyperlink;
		switch(type) {
			case "EMAIL":
				hyperlink = createHelper.createHyperlink(Hyperlink.LINK_EMAIL);
				hyperlink.setAddress("mailto:" + url);
				break;
			default:
				hyperlink = createHelper.createHyperlink(Hyperlink.LINK_URL);
				hyperlink.setAddress(url);
				break;
		}
		
		cell.setHyperlink(hyperlink);
		cell.setCellValue(url);
	}

	public void save(String fileName) {
		try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
			workbook.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

