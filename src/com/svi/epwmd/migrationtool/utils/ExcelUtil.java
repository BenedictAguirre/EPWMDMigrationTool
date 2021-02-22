package com.svi.epwmd.migrationtool.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.svi.epwmd.migrationtool.objects.Data;
import com.svi.epwmd.migrationtool.objects.ExcelDatabase;
import com.svi.epwmd.migrationtool.objects.ExcelRecord;
import com.svi.epwmd.migrationtool.objects.Field;

public class ExcelUtil {
	public int getColumnIndex(String index) {
		int result = 0;
		for (int i = 0; i < index.length(); i++) {
			result *= 26;
			result += index.charAt(i) - 'A' + 1;
		}
		return result -= 1;
	}

	public ExcelDatabase getExcelDatabase(String inputFile, int startingRow) {
		XSSFWorkbook wBook;
		List<ExcelRecord> excelDatabase = new ArrayList<>();
		try {
			wBook = new XSSFWorkbook(new FileInputStream(inputFile));
			XSSFSheet sheet = wBook.getSheetAt(0);

			for (int i = startingRow - 1; i < sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				Map<Integer, String> record = new HashMap<>();
				for (Cell cell : row) {
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						record.put(cell.getColumnIndex(), cell.getStringCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							// SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
							Date date = cell.getDateCellValue();
							long millis = date.getTime();
							record.put(cell.getColumnIndex(), String.valueOf(millis));
						} else {
							String value = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
							if (value.contains(".")) {
								if (value.substring(value.indexOf(".")).equals(".0")) {
									value = value.substring(0, value.indexOf("."));
									// System.out.println(value+" = "+value.substring(value.indexOf(".")));
								}

								// System.out.println(value+" = "+value.substring(value.indexOf(".")));
							}

							record.put(cell.getColumnIndex(), value);
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						record.put(cell.getColumnIndex(), String.valueOf(cell.getBooleanCellValue()));
						break;
					}
				}
				if (!record.isEmpty()) {
					UUID uuid = UUID.randomUUID();
					excelDatabase.add(new ExcelRecord(record, uuid.toString()));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ExcelDatabase(excelDatabase);
	}

	public Map<Integer, Field> getMapping(String fieldMap, String columnIndex, String headerIndex, String dbFieldIndex) {
		Map<Integer, Field> map = new HashMap<>();
		XSSFWorkbook wBook;
		try {
			wBook = new XSSFWorkbook(new FileInputStream(
					fieldMap));
			XSSFSheet sheet = wBook.getSheetAt(0);
			int startingRow = 3;

			for (int i = startingRow - 1; i < sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				String index = "";
				String header = "";
				String field = "";
				for (Cell cell : row) {

					cell.setCellType(Cell.CELL_TYPE_STRING);
					if (cell.getColumnIndex() == getColumnIndex(columnIndex)) {
						index = cell.getStringCellValue();
					}
					if (cell.getColumnIndex() == getColumnIndex(headerIndex)) {
						header = cell.getStringCellValue();
					}
					if (cell.getColumnIndex() == getColumnIndex(dbFieldIndex)){
						field = cell.getStringCellValue();
					}
				}
				if (!index.equals("") && !header.equals("")) {
					map.put(getColumnIndex(index), new Field(header, field));
					// System.out.print("<" + index + "=" + header + "=" + field + ">");
				}
				// System.out.println("");
			}

		} catch (FileNotFoundException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return map;
	}
}
