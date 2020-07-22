package com.hotelbooking.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hotelbooking.utils.JsonDateSerializer;
import com.hotelbooking.utils.JsonDateDeserializer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

@Entity
@Table(name = "pricedetails")
@Data
public class PriceDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "room_id")
	@NotNull(message = "RoomID cannot be null")
	@PositiveOrZero(message = "RoomID cannot be negative")
	@ApiModelProperty(notes = "ID of the room", required = true)
	private long roomId;
	
	@Column(name = "price")
	@NotNull(message = "Price cannot be null")
	@Positive(message = "Price should be in positive value")
	@ApiModelProperty(notes = "Price of the room", required = true)
	private int price;
	
	@Column(name = "from_date")
	@JsonSerialize(using= JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@NotNull(message = "From Date name cannot be null")
	@FutureOrPresent(message = "Price can be added only for future or present date")
	@ApiModelProperty(notes = "Start date of the duration", required = true)
	private Date fromDate;
	
	@Column(name = "to_date")
	@JsonSerialize(using= JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@NotNull(message = "To Date cannot be null")
	@FutureOrPresent(message = "Price can be added only for future or present date")
	@ApiModelProperty(notes = "End Date of the duration", required = true)
	private Date toDate;
	
	@Column(name = "room_type")
	@NotNull(message = "Room type cannot be null")
	@NotEmpty(message = "Room type cannot be empty")
	@ApiModelProperty(notes = "Type of the room", required = true)
	private String roomType;

}
