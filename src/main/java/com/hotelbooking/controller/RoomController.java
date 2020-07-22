package com.hotelbooking.controller;

import com.hotelbooking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotelbooking.response.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @Purpose Contains the rest services to get rooms and to get the price
 *          details.
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */
@RestController
@RequestMapping("/hotel")
@Api(value = "Manage the hotel room")
public class RoomController {

	@Autowired
	private RoomService roomService;

	/**
	 * Get the room details.
	 * 
	 * @return Response.
	 */
	@ApiOperation(value = "Used to get all the rooms", response = Response.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "List of Room and its details")})
	@GetMapping("/getRooms")
	public Response getRooms() {
		return roomService.getRooms();
	}

	/**
	 * Get the price details of the room for the given duration.
	 * 
	 * @param type
	 *            -- Type of the room to fetch the price details.
	 * @param fromDate
	 *            -- Date from which the duration starts.
	 * @param toDate
	 *            -- Date till the duration ends.
	 * @return Response.
	 */
	@ApiOperation(value = "Used to the price of the room for the given duration", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "1. Price details" + "\n\n2. Invalid Room type"
			+ "\n\n3. Invalid Date") })
	@GetMapping("/getPrice/{type}")
	public Response getPrice(@PathVariable(value = "type", required = true) String type,
			@RequestParam(value = "fromDate", required = true) String fromDate,
			@RequestParam(value = "toDate", required = true) String toDate) {
		return roomService.calculatetPriceByType(type, fromDate, toDate);
	}

}
