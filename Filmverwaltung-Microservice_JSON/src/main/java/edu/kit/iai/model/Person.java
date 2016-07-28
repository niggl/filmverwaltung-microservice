package edu.kit.iai.model;

//import gui.IGUIConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * A class to represent a Person 
 */
//@DiscriminatorColumn(name="typ", length=8)
//@DiscriminatorValue(value="PERSON")
@Entity
@Table( name="Person")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Person extends AbstractElement{

	protected static int currentID;
	
	
	@Basic
	@Column(length = 100, name = "Vorname")
	private String vorname;
	
	@Basic
	@Column(length = 100, name = "Nachname")
	private String nachname;
	
	@Basic
	@Column(length = 50, name = "Nationalitaet")
	private String nationalitaet;
	
	@Basic
	@Temporal(value = TemporalType.DATE)
	@Column(length = 9, name = "geb_datum")
	private Date geburtsDatum;
	
	@Basic
	@Column(length = 100, name = "geb_ort")
	private String geburtsOrt;
	
	@Basic
	@Column(name = "Homepage")
	private String hompage;

	@ManyToMany
	@JoinTable(name="PERSON_FILM", joinColumns=@JoinColumn(name="PERSON_ID"), inverseJoinColumns=@JoinColumn(name="FILM_ID"))
	@JsonBackReference
	private List<Film> filme = new ArrayList<Film>();
	
	private static final String DEFAULT_DELIMMTER = ",";
	
    /**
     * the attribute names that are used for the columnNames
     */
    public static final String[] ATTRIBUTE_NAMES_4_ANLEGEN = new String[] {"Vorname", "Nachname", "Nationalitaet", "Geburtsdatum",
        "Geburtsort", "Homepage", "Beschreibung"};

    /**
     * the key for a HasMap to get all films
     */
    public static final String ATTRIBUTE_4_AUSWAHLISTE_FILME = "Filme";

    /**
     * the key for a HashMap to get all Films
     */
    public static final String[] ATTRIBUTE_NAMES_4_ANLEGEN_JLIST = new String[] {ATTRIBUTE_4_AUSWAHLISTE_FILME};

    /**
     * the attribute names that are used for the columnNames
     */
	public static final String[] ATTRIBUTE_NAMES_4_TABLEDATA = new String[] {"ID", "Vorname", "Nachname", "Nationalitaet", "Geb. Datum",
		"Geburtsort", "Homepage", "Beschreibung"};

	@Transient
	private List<String> allAttributes = new ArrayList<String>();
	
	/**
	 * Creates a Person with a available ID
	 */
	public Person() {
	}
	
	/**
	 * Creates a Person with a given ID<br>
	 * if the ID already exists, it will be incremented until it is a plausible ID
	 * @param iD the id that will be used 
	 */
	public Person( Long iD) {
		this.iD = iD;
	}

	/**
	 * returns the vorname
	 * @return the vorname
	 */
	public String getVorname() {
		return this.vorname;
	}

	/**
	 * sets the vorname 
	 * @param vorname the vorname that will be set
	 */
	public void setVorname( String vorname ) {
		this.vorname = vorname;
	}

	/**
	 * returns the nachname
	 * @return the nachname 
	 */
	public String getNachname() {
		return this.nachname;
	}

	/**
	 * sets the nachname 
	 * @param nachname the nachname that will be set
	 */
	public void setNachname( String nachname ) {
		this.nachname = nachname;
	}

	/**
	 * returns the nationalitaet
	 * @return the nationalitaet
	 */
	public String getNationalitaet() {
		return this.nationalitaet;
	}

	/**
	 * sets the nationalitaet
	 * @param nationalitaet that will be set
	 */
	public void setNationalitaet( String nationalitaet ) {
		this.nationalitaet = nationalitaet;
	}

	/**
	 * returns the geburtsdatum
	 * @return the geburtsdatum
	 */
	public Date getGeburtsDatum() {
		return this.geburtsDatum;
	}

	/**
	 * sets the geburtsdatum
	 * @param geburtsDatum the geburtsdatum that will be set
	 */
	public void setGeburtsDatum( Date geburtsDatum ) {
		this.geburtsDatum = geburtsDatum;
	}

	/**
	 * returns the geburtsort
	 * @return the geburtsort
	 */
	public String getGeburtsOrt() {
		return this.geburtsOrt;
	}

	/**
	 * sets the geburtsort
	 * @param geburtsOrt the geburtsort that will be set
	 */
	public void setGeburtsOrt( String geburtsOrt ) {
		this.geburtsOrt = geburtsOrt;
	}
	
	/**
	 * returns the homepage
	 * @return the homepage
	 */
	public String getHompage() {
		return this.hompage;
	}
	
	/**
	 * sets the homepage
	 * @param hompage
	 */
	public void setHompage( String hompage ) {
		this.hompage = hompage;
	}
	
	/**
	 * returns all filme as a List<Film>
	 * @return filme as a List<Film>
	 */
	public List<Film> getFilme() {
		return this.filme;
	}
	
	/**
	 * to print out the whole class
	 */
	public void printOut(){
		System.out.println("iD: " + this.getiD());
		System.out.println("Vorname: " + this.getVorname());
		System.out.println("Nachname: " + this.getNachname());
		System.out.println("Nationalitaet: " + this.getNationalitaet());
		System.out.println("Geburtsdatum: " + this.getGeburtsDatum());
		System.out.println("Geburtsort: " + this.getGeburtsOrt());
		System.out.println("Hompage: " + this.getHompage());
		System.out.println("Beschreibung: " + this.getBeschreibung());
		if(this.filme != null && this.filme.size() > 0 ){
			for (Film f : filme) {
				f.printOut();
			}
		}
		System.out.println("------------------------------------------------------");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getNachname() + ", " + this.vorname ;
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
		else if( this.iD == ( (Person)obj ).getiD() ){
			return true;
		}

		return false;
	}

	/**
	 * adds a film to the list
	 * @param film the film that will be added
	 */
	public void addFilm( Film film ) {
		this.filme.add( film );
	}

}