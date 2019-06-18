package rs.ac.uns.ftn.isa9.tim8.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.isa9.tim8.dto.BoravakDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.BrzaRezervacijaKarteDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.DatumiZaPrihodDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.IzvjestajDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.IzvrsavanjeRezervacijeSjedistaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.KorisnikDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.LetDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PodaciPutnikaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaAviokompanijaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaLetaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PrikazRezSjedistaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PrikazSegmentaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PrikazSjedistaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.UslugaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorAviokompanije;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Avion;
import rs.ac.uns.ftn.isa9.tim8.model.BrzaRezervacijaSjedista;
import rs.ac.uns.ftn.isa9.tim8.model.Destinacija;
import rs.ac.uns.ftn.isa9.tim8.model.Let;
import rs.ac.uns.ftn.isa9.tim8.model.NacinPlacanjaUsluge;
import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
import rs.ac.uns.ftn.isa9.tim8.model.Pozivnica;
import rs.ac.uns.ftn.isa9.tim8.model.Putovanje;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSjedista;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSobe;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaVozila;
import rs.ac.uns.ftn.isa9.tim8.model.Segment;
import rs.ac.uns.ftn.isa9.tim8.model.Sjediste;
import rs.ac.uns.ftn.isa9.tim8.model.Usluga;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.AviokompanijaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.AvionRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.BrzaRezervacijaSjedistaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.KorisnikRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.LetoviRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.PutovanjeRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.Rezervacija_sjedistaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.SjedisteRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.UslugeRepository;

@Service
public class AviokompanijaService {

	@Autowired
	protected AviokompanijaRepository aviokompanijaRepository;

	@Autowired
	protected AdresaRepository adresaRepository;

	@Autowired
	protected LetoviRepository letoviRepository;

	@Autowired
	protected AvionRepository avionRepository;

	@Autowired
	protected KorisnikRepository korisnikRepository;

	@Autowired
	protected SjedisteRepository sjedisteRepository;

	@Autowired
	protected UslugeRepository uslugeRepository;

	@Autowired
	protected Rezervacija_sjedistaRepository rezervacijaSjedistaRepository;

	@Autowired
	protected BrzaRezervacijaSjedistaRepository brzaRezervacijaSjedistaRepository;

	@Autowired
	protected PutovanjeRepository putovanjeRepository;

	@Autowired
	protected BonusSkalaService bonusSkalaService;

	@Autowired
	protected RentACarServisService rentACarService;

	@Autowired
	protected RezervacijeSobaService sobeService;

	public Aviokompanija dodajAviokompaniju(Aviokompanija novaAviokompanija) throws NevalidniPodaciException {

		Aviokompanija avio = aviokompanijaRepository.findOneByNaziv(novaAviokompanija.getNaziv());
		Adresa adresa = adresaRepository.findOneByPunaAdresa(novaAviokompanija.getAdresa().getPunaAdresa());
		if (avio != null) {
			throw new NevalidniPodaciException("Vec postoji aviokompanija sa zadatim nazivom");
		}
		if (adresa != null) {
			throw new NevalidniPodaciException("Vec postoji poslovnica na zadatoj adresi.");
		}
		if (novaAviokompanija.getNaziv().equals("")) {
			throw new NevalidniPodaciException("Naziv aviokompanije mora biti zadat.");
		}
		if (novaAviokompanija.getAdresa().getPunaAdresa().equals("")) {
			throw new NevalidniPodaciException("Adresa aviokompanije mora biti zadata.");
		}
		aviokompanijaRepository.save(novaAviokompanija);
		return novaAviokompanija;
	}

	public Collection<Aviokompanija> dobaviAviokompanije() {
		return aviokompanijaRepository.findAll();
	}

	public Collection<Let> dobaviLetove() {
		return letoviRepository.findAll();
	}

	public Let dodajLet(LetDTO letDTO) throws NevalidniPodaciException {
		Let let = napraviLet(letDTO);
		if (let == null) {
			throw new NevalidniPodaciException("Broj leta je zauzet.");
		}
		return let;

	}

	public boolean destinacijePostojeuAviokompaniji(Collection<Long> potencijalneDestinacije,
			Collection<Destinacija> destinacijeAviokompanije) {
		int counter = 0;

		for (Long idDest : potencijalneDestinacije) {
			for (Destinacija dest : destinacijeAviokompanije) {
				if (idDest == dest.getId()) {
					counter++;
					break;
				}
			}
		}

		return (counter == potencijalneDestinacije.size());
	}

	public HashSet<Destinacija> vratiDestinacijePoIDevima(Collection<Long> potencijalneDestinacije,
			Collection<Destinacija> destinacijeAviokompanije) {
		HashSet<Destinacija> retVal = new HashSet<Destinacija>();

		for (Long idDest : potencijalneDestinacije) {
			for (Destinacija dest : destinacijeAviokompanije) {
				if (idDest == dest.getId()) {
					retVal.add(dest);
					break;
				}
			}
		}

		return retVal;
	}

	public Let napraviLet(LetDTO letDTO) throws NevalidniPodaciException {
		Let postojiLet = letoviRepository.findOneByBrojLeta(letDTO.getBrojLeta());
		if (postojiLet != null) {
			throw new NevalidniPodaciException("Vec postoji let sa datim brojem leta.");
		}

		Optional<Aviokompanija> aviokompanijaSearch = aviokompanijaRepository.findById(letDTO.getIdAviokompanije());

		if (!aviokompanijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji aviokompanija sa datim ID-jem.");
		}

		Collection<Destinacija> destinacije = aviokompanijaSearch.get().getDestinacije();

		if (destinacije.size() < 2) {
			throw new NevalidniPodaciException("Ne postoje makar dvije destinacije definisane za datu aviokompaniju.");
		}

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Date datumPoletanjaAviona = null;
		Date datumSletanjaAviona = null;
		Date datumPovratkaAviona = null;

		try {
			datumPoletanjaAviona = df.parse(letDTO.getDatumPoletanja());
			datumSletanjaAviona = df.parse(letDTO.getDatumSletanja());
			datumPovratkaAviona = df.parse(letDTO.getDuzinaPutovanja());
		} catch (ParseException e) {
			throw new NevalidniPodaciException("Nevalidan format datuma.");
		}

		if (datumSletanjaAviona.before(datumPoletanjaAviona)) {
			throw new NevalidniPodaciException("Datum poletanja mora biti prije datuma sletanja");
		}

		if (datumPovratkaAviona.before(datumSletanjaAviona)) {
			throw new NevalidniPodaciException("Datum povratka mora biti nakon datuma sletanja.");
		}

		Date trenutnoVrijeme = new Date();

		if (datumPoletanjaAviona.before(trenutnoVrijeme)) {
			throw new NevalidniPodaciException("Datum poletanja je prije trenutnog datuma.");
		}

		if (datumSletanjaAviona.before(trenutnoVrijeme)) {
			throw new NevalidniPodaciException("Datum sletanja je prije trenutnog datuma.");
		}

		// Provjera postoji li avion
		Optional<Avion> avionSearch = avionRepository.findById(letDTO.getIdAviona());

		if (!avionSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji avion sa datim ID-jem.");
		}

		if (letDTO.getCijenaKarte() < 0) {
			throw new NevalidniPodaciException("Cijena karte ne moze biti negativna.");
		}

		Long idPolaziste = letDTO.getIdPolaziste();
		Long idOdrediste = letDTO.getIdOdrediste();

		boolean nadjenoPolaziste = false;
		boolean nadjenoOdrediste = false;

		Destinacija polazna = new Destinacija();
		Destinacija odredisna = new Destinacija();

		for (Destinacija dest : destinacije) {
			if (dest.getId() == idPolaziste) {
				nadjenoPolaziste = true;
				polazna = dest;
			}
		}

		if (nadjenoPolaziste == false) {
			throw new NevalidniPodaciException("Ne postoji polaziste sa ID-jem u aviokompanijinim destinacijama.");
		}

		for (Destinacija dest : destinacije) {
			if (dest.getId() == idOdrediste) {
				nadjenoOdrediste = true;
				odredisna = dest;
			}
		}

		if (nadjenoOdrediste == false) {
			throw new NevalidniPodaciException("Ne postoji odrediste sa ID-jem u aviokompanijinim destinacijama.");
		}

		boolean postojeLiSveDestinacijeZaPresjedanja = destinacijePostojeuAviokompaniji(
				letDTO.getIdDestinacijePresjedanja(), destinacije);

		if (!postojeLiSveDestinacijeZaPresjedanja) {
			throw new NevalidniPodaciException("Sve destinacije presjedanja moraju postojati u aviokompaniji.");
		}

		Aviokompanija avio = aviokompanijaSearch.get(); // potrebno joj je dodati let
		Avion avion = avionSearch.get();
		HashSet<Destinacija> presjedanja = vratiDestinacijePoIDevima(letDTO.getIdDestinacijePresjedanja(), destinacije);

		Let let = new Let();

		let.setBrojLeta(letDTO.getBrojLeta());
		let.setDatumPoletanja(datumPoletanjaAviona);
		let.setDatumSletanja(datumSletanjaAviona);
		let.setDuzinaPutovanja(datumPovratkaAviona);
		let.setCijenaKarte(letDTO.getCijenaKarte());
		let.setPolaziste(polazna);
		let.setOdrediste(odredisna);
		let.setAvion(avion);
		let.setPresjedanja(presjedanja);

		avio.getLetovi().add(let);

		letoviRepository.save(let);
		aviokompanijaRepository.save(avio);

		return let;

	}

