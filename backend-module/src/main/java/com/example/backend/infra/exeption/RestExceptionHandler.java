package com.example.backend.infra.exeption;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


	private ResponseEntity<ErrorResponse> createResponseEntity(final Exception exception, final WebRequest request, final HttpStatus status) {
		ErrorResponse error = new ErrorResponse(); 
		error.setMessage(exception.getMessage());
		error.setDetails(request.getDescription(false));
		error.setTimestamp(LocalDateTime.now());				

		return new ResponseEntity<>(error, status);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusiness(final BusinessException ex, final WebRequest request) {
		ErrorResponse error =  new ErrorResponse(); 
		error.setMessage(ex.getMessage()); 
		error.setDetails(request.getDescription(false));
		error.setTimestamp(LocalDateTime.now()); 
		error.setFieldErrors(ex.getFieldErrors());				
		return new ResponseEntity<>(error, ex.getHttpStatus());
	}


	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgument(final IllegalArgumentException ex, final WebRequest request) {
		return createResponseEntity(ex, request, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleRecordNotFound(final RecordNotFoundException ex, final WebRequest request) {
		return createResponseEntity(ex, request, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> otherErrors(final Exception ex, final WebRequest request) {		
		return createResponseEntity(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

