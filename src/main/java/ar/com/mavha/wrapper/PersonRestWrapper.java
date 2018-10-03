package ar.com.mavha.wrapper;

import ar.com.mavha.dto.PersonDTO;
import ar.com.mavha.model.Person;
import ar.com.mavha.restendpoint.request.PersonRequest;
import ar.com.mavha.restendpoint.response.PersonResponse;

/**
 * Transforms entity model {@link Person} to {@link PersonResponse} and viceversa.-
 * @author skapcitzky
 */
public final class PersonRestWrapper {

	public static PersonDTO toDTO(PersonRequest personRequest) {
		PersonDTO personDTO = new PersonDTO();
		personDTO.setDni(personRequest.getDni());
		personDTO.setName(personRequest.getName());
		personDTO.setLastname(personRequest.getLastname());
		personDTO.setAge(personRequest.getAge());
		
		return personDTO;
	}
	
	public static PersonResponse toResponse(PersonDTO person) {
		PersonResponse personModel = new PersonResponse();
		personModel.setDni(person.getDni());
		personModel.setName(person.getName());
		personModel.setLastname(person.getLastname());
		personModel.setAge(person.getAge());
		
		return personModel;
	}
	
}
