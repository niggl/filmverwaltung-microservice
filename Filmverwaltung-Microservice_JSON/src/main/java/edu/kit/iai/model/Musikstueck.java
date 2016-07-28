package edu.kit.iai.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * A class to represent a Musikstueck 
 */
//@DiscriminatorColumn(name="typ", length=8)
//@DiscriminatorValue(value="MUSIK")

//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Entity
@Table(name="Musikstueck")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Musikstueck extends AbstractElement{
	@Basic
	@Column(length = 200, name = "Titel")
	private String titel;

	
	@Basic
	@Column(name="Interpret")
	private String interpret;
	
	@Basic
	@Column(name="Album")
	private String album;
	
	@Basic
	@Column(name="Erscheinungsjahr")
	private String erscheinungsJahr;
	
	@Basic
	@Column(name="Genre")
	private String Genre;
	
	@Basic
	@Column(name="Pfad")
	private String pfad;
	
	@Transient
	List<String> allAttributes = new ArrayList<String>();
	
    /**
     * the attribute names that are used for the columnNames
     */
	public static final String[] ATTRIBUTE_NAMES_4_TABLEDATA = new String[] {"ID", "Titel", "Interpret", "Album", "Erscheinungsjahr", "Beschreibung", "Genre", "Pfad"};

	
	/**
	 * Creates a default Musikstueck
	 */
	public Musikstueck() {
	}
	
	/**
	 * Creates a Musikstueck with a given ID<br>
	 * if the ID already exists, it will be incremented until it is a plausible ID
	 * @param iD the id that will be used 
	public Musikstueck( int iD) {
//		currentID = ( iD > currentID ? iD : currentID );
		this.iD = iD;
	}
	 */

	/**
	 * returns the interpreter
	 * @return the interpreter
	 */
	public String getInterpret() {
		return this.interpret;
	}
	
	/**
	 * sets the interpreter
	 * @param interpret the interpreter that will be set
	 */
	public void setInterpret( String interpret ) {
		this.interpret = interpret;
	}
	
	/**
	 * returns the album
	 * @return the album
	 */
	public String getAlbum() {
		return this.album;
	}
	
	/**
	 * sets the album 
	 * @param album the album that will be set
	 */
	public void setAlbum( String album ) {
		this.album = album;
	}

	/**
	 * returns the erscheinungsjahr
	 * @return the erscheinungsjahr that will be set
	 */
	public String getErscheinungsJahr() {
		return this.erscheinungsJahr;
	}

	/**
	 * sets the erscheinungsjahr
	 * @param erscheinungsjahr the erscheinungsjahr that will be set
	 */
	public void setErscheinungsJahr( String erscheinungsjahr ) {
		this.erscheinungsJahr = erscheinungsjahr;
	}
	
	/**
	 * returns the genre
	 * @return the genre
	 */
	public String getGenre() {
		return this.Genre;
	}
	
	/**
	 * sets the genre
	 * @param genre the genre that will be set
	 */
	public void setGenre( String genre ) {
		this.Genre = genre;
	}
	
	/**
	 * returns the pfad
	 * @return the pfad
	 */
	public String getPfad() {
		return this.pfad;
	}
	
	/**
	 * sets the pfad
	 * @param pfad the pfad that will be set
	 */
	public void setPfad( String pfad ) {
		this.pfad = pfad;
	}

	/**
	 * to print out the whole class
	 */
	public void printOut() {
		System.out.println("iD: " + this.getiD());
		System.out.println("Titel: " + this.titel);
		System.out.println("Interpret: " + this.getInterpret());
		System.out.println("Album: " + this.getAlbum());
		System.out.println("Erscheinungsjahr: " + this.getErscheinungsJahr());
		System.out.println("Beschreibung: " + this.getBeschreibung());
		System.out.println("Genre: " + this.getGenre());
		System.out.println("Pfad: " + this.getPfad());
		System.out.println("------------------------------------------------------");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + this.getiD() + ", Name: " + this.titel + ", Interpret: " + this.getInterpret();
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
		else if( this.iD == ( (Musikstueck)obj ).getiD() ){
			return true;
		}

		return false;
	}

	public String getTitel() {
		return this.titel;
	}

	public void setTitel( String titel ) {
		this.titel = titel;
	}

	
}
