package edu.kit.iai.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonBackReference;

import edu.kit.iai.ElementNotFoundException;
import edu.kit.iai.model.Datentraeger;
import edu.kit.iai.model.Film;
import edu.kit.iai.model.Person;
import edu.kit.iai.repositories.IFilmRepository;
import edu.kit.iai.repositories.IPersonRepository;
import edu.kit.iai.util.CSVReader;
import edu.kit.iai.util.IOUtilities;


@RestController
@RequestMapping( value="person" )
public class PersonController {

	@Autowired
	private IPersonRepository personRepository;
	
	@Autowired
	private IFilmRepository filmRepository;
	
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
		
		List<String[]> allPersonsCSV = null;
		try{
			Person p = null;
			allPersonsCSV = CSVReader.loadDataFromCSVFile( "/localhome/klee/csvData/csvShortData/Person.csv" );
			IOUtilities.printArrayContents( allPersonsCSV.toArray() );
			
			int ii = 0;
			for( String[] csvVals: allPersonsCSV ){
				p = new Person();
				p.setVorname( csvVals[1] );
				p.setNachname( csvVals[2] );
				p.setNationalitaet( csvVals[3] );
				p.setGeburtsDatum(  new SimpleDateFormat("dd.MM.yyyy").parse( csvVals[4] ));
				p.setGeburtsOrt( csvVals[5] );
				p.setHompage( csvVals[6] );
				p.setBeschreibung( csvVals[8] );
				
				ii++;
//				for( String string: csvVals ){
//					System.out.print( string + " # " );
//				}
				this.personRepository.save( p );
//				System.out.println( csvVals );
			} 
			
			return "done ... (" + ii + " Personen erzeugt)";

		}
		catch( IOException | ParseException e ){
			e.printStackTrace();
			return "ERROR: " + e.getMessage();
		}
	}
	
	@RequestMapping(value="all")
	public List<Person> getPersons(){
		
		List<Person> allPers = this.personRepository.findAll();
		for( Person person: allPers ){
			person.printOut();
		}
		
		return allPers;
	}
	@RequestMapping(value="{id}/film/{idFilm}", method = RequestMethod.POST)
	public String addFilmToDatentraeger(@PathVariable Long id, @PathVariable Long idFilm){
		
		Person p = this.personRepository.findOne(id);
		if ( p != null ){
			Film f = filmRepository.findOne(idFilm);
			if ( f != null ){
				p.addFilm(f);
				personRepository.save(p);
				return "Film (id="+ idFilm + ") added to Person(id="+ id + ") successfully";
			}
		}
		
		return "Person (id="+ id + ") not found";
	}

	@RequestMapping ( value= "{id}", method = RequestMethod.GET)
	public Person getPerson (@PathVariable("id") Long id){
		Person p = this.personRepository.findOne(id);
//		p.printOut();
		if(p == null){
			throw new ElementNotFoundException("Could not found person for id: " + id);
		}
		else{
			return p;
		}
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public List<Person> search ( @RequestParam("name") String nachname){
		return this.personRepository.findByNachname(nachname);
	}
	
	/**
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Person savePerson(@RequestBody Person person){
		return this.personRepository.save(person);
	}
}
