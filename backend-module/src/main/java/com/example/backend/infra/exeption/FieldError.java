package com.example.backend.infra.exeption;

import java.io.Serializable;

public class FieldError implements Serializable {

	private static final long serialVersionUID = 394062597147148641L;

	private String field;
	
	private String error;
	
	public FieldError(String field, String error) {
		super();
		this.field = field;
		this.error = error;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}