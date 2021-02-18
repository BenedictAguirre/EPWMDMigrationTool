package com.svi.epwmd.migrationtool.objects;

import java.util.Map;

public class SolrField {
	private Map<Integer, Data> solrData;
	private String uuid;
	public SolrField(Map<Integer, Data> solrData, String uuid) {
		this.solrData = solrData;
		this.uuid = uuid;
	}
	public Map<Integer, Data> getSolrData() {
		return solrData;
	}
	public void setSolrData(Map<Integer, Data> solrData) {
		this.solrData = solrData;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
 
}
