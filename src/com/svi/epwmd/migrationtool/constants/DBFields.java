package com.svi.epwmd.migrationtool.constants;

public enum DBFields {
	CLEARANCE_TYPE("clearance_type"), 
	GEOCODE("geocode"),
	OCCUPANCY_TYPE("occupancy_type"),
	RISK_LEVEL("risk_level"),
	SUBMITTED_DOCS_VALIDITY("submitted_docs_validity"),
	SUBMITTED_DOCS_ISSUED("submitted_docs_issued"),
	AMT_PAID("amt_paid"),
	ESTABLISHMENT_ID("establishment_id"),
	TOTAL_EMPLOYEES("total_employees"),
	TEL_NOS("tel_nos"),
	CELL_NOS("cell_nos"),
	EMAILS("emails"),
	WORK_SCHED_DAYS("work_sched_days"),
	DATE_APPLIED("date_applied"),
	DATE_ISSUED("date_issued"),
	DATE_INSPECTED("date_inspected"),
	DATE_REVOKED("date_revoked"),
	VALID_UNTIL("valid_until"),
	COMPLIANCE_DEADLINE("compliance_deadline"),
	PAYMENT_DATE("payment_date"),
	TOTAL_AMOUNT_PAID("total_amt_paid"),
	DATE_CREATED("date_created"),
	CREATED_BY("created_by");
	
	private String constant;

	public String getString() {
		return this.constant;
	}

	private DBFields(String constant) {
		this.constant = constant;
	}
}
