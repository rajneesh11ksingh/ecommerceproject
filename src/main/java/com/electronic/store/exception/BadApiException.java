package com.electronic.store.exception;

public class BadApiException extends RuntimeException {
     
	public BadApiException(String message) {
		super(message);
	}
	
	public BadApiException() {
		super("Bad Request !!");
	}
}
