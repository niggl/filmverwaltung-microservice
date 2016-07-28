package edu.kit.iai.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


/**
 * A class to represent a FilmGenre 
 */
//@DiscriminatorColumn(name="typ", length=8)
//@DiscriminatorValue(value="FILMGENRE")
@Entity
@Table( name="Filmgenre")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class FilmGenre extends AbstractElement{
	
	public FilmGenre() {
	}

	/**
	 * Creates a filmgenre with a given ID<br>
	 * if the ID already exists, it will be incremented until it is a plausible ID
	 * @param iD the id that will be used 
	public FilmGenre( int iD) {
//		currentID = ( iD > currentID ? iD : currentID );
		this.iD = iD;
	}
	 */

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getBeschreibung();
	};

}
