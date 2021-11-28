/**
 * 
 */
package com.gcp.bq.view.exception;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.RequiredArgsConstructor;

/**
 * @author Shaikh Ahmed Reza
 *
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity handleConstraintViolationException(
			ConstraintViolationException constraintViolationException) {
		Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
		String errorMessage = "";
		if (!violations.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			violations.forEach(violation -> builder.append(" " + violation.getMessage()));
			errorMessage = builder.toString();
		} else {
			errorMessage = "ConstraintViolationException occured.";
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {

			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ErrorDetails err = new ErrorDetails(new Timestamp(System.currentTimeMillis()), "AMP-ACK-EX500",
				HttpStatus.BAD_REQUEST, errors);
		return handleExceptionInternal(ex, err, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Object> handleValidationException(ValidationException ex) {
		List<String> errors = new ArrayList<String>();
		errors.add(ex.getMessage());

		ErrorDetails err = new ErrorDetails(new Timestamp(System.currentTimeMillis()), "AMP-ACK-EX500",
				HttpStatus.BAD_REQUEST, errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}