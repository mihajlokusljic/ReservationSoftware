# Reservation software - Tim 8

Projektni zadatak iz predmeta: Internet softverske arhitekture (ISA) i Metodologije razvoja softvera (MRS)

Članovi tima su:
* Nikolina Batinić SW-01/2016
* Nikola Zubić SW-03/2016
* Mihajlo Kušljić SW-53/2016

# Uputstvo za pokretanje projektnog zadatka
Projektni zadatak se može pokrenuti lokalno, a takođe je i deploy-ovan na Heroku.

## Programi za lokalno pokretanje projektnog zadatka
Za pokretanje ove web aplikacije potrebno je instalirati sledeće programe:
* [MySQL](https://www.mysql.com/downloads/) baza podataka
* [Eclipse IDE for Java EE Developers](https://www.eclipse.org/downloads/packages/release/kepler/sr2/eclipse-ide-java-ee-developers) razvojno okruženje Eclipse-a za web developer-e koje služi za pokretanje Spring Boot aplikacije

## Pokretanje projektnog zadatka koji je deploy-ovan na Heroku
Deploy-ovan je koristeći [ClearDB MySQL](https://devcenter.heroku.com/articles/cleardb) bazu podataka i Heroku server.
Link za pokretanje je: [ReservationSoftware](http://reservation-software.herokuapp.com)

Klonirati repozitorijum:
```
git clone https://github.com/mihajlokusljic/ReservationSoftware.git
```
Odatle on se može pokrenuti koristeći Eclipse EE kao Spring Boot aplikacija na: localhost:8080/pocetnaStranica/index.html (lokalna verzija).
Dodatno, za lakše rukovanje i manipulisanje mySQL bazom podataka može se instalirati [MySQL WorkBench](https://www.mysql.com/products/workbench/).

