package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Ocjenjivo {
	
	@Column(name = "suma_ocjena", nullable = true)
	protected int sumaOcjena;
	@Column(name = "broj_ocjena", nullable = true)
	protected int brojOcjena;
	
	
	public Ocjenjivo() {
		super();
	}


	public Ocjenjivo(int sumaOcjena, int brojOcjena) {
		super();
		this.sumaOcjena = sumaOcjena;
		this.brojOcjena = brojOcjena;
	}


	public int getSumaOcjena() {
		return sumaOcjena;
	}


	public void setSumaOcjena(int sumaOcjena) {
		this.sumaOcjena = sumaOcjena;
	}


	public int getBrojOcjena() {
		return brojOcjena;
	}


	public void setBrojOcjena(int brojOcjena) {
		this.brojOcjena = brojOcjena;
	}
	
	public double azurirajPodatke(int novaOcjena) {
		this.brojOcjena++;
		this.sumaOcjena += novaOcjena;
		return (double)sumaOcjena/brojOcjena;
	}
}
