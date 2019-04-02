package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.Let;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;

@Repository
@Scope("singleton")
public class MemorijskoSkladiste implements MenadzerBazePodataka {
	protected ConcurrentHashMap<String, RentACarServis> rentACarServisi;
	protected ConcurrentHashMap<String, Aviokompanija> aviokompanije;
	protected ConcurrentHashMap<String, Let> letovi;
	protected ConcurrentHashMap<String, Hotel> hoteli;
	
	public MemorijskoSkladiste() {
		super();
		this.rentACarServisi = new ConcurrentHashMap<String, RentACarServis>();
		this.aviokompanije = new ConcurrentHashMap<String, Aviokompanija>();
		this.letovi = new ConcurrentHashMap<String, Let>();
		this.hoteli = new ConcurrentHashMap<String, Hotel>();
	}
	

	@Override
	public boolean dodajRentACarServis(RentACarServis noviServis) {
		if (this.rentACarServisi.containsKey(noviServis.getNaziv())) {
			return false;
		}
		this.rentACarServisi.put(noviServis.getNaziv(), noviServis);
		return true;
	}

	@Override
	public boolean obrisiRentACarServis(RentACarServis servisZaBrisanje) {
		if (!this.rentACarServisi.containsKey(servisZaBrisanje.getNaziv())) {
			return false;
		}
		this.rentACarServisi.remove(servisZaBrisanje.getNaziv());
		return true;
	}
	
	@Override
	public RentACarServis izmjeniRentACarServis(RentACarServis noviPodaci) {
		if (!this.rentACarServisi.containsKey(noviPodaci.getNaziv())) {
			return null;
		}
		RentACarServis temp = pronadjiRentACarServis(noviPodaci.getNaziv());
		noviPodaci.setFilijale(temp.getFilijale());
		noviPodaci.setBrojOcjena(temp.getBrojOcjena());
		noviPodaci.setSumaOcjena(temp.getSumaOcjena());
		noviPodaci.setVozila(temp.getVozila());
		this.rentACarServisi.put(noviPodaci.getNaziv(), noviPodaci);
		return noviPodaci;
	}

	@Override
	public Collection<RentACarServis> dobaviRentACarServise() {
		return this.rentACarServisi.values();
	}

	@Override
	public RentACarServis pronadjiRentACarServis(String nazivServisa) {
		return this.rentACarServisi.get(nazivServisa);
	}


	public ConcurrentHashMap<String, RentACarServis> getRentACarServisi() {
		return rentACarServisi;
	}


	public void setRentACarServisi(ConcurrentHashMap<String, RentACarServis> rentACarServisi) {
		this.rentACarServisi = rentACarServisi;
	}


	@Override
	public boolean dodajAviokompaniju(Aviokompanija novaAviokompanija) {
		if(!this.aviokompanije.containsKey(novaAviokompanija.getNaziv())) {
			this.aviokompanije.put(novaAviokompanija.getNaziv(), novaAviokompanija);
			return true;
		}
		return false;
	}


	@Override
	public boolean obrisiAviokompaniju(Aviokompanija aviokompanijaZaBrisanje) {
		if(this.aviokompanije.containsKey(aviokompanijaZaBrisanje.getNaziv())) {
			this.aviokompanije.remove(aviokompanijaZaBrisanje.getNaziv());
			return true;
		}
		return false;
	}


	@Override
	public boolean izmjeniAviokompanju(Aviokompanija noviPodaci) {
		if(this.aviokompanije.containsKey(noviPodaci.getNaziv())) {
			this.aviokompanije.put(noviPodaci.getNaziv(), noviPodaci);
			return true;
		}
		return false;
	}


	@Override
	public Collection<Aviokompanija> dobaviAviokompanije() {
		return this.aviokompanije.values();
	}


	@Override
	public Aviokompanija pronadjiAviokompaniju(String nazivAviokompanije) {
		return this.aviokompanije.get(nazivAviokompanije);
	}


	@Override
	public boolean dodajLet(Let noviLet) {
		if (this.letovi.containsKey(noviLet.getBrojLeta())) {
			return false;
		}
		this.letovi.put(noviLet.getBrojLeta(), noviLet);
		return true;
	}


	@Override
	public boolean obrisiLet(Let letZaBrisanje) {
		if (!this.letovi.containsKey(letZaBrisanje.getBrojLeta())) {
			return false;
		}
		this.letovi.remove(letZaBrisanje.getBrojLeta());
		return true;
	}


	@Override
	public boolean izmjeniLet(Let azuriranLet) {
		if (!this.letovi.containsKey(azuriranLet.getBrojLeta())) {
			return false;
		}
		this.letovi.put(azuriranLet.getBrojLeta(), azuriranLet);
		return true;
	}


	@Override
	public Collection<Let> dobaviLetove() {
		return this.letovi.values();
	}


	@Override
	public Let pronadjiLet(String brojLeta) {
		return this.letovi.get(brojLeta);
	}
	

	@Override
	public boolean dodajVozilo(String nazivServisa, Vozilo vozilo) {
		if (this.rentACarServisi.get(nazivServisa) != null) {
			this.rentACarServisi.get(nazivServisa).getVozila().add(vozilo);
			return true;
		}
		else {
			return false;
		}
		
	}


	@Override
	public Collection<Vozilo> prikaziVozilaServisa(String nazivServisa) {
		return this.pronadjiRentACarServis(nazivServisa).getVozila();
	}


	@Override
	public boolean dodajHotel(Hotel noviHotel) {
		if(!this.hoteli.containsKey(noviHotel.getNaziv())) {
			this.hoteli.put(noviHotel.getNaziv(), noviHotel);
			return true;
		}
		return false;
	}


	@Override
	public boolean obrisiHotel(Hotel hotelZaBrisanje) {
		if(this.hoteli.containsKey(hotelZaBrisanje.getNaziv())) {
			this.hoteli.remove(hotelZaBrisanje.getNaziv());
			return true;
		}
		return false;
	}


	@Override
	public boolean izmjeniHotel(Hotel azuriranHotel) {
		if(this.hoteli.containsKey(azuriranHotel.getNaziv())) {
			this.hoteli.put(azuriranHotel.getNaziv(), azuriranHotel);
			return true;
		}
		return false;
	}


	@Override
	public Collection<Hotel> dobaviHotele() {
		return this.hoteli.values();
	}


	@Override
	public Hotel pronadjiHotel(String nazivHotela) {
		return this.hoteli.get(nazivHotela);
	}
}
