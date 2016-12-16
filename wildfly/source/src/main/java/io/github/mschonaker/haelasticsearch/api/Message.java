package io.github.mschonaker.haelasticsearch.api;

import java.util.Date;

public class Message {

	private String id;
	private Date date;
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
