package com.svi.epwmd.migrationtool.objects;

import java.util.HashMap;
import java.util.Map;

public class Data {
	private String field;
	private Object value;

	
	public Data(String field, Object value) {
		this.field = field;
		this.value = value;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}


	public Object getValue() {
		return value;
	}


	public void setValue(Object value) {
		this.value = value;
	}
	
	}
