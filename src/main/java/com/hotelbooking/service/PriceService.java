package com.hotelbooking.service;

import com.hotelbooking.model.PriceDetail;
import com.hotelbooking.response.Response;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

public interface PriceService {

	public Response addPriceDetails(PriceDetail priceDetail);

}
