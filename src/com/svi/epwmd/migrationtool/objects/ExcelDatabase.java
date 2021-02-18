package com.svi.epwmd.migrationtool.objects;

import java.util.List;

public class ExcelDatabase {
	private List<ExcelRecord> excelDatabase;
	public ExcelDatabase(List<ExcelRecord> excelDatabase) {
		this.excelDatabase = excelDatabase;
	}

	public List<ExcelRecord> getExcelDatabase() {
		return excelDatabase;
	}
	public void setExcelDatabase(List<ExcelRecord> excelDatabase) {
		this.excelDatabase = excelDatabase;
	}
}
