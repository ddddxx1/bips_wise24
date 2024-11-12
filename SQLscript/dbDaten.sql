-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: bips_wise24
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `antwort`
--

DROP TABLE IF EXISTS `antwort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `antwort` (
  `id_antwort` int NOT NULL,
  `id_frage` int NOT NULL,
  `antworttext` varchar(250) NOT NULL,
  `punkte` int NOT NULL,
  PRIMARY KEY (`id_antwort`,`id_frage`),
  KEY `fk_Antwort_Frage1_idx` (`id_frage`),
  CONSTRAINT `fk_Antwort_Frage1` FOREIGN KEY (`id_frage`) REFERENCES `frage` (`id_frage`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `antwort`
--

LOCK TABLES `antwort` WRITE;
/*!40000 ALTER TABLE `antwort` DISABLE KEYS */;
INSERT INTO `antwort` VALUES (1,1,'A:Innerhalb von 24 Stunden',3),(2,1,'B:Innerhalb von 1 Woche',1),(3,2,'A:Ja, umfassend',3),(4,2,'B:Ja, begrenzt',1),(5,3,'A:Schnell, Innerhalb von Tagen',3),(6,3,'B:Innerhalb von Wochen',1),(7,4,'A:Sehr flexibel',3),(8,4,'B:Weniger flexibel',1),(9,5,'A:11-20 Varianten',3),(10,5,'B:1-5 Varianten',1),(11,6,'A:1-3 Tage ',3),(12,6,'B:8-14 Tage',1),(13,7,'A:Nie',3),(14,7,'B:3-5 Mal ',1),(15,8,'A:Gut',3),(16,8,'B:Durchschnittlich',1),(17,9,'A:0-2 Tage ',3),(18,9,'B:3-7 Tage',1),(19,10,'A:0-2 Stunden',3),(20,10,'B:3-6 Stunden',1),(21,11,'A: Ja, wir sind ISO-zertifiziert. ',3),(22,11,'B: Unsere Produktionsprozesse entsprechen den entsprechenden internationalen Normen, sind aber noch nicht ISO-zertifiziert.',1),(23,12,'A: Wir haben strenge Kriterien für die Auswahl der Rohstoffe. ',3),(24,12,'B: Wir bemühen uns, die qualitativ besten Rohstoffe auszuwählen, aber die Kriterien sind relativ flexibel.',1),(25,13,'A: Ja, wir führen eine kontinuierliche Qualitätsüberwachung und -verbesserung durch. ',3),(26,13,'B: Wir führen eine regelmäßige Qualitätsüberwachung durch, aber die Verbesserungen sind nicht häufig genug.',1),(27,14,'A: Ja, wir arbeiten mit anderen Unternehmen zusammen, um die Produktqualität zu gewährleisten. ',3),(28,14,'B: Wir verlassen uns hauptsächlich auf interne Qualitätskontrollmaßnahmen.',1),(29,15,'A: Wir haben ein spezielles Kundendienstteam, das sich um Reklamationen kümmert.',1),(30,15,'B: Wir haben eine schnelle Rückgabe- und Ersatzpolitik.',3),(31,16,'A: Wir veröffentlichen Informationen auf unserer Website, in sozialen Medien und traditionellen Medien.',3),(32,16,'B: Wir veröffentlichen Informationen hauptsächlich über unsere Website und nutzen andere Kanäle weniger.',1),(33,17,'A: Ja, wir stellen ausführliche Produktverwendungsanleitungen und Hilfedateien zur Verfügung. ',3),(34,17,'B: Zurzeit sind keine derartigen Support-Dateien verfügbar.',1),(35,18,'A: Wir haben ein spezielles Team, das für die Beobachtung des Marktes und die regelmäßige Aktualisierung der Produktinformationen zuständig ist.',3),(36,18,'B: Wir aktualisieren die Produktinformationen entsprechend den Bedürfnissen unserer Kunden.',1),(37,19,'A:  Wir ergreifen strenge Maßnahmen zur Informationssicherheit, um die Privatsphäre unserer Kunden zu schützen.',3),(38,19,'B: Wir ergreifen grundlegende Sicherheitsmaßnahmen, aber es ist schwierig, absolute Sicherheit zu gewährleisten.',1),(39,20,'A: Wir verfügen über einen Mechanismus zur schnellen Beantwortung von Kundenfragen und -informationen.',3),(40,20,'B: Wir tun unser Bestes, um Kundenfragen zu beantworten, aber es kann etwas länger dauern.',1);
/*!40000 ALTER TABLE `antwort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `frage`
--

