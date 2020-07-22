package com.hotelbooking.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

public class DateUtilityTest {

		private String startDateInString ;
		
		private  String endDateInString ; 
		
		private String existingStartDateInString ;
		
		private String existingEndDateInString ;
	 
	@Test
	public void shouldReturnTrueForcheckInRangeTestWhenCompletelyLayoverDates() {
		startDateInString = "2020-08-04";
		endDateInString = "2020-08-07";
		existingStartDateInString = "2020-08-05";
		existingEndDateInString = "2020-08-06";
		boolean isDateAvailable = DateUtility.checkInRange(startDateInString, endDateInString, existingStartDateInString, existingEndDateInString);
		Assert.assertTrue(isDateAvailable);
	}
	
	@Test
	public void shouldReturnTrueForCheckInRangeTestForCompletelyLayoverDates() {
		startDateInString = "2020-08-05";
		endDateInString = "2020-08-06";
		existingStartDateInString = "2020-08-04";
		existingEndDateInString = "2020-08-07";
		boolean isDateAvailable = DateUtility.checkInRange(startDateInString, endDateInString, existingStartDateInString, existingEndDateInString);
		Assert.assertTrue(isDateAvailable);
	}
	
	
	@Test
	public void shouldReturnTrueForCheckInRangeTestForPartialLayoverDates() {
		startDateInString = "2020-08-04";
		endDateInString = "2020-08-07";
		existingStartDateInString = "2020-08-05";
		existingEndDateInString = "2020-08-08";
		boolean isDateAvailable = DateUtility.checkInRange(startDateInString, endDateInString, existingStartDateInString, existingEndDateInString);
		Assert.assertTrue(isDateAvailable);
	}
	
	
	@Test
	public void shouldReturnTrueForCheckInRangeTestForPartialLayoverDatesOtherCase() {
		startDateInString = "2020-08-05";
		endDateInString = "2020-08-08";
		existingStartDateInString = "2020-08-04";
		existingEndDateInString = "2020-08-06";
		boolean isDateAvailable = DateUtility.checkInRange(startDateInString, endDateInString, existingStartDateInString, existingEndDateInString);
		Assert.assertTrue(isDateAvailable);
	}
	
	@Test
	public void shouldReturnFalseForCheckInRangeTestForDifferentDates() {
		startDateInString = "2020-08-05";
		endDateInString = "2020-08-08";
		existingStartDateInString = "2020-08-03";
		existingEndDateInString = "2020-08-04";
		boolean isDateAvailable = DateUtility.checkInRange(startDateInString, endDateInString, existingStartDateInString, existingEndDateInString);
		Assert.assertFalse(isDateAvailable);
	}
	
}
