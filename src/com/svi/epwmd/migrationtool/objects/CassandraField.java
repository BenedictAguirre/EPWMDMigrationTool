package com.svi.epwmd.migrationtool.objects;

import java.util.Map;

public class CassandraField {
	private Map<Integer, Data> cassandraData;
	private String uuid;
	private String dateCreated;
	private String createdBy;
	public CassandraField(Map<Integer, Data> cassandraData, String uuid, String dateCreated, String createdBy) {
		this.createdBy = createdBy;
		this.dateCreated = dateCreated;
		this.cassandraData = cassandraData;
		this.uuid = uuid;
	}
	public Map<Integer, Data> getCassandraData() {
		return cassandraData;
	}
	public void setCassandraData(Map<Integer, Data> solrData) {
		this.cassandraData = solrData;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
