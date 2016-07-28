package edu.kit.iai.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.kit.iai.ElementNotFoundException;
import edu.kit.iai.model.Musikstueck;
import edu.kit.iai.repositories.IMusikstueckRepository;
import edu.kit.iai.util.CSVReader;
import edu.kit.iai.util.IOUtilities;

@RestController
@RequestMapping( value="musikstueck" )
public class MusikstueckController {

	@Autowired
	private IMusikstueckRepository musikstueckRepository;
	
	@RequestMapping( method = RequestMethod.GET)
	public String index() {
		return this.hello();
	}
	
	@RequestMapping( value="hello", method = RequestMethod.GET)
	public String hello(){
		return "Hello from the other side";
	}
	
	@RequestMapping(value="dummies", method = RequestMethod.POST)
	public String createMusikstueck(){
		
		List<String[]> allMusikstueckCSV = null;
		try{
			Musikstueck musikstueck = null;
			allMusikstueckCSV = CSVReader.loadDataFromCSVFile( "/localhome/klee/csvData/csvShortData/Musikstueck.csv" );
			IOUtilities.printArrayContents( allMusikstueckCSV.toArray() );
			
			int ii = 0;
			for( String[] csvVals: allMusikstueckCSV ){
				musikstueck = new Musikstueck();
				musikstueck.setTitel(csvVals[1] );
				musikstueck.setInterpret(csvVals[2]);
				musikstueck.setAlbum(csvVals[3]);
				musikstueck.setErscheinungsJahr(csvVals[4]);
				musikstueck.setBeschreibung(csvVals[5]); 
				musikstueck.setGenre(csvVals[6]);
				musikstueck.setPfad(csvVals[7]);
				
				this.musikstueckRepository.save( musikstueck );
				ii++;
//				for( String string: csvVals ){
//					System.out.print( string + " # " );
//				}
//				System.out.println( csvVals );
			} 
			
			return "done ... (" + ii + " Musikstueck erzeugt)";

		}
		catch( IOException e ){
			e.printStackTrace();
			return "ERROR: " + e.getMessage();
		}
	}
	
	@RequestMapping(value="all")
	public List<Musikstueck> getAll(){
		
		List<Musikstueck> allMusik = this.musikstueckRepository.findAll();
		for( Musikstueck musikstueck: allMusik ){
			musikstueck.printOut();
		}
		
		return allMusik;
	}

	@RequestMapping ( value= "{id}", method = RequestMethod.GET)
	public Musikstueck getMusikstueck (@PathVariable("id") Long id){
		Musikstueck musikstueck = this.musikstueckRepository.findOne(id);
		if(musikstueck == null){
			throw new ElementNotFoundException("Could not found person for id: " + id);
		}
		else{
			return musikstueck;
		}
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public List<Musikstueck> search ( @RequestParam("titel") String titel){
		return this.musikstueckRepository.findByTitel(titel);
	}
	
	/**
	 * 
	 * @param musikstueck
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Musikstueck saveMusikstueck(@RequestBody Musikstueck musikstueck){
		return this.musikstueckRepository.save(musikstueck);
	}

}
