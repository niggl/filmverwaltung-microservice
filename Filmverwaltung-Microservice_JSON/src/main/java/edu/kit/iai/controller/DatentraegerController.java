package edu.kit.iai.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.kit.iai.ElementNotFoundException;
import edu.kit.iai.model.Datentraeger;
import edu.kit.iai.model.Film;
import edu.kit.iai.model.Musikstueck;
import edu.kit.iai.repositories.IDatentraegerRepository;
import edu.kit.iai.repositories.IFilmRepository;
import edu.kit.iai.repositories.IMusikstueckRepository;
import edu.kit.iai.util.CSVReader;
import edu.kit.iai.util.IOUtilities;
import edu.kit.iai.util.StringUtilities;

@RestController
@RequestMapping( value="datentraeger" )
public class DatentraegerController {

	@Autowired
	private IDatentraegerRepository datentraegerRepository;
	
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
	
//	@RequestMapping( value="initFilme", method = RequestMethod.POST)
//	public boolean initFilme(){
//		List<String[]> allDatentraegerCSV = null;
//
//		Datentraeger datentraeger = null;
//		try {
//			allDatentraegerCSV = CSVReader.loadDataFromCSVFile( "/localhome/klee/csvData/Datentraeger.csv" );
//			for( String[] csvVals: allDatentraegerCSV ){
//				long[] ids = StringUtilities.transformNumberSequenceToArrayOLDLong(csvVals[6]);
//				for (long l : ids) {
//					System.out.println("IDS: " + l);
//				}
//				datentraeger = datentraegerRepository.findOne( Long.parseLong(csvVals[0]));
//				if(ids.length > 0 || ids != null){
//					datentraeger.setFilmIDs(ids);
//					List<Film> alleFilme = new ArrayList<Film>();
//					for (long l : ids) {
//						Film f = filmRepository.findOne(l);
//						alleFilme.add(f);
//					}
//					if(alleFilme != null || alleFilme.size() > 0){
//						datentraeger.setEnthalteneFilme(alleFilme);
//					}
//				}
//			}
//			datentraegerRepository.save(datentraeger);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return false;
//	}
	
	@RequestMapping(value="dummies", method = RequestMethod.POST)
	public String createDatentraeger(){
		List<String[]> allDatentraegerCSV = null;
		try{
			Datentraeger datentraeger = null;
			allDatentraegerCSV = CSVReader.loadDataFromCSVFile( "/localhome/klee/csvData/csvShortData/Datentraeger.csv" );
			IOUtilities.printArrayContents( allDatentraegerCSV.toArray() );
			
			int ii = 0;
			for( String[] csvVals: allDatentraegerCSV ){
				datentraeger = new Datentraeger();
				datentraeger.setTyp(csvVals[1]);
				datentraeger.setSpeicherGroesse(Integer.parseInt( csvVals[2] ));
				datentraeger.setBeschreibung(csvVals[3]);
				datentraeger.setAnzahlFilme(Integer.parseInt(csvVals[4]));
				datentraeger.setBezeichner(csvVals[5]); 
				
				this.datentraegerRepository.save( datentraeger );
				ii++;
//				for( String string: csvVals ){
//					System.out.print( string + " # " );
//				}
//				System.out.println( csvVals );
			} 
			
			return "done ... (" + ii + " Datentraeger erzeugt)";

		}
		catch( IOException e ){
			e.printStackTrace();
			return "ERROR: " + e.getMessage();
		}
	}
	@RequestMapping(value="{id}/film/{idFilm}", method = RequestMethod.POST)
	public String addFilmToDatentraeger(@PathVariable Long id, @PathVariable Long idFilm){
		
		Datentraeger d = this.datentraegerRepository.findOne(id);
		if ( d != null ){
			Film f = filmRepository.findOne(idFilm);
			if ( f != null ){
				d.addFilm(f);
				datentraegerRepository.save(d);
				return "Film (id="+ idFilm + ") added to Datentraeger (id="+ id + ") successfully";
			}
		}
		
		return "Datentraeger (id="+ id + ") not found";
	}
	
	@RequestMapping(value="all")
	public List<Datentraeger> getAll(){
		
		List<Datentraeger> allDatentraeger = this.datentraegerRepository.findAll();
//		for( Datentraeger datentraeger: allDatentraeger ){
//			datentraeger.printOut();
//		}
		
		return allDatentraeger;
	}

	@RequestMapping ( value= "{id}", method = RequestMethod.GET)
	public Datentraeger getDatentraeger (@PathVariable("id") Long id){
		Datentraeger datentraeger = this.datentraegerRepository.findOne(id);
		if(datentraeger == null){
			throw new ElementNotFoundException("Could not found Datentraeger for id: " + id);
		}
		else{
			return datentraeger;
		}
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public List<Datentraeger> search ( @RequestParam("bezeichner") String bezeichner){
		return this.datentraegerRepository.findByBezeichner(bezeichner);
	}
	
	/**
	 * 
	 * @param musikstueck
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Datentraeger saveDatentraeger(@RequestBody Datentraeger datentraeger){
		return this.datentraegerRepository.save(datentraeger);
	}

}
