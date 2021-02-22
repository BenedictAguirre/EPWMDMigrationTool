package com.svi.epwmd.migrationtool.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.svi.epwmd.migrationtool.config.Config;
import com.svi.epwmd.migrationtool.objects.CassandraField;
import com.svi.epwmd.migrationtool.objects.ExcelDatabase;
import com.svi.epwmd.migrationtool.objects.Field;
import com.svi.epwmd.migrationtool.objects.SolrField;
import com.svi.epwmd.migrationtool.utils.CassandraUtil;
import com.svi.epwmd.migrationtool.utils.ExcelUtil;
import com.svi.epwmd.migrationtool.utils.MapperUtil;
import com.svi.epwmd.migrationtool.utils.SolrUtil;

public class Main {
	private static final String CASSANDRA_SERVER = Config.getProperty("CASSANDRA_SERVER");
	private static final String CASSANDRA_KEYSPACE = Config.getProperty("CASSANDRA_KEYSPACE");
	private static final String CASSANDRA_QC_BIZ = Config.getProperty("CASSANDRA_QC_BIZ");
	private static final String CASSANDRA_EC_COMPLIANCE = Config.getProperty("CASSANDRA_EC_COMPLIANCE");
	private static final String CASSANDRA_EPWMD_MISSION_ORDERS = Config.getProperty("CASSANDRA_EPWMD_MISSION_ORDERS");
	private static final String CASSANDRA_EPWMD_VIOLATIONS = Config.getProperty("CASSANDRA_EPWMD_VIOLATIONS");
	private static final String CASSANDRA_EPWMD_SOLID_WASTE = Config.getProperty("CASSANDRA_EPWMD_SOLID_WASTE");
	private static final String CASSANDRA_EPWMD_HAZARDOUS_WASTE = Config.getProperty("CASSANDRA_EPWMD_HAZARDOUS_WASTE");
	private static final String CASSANDRA_EPWMD_STRUCTURAL_REQUIREMENTS = Config
			.getProperty("CASSANDRA_EPWMD_STRUCTURAL_REQUIREMENTS");
	private static final String COLUMN_MAP_INDEX = Config.getProperty("COLUMN_MAP_INDEX");
	private static final String HEADER_MAP_INDEX = Config.getProperty("HEADER_MAP_INDEX");
	private static final String SOLR_MAP_INDEX = Config.getProperty("SOLR_MAP_INDEX");
	private static final String CASSANDRA_QC_BIZ_INDEX = Config.getProperty("CASSANDRA_QC_BIZ_INDEX");
	private static final String CASSANDRA_EC_COMPLIANCE_MAP_INDEX = Config
			.getProperty("CASSANDRA_EC_COMPLIANCE_MAP_INDEX");
	private static final String CASSANDRA_EPWMD_MISSION_ORDERS_INDEX = Config
			.getProperty("CASSANDRA_EPWMD_MISSION_ORDERS_INDEX");
	private static final String CASSANDRA_EPWMD_VIOLATIONS_INDEX = Config
			.getProperty("CASSANDRA_EPWMD_VIOLATIONS_INDEX");
	private static final String CASSANDRA_EPWMD_SOLID_WASTE_INDEX = Config
			.getProperty("CASSANDRA_EPWMD_SOLID_WASTE_INDEX");
	private static final String CASSANDRA_EPWMD_HAZARDOUS_WASTE_INDEX = Config
			.getProperty("CASSANDRA_EPWMD_HAZARDOUS_WASTE_INDEX");
	private static final String CASSANDRA_EPWMD_STRUCTURAL_REQUIREMENTS_INDEX = Config
			.getProperty("CASSANDRA_EPWMD_STRUCTURAL_REQUIREMENTS_INDEX");

