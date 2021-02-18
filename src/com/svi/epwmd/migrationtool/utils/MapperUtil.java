package com.svi.epwmd.migrationtool.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.svi.epwmd.migrationtool.constants.DBFields;
import com.svi.epwmd.migrationtool.constants.DocCodes;
import com.svi.epwmd.migrationtool.constants.Documents;
import com.svi.epwmd.migrationtool.constants.RiskLevel;
import com.svi.epwmd.migrationtool.objects.CassandraField;
import com.svi.epwmd.migrationtool.objects.Data;
import com.svi.epwmd.migrationtool.objects.ExcelDatabase;
import com.svi.epwmd.migrationtool.objects.ExcelRecord;
import com.svi.epwmd.migrationtool.objects.Field;
import com.svi.epwmd.migrationtool.objects.SolrField;

public class MapperUtil {
	ExcelUtil excelUtil = new ExcelUtil();

	public List<SolrField> mapDataToSolrFields(ExcelDatabase edb, Map<Integer, Field> solrMap) {
		List<SolrField> solr = new ArrayList<>();
		for (ExcelRecord rec : edb.getExcelDatabase()) {
			Map<Integer, Data> solrData = new HashMap<>();
			String longitude = "";
			String latitude = "";
			for (Entry<Integer, String> entry : rec.getRecord().entrySet()) {
				try {
					if (!solrMap.get(entry.getKey()).getField().equals("")) {
						if (solrMap.get(entry.getKey()).getField().equalsIgnoreCase(DBFields.GEOCODE.getString())) {
							if (solrMap.get(entry.getKey()).getHeader().equalsIgnoreCase("longitude")) {
								longitude = entry.getValue();
							}
							if (solrMap.get(entry.getKey()).getHeader().equalsIgnoreCase("latitude")) {
								latitude = entry.getValue();
							}
							if (!longitude.equals("") && !latitude.equals("")) {
								solrData.put(entry.getKey(),
										new Data(solrMap.get(entry.getKey()).getField(), longitude + "|" + latitude));
							}

						} else if (solrMap.get(entry.getKey()).getField().equalsIgnoreCase(DBFields.RISK_LEVEL.getString())) {
							if (entry.getValue().equalsIgnoreCase("H")) {
								solrData.put(entry.getKey(), new Data(solrMap.get(entry.getKey()).getField(), RiskLevel.HIGH.getString()));
							}
							if (entry.getValue().equalsIgnoreCase("M")) {
								solrData.put(entry.getKey(),
										new Data(solrMap.get(entry.getKey()).getField(), RiskLevel.MEDIUM.getString()));
							}
							if (entry.getValue().equalsIgnoreCase("L")) {
								solrData.put(entry.getKey(), new Data(solrMap.get(entry.getKey()).getField(), RiskLevel.LOW.getString()));
							}
						} else if (solrMap.get(entry.getKey()).getField().equalsIgnoreCase(DBFields.OCCUPANCY_TYPE.getString())) {
							if (entry.getValue().equalsIgnoreCase("Y")) {
								solrData.put(entry.getKey(),
										new Data(solrMap.get(entry.getKey()).getField(), "Stand Alone"));
							} else if (entry.getValue().equalsIgnoreCase("N")) {
								solrData.put(entry.getKey(), new Data(solrMap.get(entry.getKey()).getField(), "Line"));
							} else {
								solrData.put(entry.getKey(),
										new Data(solrMap.get(entry.getKey()).getField(), entry.getValue()));
							}
						} else {
							solrData.put(entry.getKey(),
									new Data(solrMap.get(entry.getKey()).getField(), entry.getValue()));
							// System.out.print("<"+map.get(entry.getKey()).getField()+"="+entry.getValue()+">");
						}
					}

				} catch (Exception e2) {
					// TODO: handle exception
					// e2.printStackTrace();
				}
			}

			solr.add(new SolrField(solrData, rec.getUuid()));

			// System.out.println("");
		}
		return solr;
	}

