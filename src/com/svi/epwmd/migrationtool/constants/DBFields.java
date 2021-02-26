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
	DATE_REVOKED("date_revoked"),
	VALID_UNTIL("valid_until"),
	COMPLIANCE_DEADLINE("compliance_deadline"),
	PAYMENT_DATE("payment_date"),
	TOTAL_AMOUNT_PAID("total_amt_paid"),
	DATE_CREATED("date_created"),
	CREATED_BY("created_by"), 
	DAILY_WASTE_GENERATION("daily_waste_generation"), 
	HAULER("hauler"),
	COLLECTION_FREQUENCY("collection_frequency"),
	HAULER_COLLECTION_FREQUENCY("hauler_collection_frequency"),
	SEPTIC_TANK("septic_tank"), 
	STP_WWTF("stp_wwtf"), 
	SEWERAGE_CONNECTION("sewerage_connection"),
	GREASE_TRAP("grease_trap"), 
	GREACE_INTERCEPTOR("grease_interceptor"),
	OIL_WATER_SEPARATOR("oil_water_separator"), 
	MATERIALS_RECOVERY("materials_recovery"),
	PAINTING_BOOTH("painting_booth"),
	SOUND_PROOFING("sound_proofing"),
	CASE_OFFICER("case_officer"),
	APPREHENDING_OFFICER("officer"),
	INSPECTOR("inspector")
	;
	

	private String constant;

	public String getString() {
		return this.constant;
	}

	private DBFields(String constant) {
		this.constant = constant;
	}
}
