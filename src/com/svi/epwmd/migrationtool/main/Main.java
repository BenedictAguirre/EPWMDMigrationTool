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
		List<SolrField> solr = m.mapDataToSolrFields(edb, solrMap);
		for (SolrField solrField : solr) {
			s.uploadToSolr(solrField, SOLR_URL);
			System.out.println("Added: " + solrField.getUuid() + "to Solr");
		}
		List<CassandraField> qcBizFields = m.mapDataToQCBizCassandraTable(edb, cassQCBizMap, DATE_CREATED, CREATED_BY);
		for (CassandraField cassandraField : qcBizFields) {
			c.uploadToCassandra(cassandraField, CASSANDRA_SERVER, CASSANDRA_KEYSPACE, CASSANDRA_QC_BIZ);
			System.out.println("uploaded: " + cassandraField.getUuid());
		}
		List<CassandraField> ecComplianceFields = m.mapDataToECComplianceCassandraTable(edb, cassECCOmplianceMap,
				DATE_CREATED, CREATED_BY);
		for (CassandraField cassandraField : ecComplianceFields) {
			c.uploadToCassandra(cassandraField, CASSANDRA_SERVER, CASSANDRA_KEYSPACE, CASSANDRA_EC_COMPLIANCE);
		}
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
		List<CassandraField> violationsFields = m.mapDataToEPWMDViolationsCassandraTable(edb, cassEPWMDViolations,
				DATE_CREATED, CREATED_BY);
		for (CassandraField cassandraField : violationsFields) {
			try {
				c.uploadToCassandra(cassandraField, CASSANDRA_SERVER, CASSANDRA_KEYSPACE, CASSANDRA_EPWMD_VIOLATIONS);
				System.out.println("uploaded: " + cassandraField.getUuid());
			} catch (Exception e2) {
				// TODO: handle exception
			}

		}
	}

}