	public List<CassandraField> mapDataToQCBizCassandraTable(ExcelDatabase edb, Map<Integer, Field> cassandraMap,
			String dateCreated, String createdBy) {
		List<CassandraField> cassandra = new ArrayList<>();
		for (ExcelRecord rec : edb.getExcelDatabase()) {
			Map<Integer, Data> cassandraData = new HashMap<>();
			String longitude = "";
			String latitude = "";
			for (Entry<Integer, String> entry : rec.getRecord().entrySet()) {
				try {
					if (!cassandraMap.get(entry.getKey()).getField().equals("")) {
						if (cassandraMap.get(entry.getKey()).getField().equalsIgnoreCase(DBFields.GEOCODE.getString())) {
							if (cassandraMap.get(entry.getKey()).getHeader().equalsIgnoreCase("longitude")) {
								longitude = entry.getValue();
							}
							if (cassandraMap.get(entry.getKey()).getHeader().equalsIgnoreCase("latitude")) {
								latitude = entry.getValue();
							}
							if (!longitude.equals("") && !latitude.equals("")) {
								cassandraData.put(entry.getKey(), new Data(cassandraMap.get(entry.getKey()).getField(),
										longitude + "|" + latitude));
							}

						} else if (cassandraMap.get(entry.getKey()).getField().equalsIgnoreCase(DBFields.RISK_LEVEL.getString())) {
							if (entry.getValue().equalsIgnoreCase("H")) {
								cassandraData.put(entry.getKey(), new Data(cassandraMap.get(entry.getKey()).getField(), RiskLevel.HIGH.getString()));
							}
							if (entry.getValue().equalsIgnoreCase("M")) {
								cassandraData.put(entry.getKey(),
										new Data(cassandraMap.get(entry.getKey()).getField(), RiskLevel.MEDIUM.getString()));
							}
							if (entry.getValue().equalsIgnoreCase("L")) {
								cassandraData.put(entry.getKey(), new Data(cassandraMap.get(entry.getKey()).getField(), RiskLevel.LOW.getString()));
							}
						} else if (cassandraMap.get(entry.getKey()).getField().equalsIgnoreCase(DBFields.OCCUPANCY_TYPE.getString())) {
							if (entry.getValue().equalsIgnoreCase("Y")) {
								cassandraData.put(entry.getKey(),
										new Data(cassandraMap.get(entry.getKey()).getField(), "Stand Alone"));
							} else if (entry.getValue().equalsIgnoreCase("N")) {
								cassandraData.put(entry.getKey(),
										new Data(cassandraMap.get(entry.getKey()).getField(), "Line"));
							} else {
								cassandraData.put(entry.getKey(),
										new Data(cassandraMap.get(entry.getKey()).getField(), entry.getValue()));
							}
						} else {
							cassandraData.put(entry.getKey(),
									new Data(cassandraMap.get(entry.getKey()).getField(), entry.getValue()));
							// System.out.print("<"+map.get(entry.getKey()).getField()+"="+entry.getValue()+">");
						}
					}

				} catch (Exception e2) {
					// TODO: handle exception
					// e2.printStackTrace();
				}
			}

			cassandra.add(new CassandraField(cassandraData, rec.getUuid(), dateCreated, createdBy));

			// System.out.println("");
		}
		return cassandra;
	}



