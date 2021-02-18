package com.svi.epwmd.migrationtool.objects;

import java.util.Map;

public class ExcelRecord {
	private Map<Integer,String> record;
	private String uuid;
	public ExcelRecord(Map<Integer, String> record, String uuid) {
		this.record = record;
		this.uuid = uuid;
	}

	public Map<Integer, String> getRecord() {
		return record;
	}

	public void setRecord(Map<Integer, String> record) {
		this.record = record;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
