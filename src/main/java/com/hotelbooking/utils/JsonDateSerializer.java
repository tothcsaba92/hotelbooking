package com.hotelbooking.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Used to serialize Java.util.Date, which is not a common JSON type, so we have
 * to create a custom serialize method;
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date> {
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
			throws IOException {
		String formattedDate = dateFormat.format(date);
		gen.writeString(formattedDate);
	}
}