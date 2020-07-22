package com.hotelbooking.service.helper;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotelbooking.exception.InvalidDateException;
import com.hotelbooking.model.PriceDetail;
import com.hotelbooking.model.Room;
import com.hotelbooking.repository.PriceDetailRepository;
import com.hotelbooking.utils.CommonUtils;
import com.hotelbooking.utils.DateUtility;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

public class PriceCalculator {

	Logger logger = LoggerFactory.getLogger(PriceCalculator.class);

	private static AtomicLong priceDetailsId = new AtomicLong();

	/**
	 * Calculate the price details of the room for the given duration dynamically.
	 * Price will be calculated based on the "priceDetail" table entries. If the
	 * roomId was available in the DB then that information will be used to
	 * calculate the price, else based on the actual room price the total price will
	 * be calculated.
	 * 
	 * @param room
	 *            -- Room details for which the price should be calculated.
	 * @param fromDateInString
	 *            -- Start date of the duration in string.
	 * @param toDateInString
	 *            -- End date of the duration in string.
	 * @param priceDetailRepository
	 *            -- Repository for the price details.
	 * @return PriceDetail -- Price detail that was calculated dynamically.
	 * @throws InvalidDateException
	 *             -- If the duration was invalid
	 */
	public PriceDetail calculate(Room room, String fromDateInString, String toDateInString,
			PriceDetailRepository priceDetailRepository) throws InvalidDateException {

		Date fromDate = DateUtility.getDateFromString(fromDateInString);
		Date toDate = DateUtility.getDateFromString(toDateInString);

		int noOfDays = DateUtility.findNumberOfDays(fromDateInString, toDateInString);
		int totalPrice;
		PriceDetail priceDetail = priceDetailRepository.findByRoomIdAndDateRange(room.getId(), fromDate, toDate);

		if (CommonUtils.isEmptyOrNullObject(priceDetail)) {
			logger.debug("DB information is null for the {} so calculating the price manually", room.getId());
			totalPrice = noOfDays == 0 ? room.getPrice() : noOfDays * room.getPrice();
		} else {
			logger.debug("Price for {} in DB is {}", room.getId(), priceDetail.getPrice());
			int pricePerDay = priceDetail.getPrice();
			totalPrice = noOfDays == 0 ? pricePerDay : noOfDays * pricePerDay;
		}

		priceDetail = buildPriceDetails(room, fromDate, toDate, totalPrice);

		logger.debug("PriceDetail : {}", priceDetail);
		return priceDetail;
	}

	/**
	 * Build the price detail information.
	 * 
	 * @param room
	 *            -- Room for which the price was calculated.
	 * @param fromDate
	 *            -- Start date of the duration.
	 * @param toDate
	 *            -- End date of the duration
	 * @param totalPrice
	 *            -- Total price of the room for the given duration.
	 * @return PriceDetail -- Price detail that was build with the given info.
	 */
	private PriceDetail buildPriceDetails(Room room, Date fromDate, Date toDate, int totalPrice) {
		PriceDetail priceDetail = new PriceDetail();
		priceDetail.setId(priceDetailsId.getAndIncrement());
		priceDetail.setRoomType(room.getType());
		priceDetail.setPrice(totalPrice);
		priceDetail.setFromDate(fromDate);
		priceDetail.setToDate(toDate);
		priceDetail.setRoomId(room.getId());
		return priceDetail;
	}
}
