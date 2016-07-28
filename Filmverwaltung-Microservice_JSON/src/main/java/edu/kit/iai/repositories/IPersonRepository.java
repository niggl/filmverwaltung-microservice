package edu.kit.iai.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.kit.iai.model.Person;


public interface IPersonRepository extends JpaRepository<Person, Long>{

	public List<Person> findByNachname(String nachname);
	
}
