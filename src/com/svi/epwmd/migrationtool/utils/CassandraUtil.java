package com.svi.epwmd.migrationtool.utils;

import java.util.Map;
import java.util.Map.Entry;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.Cluster;
import com.svi.epwmd.migrationtool.constants.DBFields;
import com.svi.epwmd.migrationtool.objects.CassandraField;
import com.svi.epwmd.migrationtool.objects.Data;

public class CassandraUtil {
	public void uploadToCassandra(CassandraField cassField, String cassandraServer, String keyspace, String tableName) {
		Cluster cluster = Cluster.builder().addContactPoint(cassandraServer).build();

		Session session = cluster.connect(keyspace);

		StringBuilder tableHeaderBuilder = new StringBuilder();
		StringBuilder valueBuilder = new StringBuilder();
		tableHeaderBuilder.append(DBFields.ESTABLISHMENT_ID.getString());
		valueBuilder.append("'" + cassField.getUuid() + "'");
		for (Entry<Integer, Data> entry : cassField.getCassandraData().entrySet()) {
			tableHeaderBuilder.append("," + entry.getValue().getField());
			if (entry.getValue().getField().equalsIgnoreCase(DBFields.TEL_NOS.getString())
					|| entry.getValue().getField().equalsIgnoreCase(DBFields.CELL_NOS.getString())
					|| entry.getValue().getField().equalsIgnoreCase(DBFields.EMAILS.getString())
					|| entry.getValue().getField().equalsIgnoreCase(DBFields.WORK_SCHED_DAYS.getString())) {
				String[] splits = entry.getValue().getValue().toString().split(",");
				StringBuilder listValue = new StringBuilder();
				String value;
				valueBuilder.append("," + "[");
				for (String string : splits) {
					listValue.append("'" + string.trim() + "'" + ",");
				}
				value = listValue.toString();
				if (value.endsWith(",")) {
					value = value.substring(0, value.lastIndexOf(","));
				}
				valueBuilder.append(value);
				valueBuilder.append("]");
			}else if (entry.getValue().getField().equalsIgnoreCase(DBFields.SUBMITTED_DOCS_VALIDITY.getString())
					|| entry.getValue().getField().equalsIgnoreCase(DBFields.SUBMITTED_DOCS_ISSUED.getString())
					|| entry.getValue().getField().equalsIgnoreCase(DBFields.HAULER_COLLECTION_FREQUENCY.getString())) {
				valueBuilder.append(",");
				valueBuilder.append("{");
				Map<String,String> value = null;
				if (entry.getValue().getValue()instanceof Map<?,?>) {
					value = (Map)entry.getValue().getValue();
				}
				StringBuilder mapValue = new StringBuilder();
				for (Entry<String, String> iterable_element : value.entrySet()) {
					mapValue.append("'"+iterable_element.getKey()+"'"+":"+"'"+iterable_element.getValue()+"'"+",");
				}
				String mappedValueString = mapValue.toString();
				if (mappedValueString.endsWith(",")) {
					mappedValueString = mappedValueString.substring(0, mappedValueString.lastIndexOf(","));
				}
				valueBuilder.append(mappedValueString);
				valueBuilder.append("}");
				
				
			} else if (entry.getValue().getField().equalsIgnoreCase(DBFields.AMT_PAID.getString())) {
				valueBuilder.append(",");
				valueBuilder.append("{");
				Map<String,Double> value = null;
				if (entry.getValue().getValue()instanceof Map<?,?>) {
					value = (Map)entry.getValue().getValue();
				}
				StringBuilder mapValue = new StringBuilder();
				for (Entry<String, Double> iterable_element : value.entrySet()) {
					mapValue.append("'"+iterable_element.getKey()+"'"+":"+iterable_element.getValue()+",");
				}
				String mappedValueString = mapValue.toString();
				if (mappedValueString.endsWith(",")) {
					mappedValueString = mappedValueString.substring(0, mappedValueString.lastIndexOf(","));
				}
				valueBuilder.append(mappedValueString);
				valueBuilder.append("}");
				
				
			}else if (entry.getValue().getField().equalsIgnoreCase(DBFields.DAILY_WASTE_GENERATION.getString())) {
				valueBuilder.append(",");
				valueBuilder.append("{");
				Map<String,Integer> value = null;
				if (entry.getValue().getValue()instanceof Map<?,?>) {
					value = (Map)entry.getValue().getValue();
				}
				StringBuilder mapValue = new StringBuilder();
				for (Entry<String, Integer> iterable_element : value.entrySet()) {
					mapValue.append("'"+iterable_element.getKey()+"'"+":"+iterable_element.getValue()+",");
				}
				String mappedValueString = mapValue.toString();
				if (mappedValueString.endsWith(",")) {
					mappedValueString = mappedValueString.substring(0, mappedValueString.lastIndexOf(","));
				}
				valueBuilder.append(mappedValueString);
				valueBuilder.append("}");
				
				
			}else if (entry.getValue().getField().equalsIgnoreCase(DBFields.DATE_APPLIED.getString())||
					entry.getValue().getField().equalsIgnoreCase(DBFields.DATE_ISSUED.getString())||
					entry.getValue().getField().equalsIgnoreCase(DBFields.DATE_INSPECTED.getString())||
					entry.getValue().getField().equalsIgnoreCase(DBFields.DATE_REVOKED.getString())||
					entry.getValue().getField().equalsIgnoreCase(DBFields.VALID_UNTIL.getString())||
					entry.getValue().getField().equalsIgnoreCase(DBFields.COMPLIANCE_DEADLINE.getString())||
					entry.getValue().getField().equalsIgnoreCase(DBFields.PAYMENT_DATE.getString())||
					entry.getValue().getField().equalsIgnoreCase(DBFields.TOTAL_AMOUNT_PAID.getString())||
					entry.getValue().getField().equalsIgnoreCase(DBFields.TOTAL_EMPLOYEES.getString())) {
				long val = Long.parseLong(entry.getValue().getValue().toString());
				valueBuilder.append("," + val);
			}else if (entry.getValue().getField().equalsIgnoreCase(DBFields.SEPTIC_TANK.getString())||
					entry.getValue().getField().equalsIgnoreCase(DBFields.STP_WWTF.getString())|| 
					entry.getValue().getField().equalsIgnoreCase(DBFields.SEWERAGE_CONNECTION.getString())||
					entry.getValue().getField().equalsIgnoreCase(DBFields.GREASE_TRAP.getString())|| 
					entry.getValue().getField().equalsIgnoreCase(DBFields.GREACE_INTERCEPTOR.getString())|| 
					entry.getValue().getField().equalsIgnoreCase(DBFields.OIL_WATER_SEPARATOR.getString())||
					entry.getValue().getField().equalsIgnoreCase(DBFields.MATERIALS_RECOVERY.getString())||
					entry.getValue().getField().equalsIgnoreCase(DBFields.PAINTING_BOOTH.getString())||
					entry.getValue().getField().equalsIgnoreCase(DBFields.SOUND_PROOFING.getString())) {
				boolean val = Boolean.getBoolean(entry.getValue().getValue().toString());
				valueBuilder.append("," + val);
			} else {
				valueBuilder.append("," + "'" + entry.getValue().getValue() + "'");
			}
		}
		tableHeaderBuilder.append("," + DBFields.DATE_CREATED.getString());
		valueBuilder.append("," + cassField.getDateCreated());
		tableHeaderBuilder.append("," + DBFields.CREATED_BY.getString());
		valueBuilder.append("," + "'" + cassField.getCreatedBy() + "'");
		String tableHeaderString = tableHeaderBuilder.toString();
		String valueString = valueBuilder.toString();
		String add = "INSERT INTO " + tableName + " (" + tableHeaderString + ")" + " VALUES (" + valueString + ");";
//		System.out.println(tableName+" "+add);
		session.execute(add);
	}
}
