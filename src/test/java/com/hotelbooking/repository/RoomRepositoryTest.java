package com.hotelbooking.repository;

import java.util.List;

import com.hotelbooking.model.Room;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoomRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private RoomRepository roomrepository;

	@Test
	public void shouldReturnRoomForFindByType() {

		Room room = new Room();
		String roomtype = "Single";
		long expectedRoomId = 1L;
		room.setPrice(500);
		room.setType(roomtype);
		room.setRating(4);
		room.setRoomName("RoomName");
		entityManager.persist(room);
		entityManager.flush();
		Room actualRoom = roomrepository.findByType(roomtype).get(0);
		long actualRoomId = actualRoom.getId();
		Assert.assertEquals("Room Id should be equal", expectedRoomId, actualRoomId);
	}

	@Test
	public void shouldReturnEmptyForFindByType() {

		String roomType = "Single";
		List<Room> actualRoom = roomrepository.findByType(roomType);
		Assert.assertTrue(actualRoom.isEmpty());
	}

}
