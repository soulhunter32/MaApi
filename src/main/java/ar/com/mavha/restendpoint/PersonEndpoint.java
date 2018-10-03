package ar.com.mavha.restendpoint;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;

import ar.com.mavha.dto.PersonDTO;
import ar.com.mavha.exception.EmptyBodyException;
import ar.com.mavha.exception.ExistingPersonCollitionException;
import ar.com.mavha.exception.PersonNotFoundException;
import ar.com.mavha.restendpoint.request.PersonRequest;
import ar.com.mavha.restendpoint.response.PersonResponse;

/**
 * REST service in charge of CRUD operations related to Person models.-
 * 
 * @author skapcitzky
 */
@Path("/personas")
public interface PersonEndpoint {

	/**
	 * Retrieves all the people stored.-
	 * 
	 * @return a List of {@link PersonDTO}
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_JSON)
	public List<PersonResponse> getAllPerson();
	
	/**
	 * Retrieves a person by its DNI.-
	 * 
	 * @return a {@link PersonDTO} whose DNI in query belongs to
	 * @throws PersonNotFoundException 
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public PersonResponse getPersonByDni(@PathParam(value = "id") @NotNull Long id) throws PersonNotFoundException;
	
	/**
	 * Creates a me person based in the info provided in the request body as a {@link PersonRequest}.-
	 * 
	 * @param personRequest the {@link PersonRequest} object with the new person information
	 * @return a {@link PersonResponse} with new new person created
	 * @throws EmptyBodyException 
	 * @throws ExistingPersonCollitionException 
	 */
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_JSON)
	public PersonResponse createPerson(@Valid PersonRequest personRequest) throws EmptyBodyException, ExistingPersonCollitionException;
}
