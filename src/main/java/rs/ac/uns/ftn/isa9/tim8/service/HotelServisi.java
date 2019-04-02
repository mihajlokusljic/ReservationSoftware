package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.model.Hotel;

public interface HotelServisi {
	
	public boolean dodajHotel(Hotel noviHotel);
	public boolean obrisiHotel(Hotel hotelZaBrisanje);
	public boolean izmjeniHotel(Hotel azuriranHotel);
	public Collection<Hotel> dobaviHotele();
	public Hotel pronadjiHotel(String nazivHotela);

}
