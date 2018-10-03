package ar.com.mavha.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.mavha.dto.PersonDTO;
import ar.com.mavha.exception.ExistingPersonCollitionException;
import ar.com.mavha.exception.PersonNotFoundException;
import ar.com.mavha.model.Person;
import ar.com.mavha.repository.PersonRepository;
import ar.com.mavha.service.PersonService;
import ar.com.mavha.wrapper.PersonWrapper;

@Service
public class PersonServiceImpl implements PersonService{

	private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);
	
	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public List<PersonDTO> getAllPerson() {
		logger.debug("[getAllPerson] - Finding all person...");
		Iterable<Person> allPerson = personRepository.findAll();
		
		logger.debug("[getAllPerson] - Found " + StreamSupport.stream(allPerson.spliterator(), false).count() + " people");
		
		List<PersonDTO> personList = new ArrayList<PersonDTO>();
		
		allPerson.forEach(person -> personList.add(PersonWrapper.toDTO(person)));
		
		return personList;
	}

	@Override
	public PersonDTO getPersonById(Long id) throws PersonNotFoundException {
		logger.debug("[getAllPerson] - Finding person with DNI " + id);
		Optional<Person> personFound = personRepository.findById(id);
		
		if (!personFound.isPresent()) {
			logger.error("[getAllPerson] - There was an error, user cannot be found with DNI " + id);
			throw new PersonNotFoundException(id);
		}
		return PersonWrapper.toDTO(personFound.get());
	}

	@Override
	public PersonDTO createPerson(PersonDTO person) throws ExistingPersonCollitionException {
		
		if (personRepository.findById(person.getDni()).isPresent()) {
			throw new ExistingPersonCollitionException(person.getDni());
		}
		return PersonWrapper.toDTO(personRepository.save(PersonWrapper.toModel(person)));
	}

	public void setPersonRepository(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
}
