package edu.kit.iai.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.kit.iai.ElementNotFoundException;
import edu.kit.iai.model.FilmGenre;
import edu.kit.iai.repositories.IFilmGenreRepository;
import edu.kit.iai.util.CSVReader;
import edu.kit.iai.util.IOUtilities;

@RestController
@RequestMapping( value="filmgenre" )
public class FilmGenreController {

	@Autowired
	private IFilmGenreRepository filmgenreRepository;
	
	@RequestMapping( method = RequestMethod.GET)
	public String index() {
		return this.hello();
	}
	
	@RequestMapping( value="hello", method = RequestMethod.GET)
	public String hello(){
		return "Hello from the other side";
	}
	
	@RequestMapping( value="dummies", method = RequestMethod.POST)
	public String createPersons(){
		
		List<String[]> allfilmgenreCSV = null;
		try{
			FilmGenre filmgenre = null;
			allfilmgenreCSV = CSVReader.loadDataFromCSVFile( "/localhome/klee/csvData/csvShortData/FilmGenre.csv" );
			IOUtilities.printArrayContents( allfilmgenreCSV.toArray() );
			
			int ii = 0;
			for( String[] csvVals: allfilmgenreCSV ){
				filmgenre = new FilmGenre();
				filmgenre.setBeschreibung(csvVals[1]); 
				
				this.filmgenreRepository.save( filmgenre );
				ii++;
//				for( String string: csvVals ){
//					System.out.print( string + " # " );
//				}
//				System.out.println( csvVals );
			} 
			
			return "done ... (" + ii + " Filmgenre erzeugt)";

		}
		catch( IOException e ){
			e.printStackTrace();
			return "ERROR: " + e.getMessage();
		}
	}
	
	@RequestMapping(value="all")
	public List<FilmGenre> getPersons(){
		
		List<FilmGenre> allPers = this.filmgenreRepository.findAll();
		
		return allPers;
	}

	@RequestMapping ( value= "{id}", method = RequestMethod.GET)
	public FilmGenre getFilmgenre (@PathVariable("id") Long id){
		FilmGenre filmgenre = this.filmgenreRepository.findOne(id);
		if(filmgenre == null){
			throw new ElementNotFoundException("Could not found Filmgenre for id: " + id);
		}
		else{
			return filmgenre;
		}
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public List<FilmGenre> search ( @RequestParam("beschreibung") String beschreibung){
		return this.filmgenreRepository.findByBeschreibung(beschreibung);
	}
	
	/**
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public FilmGenre saveFilmGenre(@RequestBody FilmGenre filmGenre){
		return this.filmgenreRepository.save(filmGenre);
	}
}
