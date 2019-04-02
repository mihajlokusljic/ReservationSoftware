package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

public class Hotel extends Servis {
	protected Set<AdministratorHotela> administratoriHotela;
	protected Set<HotelskaSoba> sobe;
	
	public Hotel() {
		super();
	}
	
	

	public Hotel(String naziv, String promotivniOpis, Adresa adresa, Set<Usluga> usluge, Set<AdministratorHotela> administratoriHotela,
			Set<HotelskaSoba> sobe) {
		super(naziv, promotivniOpis, adresa, usluge);
		this.administratoriHotela = administratoriHotela;
		this.sobe = sobe;
	}



	public Set<HotelskaSoba> getSobe() {
		return sobe;
	}

	public void setSobe(Set<HotelskaSoba> sobe) {
		this.sobe = sobe;
	}



	public Set<AdministratorHotela> getAdministratoriHotela() {
		return administratoriHotela;
	}



	public void setAdministratoriHotela(Set<AdministratorHotela> administratoriHotela) {
		this.administratoriHotela = administratoriHotela;
	}
		
	
}
