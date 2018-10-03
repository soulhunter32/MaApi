package ar.com.mavha.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ar.com.mavha.exception.PersonNotFoundException;

@Provider
public class PersonNotFoundMapper implements ExceptionMapper<PersonNotFoundException> {

	@Override
	public Response toResponse(PersonNotFoundException exception) {
		return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN).build();
	}
}
