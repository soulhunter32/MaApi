package ar.com.mavha.restendpoint.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.mavha.dto.PersonDTO;
import ar.com.mavha.exception.EmptyBodyException;
import ar.com.mavha.exception.ExistingPersonCollitionException;
import ar.com.mavha.exception.PersonNotFoundException;
import ar.com.mavha.restendpoint.PersonEndpoint;
import ar.com.mavha.restendpoint.request.PersonRequest;
import ar.com.mavha.restendpoint.response.PersonResponse;
import ar.com.mavha.service.impl.PersonServiceImpl;
import ar.com.mavha.wrapper.PersonRestWrapper;

@Component
public class PersonEndpointImpl implements PersonEndpoint {

	private static final Logger logger = LogManager.getLogger(PersonEndpointImpl.class);
	
	@Autowired
	PersonServiceImpl personService;
	
	@Override
	public List<PersonResponse> getAllPerson() {
		logger.info("[getAllPerson] - Starting the get all person query...");
		
		List<PersonDTO> allPersons = personService.getAllPerson();
		
		List<PersonResponse> returnList = allPersons.stream()
				.map(person -> PersonRestWrapper.toResponse(person))
				.collect(Collectors.toList());
		
		return returnList;
	}

	@Override
	public PersonResponse getPersonByDni(@NotNull Long id) throws PersonNotFoundException {
		logger.info("[getPersonByDni] - Starting query: Querying user with DNI: " + id + " ...");
		
		PersonDTO personFound;
		personFound = personService.getPersonById(id);
		
		logger.info("[getPersonByDni] - Person found! - " + personFound.getName() + " " + personFound.getLastname());
		
		return PersonRestWrapper.toResponse(personFound);
	}

	@Override
	public PersonResponse createPerson(@Valid PersonRequest personRequest) throws EmptyBodyException, ExistingPersonCollitionException {
		logger.info("[createPerson] - Starting person creation...");
		if (personRequest == null) {
			throw new EmptyBodyException();
		}
		return PersonRestWrapper.toResponse(personService.createPerson(PersonRestWrapper.toDTO(personRequest)));
	}

	public void setPersonService(PersonServiceImpl personService) {
		this.personService = personService;
	}

}
