/**
 * 
 */
package com.gcp.bq.view.service;

import com.gcp.bq.view.dto.BqViewRequest;
import com.gcp.bq.view.response.Document;

/**
 * @author Shaikh Ahmed Reza
 *
 */
public interface GcpBqService {

	Document createAuthorizedView(BqViewRequest bqViewRequest);

}
