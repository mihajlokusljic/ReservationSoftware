package rs.ac.uns.ftn.isa9.tim8;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("rs.ac.uns.ftn.isa9.tim8")
@SpringBootApplication
public class ReservationSoftwareApplication {

	/*
	 * Ukoliko je potreban drop baze radi promjene modela:
	   		spring.jpa.hibernate.ddl-auto=create-drop
	   		spring.datasource.initialization-mode=always
	 */
	
	@PostConstruct
	static void started() {
		// podesi JVM vremensku zonu kao UTC
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		started();
		SpringApplication.run(ReservationSoftwareApplication.class, args);
	}

}