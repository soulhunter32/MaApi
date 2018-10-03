package ar.com.mavha.service;

import java.util.List;

import ar.com.mavha.dto.PersonDTO;
import ar.com.mavha.exception.ExistingPersonCollitionException;
import ar.com.mavha.exception.PersonNotFoundException;

public interface PersonService {

	public List<PersonDTO> getAllPerson();
	
	public PersonDTO getPersonById(Long id) throws PersonNotFoundException;
	
	public PersonDTO createPerson(PersonDTO person) throws ExistingPersonCollitionException;
}
