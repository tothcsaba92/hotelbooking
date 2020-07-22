package com.hotelbooking.service.impl;

import java.util.List;

import com.hotelbooking.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelbooking.model.PriceDetail;
import com.hotelbooking.model.Room;
import com.hotelbooking.repository.PriceDetailRepository;
import com.hotelbooking.repository.RoomRepository;
import com.hotelbooking.response.Response;
import com.hotelbooking.response.ResponseBuilder;
import com.hotelbooking.service.helper.PriceCalculator;
import com.hotelbooking.service.helper.RoomValidator;
import com.hotelbooking.utils.DateUtility;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

@Service
public class RoomServiceImpl implements RoomService {

	Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private PriceDetailRepository priceDetailRepository;

	/**
	 * This method implementation used to retrieve all room details including
	 * pricing,type and rating
	 * 
	 */
	@Override
	public Response getRooms() {
		Response response;
		try {
			logger.debug("Getting the room details");
			List<Room> roomList = roomRepository.findAll();
			response = ResponseBuilder.buildSucessResponse(roomList);
			logger.debug("Successfully got the rooms : {}", roomList);
		} catch (Exception e) {
			logger.error("Error: ", e);
			response = ResponseBuilder.buildFailureResponse(e.getMessage());
		}
		return response;
	}

	/**
	 * This method implementation used to retrieve the price based on from date and
	 * to date
	 * 
	 */
	@Override
	public Response calculatetPriceByType(String type, String fromDateInString, String toDateInString) {
		Response response;
		try {
			logger.debug("Getting the room price for type : {} and duration from : {}  till : {}", type,
					fromDateInString, toDateInString);
			List<Room> roomList = roomRepository.findByType(type);

			getRoomValidator().validate(type, roomList);
			DateUtility.validateDate(fromDateInString, toDateInString);
			Room room = roomList.get(0);
			PriceDetail priceDetail = getPriceCalculator().calculate(room, fromDateInString, toDateInString,
					priceDetailRepository);
			response = ResponseBuilder.buildSucessResponse(priceDetail);
			logger.debug("Response : {} ", response);

		} catch (Exception e) {
			logger.error("Error: ", e);
			response = ResponseBuilder.buildFailureResponse(e.getMessage());
		}

		return response;
	}

	public RoomValidator getRoomValidator() {
		return new RoomValidator();
	}

	public PriceCalculator getPriceCalculator() {
		return new PriceCalculator();
	}
}
