package com.hotelbooking.service.impl;

import java.util.List;

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
import com.hotelbooking.service.PriceService;
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
public class PriceServiceImpl implements PriceService {

	Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);

	@Autowired
	private PriceDetailRepository priceDetailRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Override
	public Response<?> addPriceDetails(PriceDetail priceDetail) {
		Response<?> response;
		try {
			DateUtility.validateDate(priceDetail.getFromDate(), priceDetail.getToDate());

			List<Room> roomList = roomRepository.findByType(priceDetail.getRoomType());
			getRoomValidator().validate(priceDetail.getRoomType(), roomList);

			Room room = roomRepository.findById(priceDetail.getRoomId());
			getRoomValidator().validate(room, priceDetail.getRoomId());

			PriceDetail priceDetails = priceDetailRepository.save(priceDetail);

			response = ResponseBuilder.buildSucessResponse(priceDetails);
		} catch (Exception exception) {
			logger.error("Error :", exception);
			response = ResponseBuilder.buildFailureResponse(exception.getMessage());
		}
		return response;

	}

	public RoomValidator getRoomValidator() {
		return new RoomValidator();
	}

}
