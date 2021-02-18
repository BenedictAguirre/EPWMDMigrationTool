package com.svi.epwmd.migrationtool.constants;

public enum Documents {
	BC("BC"),
	BP("BP"),
	FSIC("FSIC"),
	LOCATIONAL_CLEARANCE("Locational Clearance"),
	DISCHARGE_PERMIT("Discharge Permit"),
	PO_APSI("PO-APSI"),
	PCO("PCO"),
	MOA("Service Agreement Haz Waste/MOA"),
	SERVICE_AGREEMENT_SOLID_WASTE("Service Agreement Solid Waste"),
	CERTIFICATE_OF_INTERCONNECTION("Certificate of Interconnection"),
	ECC("ECC/CNC"),
	LLDA_CLEARANCE("LLDA Clearance"),
	HWID("HWID"),
	CERTIFICATE_OF_TREATMENT_AND_DISPOSAL("Certificate of Treatment & Disposal"),
	WATER_BILLING("Water Billing");
	
	private String constant;

	public String getString() {
		return this.constant;
	}

	private Documents(String constant) {
		this.constant = constant;
	}
}
