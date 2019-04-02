package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "RezervacijaSoba")
public class RezervacijaSoba extends Rezervacija{
	
	@OneToMany(mappedBy = "rezervacijasoba", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
