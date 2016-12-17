package io.github.mschonaker.haelasticsearch.services;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class Message {

	@NotNull
	private String id;

	@NotNull
	@Past
	private Date date;

	@NotNull
	@Size(min = 1)
	private String message;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", date=" + date + ", message=" + message + "]";
	}
}
