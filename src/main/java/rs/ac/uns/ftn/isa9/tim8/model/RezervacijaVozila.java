package rs.ac.uns.ftn.isa9.tim8.model;

public class RezervacijaVozila {
	protected Vozilo rezervisanoVozilo;
	protected Filijala mjestoPreuzimanjaVozila;
	protected Filijala mjestoVracanjaVozila;
	
	public RezervacijaVozila() {
		super();
	}

	public Vozilo getRezervisanoVozilo() {
		return rezervisanoVozilo;
	}

	public void setRezervisanoVozilo(Vozilo rezervisanoVozilo) {
		this.rezervisanoVozilo = rezervisanoVozilo;
	}

	public Filijala getMjestoPreuzimanjaVozila() {
		return mjestoPreuzimanjaVozila;
	}

	public void setMjestoPreuzimanjaVozila(Filijala mjestoPreuzimanjaVozila) {
		this.mjestoPreuzimanjaVozila = mjestoPreuzimanjaVozila;
	}

	public Filijala getMjestoVracanjaVozila() {
		return mjestoVracanjaVozila;
	}

	public void setMjestoVracanjaVozila(Filijala mjestoVracanjaVozila) {
		this.mjestoVracanjaVozila = mjestoVracanjaVozila;
	}
	
	
}
