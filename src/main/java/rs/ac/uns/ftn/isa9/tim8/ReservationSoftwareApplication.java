package rs.ac.uns.ftn.isa9.tim8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("rs.ac.uns.ftn.isa9.tim8")
@SpringBootApplication
public class ReservationSoftwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationSoftwareApplication.class, args);
	}

}


/*
 	Ukoliko je potreban drop baze radi promjene modela:
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.initialization-mode=always
 */