	@SuppressWarnings("unused")
	public Collection<Let> pretraziLetove(PretragaLetaDTO kriterijumiPretrage) throws NevalidniPodaciException {
		/*
		 * String brojLeta; String nazivAviokompanije; String nazivPolazista; String
		 * nazivOdredista; Date datumPoletanja; Date datumSletanja; Date
		 * duzinaPutovanja; double cijenaKarte;
		 */
		Collection<Let> rezultat;
		Date trazeniDatumPoletanja = null;
		Date trazeniDatumSletanja = null;
		Date trazeniDatumPovratka = null;
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try {
			if (!kriterijumiPretrage.getDatumPoletanja().equals("")
					&& kriterijumiPretrage.getDatumPoletanja() != null) {
				trazeniDatumPoletanja = df.parse(kriterijumiPretrage.getDatumPoletanja());
			}
			if (!kriterijumiPretrage.getDatumSletanja().equals("") && kriterijumiPretrage.getDatumSletanja() != null) {
				trazeniDatumSletanja = df.parse(kriterijumiPretrage.getDatumSletanja());
			}
			if (!kriterijumiPretrage.getDuzinaPutovanja().equals("")
					&& kriterijumiPretrage.getDuzinaPutovanja() != null) {
				trazeniDatumPovratka = df.parse(kriterijumiPretrage.getDuzinaPutovanja());
			}
		} catch (ParseException e) {
			throw new NevalidniPodaciException("Nevalidan format datuma.");
		}

		if (kriterijumiPretrage.getNazivAviokompanije().length() == 0) {
			rezultat = letoviRepository.findAll();
		} else {
			Aviokompanija aviokompanija = aviokompanijaRepository
					.findOneByNaziv(kriterijumiPretrage.getNazivAviokompanije());
			if (aviokompanija == null) {
				rezultat = new HashSet<Let>();
				return rezultat;
			} else {
				rezultat = aviokompanija.getLetovi();
			}
		}

		Iterator<Let> it = rezultat.iterator();
		Let trenutniLet;

		while (it.hasNext()) {
			trenutniLet = it.next();
			if (!trenutniLet.getBrojLeta().toUpperCase().contains(kriterijumiPretrage.getBrojLeta().toUpperCase())) {
				it.remove();
			}
		}

		it = rezultat.iterator();

		while (it.hasNext()) {
			trenutniLet = it.next();

			for (Segment s : trenutniLet.getAvion().getSegmenti()) {
				if (s.getNaziv().equals("")) {
					it.remove();
					break;
				}
			}
		}

		it = rezultat.iterator();

		while (it.hasNext()) {
			trenutniLet = it.next();
			if (!trenutniLet.getPolaziste().getNazivDestinacije().toUpperCase()
					.contains(kriterijumiPretrage.getNazivPolazista().toUpperCase())) {
				it.remove();
			}
		}

		it = rezultat.iterator();

		while (it.hasNext()) {
			trenutniLet = it.next();
			if (!trenutniLet.getOdrediste().getNazivDestinacije().toUpperCase()
					.contains(kriterijumiPretrage.getNazivOdredista().toUpperCase())) {
				it.remove();
			}
		}

		it = rezultat.iterator();
		String trenutniDatumPoletanja = "";

		while (it.hasNext()) {
			trenutniLet = it.next();

			if (kriterijumiPretrage.getDatumPoletanja() == null || kriterijumiPretrage.getDatumPoletanja().equals("")) {
				break;
			}

			trenutniDatumPoletanja = df.format(trenutniLet.getDatumPoletanja());
			if (!trenutniDatumPoletanja.equals(kriterijumiPretrage.getDatumPoletanja())) {
				it.remove();
			}
		}

		it = rezultat.iterator();
		String trenutniDatumSletanja = "";

		while (it.hasNext()) {
			trenutniLet = it.next();

			if (kriterijumiPretrage.getDatumSletanja() == null || kriterijumiPretrage.getDatumSletanja().equals("")) {
				break;
			}

			trenutniDatumSletanja = df.format(trenutniLet.getDatumSletanja());
			if (!trenutniDatumSletanja.equals(kriterijumiPretrage.getDatumSletanja())) {
				it.remove();
			}
		}

		it = rezultat.iterator();

		while (it.hasNext()) {
			trenutniLet = it.next();

			if (kriterijumiPretrage.getDuzinaPutovanja() == null
					|| kriterijumiPretrage.getDuzinaPutovanja().equals("")) {
				break;
			}

			String trenutniDatumPovratka = df.format(trenutniLet.getDuzinaPutovanja());
			if (!trenutniDatumPovratka.equals(kriterijumiPretrage.getDuzinaPutovanja())) {
				it.remove();
			}
		}

		it = rezultat.iterator();

		while (it.hasNext()) {
			trenutniLet = it.next();

			if (kriterijumiPretrage.getCijenaKarte() == 0) {
				continue;
			}

			if (trenutniLet.getCijenaKarte() > kriterijumiPretrage.getCijenaKarte()) {
				it.remove();
			}
		}

		return rezultat;
	}

