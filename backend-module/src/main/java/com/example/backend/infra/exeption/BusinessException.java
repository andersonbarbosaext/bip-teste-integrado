package com.example.backend.infra.exeption;

import java.util.List;

import org.springframework.http.HttpStatus;


public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

	private List<FieldError> fieldErrors;

	public BusinessException(final Exception exception) {
		super(exception);
	}

	public BusinessException(final Exception exception, final HttpStatus httpStatus) {
		super(exception);
		this.httpStatus = httpStatus;
	}

	public BusinessException(final String message) {
		super(message);
	}

	public BusinessException(final String message, final Exception exception) {
		super(message, exception);
	}

	public BusinessException(final String message, final Exception exception, final HttpStatus httpStatus) {
		super(message, exception);
		this.httpStatus = httpStatus;
	}

	public BusinessException(final String message, final HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public BusinessException(final List<FieldError> fieldErrors, final HttpStatus httpStatus) {
		super(HttpStatus.BAD_REQUEST.getReasonPhrase());
		this.fieldErrors = fieldErrors;
		this.httpStatus = httpStatus;
	}

	public BusinessException(final String message, final List<FieldError> fieldErrors, final HttpStatus httpStatus) {
		super(message);
		this.fieldErrors = fieldErrors;
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}
