package com.hotelbooking.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.hotelbooking.model.PriceDetail;
import com.hotelbooking.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotelbooking.response.Response;
import com.hotelbooking.response.ResponseBuilder;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

@RestController
@RequestMapping("/hotel")
public class PriceController {

	
	@Autowired
	private PriceService priceService;
	
	@PostMapping("/addPrice")
	public Response addPriceDetails(@Valid @RequestBody PriceDetail priceDetail , Errors errors) {
		
		List<String> errorMessage = new ArrayList<>();
		if (errors.hasErrors()) {
			for (ObjectError objectError : errors.getAllErrors()) {
				errorMessage.add(objectError.getDefaultMessage());
			}
			return ResponseBuilder.buildFailureResponse(errorMessage);
		}
		
		return priceService.addPriceDetails(priceDetail);
	}
}
