package com.hotelbooking.repository;

import com.hotelbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * @purpose Fetch the user with the given user name.
	 * @param userName
	 *            -- User name of the user to search.
	 * @return User -- User with the given user name.
	 */
	public User findByUserName(String userName);
}
