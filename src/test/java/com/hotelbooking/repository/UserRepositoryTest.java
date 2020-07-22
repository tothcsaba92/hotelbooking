package com.hotelbooking.repository;

import com.hotelbooking.model.User;
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
public class UserRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository userRepository;
	
	private String userName= "userName" ; 
	@Test
	public void shouldReturnUserForFindByUserName() {

		User user = new User();
		long expectedUserId = 1L;
		user.setUserName(userName);
		entityManager.persist(user);
		entityManager.flush();
		User actualUser = userRepository.findByUserName(userName);
		long actualUserId =  actualUser.getUserId();
		Assert.assertEquals("User id must be equal", expectedUserId , actualUserId);;
	}
	
	@Test
	public void shouldReturnNullForFindByUserName() {

		User found = userRepository.findByUserName(userName);
		Assert.assertNull(found);
	}

}
