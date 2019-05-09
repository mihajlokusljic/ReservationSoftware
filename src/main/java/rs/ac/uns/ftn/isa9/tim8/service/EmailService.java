package rs.ac.uns.ftn.isa9.tim8.service;

import java.net.URLEncoder;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.VerificationToken;


@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	/*
	 * Koriscenje klase za ocitavanje vrednosti iz application.properties fajla
	 */
	@Autowired
	private Environment env;

	@Autowired
	private VerificationService verificationService;
	
	@Async
	public void sendMailAsync(RegistrovanKorisnik korisnik) throws MailException, InterruptedException {
		
		String token = UUID.randomUUID().toString();
		VerificationToken verToken = new VerificationToken();
		verToken.setId(null);
		verToken.setToken(token);
		verToken.setKorisnik(korisnik);
		verificationService.saveToken(verToken);
		
		//Simulacija duze aktivnosti da bi se uocila razlika
		Thread.sleep(10000);
		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(korisnik.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Registracija korisnika");
		String tekst = null;
		try {
			tekst = String.format("Potvrdite registracije na sledecem linku: \nhttp://localhost:8080/auth/confirm?token=%s",URLEncoder.encode(token, "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mail.setText(tekst);
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}


}