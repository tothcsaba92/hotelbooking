package com.hotelbooking.utils;

import java.util.Collection;

import com.hotelbooking.constants.Constants;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

public class CommonUtils {

	private CommonUtils() {
		
	}
	
	public static boolean isEmptyOrNullObject(Object value) {
		return null == value || Constants.EMPTY.equals(value);
	}
	
	
	/**
	 * Check object is empty or null.
	 *
	 * @param collection
	 *            string value
	 * @return boolean
	 */
	public static boolean isEmptyOrNullCollection(Collection<?> collection) {
		return null == collection || collection.isEmpty();
	}
}
