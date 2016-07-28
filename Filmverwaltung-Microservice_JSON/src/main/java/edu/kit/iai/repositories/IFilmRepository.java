package edu.kit.iai.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.kit.iai.model.Film;
import edu.kit.iai.model.Musikstueck;

public interface IFilmRepository extends JpaRepository<Film, Long>{

	public List<Film> findByTitel(String titel);

}
