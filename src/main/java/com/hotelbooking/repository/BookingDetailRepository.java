package com.hotelbooking.repository;

import java.util.Date;
import java.util.List;

import com.hotelbooking.model.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {

	/**
	 * @purpose This method is used to fetch the records that satisfy the given
	 *          criteria. Fetch the records that matches the given roomId and given
	 *          date range.
	 * @param roomId
	 *            -- Id of the room to fetch.
	 * @param fromDate
	 *            -- start date of the range.
	 * @param toDate
	 *            -- End date of the range.
	 * @return List<BookingDetails> -- List of booked details that satisfy the given
	 *         criteria.
	 */
	@Query("select bd from BookingDetail bd where bd.roomId =?1 and ((bd.fromDate >= ?2 and bd.fromDate <= ?3) or (bd.toDate >= ?2 and bd.toDate <=?3) or ((?2 between bd.fromDate and bd.toDate) and (?3 between bd.fromDate and bd.toDate)))")
	public List<BookingDetail> findByRoomIdAndDateRange(Long roomId, Date fromDate, Date toDate);

}