	public List<CassandraField> mapDataToECComplianceCassandraTable(ExcelDatabase edb, Map<Integer, Field> cassandraMap,
			String dateCreated, String createdBy) {
		List<CassandraField> cassandra = new ArrayList<>();
		for (ExcelRecord rec : edb.getExcelDatabase()) {
			Map<Integer, Data> cassandraData = new HashMap<>();
			int lastSubmittedDocsValidityIndex = 0;
			int lastSubmittedDocsIssuedIndex = 0;
			int lastAmountPaidIndex = 0;
			Map<String, String> submittedDocsValidity = new HashMap<>();
			Map<String, String> submittedDocsIssued = new HashMap<>();
			HashMap<String, Double> amountPaid = new HashMap<>();
			for (Entry<Integer, String> entry : rec.getRecord().entrySet()) {
				try {
					if (!cassandraMap.get(entry.getKey()).getField().equals("")) {
						if (cassandraMap.get(entry.getKey()).getField().equalsIgnoreCase(DBFields.CLEARANCE_TYPE.getString())) {
							if (entry.getValue().equalsIgnoreCase("N")) {
								cassandraData.put(entry.getKey(),
										new Data(cassandraMap.get(entry.getKey()).getField(), "NEW"));
							}
							if (entry.getValue().equalsIgnoreCase("R")) {
								cassandraData.put(entry.getKey(),
										new Data(cassandraMap.get(entry.getKey()).getField(), "RENEWAL"));
							}
						} else if (cassandraMap.get(entry.getKey()).getField().equalsIgnoreCase(DBFields.RISK_LEVEL.getString())) {
							if (entry.getValue().equalsIgnoreCase("H")) {
								cassandraData.put(entry.getKey(),
										new Data(cassandraMap.get(entry.getKey()).getField(), "HIGH"));
							}
							if (entry.getValue().equalsIgnoreCase("M")) {
								cassandraData.put(entry.getKey(),
										new Data(cassandraMap.get(entry.getKey()).getField(), "MEDIUM"));
							}
							if (entry.getValue().equalsIgnoreCase("L")) {
								cassandraData.put(entry.getKey(),
										new Data(cassandraMap.get(entry.getKey()).getField(), "LOW"));
							}
						} else if (cassandraMap.get(entry.getKey()).getField()
								.equalsIgnoreCase(DBFields.SUBMITTED_DOCS_VALIDITY.getString())) {
							if (entry.getValue().equalsIgnoreCase("Y")) {
								if (cassandraMap.get(entry.getKey()).getHeader().equalsIgnoreCase(Documents.BC.getString())) {
									String docCode = DocCodes.BC.getString();
									String docDate = rec.getRecord().get(entry.getKey() + 1);
									submittedDocsValidity.put(docCode, docDate);
									lastSubmittedDocsIssuedIndex = entry.getKey();
								}
								if (cassandraMap.get(entry.getKey()).getHeader().equalsIgnoreCase(Documents.BP.getString())) {
									String docCode = DocCodes.BP.getString();
									String docDate = rec.getRecord().get(entry.getKey() + 1);
									submittedDocsValidity.put(docCode, docDate);
									lastSubmittedDocsValidityIndex = entry.getKey();
								}
								if (cassandraMap.get(entry.getKey()).getHeader().equalsIgnoreCase(Documents.FSIC.getString())) {
									String docCode = DocCodes.FSIC.getString();
									String docDate = rec.getRecord().get(entry.getKey() + 1);
									submittedDocsValidity.put(docCode, docDate);
									lastSubmittedDocsValidityIndex = entry.getKey();
								}
								if (cassandraMap.get(entry.getKey()).getHeader()
										.equalsIgnoreCase(Documents.LOCATIONAL_CLEARANCE.getString())) {
									String docCode = DocCodes.LOCATIONAL_CLEARANCE.getString();
									String docDate = rec.getRecord().get(entry.getKey() + 1);
									submittedDocsValidity.put(docCode, docDate);
									lastSubmittedDocsValidityIndex = entry.getKey();
								}

								if (cassandraMap.get(entry.getKey()).getHeader().equalsIgnoreCase(Documents.DISCHARGE_PERMIT.getString())) {
									String docCode = DocCodes.DISCHARGE_PERMIT.getString();
									String docDate = rec.getRecord().get(entry.getKey() + 1);
									submittedDocsValidity.put(docCode, docDate);
									lastSubmittedDocsValidityIndex = entry.getKey();
								}
								if (cassandraMap.get(entry.getKey()).getHeader().equalsIgnoreCase(Documents.PO_APSI.getString())) {
									String docCode = DocCodes.PO_APSI.getString();
									String docDate = rec.getRecord().get(entry.getKey() + 1);
									submittedDocsValidity.put(docCode, docDate);
									lastSubmittedDocsValidityIndex = entry.getKey();
								}
								if (cassandraMap.get(entry.getKey()).getHeader().equalsIgnoreCase(Documents.PCO.getString())) {
									String docCode = DocCodes.PCO.getString();
									String docDate = rec.getRecord().get(entry.getKey() + 1);
									submittedDocsValidity.put(docCode, docDate);
									lastSubmittedDocsValidityIndex = entry.getKey();
								}

								if (cassandraMap.get(entry.getKey()).getHeader()
										.equalsIgnoreCase(Documents.MOA.getString())) {
									String docCode = DocCodes.MOA.getString();
									String docDate = rec.getRecord().get(entry.getKey() + 1);
									submittedDocsValidity.put(docCode, docDate);
									lastSubmittedDocsValidityIndex = entry.getKey();
								}

								if (cassandraMap.get(entry.getKey()).getHeader()
										.equalsIgnoreCase(Documents.SERVICE_AGREEMENT_SOLID_WASTE.getString())) {
									String docCode = DocCodes.SERVICE_AGREEMENT_SOLID_WASTE.getString();
									String docDate = rec.getRecord().get(entry.getKey() + 1);
									submittedDocsValidity.put(docCode, docDate);
									lastSubmittedDocsValidityIndex = entry.getKey();
								}
								if (cassandraMap.get(entry.getKey()).getHeader()
										.equalsIgnoreCase(Documents.CERTIFICATE_OF_INTERCONNECTION.getString())) {
									String docCode = DocCodes.CERTIFICATE_OF_INTERCONNECTION.getString();
									String docDate = rec.getRecord().get(entry.getKey() + 1);
									submittedDocsValidity.put(docCode, docDate);
									lastSubmittedDocsValidityIndex = entry.getKey();
									// }
								}
							}
						} else if (cassandraMap.get(entry.getKey()).getField()
								.equalsIgnoreCase(DBFields.SUBMITTED_DOCS_ISSUED.getString())) {
							if (entry.getValue().equalsIgnoreCase("Y")) {
								if (cassandraMap.get(entry.getKey()).getHeader().equalsIgnoreCase(Documents.ECC.getString())) {
									String docCode = DocCodes.ECC.getString();
									String docDate = rec.getRecord().get(entry.getKey() + 1);
									submittedDocsIssued.put(docCode, docDate);
									lastSubmittedDocsIssuedIndex = entry.getKey();
								}
								if (cassandraMap.get(entry.getKey()).getHeader().equalsIgnoreCase(Documents.LLDA_CLEARANCE.getString())) {
									String docCode = DocCodes.LLDA_CLEARANCE.getString();
									String docDate = rec.getRecord().get(entry.getKey() + 1);
									submittedDocsIssued.put(docCode, docDate);
									lastSubmittedDocsIssuedIndex = entry.getKey();
								}
								if (cassandraMap.get(entry.getKey()).getHeader().equalsIgnoreCase(Documents.HWID.getString())) {
									String docCode = DocCodes.HWID.getString();
									String docDate = rec.getRecord().get(entry.getKey() + 1);
									submittedDocsIssued.put(docCode, docDate);
									lastSubmittedDocsIssuedIndex = entry.getKey();
								}
								if (cassandraMap.get(entry.getKey()).getHeader()
										.equalsIgnoreCase(Documents.CERTIFICATE_OF_TREATMENT_AND_DISPOSAL.getString())) {
									String docCode = DocCodes.CERTIFICATE_OF_TREATMENT_AND_DISPOSAL.getString();
									String docDate = rec.getRecord().get(entry.getKey() + 1);
									submittedDocsIssued.put(docCode, docDate);
									lastSubmittedDocsIssuedIndex = entry.getKey();

								}
								if (cassandraMap.get(entry.getKey()).getHeader().equalsIgnoreCase(Documents.WATER_BILLING.getString())) {
									String docCode = DocCodes.WATER_BILLING.getString();
									String docDate = rec.getRecord().get(entry.getKey() + 1);
									submittedDocsIssued.put(docCode, docDate);
									lastSubmittedDocsIssuedIndex = entry.getKey();
								}
							}

						} else if (cassandraMap.get(entry.getKey()).getField().equalsIgnoreCase(DBFields.AMT_PAID.getString())) {
							String fee = cassandraMap.get(entry.getKey()).getHeader();
							double amt = Double.parseDouble(entry.getValue());
							amountPaid.put(fee, amt);
							lastAmountPaidIndex = entry.getKey();
						} else {
							cassandraData.put(entry.getKey(),
									new Data(cassandraMap.get(entry.getKey()).getField(), entry.getValue()));
						}
					}
				} catch (Exception e2) {
					// TODO: handle exception
					// e2.printStackTrace();
				}
			}
			if (submittedDocsValidity.size() != 0) {
				cassandraData.put(lastSubmittedDocsValidityIndex,
						new Data(DBFields.SUBMITTED_DOCS_VALIDITY.getString(), submittedDocsValidity));
			}
			if (submittedDocsIssued.size() != 0) {
				cassandraData.put(lastSubmittedDocsIssuedIndex, new Data(DBFields.SUBMITTED_DOCS_ISSUED.getString(), submittedDocsIssued));
			}
			if (!amountPaid.isEmpty()) {
				cassandraData.put(lastAmountPaidIndex, new Data(DBFields.AMT_PAID.getString(), amountPaid));

			}

			cassandra.add(new CassandraField(cassandraData, rec.getUuid(), dateCreated, createdBy));

			// System.out.println("");
		}
		return cassandra;
	}

