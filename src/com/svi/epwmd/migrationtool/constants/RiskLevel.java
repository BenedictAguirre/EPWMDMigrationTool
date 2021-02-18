package com.svi.epwmd.migrationtool.constants;

public enum RiskLevel {
	HIGH("HIGH"),
	MEDIUM("MEDIUM"),
	LOW("LOW")
	;
	private String constant;

	public String getString() {
		return this.constant;
	}

	private RiskLevel(String constant) {
		this.constant = constant;
	}
}
