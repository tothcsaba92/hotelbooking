package com.hotelbooking.exception;

/**
 * @purpose Room was already booked Exception.
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */
public class NoAvailableRoomException extends Exception {

	private static final long serialVersionUID = -971705421507493007L;

	public NoAvailableRoomException() {
		super("Room was not available to book");
	}

}
