//package model;
//
//import gui.IGUIConstants;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.DiscriminatorColumn;
//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;
//import javax.persistence.Inheritance;
//import javax.persistence.InheritanceType;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
///**
// * A class to represent a Person 
// */
//@Entity
//@Table( name="Person")
//@DiscriminatorColumn(name="typ", length=8)
//@DiscriminatorValue(value="PERSON")
//@Inheritance(strategy=InheritanceType.JOINED)
//public class Person extends AbstractElement{
//
//	protected static int currentID;
//	
//	@Basic
//	@Column(length = 50, name = "Vorname")
//	private String vorname;
//	
//	@Basic
//	@Column(length = 50, name = "Nachname")
//	private String nachname;
//	
//	@Basic
//	@Column(length = 50, name = "Nationalitaet")
//	private String nationalitaet;
//	
//	@Basic
//	@Column(length = 10, name = "GebDatum")
//	private Date geburtsDatum;
//	
//	@Basic
//	@Column(length = 50, name = "GebOrt")
//	private String geburtsOrt;
//	
//	@Basic
//	@Column(length = 100, name = "Homepage")
//	private String hompage;
//	
//	@OneToMany
//	@JoinTable(name="Person_Musikstueck", joinColumns=@JoinColumn(name = "Person_ID"), inverseJoinColumns= @JoinColumn(name="Film_ID"))
//	private List<Film> filme = new ArrayList<Film>();
//	
//	private static final String DEFAULT_DELIMMTER = ",";
//	
//    /**
//     * the attribute names that are used for the columnNames
//     */
//    public static final String[] ATTRIBUTE_NAMES_4_ANLEGEN = new String[] {"Vorname", "Nachname", "Nationalitaet", "Geburtsdatum",
//        "Geburtsort", "Homepage", "Beschreibung"};
//
//    /**
//     * the key for a HasMap to get all films
//     */
//    public static final String ATTRIBUTE_4_AUSWAHLISTE_FILME = "Filme";
//
//    /**
//     * the key for a HashMap to get all Films
//     */
//    public static final String[] ATTRIBUTE_NAMES_4_ANLEGEN_JLIST = new String[] {ATTRIBUTE_4_AUSWAHLISTE_FILME};
//
//    /**
//     * the attribute names that are used for the columnNames
//     */
//	public static final String[] ATTRIBUTE_NAMES_4_TABLEDATA = new String[] {"ID", "Vorname", "Nachname", "Nationalitaet", "Geb. Datum",
//		"Geburtsort", "Homepage", "Beschreibung"};
//
//	
//	private int[] filmIDs;
//	
//	
//	private List<String> allAttributes = new ArrayList<String>();
//	
//	/**
//	 * Creates a Person with a available ID
//	 */
//	public Person() {
//		this( currentID++ );
//	}
//	
//	/**
//	 * Creates a Person with a given ID<br>
//	 * if the ID already exists, it will be incremented until it is a plausible ID
//	 * @param iD the id that will be used 
//	 */
//	public Person( int iD) {
//		currentID = ( iD > currentID ? iD : currentID );
//		this.iD = currentID;
//	}
//
//	/**
//	 * returns the vorname
//	 * @return the vorname
//	 */
//	public String getVorname() {
//		return this.name;
//	}
//
//	/**
//	 * sets the vorname 
//	 * @param vorname the vorname that will be set
//	 */
//	public void setVorname( String vorname ) {
//		this.name = vorname;
//	}
//
//	/**
//	 * returns the nachname
//	 * @return the nachname 
//	 */
//	public String getNachname() {
//		return this.nachname;
//	}
//
//	/**
//	 * sets the nachname 
//	 * @param nachname the nachname that will be set
//	 */
//	public void setNachname( String nachname ) {
//		this.nachname = nachname;
//	}
//
//	/**
//	 * returns the nationalitaet
//	 * @return the nationalitaet
//	 */
//	public String getNationalitaet() {
//		return this.nationalitaet;
//	}
//
//	/**
//	 * sets the nationalitaet
//	 * @param nationalitaet that will be set
//	 */
//	public void setNationalitaet( String nationalitaet ) {
//		this.nationalitaet = nationalitaet;
//	}
//
//	/**
//	 * returns the geburtsdatum
//	 * @return the geburtsdatum
//	 */
//	public Date getGeburtsDatum() {
//		return this.geburtsDatum;
//	}
//
//	/**
//	 * sets the geburtsdatum
//	 * @param geburtsDatum the geburtsdatum that will be set
//	 */
//	public void setGeburtsDatum( Date geburtsDatum ) {
//		this.geburtsDatum = geburtsDatum;
//	}
//
//	/**
//	 * returns the geburtsort
//	 * @return the geburtsort
//	 */
//	public String getGeburtsOrt() {
//		return this.geburtsOrt;
//	}
//
//	/**
//	 * sets the geburtsort
//	 * @param geburtsOrt the geburtsort that will be set
//	 */
//	public void setGeburtsOrt( String geburtsOrt ) {
//		this.geburtsOrt = geburtsOrt;
//	}
//	
//	/**
//	 * returns the homepage
//	 * @return the homepage
//	 */
//	public String getHompage() {
//		return this.hompage;
//	}
//	
//	/**
//	 * sets the homepage
//	 * @param hompage
//	 */
//	public void setHompage( String hompage ) {
//		this.hompage = hompage;
//	}
//	
//	/**
//	 * returns all filme as a List<Film>
//	 * @return filme as a List<Film>
//	 */
//	public List<Film> getFilme() {
//		return this.filme;
//	}
//	
//	/**
//	 * sets all film ids in order to the int[]
//	 * @param filmIDs the film ids that will be used
//	 */
//	public void setFilmIDs( int[] filmIDs ) {
//		this.filmIDs = new int[filmIDs.length]; 
//		System.arraycopy( filmIDs, 0, this.filmIDs, 0, filmIDs.length );
//	}
//	
//	/**
//	 * returns all filmIds
//	 * @return all filmIDs
//	 */
//	public int[] getFilmIDs() {
//		return this.filmIDs;
//	}
//	
//	/**
//	 * to print out the whole class
//	 */
//	public void printOut(){
//		System.out.println("iD: " + this.getiD());
//		System.out.println("Vorname: " + this.getVorname());
//		System.out.println("Nachname: " + this.getNachname());
//		System.out.println("Nationalitaet: " + this.getNationalitaet());
//		System.out.println("Geburtsdatum: " + this.getGeburtsDatum());
//		System.out.println("Geburtsort: " + this.getGeburtsOrt());
//		System.out.println("Hompage: " + this.getHompage());
//		System.out.println("Beschreibung: " + this.getBeschreibung());
//		System.out.println("------------------------------------------------------");
//	}
//	
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public String toString() {
//		return this.getNachname() + ", " + this.name ;
//	}
//	
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public boolean equals( Object obj ) {
//
//		if( obj == null ){
//			return false;
//		}
//		else if( this == obj ){
//			return true;
//		}
//		else if( obj.getClass() != this.getClass() ){
//			return false;
//		}
//		else if( this.iD == ( (Person)obj ).getiD() ){
//			return true;
//		}
//
//		return false;
//	}
//
//	/**
//	 * returns all Attribute as a List<String>
//	 * @return 
//	 */
//	public List<String> getAttributesAsList() {
//		this.allAttributes.add( "" + this.iD );
//		this.allAttributes.add( this.name );
//		this.allAttributes.add( this.nachname );
//		this.allAttributes.add( this.nationalitaet );
////		Hier liegt das Parse Problem
//		this.allAttributes.add( "" + IGUIConstants.DEFAULT_DATE_FORMAT.format( this.geburtsDatum ) );
//		this.allAttributes.add( this.geburtsOrt );
//		this.allAttributes.add( this.hompage );
//		this.allAttributes.add( this.addIDsNew( this.filme ) );
//		this.allAttributes.add( this.beschreibung );
//		
//		return this.allAttributes;
//	}
//	
//	/**
//	 * adds the ids to a given List<Film>
//	 * @param list the list that will be used
//	 * @return the id
//	 */
//	private String addIDsNew(List<Film> list) {
//		String iD = "";
//		for( int i = 0 ; i < list.size(); i++ ){
//			if( i == list.size() - 1 ){
//				iD = iD + list.get( i ).iD;
//
//			}
//			else{
//				iD = iD + list.get( i ).iD + DEFAULT_DELIMMTER;
//			}
//			
//		}
//		return iD;
//	}
//
//	/**
//	 * adds a film to the list
//	 * @param film the film that will be added
//	 */
//	public void addFilm( Film film ) {
//		this.filme.add( film );
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public String[] getAttributes4TableData() {
//		String[] attArray = new String[ATTRIBUTE_NAMES_4_TABLEDATA.length];
//		int ii = 0;
//		attArray[ii++] = "" + this.iD;
//		attArray[ii++] = this.name;
//		attArray[ii++] = this.nachname;
//		attArray[ii++] = this.nationalitaet;
//		attArray[ii++] = IGUIConstants.DEFAULT_DATE_FORMAT.format( this.geburtsDatum );
//		attArray[ii++] = this.geburtsOrt;
//		attArray[ii++] = this.hompage;
//		attArray[ii++] = this.beschreibung;
//		
//		return attArray;
//	}
//}
