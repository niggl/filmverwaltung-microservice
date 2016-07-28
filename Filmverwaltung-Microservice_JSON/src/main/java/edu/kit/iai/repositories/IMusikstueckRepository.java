package edu.kit.iai.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.kit.iai.model.Musikstueck;
import edu.kit.iai.model.Person;

public interface IMusikstueckRepository extends JpaRepository<Musikstueck, Long>{
	public List<Musikstueck> findByTitel(String titel);

}
