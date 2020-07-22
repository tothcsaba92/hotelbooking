package com.hotelbooking.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.hotelbooking.exception.InvalidDateException;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */
public class DateUtility {

	private DateUtility() {

	}

	/**
	 * 
	 * @param date
	 * @return java.util.Date This method used to convert date from String
	 */
	public static Date getDateFromString(String date) throws InvalidDateException {
		LocalDate localDate = getLocalDateFromString(date);
		return convertToUtilDate(localDate);
	}

	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @throws Exception
	 *             This method used to validate the from and to date
	 */
	public static void validateDate(Date fromDate, Date toDate) throws InvalidDateException {

		if (fromDate.after(toDate))
			throw new InvalidDateException("From date should not be greater than to date");

	}

	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @throws InvalidDateException
	 */
	public static void validateDate(String fromDate, String toDate) throws InvalidDateException {
		Date fD = convertToUtilDate(getLocalDateFromString(fromDate));
		Date tD = convertToUtilDate(getLocalDateFromString(toDate));
		validateDate(fD, tD);
	}

	/**
	 * 
	 * @param dateToConvert
	 * @return java.util.Date
	 * @throws Exception
	 *             This utility method used to convert local date into java util
	 *             date
	 */
	private static Date convertToUtilDate(LocalDate dateToConvert) throws InvalidDateException {
		try {
			return java.util.Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		} catch (Exception e) {
			throw new InvalidDateException("Invalid date format");
		}
	}

	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return java.lang.Integer
	 * @throws Exception
	 *             This method used to find the difference between two dates in days
	 */

	public static Integer findNumberOfDays(String fromDate, String toDate) throws InvalidDateException {
		try {
			LocalDate fromDt = getLocalDateFromString(fromDate);
			LocalDate toDt = getLocalDateFromString(toDate);
			Period period = getPeriod(fromDt, toDt);
			return period.getDays() + 1;
		} catch (Exception e) {
			throw new InvalidDateException();
		}

	}

	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return java.time.Period find the period between two local dates
	 */
	private static Period getPeriod(LocalDate fromDate, LocalDate toDate) {
		return Period.between(fromDate, toDate);
	}

	/**
	 * 
	 * @param date
	 * @return java.time.LocalDate This utility method used to get local date from
	 *         String
	 */
	public static LocalDate getLocalDateFromString(String date) {

		return LocalDate.parse(date);
	}

	/**
	 * 
	 * @param fromDateInString
	 * @param toDateInString
	 * @return
	 */
	public static List<LocalDate> getInBetweenDates(String fromDateInString, String toDateInString) {

		LocalDate startDate = getLocalDateFromString(fromDateInString);

		LocalDate endDate = getLocalDateFromString(toDateInString);

		return new ArrayList<>(getDates(startDate, endDate));

	}

	/**
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	private static List<LocalDate> getDates(LocalDate begin, LocalDate end) {

		long numOfDaysBetween = ChronoUnit.DAYS.between(begin, end) + 1;
		return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> begin.plusDays(i))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param startDateInString
	 * @param endDateInString
	 * @param existingStartDateInString
	 * @param existingEndDateInString
	 * @return
	 */
	public static boolean checkInRange(String startDateInString, String endDateInString,
			String existingStartDateInString, String existingEndDateInString) {
		LocalDate startDate = getLocalDateFromString(startDateInString);
		LocalDate endDate = getLocalDateFromString(endDateInString);
		LocalDate existingStartDate = getLocalDateFromString(existingStartDateInString);
		LocalDate existingEndDate = getLocalDateFromString(existingEndDateInString);
		LocalDate maxdate = max(startDate, endDate);
		LocalDate minDate = min(startDate, endDate);
		LocalDate existingMinDate = min(existingStartDate, existingEndDate);
		LocalDate existingMaxDate = max(existingStartDate, existingEndDate);
		List<LocalDate> existingIntervalDates = getDates(existingStartDate, existingEndDate);
		List<LocalDate> intervalDates = getDates(startDate, endDate);
		return (existingIntervalDates.contains(maxdate) || existingIntervalDates.contains(minDate)
				|| intervalDates.contains(existingMinDate) || intervalDates.contains(existingMaxDate));
	}
	


	/**
	 * Find the maximum date.
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static LocalDate max(LocalDate date1, LocalDate date2) {
		if (date1 == null && date2 == null)
			return null;
		if (date1 == null)
			return date2;
		if (date2 == null)
			return date1;
		return (date1.isAfter(date2)) ? date1 : date2;
	}

	/**
	 * Find the minimum of two dates.
	 * @param date1 
	 * @param date2
	 * @return Minimum date
	 */
	public static LocalDate min(LocalDate date1, LocalDate date2) {
		if (date1 == null && date2 == null)
			return null;
		if (date1 == null)
			return date2;
		if (date2 == null)
			return date1;
		return (date1.isBefore(date2)) ? date1 : date2;
	}
}
