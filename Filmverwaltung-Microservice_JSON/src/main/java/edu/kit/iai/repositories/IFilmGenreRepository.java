package edu.kit.iai.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.kit.iai.model.FilmGenre;
import edu.kit.iai.model.Musikstueck;

public interface IFilmGenreRepository extends JpaRepository<FilmGenre, Long>{
	public List<FilmGenre> findByBeschreibung(String beschreibung);
}