	public void validirajAviokompaniju(Aviokompanija aviokompanija) throws NevalidniPodaciException {
		if (aviokompanija.getNaziv().equals("") || aviokompanija.getNaziv() == null) {
			throw new NevalidniPodaciException("Naziv aviokompanije mora biti zadat.");
		}
		Aviokompanija zauzimaNaziv = aviokompanijaRepository.findOneByNaziv(aviokompanija.getNaziv());
		if (zauzimaNaziv != null) {
			throw new NevalidniPodaciException("Vec postoji aviokompanija sa zadatim nazivom.");
		}
	}

	public void validirajAdresu(Adresa adresa) throws NevalidniPodaciException {
		if (adresa == null) {
			throw new NevalidniPodaciException("Adresa hotela mora biti zadata.");
		}
		if (adresa.getPunaAdresa().equals("")) {
			throw new NevalidniPodaciException("Adresa hotela mora biti zadata.");
		}
		Adresa zauzimaAdresu = adresaRepository.findOneByPunaAdresa(adresa.getPunaAdresa());
		if (zauzimaAdresu != null) {
			throw new NevalidniPodaciException("Vec postoji poslovnica na zadatoj adresi");
		}
	}

	public Aviokompanija izmjeniAviokompaniju(Aviokompanija noviPodaciZaAviokompaniju) throws NevalidniPodaciException {
		AdministratorAviokompanije admin = (AdministratorAviokompanije) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
		Optional<Aviokompanija> getAvio = aviokompanijaRepository.findById(admin.getAviokompanija().getId());
		if (!getAvio.isPresent()) {
			throw new NevalidniPodaciException("Doslo je do greske.");
		}
		
		Aviokompanija target = getAvio.get();

		if (target == null) {
			throw new NevalidniPodaciException("Niste ulogovani kao administrator aviokompanije.");
		}

		if (noviPodaciZaAviokompaniju.getNaziv() == null) {
			throw new NevalidniPodaciException("Naziv aviokompanije mora biti zadat.");
		}

		if (!noviPodaciZaAviokompaniju.getNaziv().equals(target.getNaziv())) {
			this.validirajAviokompaniju(noviPodaciZaAviokompaniju);
			target.setNaziv(noviPodaciZaAviokompaniju.getNaziv());
		}

		Adresa staraAdresa = null;
		Adresa novaAdresa = noviPodaciZaAviokompaniju.getAdresa();

		if (novaAdresa == null) {
			throw new NevalidniPodaciException("Adresa aviokompanije mora biti zadata.");
		}

		target.getAdresa().setLatituda(novaAdresa.getLatituda());
		target.getAdresa().setLongituda(novaAdresa.getLongituda());
		if (!novaAdresa.getPunaAdresa().equals(target.getAdresa().getPunaAdresa())) {
			validirajAdresu(novaAdresa);
			staraAdresa = target.getAdresa();
			target.setAdresa(novaAdresa);
		}

		target.setPromotivniOpis(noviPodaciZaAviokompaniju.getPromotivniOpis());
		this.aviokompanijaRepository.save(target);

		if (staraAdresa != null) {
			// Oslobadjanje stare adrese
			this.adresaRepository.delete(staraAdresa);
		}

		return target;

	}

	public KorisnikDTO izmjeniProfilAdminaAviokompanije(KorisnikDTO korisnik) {
		Optional<Osoba> pretragaOsoba = korisnikRepository.findById(korisnik.getId());

		if (!pretragaOsoba.isPresent()) {
			return null;
		}

		Osoba o = pretragaOsoba.get();

		o.setIme(korisnik.getIme());
		o.setPrezime(korisnik.getPrezime());
		o.setBrojTelefona(korisnik.getBrojTelefona());
		o.setAdresa(korisnik.getAdresa());
		korisnikRepository.save(o);
		return korisnik;

	}

	public Osoba vratiKorisnikaPoTokenu(String token) {
		return korisnikRepository.findByToken(token);
	}

	public Aviokompanija dobaviAviokompaniju(Long aviokompanijaId) throws NevalidniPodaciException {
		Optional<Aviokompanija> aviokompanijaSearch = this.aviokompanijaRepository.findById(aviokompanijaId);
		if (!aviokompanijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji aviokompanija sa zadatim id-em.");
		}
		return aviokompanijaSearch.get();
	}

	public Collection<Let> dobaviLetoveAviokompanije(Long aviokompanijaId) throws NevalidniPodaciException {
		Optional<Aviokompanija> aviokompanijaSearch = this.aviokompanijaRepository.findById(aviokompanijaId);
		if (!aviokompanijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji aviokompanija sa zadatim id-em.");
		}
		return aviokompanijaSearch.get().getLetovi();
	}

	public Collection<Aviokompanija> pretraziAviokompanije(PretragaAviokompanijaDTO kriterijumPretrage)
			throws NevalidniPodaciException {
		// inicijalno su sve aviokompanije u rezultatu
		Collection<Aviokompanija> rezultat = this.aviokompanijaRepository.findAll();

		if (kriterijumPretrage.getNazivAviokompanije().equals("")) {
			return rezultat;
		}

		// odbacujemo aviokompanije koje ne zadovoljavaju naziv u "contains" smislu
		Iterator<Aviokompanija> it = rezultat.iterator();
		Aviokompanija trenutnaAviokompanija;

		while (it.hasNext()) {
			trenutnaAviokompanija = it.next();

			if (!trenutnaAviokompanija.getNaziv().toUpperCase()
					.contains(kriterijumPretrage.getNazivAviokompanije().toUpperCase())) {
				it.remove();
			}
		}

		return rezultat;
	}

	public Usluga dodajUsluguAviokompanije(UslugaDTO novaUsluga) throws NevalidniPodaciException {

		Optional<Aviokompanija> aviokompanijaSearch = aviokompanijaRepository.findById(novaUsluga.getIdPoslovnice());

		if (!aviokompanijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji aviokompanija sa zadatim id-jem.");
		}

		Aviokompanija aviokompanija = aviokompanijaSearch.get();
		for (Usluga usluga : aviokompanija.getCjenovnikDodatnihUsluga()) {
			if (usluga.getNaziv().equals(novaUsluga.getNaziv())) {
				throw new NevalidniPodaciException("Vec postoji usluga sa zadatim tipom prtljaga.");
			}
		}

		NacinPlacanjaUsluge nacinPlacanja = NacinPlacanjaUsluge.FIKSNO_PO_OSOBI;

		Usluga usluga = new Usluga(novaUsluga.getNaziv(), novaUsluga.getCijena(), novaUsluga.getProcenatPopusta(),
				nacinPlacanja, novaUsluga.getOpis(), aviokompanija);

		aviokompanija.getCjenovnikDodatnihUsluga().add(usluga);
		uslugeRepository.save(usluga);

		aviokompanijaRepository.save(aviokompanija);

		return usluga;
	}
	
	public Boolean sjedisteJeRezervisano(Sjediste sjediste, Date datumPolaska, Date datumDolaska) {
		if (datumPolaska == null || datumDolaska == null) {
			return false;
		}

		for (RezervacijaSjedista r : rezervacijaSjedistaRepository.findAllBySjediste(sjediste)) {
			if (datumPolaska.compareTo(r.getLet().getDatumPoletanja()) < 0
					&& datumDolaska.compareTo(r.getLet().getDatumSletanja()) > 0) {
				return true;
			}
		}

		return false;
	}

