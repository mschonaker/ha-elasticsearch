package io.github.mschonaker.haelasticsearch.rest;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import io.github.mschonaker.haelasticsearch.services.Message;
import io.github.mschonaker.haelasticsearch.services.MessagesService;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("message")
public class MessageResource {

	@Inject
	MessagesService service;

	@GET
	@Path("{id}")
	public Message find(@PathParam("id") @NotNull String id) {

		return service.find(id);

	}

	@POST
	@Path("{id}")
	public void update(@NotNull Message message) {

		service.update(message);

	}
}
