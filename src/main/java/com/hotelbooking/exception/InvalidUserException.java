package com.hotelbooking.exception;

public class InvalidUserException extends Exception {

	/**
	 *
	 * @author csaba
	 * @version 1.0
	 * @since 20-07-2020
	 *
	 */

	private static final long serialVersionUID = -4002696843718742004L;

	public InvalidUserException() {
		super("Invalid user");
	}
}
