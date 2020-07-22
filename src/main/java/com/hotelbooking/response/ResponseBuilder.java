package com.hotelbooking.response;

import com.hotelbooking.constants.Constants;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

public class ResponseBuilder {

	/**
	 * private constructor to prevent the object creation.
	 */
	private ResponseBuilder() {

	}

	/**
	 * @Purpose - Build the success response with the given message.
	 * @param message
	 *            -- Message of the response.
	 * @return Response -- success response with the given message.
	 */
	public static <T> Response<T> buildSucessResponse(T message) {
		Response<T> response = new Response<>();
		response.setMessage(message);
		response.setStatus(Constants.SUCCESS);
		return response;
	}

	/**
	 * @purpose - Build the failure response with the given message.
	 * @param message
	 *            -- Message of the response.
	 * @return Response -- Failure response with the given message.
	 */
	public static <T> Response<T> buildFailureResponse(T message) {
		Response<T> response = new Response<>();
		response.setMessage(message);
		response.setStatus(Constants.FAILURE);
		return response;
	}
}
