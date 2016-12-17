package io.github.mschonaker.haelasticsearch.rest;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import io.github.mschonaker.haelasticsearch.services.Message;
import io.github.mschonaker.haelasticsearch.services.MessagesService;
import io.github.mschonaker.haelasticsearch.services.Results;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("messages")
public class MessagesResource {

	@Inject
	MessagesService service;

	@GET
	public Results<Message> findAll() {

		return service.findAll();

	}

	@PUT
	public Results<Message> insert(@NotNull String message) {

		service.insert(message);
		return service.findAll();

	}

	@DELETE
	@Path("{id}")
	public Results<Message> delete(@PathParam("id") @NotNull String id) {

		service.delete(id);
		return service.findAll();

	}
}
