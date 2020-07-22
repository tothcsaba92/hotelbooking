package com.hotelbooking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

@Entity
@Table(name = "room")
@Data
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private long id;

	private String type;

	private String roomName;

	private int price;

	private float rating;
}
