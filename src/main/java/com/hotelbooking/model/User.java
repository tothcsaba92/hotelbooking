package com.hotelbooking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

@Entity
@Table(name = "user")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	@NotNull(message = "Username cannot be null")
	@NotEmpty(message = "Username cannot be empty")
	private String userName;
	
	@Email(message = "Enter a valid email address")
	private String email;

}
