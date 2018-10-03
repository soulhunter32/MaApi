package ar.com.mavha.wrapper;

import ar.com.mavha.dto.PersonDTO;
import ar.com.mavha.model.Person;

/**
 * Transforms entity model {@link Person} to DTO {@link PersonDTO} and viceversa.-
 * @author skapcitzky
 */
public final class PersonWrapper {

	public static PersonDTO toDTO(Person person) {
		PersonDTO personDTO = new PersonDTO();
		personDTO.setDni(person.getDni());
		personDTO.setName(person.getName());
		personDTO.setLastname(person.getLastname());
		personDTO.setAge(person.getAge());
		
		return personDTO;
	}
	
	public static Person toModel(PersonDTO person) {
		Person personModel = new Person();
		personModel.setDni(person.getDni());
		personModel.setName(person.getName());
		personModel.setLastname(person.getLastname());
		personModel.setAge(person.getAge());
		
		return personModel;
	}
	
}
