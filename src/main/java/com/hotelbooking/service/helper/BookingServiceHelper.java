package com.hotelbooking.service.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotelbooking.exception.InvalidUserException;
import com.hotelbooking.model.BookingDetail;
import com.hotelbooking.model.Room;
import com.hotelbooking.model.User;
import com.hotelbooking.repository.BookingDetailRepository;
import com.hotelbooking.repository.UserRepository;
import com.hotelbooking.request.BookingRequest;
import com.hotelbooking.response.Response;
import com.hotelbooking.response.ResponseBuilder;
import com.hotelbooking.utils.CommonUtils;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

public class BookingServiceHelper {

	Logger logger = LoggerFactory.getLogger(BookingServiceHelper.class);

	/**
	 * Builds the Success / Failure response.
	 * 
	 * @param type
	 *            -- Type of the room
	 * @param fromDate
	 *            -- Start date of the duration.
	 * @param toDate
	 *            -- End date of the duration.
	 * @param bookedDetailList
	 *            -- List of booked details for the room type and for the given
	 *            duration
	 * @return Response -- 1) Success -- If the room with the type was available. --
	 *         2) Failure -- If there is no room available for the type.
	 */
	public Response frameResponse(String type, Date fromDate, Date toDate, List<BookingDetail> bookedDetailList) {
		if (CommonUtils.isEmptyOrNullCollection(bookedDetailList)) {
			return ResponseBuilder.buildSucessResponse(
					type + " type room is avilable for the given duration : " + fromDate + " - " + toDate);
		} else {
			return ResponseBuilder
					.buildFailureResponse(type + " type room is not avilable from: " + fromDate + " to " + toDate);
		}
	}

	/**
	 * Fetch the booking details for the room type and for the duration.
	 * 
	 * @param roomList
	 *            -- List of same typed rooms.
	 * @param fromDate
	 *            -- Start date of the duration.
	 * @param toDate
	 *            -- End date of the duration.
	 * @param bookingDetailsRepository
	 *            -- Booking detail repository.
	 * @return List<BookingDetail> -- List of booking detail for the type and the
	 *         duration -- Empty if there is no details available for the room.
	 */
	public List<BookingDetail> fetchBookedDetails(List<Room> roomList, Date fromDate, Date toDate,
			BookingDetailRepository bookingDetailsRepository) {
		List<BookingDetail> bookingDetailsForTheRoomType = new ArrayList<>();
		for (Room room : roomList) {
			List<BookingDetail> roomBookingList = bookingDetailsRepository.findByRoomIdAndDateRange(room.getId(),
					fromDate, toDate);
			bookingDetailsForTheRoomType.addAll(roomBookingList);
		}
		return bookingDetailsForTheRoomType;
	}

	/**
	 * Build the booking detail information.
	 * 
	 * @param fromDate
	 *            -- Start date of the duration.
	 * @param toDate
	 *            -- End date of the duration.
	 * @param user
	 *            -- Booked user.
	 * @param room
	 *            -- Booked room.
	 * @return BookingDetail -- Booking detail information.
	 */
	public BookingDetail buildBookingDetails(Date fromDate, Date toDate, User user, Room room) {
		BookingDetail bookingDetails = new BookingDetail();
		bookingDetails.setUserId(user.getUserId());
		bookingDetails.setFromDate(fromDate);
		bookingDetails.setToDate(toDate);
		bookingDetails.setUserName(user.getUserName());
		bookingDetails.setRoomId(room.getId());
		return bookingDetails;
	}

	/**
	 * Gets the user and validates it
	 * 
	 * @param bookingRequest
	 *            -- Booking request
	 * @param userRepository
	 *            -- Repository of the user details.
	 * @throws InvalidUserException
	 *             -- If the user is null or empty.
	 * @return user -- User information for the given user name.
	 */
	public User getUser(BookingRequest bookingRequest, UserRepository userRepository) throws InvalidUserException {

		User user = userRepository.findByUserName(bookingRequest.getUserName());
		if (user == null) {
			logger.debug("invalid user " + bookingRequest.getUserName());
			throw new InvalidUserException();
		}
		return user;
	}
}
