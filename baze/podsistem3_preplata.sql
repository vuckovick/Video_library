CREATE DATABASE  IF NOT EXISTS `podsistem3` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `podsistem3`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem3
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `preplata`
--

DROP TABLE IF EXISTS `preplata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preplata` (
  `idPreplata` int NOT NULL AUTO_INCREMENT,
  `idPaket` int NOT NULL,
  `idKorisnik` int NOT NULL,
  `Datum` datetime NOT NULL,
  `Cena` int NOT NULL,
  PRIMARY KEY (`idPreplata`),
  UNIQUE KEY `idPreplata_UNIQUE` (`idPreplata`),
  KEY `paket_idx` (`idPaket`),
  KEY `korisnik_idx` (`idKorisnik`),
  CONSTRAINT `korisnik` FOREIGN KEY (`idKorisnik`) REFERENCES `korisnik` (`idKorisnik`) ON UPDATE CASCADE,
  CONSTRAINT `paket` FOREIGN KEY (`idPaket`) REFERENCES `paket` (`idpaket`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preplata`
--

LOCK TABLES `preplata` WRITE;
/*!40000 ALTER TABLE `preplata` DISABLE KEYS */;
INSERT INTO `preplata` VALUES (1,1,1,'2024-02-11 20:58:42',2000),(2,4,4,'2024-02-11 21:22:14',2500),(3,3,2,'2024-02-11 21:23:24',3500),(4,6,5,'2024-02-12 00:12:48',2500);
/*!40000 ALTER TABLE `preplata` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-12 14:23:51
