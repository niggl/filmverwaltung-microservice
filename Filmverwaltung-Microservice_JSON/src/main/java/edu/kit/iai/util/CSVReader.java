package edu.kit.iai.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

	public static final char DEFAULT_DELIMITER = ';';
	
	public CSVReader() {
		
	}
	
	/**
	 * Liest aus einer CSV-Datei Datentraeger aus und erzeugt fuer jeden Datentraeger eine Instanz im Entity-Manager
	 * @param fileName Pfad zur CSV-Datei.
	 * @throws IOException Wenn die CSV-Datei nicht gefunden wird/nicht existiert.
	 * @throws NumberFormatException 
	 * @throws ParseException 
	 */
	public final static void loadDatentraegerFromCSVFile( String fileName ) throws IOException, NumberFormatException, ParseException {
//		String[] args = null;
//		File csvFile = null;
//		try{
//			csvFile = checkFile( fileName );		
//		}
//		catch( Exception e ){
//			System.out.println(e.getMessage());
//			return; 
//		}
//
//		BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( csvFile ), "UTF8" ) );
//		String line = "";
//
//		// Hardcodiert fuer laenge = 4
//		while( line != null ){
//			line = reader.readLine();
//			if( line == null ) break;
//			Object c;
//			if( line.startsWith( "#" ) ){
//				continue;
//			}
//			args = StringUtilities.splitString( line, DEFAULT_DELIMITER, true );
//			Datentraeger dt = (Datentraeger)ElementFactory.createElement( Datentraeger.class, args );
//			if ( !eManager.containsElement( dt )) {
//				eManager.persist( dt );
//			}
//		}
	}
	
	public final static void loadFilmGenreFromCSVFile( String fileName ) throws IOException, ParseException {
//		String[] args = null;
//		File csvFile = null;
//		try{
//			csvFile = checkFile( fileName );		
//		}
//		catch( Exception e ){
//			System.out.println(e.getMessage());
//			return; 
//		}		
//		EntityManager eManager = EntityManager.getInstance();
//		BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( csvFile ), "UTF8" ) );
//		String line = "";
//
//		// Hardcodiert fuer laenge = 4
//		while( line != null ){
//			line = reader.readLine();
//			if( line == null ) break;
//			Object c;
//			if( line.startsWith( "#" ) ){
//				continue;
//			}
//			args = StringUtilities.splitString( line, DEFAULT_DELIMITER, true );
//			
//			for( int i = 0 ; i < args.length ; i++ ){
//				System.out.println("####" + args);
//			}
//			
//			FilmGenre dt = (FilmGenre)ElementFactory.createElement( FilmGenre.class, args );
//			if ( !eManager.containsElement( dt ))	eManager.persist( dt );
//			
//		}
//
	}

	
	private static final File checkFile(String fileName) throws IllegalArgumentException{
		if(fileName == null || fileName.length() == 0) throw new IllegalArgumentException( "Filenname muss gegeben sein" );
		File csvFile = new File( fileName );
		if( !csvFile.exists())throw new IllegalArgumentException( "Datei existiert nicht" );

		return csvFile;
	}
	
	public final static List<String[]> loadDataFromCSVFile( String fileName ) throws IOException {
		List<String[]> allLines = new ArrayList<String[]>();
		
		File csvFile = null;
		try{
			csvFile = checkFile( fileName );		
		}
		catch( Exception e ){
			System.out.println(e.getMessage());
			return allLines; 
		}
		BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( csvFile ), "UTF8" ) );
