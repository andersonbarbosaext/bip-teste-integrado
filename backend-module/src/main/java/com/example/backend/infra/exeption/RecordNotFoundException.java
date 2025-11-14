package com.example.backend.infra.exeption;



public class RecordNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RecordNotFoundException() {
		super("Not found");
	}

	public RecordNotFoundException(final Exception exception) {
		super(exception);
	}

	public RecordNotFoundException(final String message) {
		super(message);
	}

	public RecordNotFoundException(final String message, final Exception exception) {
		super(message, exception);
	}

}