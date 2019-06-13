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
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.BrzaRezervacijaKarteDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.KorisnikDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.LetDTO;
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
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.Let;
import rs.ac.uns.ftn.isa9.tim8.model.NacinPlacanjaUsluge;
import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSjedista;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSobe;
import rs.ac.uns.ftn.isa9.tim8.model.Sjediste;
import rs.ac.uns.ftn.isa9.tim8.model.Usluga;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.AviokompanijaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.AvionRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.BrzaRezervacijaSjedistaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.KorisnikRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.LetoviRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.Rezervacija_sjedistaRepository;
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
	protected UslugeRepository uslugeRepository;

	@Autowired
	protected Rezervacija_sjedistaRepository rezervacijaSjedistaRepository;

	@Autowired
	protected BrzaRezervacijaSjedistaRepository brzaRezervacijaSjedistaRepository;

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
		Aviokompanija target = admin.getAviokompanija();

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
		aviokompanijaRepository.save(aviokompanija);
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
				for (Sjediste s : sjedista) {
					// kroz sjedista i gledas postoji li rezervacija za to sjediste
					if (!jeLiSjedisteRezervisano(s, l.getRezervacije(),
							l.getAvion().getAviokompanija().getBrzeRezervacije())) {
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
			if (jeLiSjedisteRezervisano(s, let.getRezervacije(),
					let.getAvion().getAviokompanija().getBrzeRezervacije())) {
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

		RezervacijaSjedista rs = new RezervacijaSjedista(null, korisnik.getIme(), korisnik.getPrezime(), "", cijena,
				brs.getSjediste(), korisnik, brs.getAviokompanija(), brs.getLet(), null);

		brzaRezervacijaSjedistaRepository.delete(brs);
		rezervacijaSjedistaRepository.save(rs);

		return "Rezervacija je uspjesno izvrsena.";
	}

	public String otkaziRezervaciju(Long id) throws NevalidniPodaciException{
		
		Optional<RezervacijaSjedista> pretragaRez = rezervacijaSjedistaRepository.findById(id);
		
		if (!pretragaRez.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji rezervacija zadatim id-em");
		}
		
		RezervacijaSjedista rez = pretragaRez.get();
		rezervacijaSjedistaRepository.delete(rez);
		
		BrzaRezervacijaSjedista brs = new BrzaRezervacijaSjedista(rez.getSjediste(), rez.getLet().getDatumPoletanja(), rez.getLet().getDatumSletanja(), 0, 0);

		brs.setCijena(rez.getLet().getCijenaKarte() + rez.getSjediste().getSegment().getCijena());
		brs.setAviokompanija(rez.getAviokompanija());
		brs.setLet(rez.getLet());
		brzaRezervacijaSjedistaRepository.save(brs);
		return "Uspjesno ste otkazali rezervaciju leta";
	}

	public Boolean rezervacijaLetaOcjenjena(Long idRezervacije) throws NevalidniPodaciException {
		Optional<RezervacijaSjedista> pretragaRez = rezervacijaSjedistaRepository.findById(idRezervacije);
		if (!pretragaRez.isPresent()) {
			throw new NevalidniPodaciException("Doslo je do greske.");
		}
		
		RezervacijaSjedista rLet = pretragaRez.get();
		
		if (rLet.isOcjenjeno()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//ocjenjivanje leta nakon njegovog sletanja
	public String ocjeniLet(Long id, int ratingValue) throws NevalidniPodaciException {
		Optional<RezervacijaSjedista> pretragaRez = rezervacijaSjedistaRepository.findById(id);
		if (!pretragaRez.isPresent()) {
			throw new NevalidniPodaciException("Doslo je do greske.");
		}
		
		RezervacijaSjedista rSjed = pretragaRez.get();
		
		Optional<Aviokompanija> pretragaAviokompanije = aviokompanijaRepository.findById(rSjed.getAviokompanija().getId());
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

}
