# Reservation software - Tim 8 : [https://reservation-software.herokuapp.com](https://reservation-software.herokuapp.com)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d9ee2959fe3d418d9cd05e706d3e1969)](https://app.codacy.com/app/mihajlokusljic/ReservationSoftware?utm_source=github.com&utm_medium=referral&utm_content=mihajlokusljic/ReservationSoftware&utm_campaign=Badge_Grade_Dashboard)

Projektni zadatak iz predmeta: Internet softverske arhitekture (ISA) i Metodologije razvoja softvera (MRS).

Članovi tima su:
* Nikolina Batinić SW-01/2016
* Nikola Zubić SW-03/2016
* Mihajlo Kušljić SW-53/2016

# Uputstvo za pokretanje projektnog zadatka
Projektni zadatak se može pokrenuti lokalno, a takođe je i deploy-ovan na [Heroku](https://www.heroku.com/).

## Programi za lokalno pokretanje projektnog zadatka
Za pokretanje ove web aplikacije potrebno je instalirati sledeće programe:
* [MySQL](https://www.mysql.com/downloads/) baza podataka
* [Eclipse IDE for Java EE Developers](https://www.eclipse.org/downloads/packages/release/kepler/sr2/eclipse-ide-java-ee-developers) razvojno okruženje Eclipse-a za web developer-e koje služi za pokretanje Spring Boot aplikacije

Klonirati repozitorijum:
```
git clone https://github.com/mihajlokusljic/ReservationSoftware.git
```
Odatle on se može pokrenuti koristeći Eclipse EE kao Spring Boot aplikacija u rs.ac.uns.ftn.isa9.tim8.ReservationSoftware.ReservationSoftwareApplication.java, a početna stranica je: localhost:8080/pocetnaStranica/index.html (lokalna verzija).
Dodatno, za lakše rukovanje i manipulisanje mySQL bazom podataka može se instalirati [MySQL WorkBench](https://www.mysql.com/products/workbench/).

## Pokretanje projektnog zadatka koji je deploy-ovan na Heroku
Deploy-ovan je koristeći [ClearDB MySQL](https://devcenter.heroku.com/articles/cleardb) bazu podataka i Heroku server.
Link za pokretanje je: [ReservationSoftware](http://reservation-software.herokuapp.com)
