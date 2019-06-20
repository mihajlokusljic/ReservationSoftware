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
-- Dumping data for table `let`
--

LOCK TABLES `let` WRITE;
/*!40000 ALTER TABLE `let` DISABLE KEYS */;
INSERT INTO `let` VALUES (29,'L0001',1,200,'2019-05-07 12:00:00','2019-05-09 09:00:00','2019-05-27 12:00:00',3,1,1,2),(30,'L0002',0,190,'2019-07-27 12:00:00','2019-07-29 09:00:00','2019-08-17 12:00:00',0,1,2,1),(35,'F001',0,250,'2019-06-23 08:00:00','2019-06-24 14:00:00','2019-06-26 12:00:00',0,2,4,6),(36,'FBR01',0,150,'2019-07-19 12:00:00','2019-07-21 14:30:00','2019-08-03 12:00:00',0,2,4,6),(37,'LOR01',0,320,'2019-09-01 12:00:00','2019-09-03 10:00:00','2019-09-14 10:00:00',0,1,2,1),(38,'LOR02',0,250,'2019-09-14 07:00:00','2019-09-16 10:00:00','2019-09-21 14:00:00',0,1,1,2);
/*!40000 ALTER TABLE `let` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:49:30