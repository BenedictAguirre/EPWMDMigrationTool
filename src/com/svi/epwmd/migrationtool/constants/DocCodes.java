package com.svi.epwmd.migrationtool.constants;

public enum DocCodes {
	BC("BCL"),
	BP("BZP"),
	FSIC("FSIC"),
	LOCATIONAL_CLEARANCE("LCL"),
	DISCHARGE_PERMIT("DCP"),
	PO_APSI("PO-APSI"),
	PCO("PCO"),
	MOA("SAHW"),
	SERVICE_AGREEMENT_SOLID_WASTE("SASW"),
	CERTIFICATE_OF_INTERCONNECTION("CIT"),
	ECC("ECC"),
	LLDA_CLEARANCE("LLDA"),
	HWID("HWID"),
	CERTIFICATE_OF_TREATMENT_AND_DISPOSAL("CTD"),
	WATER_BILLING("WTB");
	
	private String constant;

	public String getString() {
		return this.constant;
	}

	private DocCodes(String constant) {
		this.constant = constant;
	}
}
