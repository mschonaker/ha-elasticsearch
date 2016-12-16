package io.github.mschonaker.haelasticsearch.api;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;

@Provider
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
@Path("message")
public class MessageResource {

	@Inject
	Client client;

	@GET
	@Path("{id}")
	public Message get(@PathParam("id") @NotNull String id) {
		GetResponse response = client.prepareGet("messages", "message", id).get();

		Message message = new Message();
		message.setId(response.getId());
		message.setDate((Date) response.getField("date").getValue());
		message.setMessage((String) response.getField("message").getValue());

		return message;
	}

	@POST
	public String post(String message) {

		XContentBuilder document = null;
		try {

			document = jsonBuilder().startObject()//
					.field("date", new Date())//
					.field("message", message)//
					.endObject();

		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		return client.prepareIndex("messages", "message").setSource(document).get().getId();
	}

	@PUT
	@Path("{id}")
	public String put(@PathParam("id") @NotNull String id, String message) {

		XContentBuilder document = null;
		try {

			document = jsonBuilder().startObject()//
					.field("date", new Date())//
					.field("message", message)//
					.endObject();

		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		return client.prepareIndex("messages", "message", id).setSource(document).get().getId();
	}

	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") @NotNull String id) {

		client.prepareDelete("messages", "message", id).get();
	}
}
