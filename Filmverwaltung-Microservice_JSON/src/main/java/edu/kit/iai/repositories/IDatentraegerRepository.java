package edu.kit.iai.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.kit.iai.model.Datentraeger;
import edu.kit.iai.model.Person;

public interface IDatentraegerRepository extends JpaRepository<Datentraeger, Long>{
	public List<Datentraeger> findByBezeichner(String bezeichner);

}