	public Boolean sjedisteJeNaBrzojRezervaciji(Sjediste sjediste, Date datumPolaska, Date datumDolaska) {
		if (datumPolaska == null || datumDolaska == null) {
			return false;
		}

		for (BrzaRezervacijaSjedista r : brzaRezervacijaSjedistaRepository.findAllBySjediste(sjediste)) {
			if (datumPolaska.compareTo(r.getLet().getDatumPoletanja()) < 0
					&& datumDolaska.compareTo(r.getLet().getDatumSletanja()) > 0) {
				return true;
			}
		}
		return false;
	}

	public BrzaRezervacijaKarteDTO dodajBrzuRezervaciju(BrzaRezervacijaKarteDTO novaRezervacija)
			throws NevalidniPodaciException {
		Optional<Let> letSearch = letoviRepository.findById(novaRezervacija.getLetId());

		if (!letSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji let sa datim id-jem.");
		}

		Let trazeniLet = letSearch.get();
		Sjediste trazenoSjediste = null;

		Boolean postojiLiTakvoSjediste = false;

		for (Sjediste s : trazeniLet.getAvion().getSjedista()) {
			if (s.getId().equals(novaRezervacija.getSjedisteId())) {
				postojiLiTakvoSjediste = true;
				trazenoSjediste = s;
			}
		}

		if (!postojiLiTakvoSjediste) {
			throw new NevalidniPodaciException("Ne postoji sjediste sa datim id-jem.");
		}

		AdministratorAviokompanije admin = (AdministratorAviokompanije) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Aviokompanija aviokompanija = admin.getAviokompanija();

		/*
		 * Ako aviokompanija kojoj pripada trazeniLet nije ova kojom operise admin, onda
		 * nije dobro razrijeseno ovlascenje
		 */

		Boolean pripadaLiLet = false;
		for (Let l : aviokompanija.getLetovi()) {
			if (l.getId().equals(trazeniLet.getId())) {
				pripadaLiLet = true;
			}
		}

		if (!pripadaLiLet) {
			throw new NevalidniPodaciException("Niste ulogovani kao odgovarajuci administrator aviokompanije.");
		}

		Date datumPolaska = null;
		Date datumDolaska = null;
		Date danasnjiDatum = null;

		datumPolaska = trazeniLet.getDatumPoletanja();
		datumDolaska = trazeniLet.getDatumSletanja();
		danasnjiDatum = new Date();

		if (datumPolaska.before(danasnjiDatum) || datumDolaska.before(danasnjiDatum)) {
			throw new NevalidniPodaciException("Datumi ne smiju biti u proslosti.");
		}

		if (datumDolaska.before(datumPolaska)) {
			throw new NevalidniPodaciException("Datum dolaska ne moze biti prije datuma polaska.");
		}

		if (sjedisteJeRezervisano(trazenoSjediste, datumPolaska, datumDolaska)) {
			throw new NevalidniPodaciException("Dato sjediste je vec rezervisano u zadatom vremenskom periodu.");
		}

		if (sjedisteJeNaBrzojRezervaciji(trazenoSjediste, datumPolaska, datumDolaska)) {
			throw new NevalidniPodaciException(
					"Dato sjediste se vec nalazi na brzoj rezervaciji u zadatom vremenskom periodu.");
		}

		BrzaRezervacijaSjedista brs = new BrzaRezervacijaSjedista(trazenoSjediste, datumPolaska, datumDolaska, 0, 0);

		brs.setCijena(trazeniLet.getCijenaKarte() + trazenoSjediste.getSegment().getCijena());
		brs.setAviokompanija(aviokompanija);
		brs.setLet(trazeniLet);
		aviokompanija.getBrzeRezervacije().add(brs);
		brzaRezervacijaSjedistaRepository.save(brs);
		//aviokompanijaRepository.save(aviokompanija);
		novaRezervacija.setId(brs.getId());
		novaRezervacija.setCijena(brs.getCijena());
		return novaRezervacija;
	}

	public Collection<Let> dobaviLetove(Long idAviokompanije) {
		Optional<Aviokompanija> aviokompanijaSearch = aviokompanijaRepository.findById(idAviokompanije);
		Aviokompanija trazenaAviokompanija = aviokompanijaSearch.get();

		return trazenaAviokompanija.getLetovi();
	}

	public BrzaRezervacijaKarteDTO zadajPopustBrzeRezervacije(BrzaRezervacijaKarteDTO brzaRezervacija)
			throws NevalidniPodaciException {
		Optional<BrzaRezervacijaSjedista> rezervacijaSearch = brzaRezervacijaSjedistaRepository
				.findById(brzaRezervacija.getId());

		if (!rezervacijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji brza rezervacija sa datim id-jem.");
		}

		BrzaRezervacijaSjedista rezervacija = rezervacijaSearch.get();
		AdministratorAviokompanije admin = (AdministratorAviokompanije) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Aviokompanija aviokompanija = admin.getAviokompanija();

		if (!rezervacija.getAviokompanija().getId().equals(aviokompanija.getId())) {
			throw new NevalidniPodaciException(
					"Niste ulogovani kao odgovarajuci administrator aviokompanije za datu rezervaciju.");
		}

		int popust = brzaRezervacija.getProcenatPopusta();
		if (popust < 0) {
			throw new NevalidniPodaciException("Popust ne moze biti negativan.");
		}
		if (popust > 100) {
			throw new NevalidniPodaciException("Popust ne moze biti veci od 100 procenata.");
		}
		rezervacija.setProcenatPopusta(popust);
		brzaRezervacijaSjedistaRepository.save(rezervacija);

		return brzaRezervacija;
	}

	public Boolean jeLiSjedisteRezervisano(Sjediste s, Collection<RezervacijaSjedista> rezervisanaSjedista,
			Collection<BrzaRezervacijaSjedista> brzeRezervacijeSjedista) {
		for (RezervacijaSjedista rs : rezervisanaSjedista) {
			if (rs.getSjediste().getId().equals(s.getId())) {
				return true;
			}
		}

		for (BrzaRezervacijaSjedista brs : brzeRezervacijeSjedista) {
			if (brs.getSjediste().getId().equals(s.getId())) {
				return true;
			}
		}
		return false;
	}

	public static Date removeTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public Collection<Let> slobodniLetovi(Aviokompanija aviokompanija, Date pocetniDatum, Date krajnjiDatum) {
		Collection<Let> rezultat = new HashSet<Let>();

		Date trenutniDatum = new Date();

		Set<Let> sviLetoviAviokompanije = aviokompanija.getLetovi();

		for (Let l : sviLetoviAviokompanije) {

			Date datumPoletanja = removeTime(l.getDatumPoletanja());
			Date datumSletanja = removeTime(l.getDatumSletanja());
			Date pocetni = removeTime(pocetniDatum);
			Date krajnji = removeTime(krajnjiDatum);

			if (!l.getDatumPoletanja().before(trenutniDatum) && !l.getDatumSletanja().before(trenutniDatum)
					&& !pocetniDatum.after(krajnjiDatum) && datumPoletanja.compareTo(pocetni) == 0
					&& datumSletanja.compareTo(krajnji) == 0) {

				Set<Sjediste> sjedista = l.getAvion().getSjedista();
				Collection<BrzaRezervacijaSjedista> brzeRezervacijeZaLet = brzaRezervacijaSjedistaRepository
						.findAllByLet(l);
				for (Sjediste s : sjedista) {
					// kroz sjedista i gledas postoji li rezervacija za to sjediste
					if (!jeLiSjedisteRezervisano(s, l.getRezervacije(), brzeRezervacijeZaLet)) {
						rezultat.add(l);
						break;
					}
				}
			}
		}

		return rezultat;
	}

