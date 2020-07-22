package com.hotelbooking.service;

import java.util.ArrayList;
import java.util.List;

import com.hotelbooking.model.PriceDetail;
import com.hotelbooking.model.Room;
import com.hotelbooking.service.impl.RoomServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.hotelbooking.constants.Constants;
import com.hotelbooking.repository.PriceDetailRepository;
import com.hotelbooking.repository.RoomRepository;
import com.hotelbooking.response.Response;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

@RunWith(SpringRunner.class)
public class RoomServiceImplTest {

	@TestConfiguration
	static class RoomServiceImplTestContextConfiguration {

		@Bean
		public RoomService roomService() {
			return new RoomServiceImpl();
		}
	}

	@MockBean
	private RoomRepository roomRepository;

	@MockBean
	private PriceDetailRepository priceDetailRepository;

	@MockBean
	private Response response;

	@MockBean
	private PriceDetail priceDetail;

	@MockBean
    Room room;

	@Autowired
	private RoomService roomService;

	private List<Room> roomList = new ArrayList<>();

	private String roomType = "Single";

	@Before
	public void setUp() {
		roomList.add(room);
		Mockito.when(room.getId()).thenReturn(1L);
	}

	@Test
	public void shouldReturnSuccessForCalculatePriceByType() {
		String expectedStatus = Constants.SUCCESS;
		Mockito.when(roomRepository.findByType(Mockito.anyString())).thenReturn(roomList);
		Mockito.when(priceDetailRepository.findByRoomIdAndDateRange(Mockito.anyLong(), Mockito.any(), Mockito.any()))
				.thenReturn(priceDetail);
		Response res = roomService.calculatetPriceByType(roomType, "2020-08-11", "2020-08-12");
		String actualStatus = res.getStatus();
		Assert.assertEquals("Status must be equal", actualStatus, expectedStatus);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldReturnRoomDetailsForGetRoom() {
		Mockito.when(roomRepository.findAll()).thenReturn(roomList);
		long expectedRoomId = 1L;
		Response response = roomService.getRooms();
		List<Room> actualRoomList = (List<Room>) response.getMessage();
		long actualRoomId = actualRoomList.get(0).getId();
		Assert.assertEquals("Room id should be equal", actualRoomId, expectedRoomId);
	}

	@Test
	public void shouldReturnFailureResponseForCalculatePriceByType() {
		String expectedStatus = Constants.FAILURE;
		String expectedMessage = "Invalid Room Type";
		Mockito.when(roomRepository.findByType(Mockito.anyString())).thenReturn(new ArrayList<>());
		Response response = roomService.calculatetPriceByType(roomType, "2020-08-11", "2020-08-10");
		String actualStatus = response.getStatus();
		String actualMessage = (String) response.getMessage();
		Assert.assertEquals("Status must be same", actualStatus, expectedStatus);
		Assert.assertEquals("Message must be equal", actualMessage, expectedMessage);
	}
}
