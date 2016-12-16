package io.github.mschonaker.haelasticsearch.api;

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
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.client.Client;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("message")
public class MessageResource {

	@Inject
	Client client;

	@GET
	@Path("{id}")
	public Message find(@PathParam("id") @NotNull String id) {

		GetResponse response = client.prepareGet("messages", "message", id)//
				.setFetchSource(true)//
				.get();

		Message message = Mapper.from(response.getSourceAsMap());
		message.setId(response.getId());
		return message;
	}

	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	public String insert(@NotNull String message) {
		return client.prepareIndex("messages", "message")//
				.setSource(Mapper.toSource(message)).setRefreshPolicy(RefreshPolicy.WAIT_UNTIL)//
				.get().getId();
	}

	@POST
	@Path("{id}")
	public void update(@PathParam("id") @NotNull String id, String message) {

		client.prepareIndex("messages", "message", id)//
				.setSource(Mapper.toSource(message)).setRefreshPolicy(RefreshPolicy.WAIT_UNTIL)//
				.execute();
	}

	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") @NotNull String id) {

		client.prepareDelete("messages", "message", id)//
				.execute();
	}
}
