/**
 * 
 */
package com.gcp.bq.view.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.gcp.bq.view.dto.BqViewRequest;
import com.gcp.bq.view.response.Document;
import com.google.cloud.bigquery.Acl;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.DatasetId;
import com.google.cloud.bigquery.DatasetInfo;
import com.google.cloud.bigquery.JobException;
import com.google.cloud.bigquery.Table;
import com.google.cloud.bigquery.TableId;
import com.google.cloud.bigquery.TableInfo;
import com.google.cloud.bigquery.ViewDefinition;

/**
 * @author Shaikh Ahmed Reza
 *
 */

/**
 * 
 * Giving a view access to the source dataset is also referred to as creating an
 * authorized view. When you create an authorized view, follow these steps:
 * 
 * Create a separate dataset where you can store the view. Create the view in
 * the new dataset. Assign access controls to the project. Assign access
 * controls to the dataset containing the view. Give the view access to the
 * source dataset.
 *
 */
@Service
public class GcpBqServiceImpl implements GcpBqService {

	@Override
	public Document createAuthorizedView(BqViewRequest bqViewRequest) {

		Document doc = new Document();
		try {

			// Initialize client that will be used to send requests. This client only needs
			// to be created
			// once, and can be reused for multiple requests.
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

			// Creating Shared Dataset
			DatasetInfo datasetInfo = DatasetInfo.newBuilder(bqViewRequest.getAccount() + "_shared_dataset").build();

			Dataset sharedDataset = bigquery.create(datasetInfo);

			Dataset sourceDataset = bigquery.getDataset(DatasetId.of("prj-df-deid", "deid"));

			// Create the view in the new dataset
			String sharedViewQuery = String.format("SELECT EMP_ID,First_Name,E_Mail,Gender FROM %s.%s.%s LIMIT 10",
					"prj-df-deid", "deid", "employee");

			ViewDefinition viewDefinition = ViewDefinition.of(sharedViewQuery);

			Table sharedView = bigquery.create(TableInfo.of(
					TableId.of(bqViewRequest.getAccount() + "_shared_dataset", bqViewRequest.getAccount() + "_view"),
					viewDefinition));

			// Grant BigQuery viewer access to the user on the shared dataset
			List<Acl> viewAcl = new ArrayList<>();
			viewAcl.addAll(sharedDataset.getAcl());
			viewAcl.add(Acl.of(new Acl.User(bqViewRequest.getEmail()), Acl.Role.READER));
			sharedDataset.toBuilder().setAcl(viewAcl).build().update();

			// Authorize the view to access the source dataset
			List<Acl> srcAcl = new ArrayList<>(sourceDataset.getAcl());
			srcAcl.add(Acl.of(new Acl.View(sharedView.getTableId())));
			sourceDataset.toBuilder().setAcl(srcAcl).build().update();

			doc.setData("Successfully Created Authorized View and provided access to the user");
			doc.setStatusCode(200);
			doc.setMessage("Success");

		} catch (BigQueryException ex) {
			doc.setData(ex.getCode());
			doc.setMessage(ex.getMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		} catch (JobException e) {
			doc.setData(e.getId());
			doc.setMessage(e.getMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

}