	public Collection<Let> pretraziLetoveZaBrzuRezervaciju(PretragaLetaDTO kriterijumiPretrage, Long aviokompanijaId)
			throws NevalidniPodaciException {
		Optional<Aviokompanija> pretragaAviokompanija = aviokompanijaRepository.findById(aviokompanijaId);

		if (!pretragaAviokompanija.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji aviokompanija sa datim id-jem. Pokusajte ponovo.");
		}

		Aviokompanija aviokompanija = pretragaAviokompanija.get();

		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		try {
			pocetniDatum = df.parse(kriterijumiPretrage.getDatumPoletanja());
			krajnjiDatum = df.parse(kriterijumiPretrage.getDatumSletanja());
		} catch (ParseException e) {
			throw new NevalidniPodaciException("Nevalidan format datuma.");
		}

		Collection<Let> letovi = slobodniLetovi(aviokompanija, pocetniDatum, krajnjiDatum);

		return letovi;

	}

	public PrikazSjedistaDTO dobaviSjedistaZaPrikazNaMapi(Long idLeta) throws NevalidniPodaciException {
		Optional<Let> pretragaLetova = letoviRepository.findById(idLeta);

		if (!pretragaLetova.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji let sa datim id-jem. Pokusajte ponovo.");
		}

		Let let = pretragaLetova.get();
		Collection<BrzaRezervacijaSjedista> brzeRezervacijeZaLet = brzaRezervacijaSjedistaRepository.findAllByLet(let);

		Collection<String> sjedista = new ArrayList<String>();
		Collection<PrikazSegmentaDTO> segmenti = new ArrayList<PrikazSegmentaDTO>();
		Collection<Long> rezervisanaSjedistaIds = new ArrayList<Long>();

		String oznake = "abcdefghij";
		int tekuciSegmentIndex = -1;
		int tekucaVrsta = 1;
		Long tekuciSegmentId = -1L;
		StringBuilder sb = new StringBuilder();

		for (Sjediste s : let.getAvion().getSjedista()) {
			if (!s.getSegment().getId().equals(tekuciSegmentId)) {
				// Naisli smo na novi segment
				tekuciSegmentIndex++;
				tekuciSegmentId = s.getSegment().getId();
				segmenti.add(new PrikazSegmentaDTO(oznake.charAt(tekuciSegmentIndex) + "",
						let.getCijenaKarte() + s.getSegment().getCijena(), s.getSegment().getNaziv()));
			}
			if (s.getRed() != tekucaVrsta) {
				// Dosli smo u novu vrstu, pa trebamo sadrzaj StringBuilder-a upisati u
				// kolekciju
				sjedista.add(sb.toString());
				sb.setLength(0);
				tekucaVrsta = s.getRed();
			}

			if (jeLiSjedisteRezervisano(s, let.getRezervacije(), brzeRezervacijeZaLet)) {
				rezervisanaSjedistaIds.add(s.getId());
			}
			sb.append(oznake.charAt(tekuciSegmentIndex));
			sb.append("[");
			sb.append(s.getId());
			sb.append("]");
		}

		sjedista.add(sb.toString());

		PrikazSjedistaDTO rezultat = new PrikazSjedistaDTO(sjedista, segmenti, rezervisanaSjedistaIds);

		return rezultat;

	}

	public Collection<PrikazRezSjedistaDTO> vratiBrzeZaPrikaz(Long idServisa) throws NevalidniPodaciException {
		Optional<Aviokompanija> aviokompanijaSearch = aviokompanijaRepository.findById(idServisa);

		if (!aviokompanijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji aviokompanija sa zadatim id-jem.");
		}

		Aviokompanija aviokompanija = aviokompanijaSearch.get();

		Collection<PrikazRezSjedistaDTO> brzeRezDTO = new ArrayList<PrikazRezSjedistaDTO>();

		for (BrzaRezervacijaSjedista brs : aviokompanija.getBrzeRezervacije()) {
			double punaCijena = brs.getCijena();
			double cijenaSaPopustom = punaCijena - brs.getProcenatPopusta() * punaCijena / 100;

			brzeRezDTO.add(new PrikazRezSjedistaDTO(brs.getId(), brs.getLet().getPolaziste().getNazivDestinacije(),
					brs.getLet().getOdrediste().getNazivDestinacije(), brs.getDatumPolaska(), brs.getDatumDolaska(),
					brs.getSjediste(), brs.getCijena(), Math.round(cijenaSaPopustom * 100) / 100D));
		}

		return brzeRezDTO;

	}

	public String izvrsiBrzuRezervacijuKarte(Long idBrzeRez) throws NevalidniPodaciException {
		Optional<BrzaRezervacijaSjedista> brzaRezPretraga = brzaRezervacijaSjedistaRepository.findById(idBrzeRez);

		if (!brzaRezPretraga.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji brza rezervacija avionske karte sa tim id-jem.");
		}

		BrzaRezervacijaSjedista brs = brzaRezPretraga.get();

		RegistrovanKorisnik korisnik = (RegistrovanKorisnik) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		double popust = brs.getCijena() * brs.getProcenatPopusta() / 100.0;
		double cijena = brs.getCijena() - popust;

		if (cijena < 40) {
			cijena = 40;
		}

		RezervacijaSjedista rs = new RezervacijaSjedista(null, korisnik.getIme(), korisnik.getPrezime(), korisnik.getBrojPasosa(), cijena,
				brs.getSjediste(), korisnik, brs.getAviokompanija(), brs.getLet(), null);

		brzaRezervacijaSjedistaRepository.delete(brs);
		HashSet<RezervacijaSjedista> rezervacije = new HashSet<RezervacijaSjedista>();
		rezervacije.add(rs);
		Putovanje putovanje = new Putovanje(null, rezervacije, new HashSet<Pozivnica>(), 
				new HashSet<RezervacijaSobe>(), new HashSet<RezervacijaVozila>(), korisnik, new HashSet<Usluga>(), 0);
		rs.setPutovanje(putovanje);
		putovanjeRepository.save(putovanje);
		rezervacijaSjedistaRepository.save(rs);
		return "Rezervacija je uspjesno izvrsena.";
	}

