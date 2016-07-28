package edu.kit.iai.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.kit.iai.ElementNotFoundException;
import edu.kit.iai.model.AbstractElement;
import edu.kit.iai.model.Datentraeger;
import edu.kit.iai.model.Film;
import edu.kit.iai.model.FilmGenre;
import edu.kit.iai.model.Musikstueck;
import edu.kit.iai.model.Person;
import edu.kit.iai.repositories.IDatentraegerRepository;
import edu.kit.iai.repositories.IFilmGenreRepository;
import edu.kit.iai.repositories.IFilmRepository;
import edu.kit.iai.repositories.IMusikstueckRepository;
import edu.kit.iai.repositories.IPersonRepository;
import edu.kit.iai.util.CSVReader;
import edu.kit.iai.util.IOUtilities;
import edu.kit.iai.util.StringUtilities;

@RestController
@RequestMapping( value="film" )
public class FilmController {

	@Autowired
	private IFilmRepository filmRepository;
	
	@Autowired
	private IPersonRepository personRepository;
	
	@Autowired
	private IMusikstueckRepository musikstueckRepository;
	
	@Autowired
	private IDatentraegerRepository datentraegerRepository;
	
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
	
	
	@RequestMapping(value="dummies", method = RequestMethod.POST)
	public String createFilme(){
		
		List<String[]> allFilmeCSV = null;
		try{
			Film film = null;
			allFilmeCSV = CSVReader.loadDataFromCSVFile( "/localhome/klee/csvData/csvShortData/Film.csv" );
			IOUtilities.printArrayContents( allFilmeCSV.toArray() );
			
			int ii = 0;
			for( String[] csvVals: allFilmeCSV ){
				System.out.println("________________________________________________--");
				for (String string : csvVals) {
					System.out.println("String" + string);
				}
				System.out.println("________________________________________________--");
				film = new Film();
				film.setTitel(csvVals[1] );
				film.setProduktionsLand(csvVals[2]);
				film.setFilmLaenge(Integer.parseInt(csvVals[3]));
				film.setOriginalSprache(csvVals[4]);
				film.setErscheinungsJahr(csvVals[5]); 
				film.setAltersFreigabe(Integer.parseInt(csvVals[6]));
				
				int[] darstellerIDs = StringUtilities.transformNumberSequenceToArrayOLD(csvVals[7]);
				
				List<Person> alleDarsteller = new ArrayList<Person>();
				for (int i : darstellerIDs) {
					alleDarsteller.add(personRepository.findOne((long) i));
					
				}
				film.setListDarsteller(alleDarsteller);
				
				int[] regisseureIDs = StringUtilities.transformNumberSequenceToArrayOLD(csvVals[8]);
				List<Person> alleRegisseure = new ArrayList<Person>();
				for (int numb : regisseureIDs) {
					alleRegisseure.add(personRepository.findOne((long) numb));

				}
				film.setListRegisseure(alleRegisseure);

				
				int[] synchronIDs = StringUtilities.transformNumberSequenceToArrayOLD(csvVals[9]);
				List<Person> alleSynchros = new ArrayList<Person>();
				for (int numb : synchronIDs) {
					alleSynchros.add(personRepository.findOne((long) numb));

				}
				film.setListSynchronsprecher(alleSynchros);
				
				Person p = personRepository.findOne((long)Integer.parseInt(csvVals[10]));
				
				film.setKameraHauptperson(p);
				
				System.out.println("Film: " + film.getTitel());
				int[] musikstueckIDs = StringUtilities.transformNumberSequenceToArrayOLD(csvVals[11]);
				List<Musikstueck> alleMusikstuecke = new ArrayList<Musikstueck>();
				for (int numb : musikstueckIDs) {
					System.out.println("Nummer: " + numb);
					alleMusikstuecke.add(musikstueckRepository.findOne((long) numb));

				}
				film.setListMusickstuecke(alleMusikstuecke);
				
				int[] datentraegerIDs = StringUtilities.transformNumberSequenceToArrayOLD(csvVals[12]);
				List<Datentraeger> alleDatentraeger = new ArrayList<Datentraeger>();
				for (int numb : datentraegerIDs) {
					alleDatentraeger.add(datentraegerRepository.findOne((long) numb));

				}
				film.setListDatentraeger(alleDatentraeger);
				
				int[] filmgenreIDs = StringUtilities.transformNumberSequenceToArrayOLD(csvVals[13]);
				List<FilmGenre> alleFilmgenres = new ArrayList<FilmGenre>();
				for (int numb : filmgenreIDs) {
					alleFilmgenres.add(filmgenreRepository.findOne((long) numb));

				}
				film.setListFilmGenre(alleFilmgenres);


				
				
				this.filmRepository.save( film );
				ii++;
			} 
			
			return "done ... (" + ii + " Film erzeugt)";

		}
		catch( IOException e ){
			e.printStackTrace();
			return "ERROR: " + e.getMessage();
		}
	}
	@RequestMapping(value ="{id}/darsteller", method = RequestMethod.GET )
	public List<Person> getAllPersons(@PathVariable Long id){
		
		Film f = filmRepository.findOne(id);
		f.printOut();
		for (Person p : f.getListDarsteller()) {
			System.out.println(":__________________________:");
			p.printOut();
		}
		
		return f.getListDarsteller();
		
	}
	@RequestMapping(value ="{id}/musikstueck", method = RequestMethod.GET )
	public List<Musikstueck> getAllMusikstuecke(@PathVariable Long id){
		
		Film f = filmRepository.findOne(id);
		
		return f.getListMusickstuecke();
		
	}
	@RequestMapping(value ="{id}/filmgenre", method = RequestMethod.GET )
	public List<FilmGenre> getAllFilmgenre(@PathVariable Long id){
		
		Film f = filmRepository.findOne(id);
		
		return f.getListFilmGenre();
		
	}

	@RequestMapping(value="all")
	public List<Film> getAll(){
//		
//		List<Film> allfilme = this.filmRepository.findAll();
//		
//		for (Film film : allfilme) {
//			film.printOut();
//		}
//		
//		System.out.println("Anzahl: " + filmRepository.count());
		return this.filmRepository.findAll();
	}

	@ResponseBody
	@RequestMapping ( value= "{id}", method = RequestMethod.GET)
	public Film getFilm (@PathVariable("id") Long id){
		Film film = this.filmRepository.findOne(id);
		if(film == null){
			throw new ElementNotFoundException("Could not found Film for id: " + id);
		}
		else{
			return film;
		}
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public List<Film> search ( @RequestParam("titel") String titel){
		return this.filmRepository.findByTitel(titel);
	}
	
	@RequestMapping(value="{id}/atts", method = RequestMethod.GET)
	public String[] getAttributes(@PathVariable Long id){
		Film f = filmRepository.findOne(id);
		
		return f.getAttributes4TableData();
	}
	
	/**
	 * 
	 * @param film
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Film saveFilm(@RequestBody Film film){
		return this.filmRepository.save(film);
	}
	
}
