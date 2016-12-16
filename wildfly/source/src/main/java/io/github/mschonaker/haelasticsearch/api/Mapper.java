package io.github.mschonaker.haelasticsearch.api;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.xml.bind.DatatypeConverter;

import org.elasticsearch.common.xcontent.XContentBuilder;

class Mapper {

	public static Message from(Map<String, Object> source) {
		Message message = new Message();

		message.setDate(Optional.ofNullable(source.get("date"))//
				.map(String.class::cast).map(Mapper::parseDate).orElse(null));
		message.setMessage(Optional.ofNullable(source.get("message"))//
				.map(String.class::cast).orElse(null));

		return message;
	}

	private static Date parseDate(String text) {
		return DatatypeConverter.parseDateTime(text).getTime();
	}

	public static XContentBuilder toSource(String message) {
		try {
			return jsonBuilder().startObject()//
					.field("date", new Date())//
					.field("message", message)//
					.endObject();

		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
