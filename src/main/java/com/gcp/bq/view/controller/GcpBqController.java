/**
 * 
 */
package com.gcp.bq.view.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcp.bq.view.dto.BqViewRequest;
import com.gcp.bq.view.response.Document;
import com.gcp.bq.view.service.GcpBqService;

/**
 * @author Shaikh Ahmed Reza
 *
 */
@RestController
@RequestMapping("/api/v1/gcp")
public class GcpBqController {

	@Autowired
	private GcpBqService bqService;

	@PostMapping("/create-authorized-view")
	public ResponseEntity<Document> createAuthorizedView(@Valid @RequestBody BqViewRequest bqViewRequest) {

		Document response = bqService.createAuthorizedView(bqViewRequest);

		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
}
