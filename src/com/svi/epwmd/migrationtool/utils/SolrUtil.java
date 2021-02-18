package com.svi.epwmd.migrationtool.utils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.svi.epwmd.migrationtool.objects.Data;
import com.svi.epwmd.migrationtool.objects.ExcelDatabase;
import com.svi.epwmd.migrationtool.objects.SolrField;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;



public class SolrUtil {
	public void uploadToSolr(SolrField solrField, String urlString) {
		SolrClient Solr = new HttpSolrClient.Builder(urlString).build();
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("establishment_id", solrField.getUuid());
		for (Entry<Integer, Data> solrInput : solrField.getSolrData().entrySet()) {
			doc.addField(solrInput.getValue().getField(), solrInput.getValue().getValue());
		}
		try {
			Solr.add(doc);
			Solr.commit();
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
