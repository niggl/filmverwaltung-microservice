package edu.kit.iai.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * A class to represent a Datentraeger
 */
@Entity
@Table( name="Datentraeger")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Datentraeger extends AbstractElement{

	/**
	 * Zur automatisierten Generierung der IDs vereinfacht wird vorausgesetzt,
	 * dass zuerst die CSV-Datei geladen wird
	 */
	protected static int currentID;
	
	@Basic
	@Column(length = 20, name= "Typ")
	private String typ;
	
	@Basic
	@Column(length = 8, name= "Groesse")
	private int speicherGroesse;
	
	@Basic
	@Column(length = 3, name= "anzahl_filme")
	private int anzahlFilme;
	
	@Basic
	@Column(length = 100, name= "Bezeichner")
	private String bezeichner;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "DATENTRAEGER_FILM", joinColumns = @JoinColumn(name = "DATENTRAEGER_ID"), inverseJoinColumns = @JoinColumn(name = "FILM_ID"))
	@JsonBackReference
	private List<Film> enthalteneFilme = new ArrayList<Film>();

	
	private static final String DEFAULT_DELIMMTER = ",";

	public static final String[] ATTRIBUTE_NAMES_4_TABLEDATA = new String[] {"ID", "Typ", "Speichergroesse", "Beschreibung", "Anzahl der Filme", "Name"};

	@Transient
	List<String> allAttributes = new ArrayList<String>();
	
	/**
	 * Creates a default Datentraeger
	 */
	public Datentraeger() {
	}

	/**
	 * Creates a Datentraeger with a given ID<br>
	 * if the ID already exists, it will be incremented until it is a plausible ID
	 * @param iD the id that will be used 
	public Datentraeger( int iD ) {
//		currentID = ( iD > currentID ? iD : currentID );
		this.iD = iD;
	}
	 */

	/**
	 * Returns the type
	 * @return the type
	 */
	public String getTyp() {
		return this.typ;
	}

	/**
	 * sets the type 
	 * @param typ the type that will be set
	 */
	public void setTyp( String typ ) {
		this.typ = typ;
	}

	/**
	 * returns the name
	 * @return the name
	 */
	public String getBezeichner() {
		return this.bezeichner;
	}

	/**
	 * sets the name 
	 * @param bezeichner the name that will be set
	 */
	public void setBezeichner( String bezeichner ) {
		this.bezeichner = bezeichner;
	}

	/**
	 * returns the speicherGroesse
	 * @return the speicherGroesse
	 */
	public int getSpeicherGroesse() {
		return this.speicherGroesse;
	}

	/**
	 * sets the speicherGroesse 
	 * @param speicherGroesse the speicherGroesse that will be set
	 */
	public void setSpeicherGroesse( int speicherGroesse ) {
		this.speicherGroesse = speicherGroesse;
	}

	/**
	 * returns the anzahlFilme
	 * @return the anzahlFilme
	 */
	public int getAnzahlFilme() {
		return this.anzahlFilme;
	}

	/**
	 * sets the anzahlFilme
	 * @param anzahlFilme the anzahlFilme that will be set
	 */
	public void setAnzahlFilme( int anzahlFilme ) {
		this.anzahlFilme = anzahlFilme;
	}

	/**
	 * returns the enthalteneFilme
	 * @return a List<Film> with the contained films
	 */
	public List<Film> getEnthalteneFilme() {
		return this.enthalteneFilme;
	}

	/**
	 * sets the enthalteneFilme
	 * @param enthalteneFilme the List that will be set
	 */
	public void setEnthalteneFilme( List<Film> enthalteneFilme ) {
		this.enthalteneFilme = enthalteneFilme;
	}

	/**
	 * To print out the whole class
	 */
	public void printOut() {
		System.out.println( "iD: " + this.getiD() );
		System.out.println( "Typ: " + this.getTyp() );
		System.out.println( "Speichergroesse: " + this.getSpeicherGroesse() );
		System.out.println( "Beschreibung: " + this.getBeschreibung() );
		System.out.println( "AnzahlFlme: " + this.getAnzahlFilme() );
		System.out.println( "Name: " + this.bezeichner );
		System.out.println("Filme: ");
		for( Film film: this.enthalteneFilme ){
			System.out.println(film);
		}
		System.out.println( "------------------------------------------------------" );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String attributes = this.getClass().getSimpleName() + ", Name: " + this.bezeichner + ", Typ: "
				+ this.getTyp() + " Speichergroesse: " + speicherGroesse + "\n Beschreibung: " + beschreibung + " anzahlFilme: " + 
				this.anzahlFilme + "\n Enthaltene Filme: \n";
		StringBuffer stringBuffer = new StringBuffer(attributes);
		
		return stringBuffer.toString();
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals( Object obj ) {

		if( obj == null ){
			return false;
		}
		else if( this == obj ){
			return true;
		}
		else if( obj.getClass() != this.getClass() ){
			return false;
		}
		else if( this.iD == ( (Datentraeger)obj ).getiD() ){
			return true;
		}

		return false;
	}

	/**
	 * Adds a film in the {@link #enthalteneFilme} list
	 * @param film the film that will be added
	 */
	public void addFilm( Film film ){
		if( film != null ){
			this.enthalteneFilme.add( film );
		}
		else{
			throw new IllegalArgumentException( "404 Film not Found!" );
		}
	}


}
