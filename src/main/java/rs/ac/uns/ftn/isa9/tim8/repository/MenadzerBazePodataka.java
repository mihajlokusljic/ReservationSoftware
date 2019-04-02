package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Collection;

import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.Let;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;

public interface MenadzerBazePodataka {
	
	//metode za rad sa rent-a-car servisima
	public boolean dodajRentACarServis(RentACarServis noviServis);
	public boolean obrisiRentACarServis(RentACarServis servisZaBrisanje);
	public RentACarServis izmjeniRentACarServis(RentACarServis noviPodaci);
	public Collection<RentACarServis> dobaviRentACarServise();
	public RentACarServis pronadjiRentACarServis(String nazivServisa);
	public boolean dodajVozilo(String nazivServisa, Vozilo vozilo);
	public Collection<Vozilo> prikaziVozilaServisa(String nazivServisa); 
	
	//metode za rad sa aviokompanijama
	public boolean dodajAviokompaniju(Aviokompanija novaAviokompanija);
	public boolean obrisiAviokompaniju(Aviokompanija aviokompanijaZaBrisanje);
	public boolean izmjeniAviokompanju(Aviokompanija noviPodaci);
	public Collection<Aviokompanija> dobaviAviokompanije();
	public Aviokompanija pronadjiAviokompaniju(String nazivAviokompanije);
	
	// metode za rukovanje sa letovima
	public boolean dodajLet(Let noviLet);
	public boolean obrisiLet(Let letZaBrisanje);
	public boolean izmjeniLet(Let azuriranLet);
	public Collection<Let> dobaviLetove();
	public Let pronadjiLet(String brojLeta);
	
	// metode za rukovanje hotelima
	public boolean dodajHotel(Hotel noviHotel);
	public boolean obrisiHotel(Hotel hotelZaBrisanje);
	public boolean izmjeniHotel(Hotel azuriranHotel);
	public Collection<Hotel> dobaviHotele();
	public Hotel pronadjiHotel(String nazivHotela);
}
