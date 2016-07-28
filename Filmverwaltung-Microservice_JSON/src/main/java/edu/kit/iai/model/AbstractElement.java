package edu.kit.iai.model;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.context.annotation.Primary;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

/**
 * A abstract class for all model classes <br>
 * It includes attributes that every model class has in common 
 */
//@Entity
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
//@Table(name="abstractElement")
@MappedSuperclass
public abstract class AbstractElement implements IAttributeProvider {
	
	/**
	 * Primaray Key (Indentificator)
	 * Its auto generated
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long iD;
	
	/**
	 * Zur automatisierten Generierung der IDs 
	 * vereinfacht wird vorausgesetzt, dass zuerst die CSV-Datei geladen wird
	 */
	@Basic
	@Column(length = 200, name = "beschreibung")
	protected String beschreibung;
	
	/**
	 * returns the id
	 * @return the id
	 */
	public Long getiD() {
		return this.iD;
	}
	
	/**
	 * returns the beschreibung
	 * @return the beschreibung
	 */
	public String getBeschreibung() {
		return this.beschreibung;
	}
	
	/**
	 * sets the beschreibung
	 * @param beschreibung the beschreibung that will be set
	 */
	public void setBeschreibung( String beschreibung ) {
		this.beschreibung = beschreibung;
	}

//	public void writeTo(OutputStream body) {
//		
//		try {
//			//Hier muss gson builder verwendet werden
//			
//			Gson gson = new GsonBuilder().create();
//			String s = gson.toJson(this.toString());
//			System.out.println("Ausgabe: " + s);
//			String s1 = "{" + s + "}";
//			body.write(s.getBytes(Charset.forName("UTF-8")));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}

	
}
