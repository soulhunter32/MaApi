package ar.com.mavha.mapper;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException e) {
		return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(
				e.getConstraintViolations().stream().map(error -> error.getMessage())
				.collect(Collectors.toList()))
				.build();
	}
}