	public String otkaziRezervaciju(Long id) throws NevalidniPodaciException {

		Optional<RezervacijaSjedista> pretragaRez = rezervacijaSjedistaRepository.findById(id);

		if (!pretragaRez.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji rezervacija zadatim id-em");
		}

		RezervacijaSjedista rez = pretragaRez.get();

		Optional<Putovanje> pretragaPutovanja = putovanjeRepository.findById(rez.getPutovanje().getId());

		if (!pretragaPutovanja.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji putovanje sa tim id-jem.");
		}

		Putovanje putovanje = pretragaPutovanja.get();

		putovanje.getRezervacijeSjedista().remove(rez);

		// u slucaju da inicijator putovanja otkaze let brisu se sve rezervacije i
		// putovanje se otkazuje
		if (rez.getPutnik().getId().equals(putovanje.getInicijatorPutovanja().getId())) {

			if (!putovanje.getRezervacijeSoba().isEmpty()) {
				RezervacijaSobe rSobe = null;
				for (Iterator<RezervacijaSobe> rezSobeIt = putovanje.getRezervacijeSoba().iterator(); rezSobeIt.hasNext(); ) {
					rSobe = rezSobeIt.next();
					if (rSobe.getPutnik().getId().equals(putovanje.getInicijatorPutovanja().getId())) {

						rezSobeIt.remove();

						this.sobeService.otkaziRezervaciju(rSobe.getId());
						// putovanjeRepository.save(putovanje);
					}
				}
			}

			if (!putovanje.getRezervacijeVozila().isEmpty()) {

				RezervacijaVozila rVozila = null;
				for (Iterator<RezervacijaVozila> rezVozIt = putovanje.getRezervacijeVozila().iterator(); rezVozIt.hasNext(); ) {
					rVozila = rezVozIt.next();
					if (rVozila.getPutnik().getId().equals(putovanje.getInicijatorPutovanja().getId())) {

						rezVozIt.remove();

						this.rentACarService.otkaziRezervaciju(rVozila.getId());
						// putovanjeRepository.save(putovanje);

					}
				}
			}

			if (!putovanje.getRezervacijeSjedista().isEmpty()) {
				RezervacijaSjedista rSjedista = null;
				for (Iterator<RezervacijaSjedista> rezSjedIt = putovanje.getRezervacijeSjedista().iterator(); rezSjedIt.hasNext(); ) {
					rSjedista = rezSjedIt.next();
					if (rSjedista.getPutnik().getId().equals(putovanje.getInicijatorPutovanja().getId())) {

						rezSjedIt.remove();

						this.rezervacijaSjedistaRepository.delete(rSjedista);
						// putovanjeRepository.save(putovanje);
					}
				}
			}

		}

		rezervacijaSjedistaRepository.delete(rez);

		if (putovanje.getRezervacijeSjedista().isEmpty()) {

			putovanjeRepository.delete(putovanje);
		} else {

			putovanjeRepository.save(putovanje);
		}

		return "Uspjesno ste otkazali rezervaciju leta";
	}

	// Funkcija koja koncertuje decimalne stepene u radijane
	public double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	// Funkcija koja konvertuje radijane u decimalne stepene
	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	// Funkcija koja sracunava rastojanje izmedju dvije tacke u kilometrima na
	// osnovu latitude/longitude
	public double rastojanjeLatLongKilometri(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344; // rastojanje u kilometrima
		return dist;
	}

	public IzvrsavanjeRezervacijeSjedistaDTO rezervisiSjedista(IzvrsavanjeRezervacijeSjedistaDTO podaciRezervacije)
			throws NevalidniPodaciException {
		// Najveće rastojanje između dvije tačke na planeti Zemlji (po ekvatoru)
		final double NAJVECE_RASTOJANJE = 12756; // u kilometrima

		Optional<Osoba> pretragaKorisnika = korisnikRepository.findById(podaciRezervacije.getIdKorisnika());

		if (!pretragaKorisnika.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji korisnik sa tim id-jem.");
		}

		RegistrovanKorisnik korisnik = (RegistrovanKorisnik) pretragaKorisnika.get();

		Optional<Let> pretragaLeta = letoviRepository.findById(podaciRezervacije.getIdLeta());

		if (!pretragaLeta.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji let sa datim id-jem.");
		}

		Let let = pretragaLeta.get();

		Destinacija polaziste = let.getPolaziste();
		Destinacija odrediste = let.getOdrediste();

		double rastojanjeUKilometrima = rastojanjeLatLongKilometri(polaziste.getAdresa().getLatituda(),
				polaziste.getAdresa().getLongituda(), odrediste.getAdresa().getLatituda(),
				odrediste.getAdresa().getLongituda());

		double faktorUdaljenosti = rastojanjeUKilometrima / NAJVECE_RASTOJANJE;

		double bonusPoeni = faktorUdaljenosti * 100;

		Putovanje putovanje = new Putovanje(null, new LinkedHashSet<RezervacijaSjedista>(),
				new LinkedHashSet<Pozivnica>(), new LinkedHashSet<RezervacijaSobe>(),
				new LinkedHashSet<RezervacijaVozila>(), korisnik, new LinkedHashSet<Usluga>(), bonusPoeni);

		Optional<Sjediste> pretragaSjedista = null;
		Sjediste s = null;
		Collection<BrzaRezervacijaSjedista> brzeRezervacijeLeta = brzaRezervacijaSjedistaRepository.findAllByLet(let);

		for (Long idSjedista : podaciRezervacije.getRezervisanaSjedistaIds()) {
			pretragaSjedista = sjedisteRepository.findById(idSjedista);

			if (!pretragaSjedista.isPresent()) {
				throw new NevalidniPodaciException("Ne postoji sjediste sa datim id-jem.");
			}

			s = pretragaSjedista.get();

			if (jeLiSjedisteRezervisano(s, let.getRezervacije(), brzeRezervacijeLeta)) {
				throw new NevalidniPodaciException("Sjediste je vec rezervisano.");
			}

			double cijenaKarte = 0;

			cijenaKarte = let.getCijenaKarte() + s.getSegment().getCijena();
			int procenatPopusta = bonusSkalaService.odrediProcenatBonusPopusta(korisnik.getBonusPoeni());
			double popust = cijenaKarte * procenatPopusta / 100.00;
			cijenaKarte -= popust;

			if (cijenaKarte < 40) {
				cijenaKarte = 40;
			}

			RezervacijaSjedista rs = new RezervacijaSjedista(null, null, null, null, cijenaKarte, s, null,
					let.getAvion().getAviokompanija(), let, putovanje);

			putovanje.getRezervacijeSjedista().add(rs);
			let.getRezervacije().add(rs);
			let.getAvion().getAviokompanija().getRezervacije().add(rs);
		}

		putovanjeRepository.save(putovanje);

		podaciRezervacije.setIdPutovanja(putovanje.getId());

		aviokompanijaRepository.save(let.getAvion().getAviokompanija());
		letoviRepository.save(let);

		return podaciRezervacije;
	}

