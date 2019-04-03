package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

public class BrzaRezervacijaSjedista {
	
	protected IzvodjenjeLeta izvodjenjeLeta;
	protected Set<Sjediste> sjedista;
	protected double cijena;
	
	public BrzaRezervacijaSjedista() {
		super();
	}
	public BrzaRezervacijaSjedista(IzvodjenjeLeta izvodjenjeLeta, Set<Sjediste> sjedista, double cijena) {
		super();
		this.izvodjenjeLeta = izvodjenjeLeta;
		this.sjedista = sjedista;
		this.cijena = cijena;
	}
	public IzvodjenjeLeta getIzvodjenjeLeta() {
		return izvodjenjeLeta;
	}
	public void setIzvodjenjeLeta(IzvodjenjeLeta izvodjenjeLeta) {
		this.izvodjenjeLeta = izvodjenjeLeta;
	}
	public Set<Sjediste> getSjedista() {
		return sjedista;
	}
	public void setSjedista(Set<Sjediste> sjedista) {
		this.sjedista = sjedista;
	}
	public double getCijena() {
		return cijena;
	}
	public void setCijena(double cijena) {
		this.cijena = cijena;
	}
	
}
