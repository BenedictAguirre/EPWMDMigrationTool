package com.svi.epwmd.migrationtool.objects;

public class Field {
	private String header;
	private String field;
	public Field( String header, String field) {
		this.header = header;
		this.field = field;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	
	
}
