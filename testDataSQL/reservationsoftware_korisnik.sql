-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: reservationsoftware
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES ('Osoba',0,NULL,'123','root@root.com',_binary '','Root','2019-04-17 04:38:03','$2a$10$LGc0HOSeXrWqm73vCjZqzuFepDQqzzupq3jp3eWcIZSYiMxQ6/RTe',_binary '','Root','',_binary '',NULL,NULL,NULL,NULL,NULL),('AdministratorAviokompanije',3,NULL,'00381649217348','airserbia+1@gmail.com',_binary '','Marko','2019-06-20 16:07:20','$2a$10$SEcxsqtZtkWfUQvPqkgWJe9C4WDS6uSIo1Ff4bfqW/XSmc99TY0yG',_binary '','Milanković','',_binary '',NULL,3,NULL,1,NULL),('AdministratorAviokompanije',4,NULL,'0038164928541','airserbia+2@gmail.com',_binary '','Marina','2019-06-20 16:07:55','$2a$10$dNI0iTaQdqya8Krce.9Bj.4wx5nSC//K.XiJ8g6.8XvzGK13UObtu',_binary '','Medić','',_binary '',NULL,4,NULL,1,NULL),('AdministratorAviokompanije',5,NULL,'154175430102076152','americanairlines+1@gmail.com',_binary '','Jack','2019-06-20 16:11:11','$2a$10$bbHo.P8t86h5G.rHMyKQwuxdJPIjHtwZTMPUZSGsHu6iiZg4dUZ26',_binary '','Smith','',_binary '',NULL,5,NULL,2,NULL),('AdministratorAviokompanije',6,NULL,'15417543010987644','americanairlines+2@gmail.com',_binary '','Jane','2019-06-20 16:11:50','$2a$10$QBThCPnDIiNo4m8JWfbsCugrmdz0qtFq6d1t5oKckPcVdlYq4S6Y2',_binary '\0','Roberts','',_binary '',NULL,6,NULL,2,NULL),('AdministratorHotela',19,NULL,'00381640205792','garnihotel@gmail.com',_binary '','Milojko','2019-06-20 16:49:29','$2a$10$eAwk5Ac2Fij2N8d2RHhoq.P2OS1MXaEjQuCsjJWvDGy136G/5IS7y',_binary '','Pantić','',_binary '',NULL,19,NULL,NULL,7),('AdministratorHotela',20,NULL,'00381640205841','crownplaza@gmail.com',_binary '','Milana','2019-06-20 16:50:23','$2a$10$nbMAxAUBdjREQ..QPjjDLuS34SA6g1.8FLVH4xCgT08ffGMPE30Vy',_binary '','Pantić','',_binary '',NULL,20,NULL,NULL,8),('AdministratorHotela',21,NULL,'00381640203812','butikkuca@gmail.com',_binary '','Slaven','2019-06-20 16:51:04','$2a$10$2NnHoA3s4d7Ree7U3Y4kBeiibBphTWg5qdBRf3PAnouIZ2Be5RKXi',_binary '','Mičić','',_binary '',NULL,20,NULL,NULL,9),('AdministratorHotela',22,NULL,'12023930912','washingtonhilton@gmail.com',_binary '','John','2019-06-20 16:54:23','$2a$10$b1aQPvJZkEGnRKbil66lR.9ucGHxqwhUTjUXXSgr3K0bs13snrzo.',_binary '','Cline','',_binary '',NULL,21,NULL,NULL,13),('AdministratorHotela',23,NULL,'12023930912','duonomad@gmail.com',_binary '','Rachel','2019-06-20 16:55:15','$2a$10$1eWHRvxRXa8DXmIEmXExJeDsOF3UFANU/KSJxXUA/8SMhqK6gut1G',_binary '','Johnson','',_binary '',NULL,22,NULL,NULL,14),('AdministratorRentACar',24,NULL,'0038164010872451','club72rac@gmail.com',_binary '','Milan','2019-06-20 16:56:22','$2a$10$uRuXiAFSHsd3toP1Ty.7n.M.827Fbh3VSA4ct4VDq5UIBDeMSnxKS',_binary '','Jokić','',_binary '',NULL,23,10,NULL,NULL),('AdministratorRentACar',25,NULL,'0038164010872298','martelloplus@gmail.com',_binary '','Nikolina','2019-06-20 16:57:10','$2a$10$46eZPkSNnayf8POqJ1ObDO33E93vDSAv5m0re.205fRGbzDNTcwNy',_binary '','Šarić','',_binary '',NULL,24,11,NULL,NULL),('AdministratorRentACar',26,NULL,'0038164010872901','racgalaxypro@gmail.com',_binary '','Jovan','2019-06-20 16:58:12','$2a$10$V6N4afNPY9DUGQvQ3pf0JeG6kNrtyyi8mOQJtT./gLeAwHjucoD2O',_binary '','Jovanović','',_binary '',NULL,25,12,NULL,NULL),('AdministratorRentACar',27,NULL,'12023930981','budgetcarrental@gmail.com',_binary '','Ben','2019-06-20 16:59:27','$2a$10$OGae4qc4fR1f8ANZsgHp5Obdg.EgIHpmc2CuRzrcn2b/vamWoEuBO',_binary '','McClay','',_binary '',NULL,26,16,NULL,NULL),('AdministratorRentACar',28,NULL,'12023930712','enterpriserentacar@gmail.com',_binary '','Janny','2019-06-20 17:00:23','$2a$10$aD2dISHIMiefjv8Zued11u6VEONRI9Hofs8rw2fb0fpMNQcTzAw4W',_binary '','Dollson','',_binary '',NULL,27,18,NULL,NULL),('RegistrovanKorisnik',31,'N23741A21','00381640309821','mihajlokusljic97@gmail.com',_binary '','Mihajlo',NULL,'$2a$10$vXQZ8y19ajYTlCBzh4WjlOT7mKsvrO7t8xDFa5/0vgjFaqEbyq2yK',_binary '\0','Kušljić',NULL,_binary '',118.8643083414446,32,NULL,NULL,NULL),('RegistrovanKorisnik',33,'877X98N2','00381641528265','mihajlokusljic97+1@gmail.com',_binary '','Bojan',NULL,'$2a$10$4u3DOXXXmpLZarKLeohsY.Q3h2VPePe35D/6AXX4G8WYkjpfvndaq',_binary '\0','Marković',NULL,_binary '\0',0,33,NULL,NULL,NULL);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:49:34
