package edu.kit.iai.model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * A class to represent a Film 
 */
//@DiscriminatorColumn(name="typ", length=8)
//@DiscriminatorValue(value="FILM")

//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Entity
@Table(name="Film")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Film extends AbstractElement {
	@Transient
    public static final String[] ATTRIBUTE_NAMES_4_TABLEDATA = new String[] {"ID", "Titel", "Produktionsland", "Laenge", "Originalsprache",
        "Erscheinungsjahr", "FSK", "Kameraperson"};

	@Transient
	private static int currentID;
	
	@Basic
	@Column(length = 200, name = "Titel")
	private String titel;

	@Basic
	@Column(length = 10, name = "Jahr")
	private String erscheinungsJahr;
	
	@Basic
	@Column(length = 50, name = "prod_land")
	private String produktionsLand;
	
	@Basic
	@Column(length = 3, name = "FSK")
	int altersFreigabe;
	
	@Basic
	@Column(length = 50, name= "Sprache")
	private String originalSprache;
	
	@Basic
	@Column(length = 4, name = "laenge")
	private int filmLaenge;
	

	
	@OneToMany( fetch = FetchType.LAZY )
	@JoinTable(name = "FILM_MUSIKSTUECK", joinColumns = @JoinColumn(name = "Film_ID"), inverseJoinColumns=@JoinColumn(name = "MUSIK_ID"))
	private List<Musikstueck> listMusickstuecke = new ArrayList<Musikstueck>();
	
	@ManyToMany( fetch = FetchType.LAZY )
	@JoinTable(name="FILM_DATENTRAEGER", joinColumns=@JoinColumn(name="Film_ID"), inverseJoinColumns=@JoinColumn(name="DATENTRAEGER_ID"))
//	@JsonManagedReference
	private List<Datentraeger> listDatentraeger = new ArrayList<Datentraeger>();
	
//	@Transient
	@ManyToMany( fetch = FetchType.LAZY )
	@JoinTable(name="FILM_DARSTELLER", joinColumns=@JoinColumn(name="Film_ID"), inverseJoinColumns=@JoinColumn(name="DARSTELLER_ID"))
	private List<Person> listDarsteller = new ArrayList<Person>();
	
	@OneToMany
	@JoinTable(name = "FILM_FILMGENRE", joinColumns = @JoinColumn(name = "Film_ID"), inverseJoinColumns = @JoinColumn(name = "film_genre_id"))
	private List<FilmGenre> listFilmGenre = new ArrayList<FilmGenre>();

	@ManyToOne
	@JoinColumn(name="KAMERAPERSON_ID")
	private Person kameraHauptperson;

	@ManyToMany( fetch = FetchType.LAZY )
	@JoinTable(name="FILM_REGISSEURE", joinColumns=@JoinColumn(name="Film_ID"), inverseJoinColumns=@JoinColumn(name="REGISSEURE_ID"))
	private List<Person> listRegisseure = new ArrayList<Person>();
	
	@ManyToMany( fetch = FetchType.LAZY )
	@JoinTable(name="FILM_SYNCHRONSPRECHER", joinColumns=@JoinColumn(name="Film_ID"), inverseJoinColumns=@JoinColumn(name="SYNCHRONSPRECHER_ID"))
	private List<Person> listSynchronsprecher = new ArrayList<Person>();
	
	
    /**
     * A default delimmeter
     */
	public static final String DEFAULT_DELIMMTER = ",";
	
	
	/**
	 * Creates a Film with a available ID
	 */
	public Film() {
	}
	
	/**
	 * Creates a Film with a given ID<br>
	 * if the ID already exists, it will be incremented until it is a plausible ID
	 * @param iD the id that will be used 
	public Film( int iD) {
//		currentID = ( iD > currentID ? iD : currentID );
		this.iD = iD;
	}
	 */
	
	/**
	 * returns the title
	 * @return the title
	 */
	public String getTitel() {
		return this.titel;
	}

	/**
	 * sets the title
	 * @param titel the title that will be set
	 */
	public void setTitel( String titel ) {
		this.titel = titel;
	}

	/**
	 * returns the erscheinungsjahr
	 * @return the erscheinungsjahr
	 */
	public String getErscheinungsJahr() {
		return this.erscheinungsJahr;
	}

	/**
	 * sets the erscheinungsjahr
	 * @param erscheinungsJahr the erscheinungsjahr that will be set
	 */
	public void setErscheinungsJahr( String erscheinungsJahr ) {
		this.erscheinungsJahr = erscheinungsJahr;
	}

	/**
	 * returns the produktionsland
	 * @return the produktionsland
	 */
	public String getProduktionsLand() {
		return this.produktionsLand;
	}

	/**
	 * sets the produktionsland
	 * @param produktionsLand the produktionsland that will be set
	 */
	public void setProduktionsLand( String produktionsLand ) {
		this.produktionsLand = produktionsLand;
	}

	/**
	 * returns the altersfreigabe
	 * @return
	 */
	public int getAltersFreigabe() {
		return this.altersFreigabe;
	}

	/**
	 * sets the altersfreigabe
	 * @param altersFreigabe the altersfeigabe that will be set
	 */
	public void setAltersFreigabe( int altersFreigabe ) {
		this.altersFreigabe = altersFreigabe;
	}

	/**
	 * returns the orgiginalsprache
	 * @return the originalsprache
	 */
	public String getOriginalSprache() {
		return this.originalSprache;
	}

	/**
	 * sets the originalsprache
	 * @param originalSprache the originalsprache that will be set
	 */
	public void setOriginalSprache( String originalSprache ) {
		this.originalSprache = originalSprache;
	}

	/**
	 * returns the filmlaenge
	 * @return the filmlaenge
	 */
	public int getFilmLaenge() {
		return this.filmLaenge;
	}

	/**
	 * sets the filmlaenge
	 * @param filmLaenge the filmlaenge that will be set
	 * 
	 */
	public void setFilmLaenge( int filmLaenge ) {
		this.filmLaenge = filmLaenge;
	}

	/**
	 * returns a List<Person> of all regisseure
	 * @return List<Person> of all regisseure
	 */
	public List<Person> getListRegisseure() {
		return this.listRegisseure;
	}

	/**
	 * sets the List<Person> of all regisseure
	 * @param listRegisseure the list that will be set
	 */
	public void setListRegisseure( List<Person> listRegisseure ) {
		this.listRegisseure.clear();
		this.listRegisseure.addAll( listRegisseure );
	}

	/**
	 * sets the list of all regisseure
	 * @param listRegisseure the list that will be set
	 */
	public void addRegisseure( List<Person> listRegisseure ) {
		this.listRegisseure.addAll( listRegisseure );
	}
	
	/**
	 * adds a regisseure to the list
	 * @param regisseur the regisseure that will be added
	 */
	public void addRegisseur( Person regisseur ) {
		this.listRegisseure.add( regisseur );
	}

	/**
	 * returns the list of all darsteller
	 * @return the list of all darsteller
	 */
	public List<Person> getListDarsteller() {
		return this.listDarsteller;
	}

	/**
	 * sets the list of all darsteller
	 * @param listDarsteller the list that will be set
	 */
	public void setListDarsteller( List<Person> listDarsteller ) {
		this.listDarsteller.clear();
		this.listDarsteller.addAll( listDarsteller );
	}

	/**
	 * adds a new darsteller
	 * @param darsteller darsteller
	 */
	public void addDarsteller( Person darsteller ) {
		this.listDarsteller.add( darsteller );
	}
	
	/**
	 * returns the list of all synchronsprecher
	 * @return list of all synchronsprecher
	 */
	public List<Person> getListSynchronsprecher() {
		return this.listSynchronsprecher;
	}

	/**
	 * sets the list of all synchronsprecher
	 * @param listSynchronsprecher the list that will be set
	 */
	public void setListSynchronsprecher( List<Person> listSynchronsprecher ) {
		this.listSynchronsprecher.clear();
		this.listSynchronsprecher.addAll( listSynchronsprecher );
	}

	/**
	 * returns the kamerahauptperson
	 * @return the kamerahauptperson
	 */
	public Person getKameraHauptperson() {
		return this.kameraHauptperson;
	}

	/**
	 * sets the kamerahauptperson 
	 * @param kameraHauptperson the kamerahauptperson that will be set
	 */
	public void setKameraHauptperson( Person kameraHauptperson ) {
		this.kameraHauptperson = kameraHauptperson;
	}

	/**
	 * returns the list of all musikstuecke
	 * @return the list of all musikstuecke
	 */
	public List<Musikstueck> getListMusickstuecke() {
		return this.listMusickstuecke;
	}

	/**
	 * sets the list of all musikstuecke
	 * @param listMusickstuecke the list that will be set
	 */
	public void setListMusickstuecke( List<Musikstueck> listMusickstuecke ) {
		this.listMusickstuecke = listMusickstuecke ;
	}

	/**
	 * returns the filmgenre
	 * @return the filmgenre
	 */
	public List<FilmGenre> getListFilmGenre() {
		return this.listFilmGenre;
	}

	/**
	 * sets the filmgenre 
	 * @param listFilmGenre the filmgenre that will be set
	 */
	public void setListFilmGenre( List<FilmGenre> listFilmGenre ) {
		this.listFilmGenre = listFilmGenre;
	}

	/**
	 * returns the list of all Datentraeger
	 * @return the list of all Datentraeger
	 */
	public List<Datentraeger> getDatentraeger() {
		return this.listDatentraeger;
	}

	/**
	 * sets the list of all Datentraeger
	 * @param listDatentraeger the list of all Datentraeger that will be set
	 */
	public void setListDatentraeger( List<Datentraeger> listDatentraeger ) {
		this.listDatentraeger = listDatentraeger;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String attributes = this.getClass().getSimpleName() + ", iD: " + this.getiD() + ", Titel: " + this.titel + ", Erscheinungsjahr: " + this.getErscheinungsJahr() + ", FSK: " + this.altersFreigabe + ", Datentraeger\n";
		StringBuffer stringBuffer = new StringBuffer(attributes);
		
		for (Datentraeger dt : this.listDatentraeger) {
			stringBuffer.append(dt.toString());
		}
		return stringBuffer.toString();
//		Stringbuffer verwenden fuer Filme
		
	}
	
	public String[] getAttributes4TableData() {
		String[] attArray = new String[ATTRIBUTE_NAMES_4_TABLEDATA.length];
		int ii = 0;
		attArray[ii++] = "" + this.iD;
		attArray[ii++] = this.titel;
		attArray[ii++] = this.produktionsLand;
		attArray[ii++] = ""+this.filmLaenge;
		attArray[ii++] = this.originalSprache;
		attArray[ii++] = this.erscheinungsJahr;
		attArray[ii++] = ""+this.altersFreigabe;
		if ( this.kameraHauptperson != null ) {
			attArray[ii++] = this.kameraHauptperson.getVorname() + " " + this.kameraHauptperson.getNachname();
		}
		else {
			attArray[ii++] = "No name given";
		}
		
		return attArray;
	}
	
	/**
	 * to print out the whole class
	 */
	public void printOut(){
		System.out.println("iD: " + this.getiD());
		System.out.println("Titel: " + this.getTitel());
		System.out.println("Produktionsland: " + this.getProduktionsLand());
		System.out.println("Filmlaenge: " + this.getFilmLaenge());
		System.out.println("Originalsprache: " + this.getOriginalSprache());
		System.out.println("Erscheinungsjahr: " + this.getErscheinungsJahr());
		System.out.println("Altersfreigabe: " + this.getAltersFreigabe());
		if(this.kameraHauptperson != null) {
			System.out.println( "Kameraperson: "  + this.getKameraHauptperson().toString() );
		}
		else {
			System.out.println( "Keine Kameraperson vorhanden ");

		}
		System.out.println("----------------------------");
		if ( this.listFilmGenre != null) {
			for( FilmGenre filmGenre: this.listFilmGenre ){
				System.out.println("Filmgenre: " + filmGenre);
			}
		}
		System.out.println("----------------------------");
		for( Person darsteller: this.listDarsteller ){
			System.out.println("Darsteller: " + darsteller);
		}
		System.out.println("----------------------------");
		for( Person synchronSpr: this.listSynchronsprecher ){
			System.out.println("synchro: " + synchronSpr);
		}
		System.out.println("----------------------------");
		for( Person regi: this.listRegisseure ){
			System.out.println("regi: " + regi);
		}
		System.out.println("----------------------------");
		for( Musikstueck mstk: this.listMusickstuecke ){
			System.out.println("Musikstueck: " + mstk);
		}
		System.out.println("----------------------------");
		System.out.println("Datentraeger: " + this.getDatentraeger());
		System.out.println("----------------------------");
	}
	
	/**
	 * {@inheritDoc}
	 */
    @Override
    public boolean equals( Object obj ) {

            if(obj == null) {
                    return false;
            }
            else if(this == obj) {
                    return true;
            }
            else if (obj.getClass() != this.getClass()) {
                    return false;
            }
            else if (this.iD == ((Film)obj).getiD() ) {
                    return true;
            }

            return false;
    }
    
	/**
	 * adds the ids for the persons
	 * @param list the list of persons the ids will be set
	 * @return
	 */
	private String addIDsNew(List<Person> list) {
		String iD = "";
		for( int i = 0 ; i < list.size(); i++ ){
			if( i == list.size() - 1 ){
				iD = iD + list.get( i ).iD;

			}
			else{
				iD = iD + list.get( i ).iD + DEFAULT_DELIMMTER;
			}
			
		}
		return iD;
	}
	
	/**
	 * adds the ids for the persons
	 * @param list the list of persons the ids will be set
	 * @return
	 */
	private String addIDsFilmGenre(List<FilmGenre> list) {
		String iD = "";
		for( int i = 0 ; i < list.size(); i++ ){
			if( i == list.size() - 1 ){
				iD = iD + list.get( i ).iD;
				
			}
			else{
				iD = iD + list.get( i ).iD + DEFAULT_DELIMMTER;
			}
			
		}
		return iD;
	}
	
	/**
	 * adds the ids for the datentraeger
	 * @param list the list of datentraeger the ids will be set
	 * @return
	 */
	private String addIDatentraegerNew(List<Datentraeger> list) {
		String iD = "";
		for( int i = 0 ; i < list.size(); i++ ){
			if( i == list.size() - 1 ){
				iD = iD + list.get( i ).iD;

			}
			else{
				iD = iD + list.get( i ).iD + DEFAULT_DELIMMTER;
			}
			
		}
		return iD;
	}
}