	private static final String SOLR_URL = Config.getProperty("SOLR_URL");
	private static final String EXCEL_DATABASE = Config.getProperty("EXCEL_DB");
	private static final String FIELD_MAPPING = Config.getProperty("FIELD_MAP");
	private static final String START_ROW = Config.getProperty("START_ROW");
	private static final String DATE_CREATED = convertStringDateToMillis(Config.getProperty("DATE_CREATED"));
	private static final String CREATED_BY = Config.getProperty("CREATED_BY");

	private static String convertStringDateToMillis(String cellValue) {
		String millisDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date;
		try {
			date = sdf.parse(cellValue);
			long millis = date.getTime();
			millisDate = Long.toString(millis);
		} catch (ParseException e) {
			return "";
		}
		return millisDate;

	}

	public static void main(String[] args) {
		MapperUtil m = new MapperUtil();
		ExcelUtil e = new ExcelUtil();
		SolrUtil s = new SolrUtil();
		CassandraUtil c = new CassandraUtil();
		ExcelDatabase edb = e.getExcelDatabase(EXCEL_DATABASE, Integer.parseInt(START_ROW.trim()));
		Map<Integer, Field> solrMap = e.getMapping(FIELD_MAPPING, COLUMN_MAP_INDEX, HEADER_MAP_INDEX, SOLR_MAP_INDEX);
		Map<Integer, Field> cassQCBizMap = e.getMapping(FIELD_MAPPING, COLUMN_MAP_INDEX, HEADER_MAP_INDEX,
				CASSANDRA_QC_BIZ_INDEX);
		Map<Integer, Field> cassECCOmplianceMap = e.getMapping(FIELD_MAPPING, COLUMN_MAP_INDEX, HEADER_MAP_INDEX,
				CASSANDRA_EC_COMPLIANCE_MAP_INDEX);
		Map<Integer, Field> cassEPWMDMissionOrdersMap = e.getMapping(FIELD_MAPPING, COLUMN_MAP_INDEX, HEADER_MAP_INDEX,
				CASSANDRA_EPWMD_MISSION_ORDERS_INDEX);
		Map<Integer, Field> cassEPWMDViolations = e.getMapping(FIELD_MAPPING, COLUMN_MAP_INDEX, HEADER_MAP_INDEX,
				CASSANDRA_EPWMD_VIOLATIONS_INDEX);
		Map<Integer, Field> cassEPWMDSolidWaste = e.getMapping(FIELD_MAPPING, COLUMN_MAP_INDEX, HEADER_MAP_INDEX,
				CASSANDRA_EPWMD_SOLID_WASTE_INDEX);
		Map<Integer, Field> cassEPWMDHazardousWaste = e.getMapping(FIELD_MAPPING, COLUMN_MAP_INDEX, HEADER_MAP_INDEX,
				CASSANDRA_EPWMD_HAZARDOUS_WASTE_INDEX);
		Map<Integer, Field> cassEPWMDStructuralRequirements = e.getMapping(FIELD_MAPPING, COLUMN_MAP_INDEX,
				HEADER_MAP_INDEX, CASSANDRA_EPWMD_STRUCTURAL_REQUIREMENTS_INDEX);
		if (!SOLR_URL.isEmpty()) {
			List<SolrField> solr = m.mapDataToSolrFields(edb, solrMap);
			for (SolrField solrField : solr) {
				s.uploadToSolr(solrField, SOLR_URL);
				System.out.println("Added: " + solrField.getUuid() + "to Solr");
			}
		}
		if (!CASSANDRA_QC_BIZ.isEmpty()) {
			List<CassandraField> qcBizFields = m.mapDataToQCBizCassandraTable(edb, cassQCBizMap, DATE_CREATED,
					CREATED_BY);
			for (CassandraField cassandraField : qcBizFields) {
				c.uploadToCassandra(cassandraField, CASSANDRA_SERVER, CASSANDRA_KEYSPACE, CASSANDRA_QC_BIZ);
				System.out.println("uploaded: " + cassandraField.getUuid());
			}
		}
		if (!CASSANDRA_EC_COMPLIANCE.isEmpty()) {
			List<CassandraField> ecComplianceFields = m.mapDataToECComplianceCassandraTable(edb, cassECCOmplianceMap,
					DATE_CREATED, CREATED_BY);
			for (CassandraField cassandraField : ecComplianceFields) {
				c.uploadToCassandra(cassandraField, CASSANDRA_SERVER, CASSANDRA_KEYSPACE, CASSANDRA_EC_COMPLIANCE);
				System.out.println("uploaded: " + cassandraField.getUuid());
			}
		}
		if (!CASSANDRA_EPWMD_MISSION_ORDERS.isEmpty()) {
			List<CassandraField> missionOrdersFields = m.mapDataToEPWMDMissionOrdersCassandraTable(edb,
					cassEPWMDMissionOrdersMap, DATE_CREATED, CREATED_BY);
			for (CassandraField cassandraField : missionOrdersFields) {
				try {
					c.uploadToCassandra(cassandraField, CASSANDRA_SERVER, CASSANDRA_KEYSPACE,
							CASSANDRA_EPWMD_MISSION_ORDERS);
					System.out.println("uploaded: " + cassandraField.getUuid());
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		if (!CASSANDRA_EPWMD_VIOLATIONS.isEmpty()) {
			List<CassandraField> violationsFields = m.mapDataToEPWMDViolationsCassandraTable(edb, cassEPWMDViolations,
					DATE_CREATED, CREATED_BY);
			for (CassandraField cassandraField : violationsFields) {
				try {
					c.uploadToCassandra(cassandraField, CASSANDRA_SERVER, CASSANDRA_KEYSPACE,
							CASSANDRA_EPWMD_VIOLATIONS);
					System.out.println("uploaded: " + cassandraField.getUuid());
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		}
		if (!CASSANDRA_EPWMD_SOLID_WASTE.isEmpty()) {
			List<CassandraField> solidWasteFields = m.mapDataToEPWMDSolidWastesCassandraTable(edb,
					cassEPWMDSolidWaste, DATE_CREATED, CREATED_BY);
			for (CassandraField cassandraField : solidWasteFields) {
				try {
					c.uploadToCassandra(cassandraField, CASSANDRA_SERVER, CASSANDRA_KEYSPACE,
							CASSANDRA_EPWMD_SOLID_WASTE);
					System.out.println("uploaded: " + cassandraField.getUuid());
				} catch (Exception e2) {
					
					// TODO: handle exception
				}

			}
		}
		if (!CASSANDRA_EPWMD_HAZARDOUS_WASTE.isEmpty()) {
			List<CassandraField> hazardousWasteFields = m.mapDataToEPWMDHazardousWastesCassandraTable(edb,
					cassEPWMDHazardousWaste, DATE_CREATED, CREATED_BY);
			for (CassandraField cassandraField : hazardousWasteFields) {
				try {
					c.uploadToCassandra(cassandraField, CASSANDRA_SERVER, CASSANDRA_KEYSPACE,
							CASSANDRA_EPWMD_HAZARDOUS_WASTE);
					System.out.println("uploaded: " + cassandraField.getUuid());
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		}
		if (!CASSANDRA_EPWMD_STRUCTURAL_REQUIREMENTS.isEmpty()) {
			List<CassandraField> structuralRequirementsFields = m.mapDataToEPWMDStructuralRequirementsCassandraTable(
					edb, cassEPWMDStructuralRequirements, DATE_CREATED, CREATED_BY);
			for (CassandraField cassandraField : structuralRequirementsFields) {
				try {
					c.uploadToCassandra(cassandraField, CASSANDRA_SERVER, CASSANDRA_KEYSPACE,
							CASSANDRA_EPWMD_STRUCTURAL_REQUIREMENTS);
					System.out.println("uploaded: " + cassandraField.getUuid());
				} catch (Exception e2) {
					e2.printStackTrace();
					// TODO: handle exception
				}

			}
		}

	}

}
