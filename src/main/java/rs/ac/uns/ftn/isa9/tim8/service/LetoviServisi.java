package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;

import rs.ac.uns.ftn.isa9.tim8.model.Let;

public interface LetoviServisi {
	
	public boolean dodajLet(Let noviLet);
	public boolean obrisiLet(Let letZaBrisanje);
	public boolean izmjeniLet(Let azuriranLet);
	public Collection<Let> dobaviLetove();
	public Let pronadjiLet(String brojLeta);
}
