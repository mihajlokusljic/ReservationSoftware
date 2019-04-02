package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;

import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;

public interface AviokompanijaServisi {
	
	//crud operacije
	public boolean dodajAviokompaniju(Aviokompanija novaAviokompanija);
	public boolean obrisiAviokompaniju(Aviokompanija aviokompanijaZaBrisanje);
	public boolean izmjeniAviokompanju(Aviokompanija noviPodaci);
	public Collection<Aviokompanija> dobaviAviokompanije();
	public Aviokompanija pronadjiAviokompaniju(String nazivAviokompanije);
}
