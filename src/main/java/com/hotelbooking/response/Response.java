package com.hotelbooking.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

@Data
public class Response<T> {

	@ApiModelProperty(notes ="Response status " , required = true)
	private String status;
	
	@ApiModelProperty(notes = "Response message " , required = true)
	private T message;
}