//		EntityManager eManager = EntityManager.getInstance();
		String line = "";
		String[] lineElements = null;
		// Hardcodiert fuer laenge = 4
		while( line != null ){
			line = reader.readLine();
			if( line == null ) break;
			
			if( line.startsWith( "#" ) ){
				continue;
			}
		
			lineElements = StringUtilities.splitString( line, DEFAULT_DELIMITER );
			allLines.add( lineElements );
		}
		
		return allLines;
	}
	/**
	 * Liest aus einer CSV-Datei Personen aus und erzeugt fuer jede Person eine Instanz im Entity-Manager
	 * @param fileName Pfad zur CSV-Datei.
	 * @return 0, wenn die CSV-Datei gelesen wurde.  
	 * @throws IOException Wenn die CSV-Datei nicht gefunden wird/nicht existiert.
	 * @throws NumberFormatException 
	 * @throws ElementAlreadyExistsException Wenn eine Person bereits existiert.
	 * @throws ParseException 
	 */
	public final static void loadPersonFromCSVFile( String fileName ) throws IOException, NumberFormatException, ParseException {
//		String[] args = null;
//		File csvFile = null;
//		try{
//			csvFile = checkFile( fileName );		
//		}
//		catch( Exception e ){
//			System.out.println(e.getMessage());
//			return; 
//		}
//		BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( csvFile ), "UTF8" ) );
////		EntityManager eManager = EntityManager.getInstance();
//		String line = "";
//		// Hardcodiert fuer laenge = 4
//		while( line != null ){
//			line = reader.readLine();
//			if( line == null ) break;
//
//			if( line.startsWith( "#" ) ){
//				continue;
//			}
//			args = StringUtilities.splitString( line, DEFAULT_DELIMITER );
//			Person p = (Person)ElementFactory.createElement( Person.class, args );
//			if ( !eManager.containsElement( p ))	eManager.persist( p );
//
////				p.printOut();
//		}
	}
	
	/**
	 * Liest aus einer CSV-Datei Personen aus und erzeugt fuer jede Person eine Instanz im Entity-Manager
	 * @param fileName Pfad zur CSV-Datei.
	 * @return 0, wenn die CSV-Datei gelesen wurde.  
	 * @throws IOException Wenn die CSV-Datei nicht gefunden wird/nicht existiert.
	 * @throws NumberFormatException 
	 * @throws ElementAlreadyExistsException Wenn eine Person bereits existiert.
	 * @throws ParseException 
	 */
	public final static void loadFilmeFromCSVFile( String fileName ) throws IOException, NumberFormatException, ParseException {
//		String[] args = null;
//		File csvFile = null;
//		try{
//			csvFile = checkFile( fileName );		
//		}
//		catch( Exception e ){
//			System.out.println(e.getMessage());
//			return; 
//		}
//		BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( csvFile ), "UTF8" ) );
//		EntityManager eManager = EntityManager.getInstance();
//		String line = "";
//		// Hardcodiert fuer laenge = 4
//		while( line != null ){
//			line = reader.readLine();
//			if( line == null ) break;
//
//			if( line.startsWith( "#" ) ){
//				continue;
//			}
//			args = StringUtilities.splitString( line, DEFAULT_DELIMITER, true );
//			Film f = (Film)ElementFactory.createElement( Film.class, args );
//			if ( !eManager.containsElement( f )) eManager.persist( f );
//		}
//		/**
//		 * Da die Datentraeger vor den Filmen erzeugt werden, kann man keine Filme hinzufuegen 
//		 * deshalb muss das hier passieren.
//		 */
//        List<AbstractElement> listDatentraeger = eManager.findAll( Datentraeger.class );
//        for( AbstractElement ae: listDatentraeger ){
//                Datentraeger dt = ((Datentraeger)ae);
//                int[] tmpIDs = dt.getFilmIDs();
//                for( int i = 0 ; i < tmpIDs.length ; i++ ){
//                        dt.addFilm( (Film)eManager.find( Film.class, tmpIDs[i] ));
//                }
//        }
//        
//        List<AbstractElement> listPersonen = eManager.findAll( Person.class );
//        for( AbstractElement ae: listPersonen ){
//                Person p = ((Person)ae);
//                int[] tmpIDs = p.getFilmIDs();
//                for( int i = 0 ; i < tmpIDs.length ; i++ ){
//                        p.addFilm( (Film)eManager.find( Film.class, tmpIDs[i] ));
//                }
//        }
	}
	
	/**
	 * Liest aus einer CSV-Datei Personen aus und erzeugt fuer jede Person eine Instanz im Entity-Manager
	 * @param fileName Pfad zur CSV-Datei.
	 * @return 0, wenn die CSV-Datei gelesen wurde.  
	 * @throws IOException Wenn die CSV-Datei nicht gefunden wird/nicht existiert.
	 * @throws NumberFormatException 
	 * @throws ElementAlreadyExistsException Wenn eine Person bereits existiert.
	 * @throws ParseException 
	 */
	public final static void loadMusikstueckFromCSVFile( String fileName ) throws IOException, NumberFormatException, ParseException {
//		String[] args = null;
//		File csvFile = null;
//		try{
//			csvFile = checkFile( fileName );		
//		}
//		catch( Exception e ){
//			System.out.println(e.getMessage());
//			return; 
//		}
//		BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( csvFile ), "UTF8" ) );
//		EntityManager eManager = EntityManager.getInstance();
//		String line = "";
//
//		// Hardcodiert fuer laenge = 4
//		while( line != null ){
//			line = reader.readLine();
//			if( line == null ) break;
//
//			if( line.startsWith( "#" ) ){
//				continue;
//			}
//			args = StringUtilities.splitString( line, DEFAULT_DELIMITER , true );
//			Musikstueck ms = (Musikstueck)ElementFactory.createElement( Musikstueck.class, args );
//			if ( !eManager.containsElement( ms ))	eManager.persist( ms );
//			ms.printOut();
//		}
	}
}