	public IzvrsavanjeRezervacijeSjedistaDTO popuniPodatkeZaPutnike(IzvrsavanjeRezervacijeSjedistaDTO podaciRezervacije)
			throws NevalidniPodaciException {
		Optional<Putovanje> pretragaPutovanja = putovanjeRepository.findById(podaciRezervacije.getIdPutovanja());

		if (!pretragaPutovanja.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji putovanje sa tim id-jem.");
		}

		Putovanje putovanje = pretragaPutovanja.get();

		int prijateljIdIndex = 0;
		int neregistrovanPutnikIndex = 0;

		Optional<Osoba> korisnikPretraga = null;
		RegistrovanKorisnik pozvaniPrijatelj = null;
		PodaciPutnikaDTO podaciNeregistrovanogPutnika = null;

		for (RezervacijaSjedista rez : putovanje.getRezervacijeSjedista()) {
			korisnikPretraga = korisnikRepository.findById(podaciRezervacije.getIdKorisnika());
			if (!korisnikPretraga.isPresent()) {
				throw new NevalidniPodaciException("Nije prepoznat autor rezervacije.");
			}

			RegistrovanKorisnik inicijatorPutovanja = (RegistrovanKorisnik) korisnikPretraga.get();
			rez.setPutnik(inicijatorPutovanja);
			rez.setImePutnika(inicijatorPutovanja.getIme());
			rez.setPrezimePutnika(inicijatorPutovanja.getPrezime());
			rez.setBrojPasosaPutnika(inicijatorPutovanja.getBrojPasosa());

			// Pozvani prijatelji
			if (prijateljIdIndex < podaciRezervacije.getPozvaniPrijateljiIds().size()) {
				korisnikPretraga = korisnikRepository
						.findById(podaciRezervacije.getPozvaniPrijateljiIds().get(prijateljIdIndex));

				if (!korisnikPretraga.isPresent()) {
					throw new NevalidniPodaciException("Ne postoji korisnik sa datim id-jem.");
				}

				pozvaniPrijatelj = (RegistrovanKorisnik) korisnikPretraga.get();
				rez.setPutnik(pozvaniPrijatelj);
				rez.setImePutnika(pozvaniPrijatelj.getIme());
				rez.setPrezimePutnika(pozvaniPrijatelj.getPrezime());
				rez.setBrojPasosaPutnika(pozvaniPrijatelj.getBrojPasosa());

				prijateljIdIndex++;
			} else {
				// Neregistrovani putnici
				if (neregistrovanPutnikIndex < podaciRezervacije.getPodaciNeregistrovanihPutnika().size()) {
					rez.setPutnik(putovanje.getInicijatorPutovanja());
					podaciNeregistrovanogPutnika = podaciRezervacije.getPodaciNeregistrovanihPutnika()
							.get(neregistrovanPutnikIndex);
					rez.setImePutnika(podaciNeregistrovanogPutnika.getIme());
					rez.setPrezimePutnika(podaciNeregistrovanogPutnika.getPrezime());
					rez.setBrojPasosaPutnika(podaciNeregistrovanogPutnika.getBrojPasosa());

					neregistrovanPutnikIndex++;
				}
			}

			rezervacijaSjedistaRepository.save(rez);
		}

		putovanjeRepository.save(putovanje);

		return podaciRezervacije;
	}

	public BoravakDTO dobaviPodatkeBoravka(Long idLeta) throws NevalidniPodaciException {
		Optional<Let> letSearch = letoviRepository.findById(idLeta);
		if (!letSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji let sa datim id-em.");
		}
		Let let = letSearch.get();
		BoravakDTO rezultat = new BoravakDTO(let.getDatumSletanja(), let.getDuzinaPutovanja(),
				let.getOdrediste().getNazivDestinacije());
		return rezultat;
	}

	public Boolean rezervacijaLetaOcjenjena(Long idRezervacije) throws NevalidniPodaciException {
		Optional<RezervacijaSjedista> pretragaRez = rezervacijaSjedistaRepository.findById(idRezervacije);
		if (!pretragaRez.isPresent()) {
			throw new NevalidniPodaciException("Doslo je do greske.");
		}

		RezervacijaSjedista rLet = pretragaRez.get();

		if (rLet.isOcjenjeno()) {
			return true;
		} else {
			return false;
		}
	}

	// ocjenjivanje leta nakon njegovog sletanja
	public String ocjeniLet(Long id, int ratingValue) throws NevalidniPodaciException {
		Optional<RezervacijaSjedista> pretragaRez = rezervacijaSjedistaRepository.findById(id);
		if (!pretragaRez.isPresent()) {
			throw new NevalidniPodaciException("Doslo je do greske.");
		}

		RezervacijaSjedista rSjed = pretragaRez.get();

		Optional<Aviokompanija> pretragaAviokompanije = aviokompanijaRepository
				.findById(rSjed.getAviokompanija().getId());
		if (!pretragaAviokompanije.isPresent()) {
			throw new NevalidniPodaciException("U medjuvremenu je obrisana aviokompanija cije su usluge koristene.");
		}

		Aviokompanija aviokompanija = pretragaAviokompanije.get();

		Optional<Let> pretragaLet = letoviRepository.findById(rSjed.getLet().getId());
		if (!pretragaLet.isPresent()) {
			throw new NevalidniPodaciException("U medjuvremenu je uklonjen let cije su usluge koristene.");
		}

		Let let = pretragaLet.get();

		Date danasnjiDatum = new Date();
		if (rSjed.getLet().getDatumSletanja().after(danasnjiDatum)) {
			return "Ne mozete da ocjenite let prije njegovog sletanja.";
		}

		let.setSumaOcjena(let.getSumaOcjena() + ratingValue);
		let.setBrojOcjena(let.getBrojOcjena() + 1);
		rSjed.setOcjenjeno(true);

		aviokompanija.setSumaOcjena(aviokompanija.getSumaOcjena() + ratingValue);
		aviokompanija.setBrojOcjena(aviokompanija.getBrojOcjena() + 1);

		aviokompanijaRepository.save(aviokompanija);
		letoviRepository.save(let);
		rezervacijaSjedistaRepository.save(rSjed);

		return null;
	}

