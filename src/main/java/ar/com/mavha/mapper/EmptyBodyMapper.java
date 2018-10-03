package ar.com.mavha.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ar.com.mavha.exception.EmptyBodyException;

@Provider
public class EmptyBodyMapper implements ExceptionMapper<EmptyBodyException> {

	@Override
	public Response toResponse(EmptyBodyException exception) {
		return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN).build();
	}
}
