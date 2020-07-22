package com.hotelbooking.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.hotelbooking.exception.InvalidDateException;
import com.hotelbooking.exception.InvalidRoomTypeException;
import com.hotelbooking.exception.NoAvailableRoomException;
import com.hotelbooking.model.BookingDetail;
import com.hotelbooking.model.Room;
import com.hotelbooking.model.User;
import com.hotelbooking.repository.BookingDetailRepository;
import com.hotelbooking.repository.RoomRepository;
import com.hotelbooking.repository.UserRepository;
import com.hotelbooking.request.BookingRequest;
import com.hotelbooking.response.Response;
import com.hotelbooking.response.ResponseBuilder;
import com.hotelbooking.service.BookingService;
import com.hotelbooking.service.helper.BookingServiceHelper;
import com.hotelbooking.service.helper.RoomValidator;
import com.hotelbooking.utils.CommonUtils;
import com.hotelbooking.utils.DateUtility;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

@Service
public class BookingServiceImpl implements BookingService {

	Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private BookingDetailRepository bookingDetailsRepository;

	private static Map<Long, Lock> roomLock = new WeakHashMap<>();

	private static Map<Long, String> fromDateDetailsMap = new WeakHashMap<>();

	private static Map<Long, String> toDateDetailsMap = new WeakHashMap<>();

	/**
	 * Book the room for the booking request
	 */
	@Override
	public Response bookRoom(BookingRequest bookingRequest) {
		Response response;
		try {
			validateRoom(bookingRequest);

			User user = getBookingServiceHelper().getUser(bookingRequest, userRepository);
			Date fromDate = DateUtility.getDateFromString(bookingRequest.getFromDate());
			Date toDate = DateUtility.getDateFromString(bookingRequest.getToDate());
			DateUtility.validateDate(fromDate, toDate);

			BookingDetail bookingDetails = bookRoom(bookingRequest.getFromDate(), bookingRequest.getToDate(),
					bookingRequest.getRoomType(), user);

			response = ResponseBuilder.buildSucessResponse(bookingDetails);
		} catch (Exception e) {
			logger.error(e.getMessage());
			response = ResponseBuilder.buildFailureResponse(e.getMessage());
		}
		return response;
	}

	/**
	 * 
	 * @param bookingRequest
	 * @throws InvalidRoomTypeException
	 */
	private void validateRoom(BookingRequest bookingRequest) throws InvalidRoomTypeException {
		String roomType = bookingRequest.getRoomType();
		List<Room> roomList = roomRepository.findByType(roomType);
		getRoomValidator().validate(roomType, roomList);
	}

	/**
	 * 
	 * @param user
	 * @param fromDate
	 * @param toDate
	 * @param roomType
	 * @throws InvalidDateException
	 * @throws InterruptedException
	 * @throws Exception
	 * @throws NoAvailableRoomException
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public BookingDetail bookRoom(String fromDate, String toDate, String roomType, User user)
			throws NoAvailableRoomException, InvalidDateException, InterruptedException {

		List<Room> roomList = roomRepository.findByType(roomType);

		Room availableRoom = getTheAvailableRoom(fromDate, toDate, roomList);

		if (CommonUtils.isEmptyOrNullObject(availableRoom)) {
			throw new NoAvailableRoomException();
		}

		BookingDetail bookingDetail = getBookingServiceHelper().buildBookingDetails(
				DateUtility.getDateFromString(fromDate), DateUtility.getDateFromString(toDate), user, availableRoom);

		return bookingDetailsRepository.save(bookingDetail);
	}

	/**
	 * 
	 * @param fromDateInString
	 * @param toDateInString
	 * @param roomList
	 * @return
	 * @throws InvalidDateException
	 * @throws InterruptedException
	 */
	private Room getTheAvailableRoom(String fromDateInString, String toDateInString, List<Room> roomList)
			throws InvalidDateException, InterruptedException {
		Room availableRoom = null;
		for (Room room : roomList) {
			availableRoom = checkForRoomAvailability(fromDateInString, toDateInString, room);
			if (!CommonUtils.isEmptyOrNullObject(availableRoom)) {
				break;
			}
		}
		return availableRoom;
	}

	/**
	 * 
	 * @param fromDateInString
	 * @param toDateInString
	 * @param room
	 * @return
	 * @throws InvalidDateException
	 * @throws InterruptedException
	 */
	private Room checkForRoomAvailability(String fromDateInString, String toDateInString, Room room)
			throws  InterruptedException {
		Room roomToBook = null;
		Lock lock = getLock(fromDateInString, toDateInString, room.getId());
		if (lock.tryLock(2, TimeUnit.MINUTES)) {
			try {
				List<BookingDetail> bookingDetailList = bookingDetailsRepository.findByRoomIdAndDateRange(room.getId(),
						DateUtility.getDateFromString(fromDateInString), DateUtility.getDateFromString(toDateInString));
				if (CommonUtils.isEmptyOrNullCollection(bookingDetailList)) {
					roomToBook = room;
				}
			} catch (Exception e) {
				logger.error("Error: ", e);
			} finally {
				lock.unlock();
			}
		} else {
			roomToBook = null;
		}
		return roomToBook;
	}

	/**
	 * 
	 * @param fromDateInString
	 * @param toDateInString
	 * @param roomId
	 * @return
	 */
	private Lock getLock(String fromDateInString, String toDateInString, long roomId) {
		boolean isAvailableInGivenRange = false;
		if (fromDateDetailsMap.containsKey(roomId) && toDateDetailsMap.containsKey(roomId)) {
			isAvailableInGivenRange = DateUtility.checkInRange(fromDateInString, toDateInString,
					fromDateDetailsMap.get(roomId), toDateDetailsMap.get(roomId));
		}

		if (!roomLock.containsKey(roomId) && !isAvailableInGivenRange) {
			roomLock.put(roomId, new ReentrantLock());
			fromDateDetailsMap.put(roomId, fromDateInString);
			toDateDetailsMap.put(roomId, toDateInString);
		}

		return roomLock.get(roomId);
	}

	/**
	 * This method implementation used to find the room availability based on room
	 * type from date and to date
	 *
	 * @param type
	 * @param fromDateInString
	 * @param toDateInString
	 */
	@Override
	public Response checkAvailability(String type, String fromDateInString, String toDateInString) {
		Response response;
		try {
			logger.debug("Checking the RoomAvailability for type: {} and for duration fromData : {} , toDate : {}",
					type, fromDateInString, toDateInString);

			DateUtility.validateDate(fromDateInString, toDateInString);

			List<Room> roomList = roomRepository.findByType(type);

			if (CommonUtils.isEmptyOrNullCollection(roomList)) {
				throw new InvalidRoomTypeException();
			}

			Date fromDate = DateUtility.getDateFromString(fromDateInString);
			Date toDate = DateUtility.getDateFromString(toDateInString);

			List<BookingDetail> listOfBookingDetailsForTheRoomType = getBookingServiceHelper()
					.fetchBookedDetails(roomList, fromDate, toDate, bookingDetailsRepository);

			response = getBookingServiceHelper().frameResponse(type, fromDate, toDate,
					listOfBookingDetailsForTheRoomType);

		} catch (Exception e) {
			logger.error(e.getMessage());
			response = ResponseBuilder.buildFailureResponse(e.getMessage());
		}
		logger.debug("Response : {} ", response);
		return response;

	}

	public RoomValidator getRoomValidator() {
		return new RoomValidator();
	}

	public BookingServiceHelper getBookingServiceHelper() {
		return new BookingServiceHelper();
	}

}