	public String izracunajPrihode(DatumiZaPrihodDTO datumiDto, Long idAviokompanije) throws NevalidniPodaciException {
		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		if (!datumiDto.getDatumPocetni().isEmpty()) {
			try {
				pocetniDatum = df.parse(datumiDto.getDatumPocetni());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}

		if (!datumiDto.getDatumKrajnji().isEmpty()) {
			try {
				krajnjiDatum = df.parse(datumiDto.getDatumKrajnji());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}
		Optional<Aviokompanija> pretragaAviokompanije = aviokompanijaRepository.findById(idAviokompanije);

		if (!pretragaAviokompanije.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji aviokompanija sa zadatim id-em");
		}

		Aviokompanija aviokompanija = pretragaAviokompanije.get();
		double prihodi = 0;

		Collection<RezervacijaSjedista> sveRezervacije = rezervacijaSjedistaRepository
				.findAllByAviokompanija(aviokompanija);

		for (RezervacijaSjedista rezSjedista : sveRezervacije) {
			if (pocetniDatum != null && krajnjiDatum != null) {
				if (pocetniDatum.compareTo(rezSjedista.getLet().getDatumPoletanja()) <= 0
						&& krajnjiDatum.compareTo(rezSjedista.getLet().getDatumPoletanja()) >= 0) {
					prihodi = prihodi + rezSjedista.getCijena();
				}
			} else if (pocetniDatum != null) {
				if (pocetniDatum.compareTo(rezSjedista.getLet().getDatumPoletanja()) <= 0) {
					prihodi = prihodi + rezSjedista.getCijena();
				}
			} else if (krajnjiDatum != null) {
				if (krajnjiDatum.compareTo(rezSjedista.getLet().getDatumPoletanja()) >= 0) {
					prihodi = prihodi + rezSjedista.getCijena();
				}
			} else {
				prihodi = prihodi + rezSjedista.getCijena();
			}
		}

		return Double.toString(prihodi);
	}

	public IzvjestajDTO dnevniIzvjestaj(Long idAviokompanije, DatumiZaPrihodDTO datumiDto)
			throws NevalidniPodaciException {

		IzvjestajDTO izvjestajDTO = new IzvjestajDTO(new ArrayList<Integer>(), new ArrayList<String>());

		Optional<Aviokompanija> pretragaAviokompanije = aviokompanijaRepository.findById(idAviokompanije);

		if (!pretragaAviokompanije.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji aviokompanija sa zadatim id-em");
		}

		Aviokompanija aviokompanija = pretragaAviokompanije.get();

		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		if (!datumiDto.getDatumPocetni().isEmpty()) {
			try {
				pocetniDatum = df.parse(datumiDto.getDatumPocetni());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}

		if (!datumiDto.getDatumKrajnji().isEmpty()) {
			try {
				krajnjiDatum = df.parse(datumiDto.getDatumKrajnji());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}

		Collection<RezervacijaSjedista> sveRezervacije = rezervacijaSjedistaRepository
				.findAllByAviokompanija(aviokompanija);
		Collection<RezervacijaSjedista> rezervacijeUOkviruDatuma = new ArrayList<>();

		for (RezervacijaSjedista rv : sveRezervacije) {

			if (!rv.getDatumRezervacije().before(pocetniDatum) && !rv.getDatumRezervacije().after(krajnjiDatum)) {
				rezervacijeUOkviruDatuma.add(rv);
			}

		}

		while (!pocetniDatum.after(krajnjiDatum)) {

			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.");
			izvjestajDTO.getVrijednostiXOse().add(sdf.format(pocetniDatum));

			int broj = 0;
			for (RezervacijaSjedista rv : rezervacijeUOkviruDatuma) {
				if (rv.getDatumRezervacije().equals(pocetniDatum)) {
					broj++;
				}
			}
			izvjestajDTO.getBrojeviYOsa().add(broj);
			Calendar c = Calendar.getInstance();
			c.setTime(pocetniDatum);
			c.add(Calendar.DATE, 1);
			pocetniDatum = c.getTime();
		}

		return izvjestajDTO;
	}

	public IzvjestajDTO nedeljniIzvjestaj(Long idAviokompanije, DatumiZaPrihodDTO datumiDto)
			throws NevalidniPodaciException {

		IzvjestajDTO izvjestajDTO = new IzvjestajDTO(new ArrayList<Integer>(), new ArrayList<String>());

		Optional<Aviokompanija> pretragaAviokompanije = aviokompanijaRepository.findById(idAviokompanije);

		if (!pretragaAviokompanije.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji aviokompanija sa zadatim id-em");
		}

		Aviokompanija aviokompanija = pretragaAviokompanije.get();

		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		if (!datumiDto.getDatumPocetni().isEmpty()) {
			try {
				pocetniDatum = df.parse(datumiDto.getDatumPocetni());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}

		if (!datumiDto.getDatumKrajnji().isEmpty()) {
			try {
				krajnjiDatum = df.parse(datumiDto.getDatumKrajnji());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}

		Collection<RezervacijaSjedista> sveRezervacije = rezervacijaSjedistaRepository
				.findAllByAviokompanija(aviokompanija);
		Collection<RezervacijaSjedista> rezervacijeUOkviruDatuma = new ArrayList<>();

		for (RezervacijaSjedista rv : sveRezervacije) {

			if (!rv.getDatumRezervacije().before(pocetniDatum) && !rv.getDatumRezervacije().after(krajnjiDatum)) {
				rezervacijeUOkviruDatuma.add(rv);
			}

		}

		while (!pocetniDatum.after(krajnjiDatum)) {

			Object zaMjesec_ = pocetniDatum.clone();

			Date zaMjesec = (Date) zaMjesec_;
			Calendar c = Calendar.getInstance();
			c.setTime(pocetniDatum);
			c.add(Calendar.DATE, 7);
			zaMjesec = c.getTime();

			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.");

			String xOsa = sdf.format(pocetniDatum);

			int broj = 0;
			for (RezervacijaSjedista rv : rezervacijeUOkviruDatuma) {
				if (!zaMjesec.after(krajnjiDatum)) {

					if (!rv.getDatumRezervacije().before(pocetniDatum) && !rv.getDatumRezervacije().after(zaMjesec)) {
						broj++;
					}

				} else {
					if (!rv.getDatumRezervacije().before(pocetniDatum)
							&& !rv.getDatumRezervacije().after(krajnjiDatum)) {
						broj++;
					}
				}
			}

			if (!zaMjesec.after(krajnjiDatum)) {
				pocetniDatum = zaMjesec;
			} else {
				pocetniDatum = krajnjiDatum;
			}
			izvjestajDTO.getBrojeviYOsa().add(broj);
			xOsa = xOsa + "-" + sdf.format(pocetniDatum);
			c.setTime(pocetniDatum);
			c.add(Calendar.DATE, 1);
			pocetniDatum = c.getTime();
			izvjestajDTO.getVrijednostiXOse().add(xOsa);

		}

		return izvjestajDTO;
	}

	public IzvjestajDTO mjesecniIzvjestaj(Long idAviokompanije, DatumiZaPrihodDTO datumiDto)
			throws NevalidniPodaciException {

		IzvjestajDTO izvjestajDTO = new IzvjestajDTO(new ArrayList<Integer>(), new ArrayList<String>());

		Optional<Aviokompanija> pretragaAviokompanije = aviokompanijaRepository.findById(idAviokompanije);

		if (!pretragaAviokompanije.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji aviokompanija sa zadatim id-em");
		}

		Aviokompanija aviokompanija = pretragaAviokompanije.get();

		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		if (!datumiDto.getDatumPocetni().isEmpty()) {
			try {
				pocetniDatum = df.parse(datumiDto.getDatumPocetni());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}

		if (!datumiDto.getDatumKrajnji().isEmpty()) {
			try {
				krajnjiDatum = df.parse(datumiDto.getDatumKrajnji());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}

		Collection<RezervacijaSjedista> sveRezervacije = rezervacijaSjedistaRepository
				.findAllByAviokompanija(aviokompanija);
		Collection<RezervacijaSjedista> rezervacijeUOkviruDatuma = new ArrayList<>();

		for (RezervacijaSjedista rv : sveRezervacije) {

			if (!rv.getDatumRezervacije().before(pocetniDatum) && !rv.getDatumRezervacije().after(krajnjiDatum)) {
				rezervacijeUOkviruDatuma.add(rv);
			}

		}

		while (!pocetniDatum.after(krajnjiDatum)) {

			Object zaMjesec_ = pocetniDatum.clone();

			Date zaMjesec = (Date) zaMjesec_;
			Calendar c = Calendar.getInstance();
			c.setTime(pocetniDatum);
			c.add(Calendar.MONTH, 1);
			zaMjesec = c.getTime();

			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.");

			String xOsa = sdf.format(pocetniDatum);

			int broj = 0;
			for (RezervacijaSjedista rv : rezervacijeUOkviruDatuma) {
				if (!zaMjesec.after(krajnjiDatum)) {

					if (!rv.getDatumRezervacije().before(pocetniDatum) && !rv.getDatumRezervacije().after(zaMjesec)) {
						broj++;
					}

				} else {
					if (!rv.getDatumRezervacije().before(pocetniDatum)
							&& !rv.getDatumRezervacije().after(krajnjiDatum)) {
						broj++;
					}
				}
			}

			if (!zaMjesec.after(krajnjiDatum)) {
				pocetniDatum = zaMjesec;
			} else {
				pocetniDatum = krajnjiDatum;
			}
			izvjestajDTO.getBrojeviYOsa().add(broj);
			xOsa = xOsa + "-" + sdf.format(pocetniDatum);
			c.setTime(pocetniDatum);
			c.add(Calendar.DATE, 1);
			pocetniDatum = c.getTime();
			izvjestajDTO.getVrijednostiXOse().add(xOsa);

		}

		return izvjestajDTO;
	}

}
