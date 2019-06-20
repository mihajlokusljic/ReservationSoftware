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
-- Dumping data for table `vozilo`
--

LOCK TABLES `vozilo` WRITE;
/*!40000 ALTER TABLE `vozilo` DISABLE KEYS */;
INSERT INTO `vozilo` VALUES (1,1,5,5,25,2017,0,'Golf','7','Volkswagen',2,'Automobil',1,10),(2,0,5,5,25,2017,0,'Polo','1.9','Volkswagen',0,'Automobil',1,10),(3,0,5,5,20,2016,0,'Passat','Karavan','Volkswagen',0,'Automobil',2,11),(4,0,5,5,25,2017,0,'C','3','Citroen',0,'Automobil',2,11),(5,0,5,5,25,2017,0,'C','3','Citroen',0,'Automobil',2,11),(6,0,5,5,25,2016,0,'Astra','TDI','Opel',0,'Automobil',3,12),(7,0,5,5,30,2017,0,'500','L','Fiat',0,'Automobil',3,12),(8,0,5,5,35,2018,0,'307','Karavan','Peugeot',0,'Automobil',3,12),(9,0,5,5,35,2017,0,'Focus','TDI','Ford',0,'Automobil',4,16),(10,0,2,2,35,2017,0,'Chevrolet','Silvedaro','Chevrolet Silvedaro',0,'Automobil',4,16),(11,0,5,5,60,2018,0,'X','3','BMW',0,'Automobil',5,18),(12,0,5,5,70,2018,0,'Jeep','Compass','Jeep Compass',0,'Automobil',5,18);
/*!40000 ALTER TABLE `vozilo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:49:31
