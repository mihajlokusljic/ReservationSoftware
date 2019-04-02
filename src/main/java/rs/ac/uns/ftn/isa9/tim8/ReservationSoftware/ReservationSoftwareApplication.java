package rs.ac.uns.ftn.isa9.tim8.ReservationSoftware;

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
