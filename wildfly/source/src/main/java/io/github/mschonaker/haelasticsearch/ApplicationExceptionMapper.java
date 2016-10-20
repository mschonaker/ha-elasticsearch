package io.github.mschonaker.haelasticsearch;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {

		if (exception instanceof IllegalArgumentException)
			return Response.status(400).entity(exception.getMessage()).build();

		throw new IllegalStateException(exception);
	}
}
