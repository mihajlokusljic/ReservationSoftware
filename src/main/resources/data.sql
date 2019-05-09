INSERT INTO `reservationsoftware`.`authority` (`id`, `tip_korisnika`) VALUES ('1', 'AdministratorSistema');
INSERT INTO `reservationsoftware`.`korisnik` 
(`dtype`, `id`, `broj_telefona`, `email`, `enabled`, `ime`, `last_password_reset_date`, `loznika`, `prezime`, `putanja_slike`, `lozinka_promjenjena`, `verifikovan_mail` ) VALUES
('Osoba', '1', '123', 'root@root.com', b'1', 'Root', '2019-04-17 04:38:03', '$2a$10$LGc0HOSeXrWqm73vCjZqzuFepDQqzzupq3jp3eWcIZSYiMxQ6/RTe', 'Root', '', b'1', b'1');
INSERT INTO `reservationsoftware`.`korisnik_autoritet` (`korisnik_id`, `autoritet_id`) VALUES ('1', '1');