DROP TABLE IF EXISTS `frage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `frage` (
  `id_frage` int NOT NULL,
  `fragetext` varchar(450) NOT NULL,
  `id_kategorie` int NOT NULL,
  PRIMARY KEY (`id_frage`),
  KEY `fk_Frage_Kategorie1_idx` (`id_kategorie`),
  CONSTRAINT `fk_Frage_Kategorie1` FOREIGN KEY (`id_kategorie`) REFERENCES `kategorie` (`id_kategorie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `frage`
--

LOCK TABLES `frage` WRITE;
/*!40000 ALTER TABLE `frage` DISABLE KEYS */;
INSERT INTO `frage` VALUES (1,'Wie schnell können Sie auf plötzliche Änderungen der Bestellmengen reagieren?',1),(2,'Bieten Sie maßgeschneiderte Lösungen für individuelle Kundenbedürfnisse an?',1),(3,'Wie schnell können Sie Produktanpassungen vornehmen?',1),(4,'Wie flexibel sind Sie bei der Anpassung von Lieferterminen?',1),(5,'Wie viele verschiedene Produktvarianten können Sie gleichzeitig produzieren?',1),(6,'Durchschnittliche Lieferzeit nach Bestellungseingang (in Tagen)',2),(7,'Wie oft haben Sie in den letzten 6 Monaten Liefertermine verfehlt?',2),(8,'Wie effizient ist Ihr Lagermanagement? (Durchschnittliche Lagerbestände im Verhältnis zu Bestellungen)',2),(9,'Durchschnittliche Bearbeitungszeit von Reklamationen (in Tagen)',2),(10,'Durchschnittliche Reaktionszeit auf Anfragen und Anliegen (in Stunden)',2),(11,'Entspricht Ihr Produktionsprozess den internationalen Normen?',3),(12,'Welche Kriterien legen Sie bei der Auswahl der Rohmaterialien an?',3),(13,'Unterliegen die Produkte',3),(14,'Arbeiten Sie mit anderen Unternehmen oder Lieferketten zusammen, um die Produktqualität sicherzustellen?',3),(15,'Wie gehen Sie mit Beschwerden über die Produktqualität und mit Rücksendungen um?',3),(16,'Über welche Kanäle und Plattformen verbreiten Sie die Informationen?',4),(17,'Stellen Sie Anleitungen zur Produktnutzung oder Hilfedateien zur Verfügung?',4),(18,'Wie aktualisieren Sie Produktinformationen und stellen sie zeitnah zur Verfügung, um der Marktnachfrage gerecht zu werden?',4),(19,'Wie gehen Sie mit Fragen der Informationssicherheit und des Datenschutzes der Kunden um?',4),(20,'Wie reagieren Sie auf Informationen und Fragen von Kunden?',4);
/*!40000 ALTER TABLE `frage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fragebogen`
--

DROP TABLE IF EXISTS `fragebogen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fragebogen` (
  `id_fragebogen` int NOT NULL,
  `beschreibung` varchar(45) NOT NULL,
  PRIMARY KEY (`id_fragebogen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fragebogen`
--

LOCK TABLES `fragebogen` WRITE;
/*!40000 ALTER TABLE `fragebogen` DISABLE KEYS */;
/*!40000 ALTER TABLE `fragebogen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fragebogen_has_frage`
--

DROP TABLE IF EXISTS `fragebogen_has_frage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fragebogen_has_frage` (
  `id_fragebogen` int NOT NULL,
  `id_frage` int NOT NULL,
  PRIMARY KEY (`id_fragebogen`,`id_frage`),
  KEY `fk_Fragebogen_has_Frage_Frage1_idx` (`id_frage`),
  KEY `fk_Fragebogen_has_Frage_Fragebogen_idx` (`id_fragebogen`),
  CONSTRAINT `fk_Fragebogen_has_Frage_Frage1` FOREIGN KEY (`id_frage`) REFERENCES `frage` (`id_frage`),
  CONSTRAINT `fk_Fragebogen_has_Frage_Fragebogen` FOREIGN KEY (`id_fragebogen`) REFERENCES `fragebogen` (`id_fragebogen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fragebogen_has_frage`
--

LOCK TABLES `fragebogen_has_frage` WRITE;
/*!40000 ALTER TABLE `fragebogen_has_frage` DISABLE KEYS */;
/*!40000 ALTER TABLE `fragebogen_has_frage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategorie`
--

DROP TABLE IF EXISTS `kategorie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorie` (
  `id_kategorie` int NOT NULL,
  `beschreibung` varchar(45) NOT NULL,
  PRIMARY KEY (`id_kategorie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorie`
--

LOCK TABLES `kategorie` WRITE;
/*!40000 ALTER TABLE `kategorie` DISABLE KEYS */;
INSERT INTO `kategorie` VALUES (1,'Flexibilität'),(2,'Zeit'),(3,'Qualität'),(4,'Informationsfähigkeit');
/*!40000 ALTER TABLE `kategorie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategoriegewicht_in_projekt`
--

DROP TABLE IF EXISTS `kategoriegewicht_in_projekt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategoriegewicht_in_projekt` (
  `id_kategorie` int NOT NULL,
  `id_projekt` int NOT NULL,
  `gewicht` float NOT NULL,
  PRIMARY KEY (`id_kategorie`,`id_projekt`),
  KEY `fk_Kategorie_has_Projekt_Projekt1_idx` (`id_projekt`),
  KEY `fk_Kategorie_has_Projekt_Kategorie1_idx` (`id_kategorie`),
  CONSTRAINT `fk_Kategorie_has_Projekt_Kategorie1` FOREIGN KEY (`id_kategorie`) REFERENCES `kategorie` (`id_kategorie`),
  CONSTRAINT `fk_Kategorie_has_Projekt_Projekt1` FOREIGN KEY (`id_projekt`) REFERENCES `projekt` (`id_projekt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategoriegewicht_in_projekt`
--

LOCK TABLES `kategoriegewicht_in_projekt` WRITE;
/*!40000 ALTER TABLE `kategoriegewicht_in_projekt` DISABLE KEYS */;
/*!40000 ALTER TABLE `kategoriegewicht_in_projekt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lieferant`
--

DROP TABLE IF EXISTS `lieferant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lieferant` (
  `id_lieferant` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `adresse` varchar(45) NOT NULL,
  `passwort` varchar(45) NOT NULL,
  PRIMARY KEY (`id_lieferant`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lieferant`
--

LOCK TABLES `lieferant` WRITE;
/*!40000 ALTER TABLE `lieferant` DISABLE KEYS */;
INSERT INTO `lieferant` VALUES (1,'A','Am Exer 1','AmExer1'),(2,'B','Am Exer 2','AmExer2'),(3,'C','Am Exer 3','AmExer3'),(4,'D','Am Exer 4','AmExer4');
/*!40000 ALTER TABLE `lieferant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projekt`
--

DROP TABLE IF EXISTS `projekt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projekt` (
  `id_projekt` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `komponente` varchar(100) NOT NULL,
  `id_fragebogen` int NOT NULL,
  PRIMARY KEY (`id_projekt`),
  KEY `fk_Projekt_Fragebogen1_idx` (`id_fragebogen`),
  CONSTRAINT `fk_Projekt_Fragebogen1` FOREIGN KEY (`id_fragebogen`) REFERENCES `fragebogen` (`id_fragebogen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projekt`
--

LOCK TABLES `projekt` WRITE;
/*!40000 ALTER TABLE `projekt` DISABLE KEYS */;
/*!40000 ALTER TABLE `projekt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projekt_has_antwort`
--

DROP TABLE IF EXISTS `projekt_has_antwort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projekt_has_antwort` (
  `id_projekt` int NOT NULL,
  `id_antwort` int NOT NULL,
  `id_frage` int NOT NULL,
  `ist_ko_kriterium` tinyint NOT NULL,
  PRIMARY KEY (`id_projekt`,`id_antwort`,`id_frage`),
  KEY `fk_Projekt_has_Antwort_Antwort1_idx` (`id_antwort`,`id_frage`),
  KEY `fk_Projekt_has_Antwort_Projekt1_idx` (`id_projekt`),
  CONSTRAINT `fk_Projekt_has_Antwort_Antwort1` FOREIGN KEY (`id_antwort`, `id_frage`) REFERENCES `antwort` (`id_antwort`, `id_frage`),
  CONSTRAINT `fk_Projekt_has_Antwort_Projekt1` FOREIGN KEY (`id_projekt`) REFERENCES `projekt` (`id_projekt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projekt_has_antwort`
--

LOCK TABLES `projekt_has_antwort` WRITE;
/*!40000 ALTER TABLE `projekt_has_antwort` DISABLE KEYS */;
/*!40000 ALTER TABLE `projekt_has_antwort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projekt_has_lieferant`
--

DROP TABLE IF EXISTS `projekt_has_lieferant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projekt_has_lieferant` (
  `id_projekt` int NOT NULL,
  `id_lieferant` int NOT NULL,
  `score` float DEFAULT NULL,
  `rank` int DEFAULT NULL,
  PRIMARY KEY (`id_projekt`,`id_lieferant`),
  KEY `fk_Projekt_has_Lieferant_Lieferant1_idx` (`id_lieferant`),
  KEY `fk_Projekt_has_Lieferant_Projekt1_idx` (`id_projekt`),
  CONSTRAINT `fk_Projekt_has_Lieferant_Lieferant1` FOREIGN KEY (`id_lieferant`) REFERENCES `lieferant` (`id_lieferant`),
  CONSTRAINT `fk_Projekt_has_Lieferant_Projekt1` FOREIGN KEY (`id_projekt`) REFERENCES `projekt` (`id_projekt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projekt_has_lieferant`
--

LOCK TABLES `projekt_has_lieferant` WRITE;
/*!40000 ALTER TABLE `projekt_has_lieferant` DISABLE KEYS */;
/*!40000 ALTER TABLE `projekt_has_lieferant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projekt_has_lieferant_has_antwort`
--

DROP TABLE IF EXISTS `projekt_has_lieferant_has_antwort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projekt_has_lieferant_has_antwort` (
  `id_projekt` int NOT NULL,
  `id_lieferant` int NOT NULL,
  `id_frage` int NOT NULL,
  `id_antwort` int NOT NULL,
  PRIMARY KEY (`id_projekt`,`id_lieferant`,`id_frage`,`id_antwort`),
  KEY `fk_Projekt_has_Lieferant_has_Antwort_Antwort1_idx` (`id_antwort`,`id_frage`),
  KEY `fk_Projekt_has_Lieferant_has_Antwort_Projekt_has_Lieferant1_idx` (`id_projekt`,`id_lieferant`),
  CONSTRAINT `fk_Projekt_has_Lieferant_has_Antwort_Antwort1` FOREIGN KEY (`id_antwort`, `id_frage`) REFERENCES `antwort` (`id_antwort`, `id_frage`),
  CONSTRAINT `fk_Projekt_has_Lieferant_has_Antwort_Projekt_has_Lieferant1` FOREIGN KEY (`id_projekt`, `id_lieferant`) REFERENCES `projekt_has_lieferant` (`id_projekt`, `id_lieferant`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projekt_has_lieferant_has_antwort`
--

LOCK TABLES `projekt_has_lieferant_has_antwort` WRITE;
/*!40000 ALTER TABLE `projekt_has_lieferant_has_antwort` DISABLE KEYS */;
/*!40000 ALTER TABLE `projekt_has_lieferant_has_antwort` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-12 16:45:27
