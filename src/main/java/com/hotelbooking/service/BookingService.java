package com.hotelbooking.service;

import com.hotelbooking.request.BookingRequest;
import com.hotelbooking.response.Response;

/**
 * Hold the services to book the room and check the availability of the room.
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */
public interface BookingService {

	/**
	 * Book the room for the given booking request.
	 * 
	 * @param bookingRequest
	 *            -- Booking request that needs to be booked.
	 *
	 * @return Response -- 1) Success if the room was available to book for the
	 *         given duration. -- 2) Failure if the room was already booked and if
	 *         there is any error while booking.
	 */
	Response bookRoom(BookingRequest bookingRequest);

	/**
	 * Check the availability for the given room type and for the given duration.
	 * 
	 * @param type
	 *            -- Type of the room to check for the availability.
	 * @param fromDate
	 *            -- start date of the duration to check for availability.
	 * @param toDate
	 *            -- End date of the duration to check for availability.
	 *
	 * @return Response -- 1) Success If the room was available for the given
	 *         duration. -- 2) Failure If the room was not available for the given
	 *         duration and any error occurred while checking for availability.
	 */
	Response checkAvailability(String type, String fromDate, String toDate);

}
