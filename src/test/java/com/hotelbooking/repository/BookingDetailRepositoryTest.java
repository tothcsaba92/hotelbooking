package com.hotelbooking.repository;

import java.util.Date;
import java.util.List;

import com.hotelbooking.model.BookingDetail;
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
public class BookingDetailRepositoryTest {

	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private BookingDetailRepository  bookingDetailRepository;
	
	
	private Date date = new Date();
	
	private long roomId = 1L;
	
	private long userId = 1L;
	
	@Test
	public void shouldReturnBookingDetailForfindByRoomIdAndDateRange() {
		BookingDetail bookingDetail = new BookingDetail();
		bookingDetail.setRoomId(roomId);
		bookingDetail.setUserId(userId);
		bookingDetail.setFromDate(date);
		bookingDetail.setToDate(date);
		entityManager.persist(bookingDetail);
		entityManager.flush();		
		List<BookingDetail> booking = bookingDetailRepository.findByRoomIdAndDateRange(roomId, date, date);
		Assert.assertFalse(booking.isEmpty());
		
		
	}
	
	
	@Test
	public void shouldReturnEmptyListForFindByRoomIdAndDateRange() {
		List<BookingDetail> booking = bookingDetailRepository.findByRoomIdAndDateRange(roomId, date, date);
		Assert.assertTrue(booking.isEmpty());
		
		
	}
}
