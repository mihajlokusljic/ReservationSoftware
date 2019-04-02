package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Date;
import java.util.Set;

public class RezervacijaSoba extends Rezervacija{
	protected Set<HotelskaSoba> rezervisaneSobe;

	public RezervacijaSoba() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RezervacijaSoba(Date pocetniDatumVazenja, Date krajnjiDatumVazenja, double cijena, boolean rezervisano,
			int procenatPopusta) {
		super(pocetniDatumVazenja, krajnjiDatumVazenja, cijena, rezervisano, procenatPopusta);
		// TODO Auto-generated constructor stub
	}
	
	
	
}
