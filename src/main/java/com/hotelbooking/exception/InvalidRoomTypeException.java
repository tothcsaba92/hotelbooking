package com.hotelbooking.exception;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

public class InvalidRoomTypeException extends Exception {

	private static final long serialVersionUID = 4079096994929673279L;

	public InvalidRoomTypeException() {
		super("Invalid Room Type");
	}
}
