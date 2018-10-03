package ar.com.mavha.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.mavha.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long>{

}
