package ar.com.mavha.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ar.com.mavha.exception.ExistingPersonCollitionException;

@Provider
public class ExistingPersonCollitionMapper implements ExceptionMapper<ExistingPersonCollitionException> {

	@Override
	public Response toResponse(ExistingPersonCollitionException exception) {
		return Response.status(Response.Status.CONFLICT).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN).build();
	}
}
