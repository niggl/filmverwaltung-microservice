package edu.kit.iai;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;


@SpringBootApplication
public class FilmverwaltungApplication_JSON {

	/**
	 * start the {@link SpringApplication}
	 * @param args
	 */
	public static void main( String[] args ) {
		SpringApplication.run(  FilmverwaltungApplication_JSON.class, args );
	}
	
//    /**
//
//    * Create custom converters, {@link GsonJsonpAwareHttpMessageConverter} in
//
//    * this case
//
//    *
//
//    * @return the converters
//    */
//    @Bean
//    public HttpMessageConverters customConverters() {
//    	return new HttpMessageConverters( false, Arrays.asList( new HttpMessageConverter<?>[] { new AbstractElementHttpMessageConverter() } ) );
//
//    }
}
