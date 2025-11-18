package com.example.backend.infra.exeption;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;


public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private final HttpStatus httpStatus;

	private final List<FieldError> fieldErrors;

	public BusinessException(final Exception exception) {
		super(exception);
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		this.fieldErrors = new ArrayList<>();
	}

	public BusinessException(final Exception exception, final HttpStatus httpStatus) {
		super(exception);
		this.httpStatus = httpStatus;
		this.fieldErrors = new ArrayList<>();
	}

	public BusinessException(final String message) {
		super(message);
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		this.fieldErrors = new ArrayList<>();
	}

	public BusinessException(final String message, final Exception exception) {
		super(message, exception);
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		this.fieldErrors = new ArrayList<>();
	}

	public BusinessException(final String message, final Exception exception, final HttpStatus httpStatus) {
		super(message, exception);
		this.httpStatus = httpStatus;
		this.fieldErrors = new ArrayList<>();
	}

	public BusinessException(final String message, final HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
		this.fieldErrors = new ArrayList<>();
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

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

}