	public List<CassandraField> mapDataToEPWMDMissionOrdersCassandraTable(ExcelDatabase edb,
			Map<Integer, Field> cassandraMap, String dateCreated, String createdBy) {
		List<CassandraField> cassandra = new ArrayList<>();
		for (ExcelRecord rec : edb.getExcelDatabase()) {
			Map<Integer, Data> cassandraData = new HashMap<>();
			for (Entry<Integer, String> entry : rec.getRecord().entrySet()) {
				try {
					if (!cassandraMap.get(entry.getKey()).getField().equals("")) {
						cassandraData.put(entry.getKey(),
								new Data(cassandraMap.get(entry.getKey()).getField(), entry.getValue()));
					}
				} catch (Exception e2) {
					// TODO: handle exception
					// e2.printStackTrace();
				}
			}

			cassandra.add(new CassandraField(cassandraData, rec.getUuid(), dateCreated, createdBy));

			// System.out.println("");
		}
		return cassandra;
	}

	public List<CassandraField> mapDataToEPWMDViolationsCassandraTable(ExcelDatabase edb,
			Map<Integer, Field> cassandraMap, String dateCreated, String createdBy) {
		List<CassandraField> cassandra = new ArrayList<>();
		for (ExcelRecord rec : edb.getExcelDatabase()) {
			Map<Integer, Data> cassandraData = new HashMap<>();
			for (Entry<Integer, String> entry : rec.getRecord().entrySet()) {
				try {
					if (!cassandraMap.get(entry.getKey()).getField().equals("")) {
						cassandraData.put(entry.getKey(),
								new Data(cassandraMap.get(entry.getKey()).getField(), entry.getValue()));
					}
				} catch (Exception e2) {
					// TODO: handle exception
					// e2.printStackTrace();
				}
			}

			cassandra.add(new CassandraField(cassandraData, rec.getUuid(), dateCreated, createdBy));

			// System.out.println("");
		}
		return cassandra;
	}
}
