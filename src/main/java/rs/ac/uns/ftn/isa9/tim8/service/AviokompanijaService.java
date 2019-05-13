package rs.ac.uns.ftn.isa9.tim8.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.LetDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaLetaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorAviokompanije;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Avion;
import rs.ac.uns.ftn.isa9.tim8.model.Destinacija;
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.Let;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.AviokompanijaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.AvionRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.LetoviRepository;

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
		AdministratorAviokompanije admin = (AdministratorAviokompanije) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

	public Aviokompanija dobaviAviokompaniju(Long aviokompanijaId) throws NevalidniPodaciException {
		Optional<Aviokompanija> aviokompanijaSearch = this.aviokompanijaRepository.findById(aviokompanijaId);
		if(!aviokompanijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji aviokompanija sa zadatim id-em.");
		}
		return aviokompanijaSearch.get();
	}

	public Collection<Let> dobaviLetoveAviokompanije(Long aviokompanijaId) throws NevalidniPodaciException {
		Optional<Aviokompanija> aviokompanijaSearch = this.aviokompanijaRepository.findById(aviokompanijaId);
		if(!aviokompanijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji aviokompanija sa zadatim id-em.");
		}
		return aviokompanijaSearch.get().getLetovi();
	}

}
