package com.hotelbooking.exception;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */
public class InvalidDateException extends Exception {

	private static final long serialVersionUID = 4059110895741877128L;

	public InvalidDateException() {
		super("Invalid Date");
	}

	public InvalidDateException(String message) {
		super(message);
	}
}
