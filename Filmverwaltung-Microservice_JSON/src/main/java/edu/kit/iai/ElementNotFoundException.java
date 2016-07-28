package edu.kit.iai;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ElementNotFoundException extends RuntimeException{
	
	public ElementNotFoundException() {
		super("No such Element");
	}
	
	public ElementNotFoundException(String message){
		super(message);
	}
}
