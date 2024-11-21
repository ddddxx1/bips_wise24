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

DROP DATABASE IF EXISTS `bips_wise24`;
CREATE DATABASE bips_wise24;
USE bips_wise24;

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
INSERT INTO `antwort` VALUES (1,1,'A:Innerhalb von 24 Stunden',3),(2,1,'B:Innerhalb von 1 Woche',1),(3,2,'A:Ja, umfassend',3),(4,2,'B:Ja, begrenzt',1),(5,3,'A:Schnell, Innerhalb von Tagen',3),(6,3,'B:Innerhalb von Wochen',1),(7,4,'A:Sehr flexibel',3),(8,4,'B:Weniger flexibel',1),(9,5,'A:11-20 Varianten',3),(10,5,'B:1-5 Varianten',1),(11,6,'A:1-3 Tage ',3),(12,6,'B:8-14 Tage',1),(13,7,'A:Nie',3),(14,7,'B:3-5 Mal ',1),(15,8,'A:Gut',3),(16,8,'B:Durchschnittlich',1),(17,9,'A:0-2 Tage ',3),(18,9,'B:3-7 Tage',1),(19,10,'A:0-2 Stunden',3),(20,10,'B:3-6 Stunden',1),(21,11,'A: Ja, wir sind ISO-zertifiziert. ',3),(22,11,'B: Unsere Produktionsprozesse entsprechen den entsprechenden internationalen Normen, sind aber noch nicht ISO-zertifiziert.',1),(23,12,'A: Wir haben strenge Kriterien für die Auswahl der Rohstoffe. ',3),(24,12,'B: Wir bemühen uns, die qualitativ besten Rohstoffe auszuwählen, aber die Kriterien sind relativ flexibel.',1),(25,13,'A: Ja, wir führen eine kontinuierliche Qualitätsüberwachung und -verbesserung durch. ',3),(26,13,'B: Wir führen eine regelmäßige Qualitätsüberwachung durch, aber die Verbesserungen sind nicht häufig genug.',1),(27,14,'A: Ja, wir arbeiten mit anderen Unternehmen zusammen, um die Produktqualität zu gewährleisten. ',3),(28,14,'B: Wir verlassen uns hauptsächlich auf interne Qualitätskontrollmaßnahmen.',1),(29,15,'A: Wir haben ein spezielles Kundendienstteam, das sich um Reklamationen kümmert.',1),(30,15,'B: Wir haben eine schnelle Rückgabe- und Ersatzpolitik.',3),(31,16,'A: Wir veröffentlichen Informationen auf unserer Website, in sozialen Medien und traditionellen Medien.',3),(32,16,'B: Wir veröffentlichen Informationen hauptsächlich über unsere Website und nutzen andere Kanäle weniger.',1),(33,17,'A: Ja, wir stellen ausführliche Produktverwendungsanleitungen und Hilfedateien zur Verfügung. ',3),(34,17,'B: Zurzeit sind keine derartigen Support-Dateien verfügbar.',1),(35,18,'A: Wir haben ein spezielles Team, das für die Beobachtung des Marktes und die regelmäßige Aktualisierung der Produktinformationen zuständig ist.',3),(36,18,'B: Wir aktualisieren die Produktinformationen entsprechend den Bedürfnissen unserer Kunden.',1),(37,19,'A:  Wir ergreifen strenge Maßnahmen zur Informationssicherheit, um die Privatsphäre unserer Kunden zu schützen.',3),(38,19,'B: Wir ergreifen grundlegende Sicherheitsmaßnahmen, aber es ist schwierig, absolute Sicherheit zu gewährleisten.',1),(39,20,'A: Wir verfügen über einen Mechanismus zur schnellen Beantwortung von Kundenfragen und -informationen.',3),(40,20,'B: Wir tun unser Bestes, um Kundenfragen zu beantworten, aber es kann etwas länger dauern.',1),(41,21,'A. Produktionspläne können angepasst werden',3),(42,21,'B. Zusätzliche Kosten sind erforderlich, um Änderungen vorzunehmen',1),(43,22,'A. Lagerbestand ist vorhanden, um auf schnelle Veränderungen in der Nachfrage zu reagieren',3),(44,22,'B. Es dauert eine Weile, um die Produktionslinie anzupassen',1),(45,23,'A. Bereit, kleine Mengen zu akzeptieren',3),(46,23,'B. Akzeptiert nur Großbestellungen',1),(47,24,'A. Kann andere Teile der Lieferkette koordinieren, um auf Veränderungen zu reagieren',3),(48,24,'B. Kann andere Teile der Lieferkette nicht anpassen',1),(49,25,'A. Nimmt aktiv neue Technologien und Methoden an',3),(50,25,'B. Bevorzugt traditionelle Methoden, zögert, neue Technologien auszuprobieren',1),(51,26,'A. Liefert immer pünktlich',3),(52,26,'B. Liefert oft verspätet',1),(53,27,'A. Kann Notfallsituationen schnell bewältigen',3),(54,27,'B. Braucht lange, um auf Notfälle zu reagieren',1),(55,28,'A. Bietet rechtzeitig Informationen über Lieferverzögerungen',3),(56,28,'B. Informiert selten oder gar nicht über Lieferverzögerungen',1),(57,29,'A. Hat eine gute Fähigkeit zur Verwaltung von Produktionszyklen',3),(58,29,'B. Produktionszyklen sind instabil und ändern sich häufig',1),(59,30,'A. Bereit, einen gemeinsamen Zeitplan zu erstellen und einzuhalten',3),(60,30,'B. Hält sich an den eigenen Zeitplan, lehnt eine Zusammenarbeit ab',1),(61,31,'A. Hat strenge Qualitätskontrollstandards',3),(62,31,'B. Die Qualitätskontrolle ist nicht ausreichend, es gibt Probleme',1),(63,32,'A. Bietet Qualitätsgarantie an und akzeptiert Rückgaben',3),(64,32,'B. Lehnt es ab, jegliche Form von Qualitätsgarantie anzubieten',1),(65,33,'A. Führt regelmäßige Qualitätsprüfungen durch',3),(66,33,'B. Führt selten oder gar keine Qualitätsprüfungen durch',1),(67,34,'A. Bereit zur Zusammenarbeit und Lösung von Qualitätsproblemen',3),(68,34,'B. Zeigt wenig Interesse an Kundenqualitätsproblemen',1),(69,35,'A. Bereit zur unabhängigen Qualitätsbewertung',3),(70,35,'B. Nicht bereit oder zögert, sich einer unabhängigen Bewertung zu unterziehen',1),(71,36,'A. Bietet zeitnah Informationen zu Bestellstatus und Lieferung',3),(72,36,'B. Informationen werden nicht rechtzeitig bereitgestellt, müssen häufig nachgefragt werden',1),(73,37,'A. Stellt detaillierte Produkt- und technische Informationen bereit',3),(74,37,'B. Fehlende detaillierte Produktinformationen',1),(75,38,'A. Bietet schnelle Reaktion und Lösungen bei Problemen',3),(76,38,'B. Benötigt viel Zeit, um Lösungen bereitzustellen',1),(77,39,'A. Bietet verschiedene Kommunikationswege und guten Kundensupport',3),(78,39,'B. Kommunikationskanäle sind begrenzt, der Kundensupport ist unzureichend',1),(79,40,'A. Bereit, Informationen über die Lieferkette zu teilen',3),(80,40,'B. Hält Informationen zur Lieferkette geheim, zeigt wenig Bereitschaft zur Weitergabe',1);
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
INSERT INTO `frage` VALUES (1,'Wie schnell können Sie auf plötzliche Änderungen der Bestellmengen reagieren?',1),(2,'Bieten Sie maßgeschneiderte Lösungen für individuelle Kundenbedürfnisse an?',1),(3,'Wie schnell können Sie Produktanpassungen vornehmen?',1),(4,'Wie flexibel sind Sie bei der Anpassung von Lieferterminen?',1),(5,'Wie viele verschiedene Produktvarianten können Sie gleichzeitig produzieren?',1),(6,'Durchschnittliche Lieferzeit nach Bestellungseingang (in Tagen)',2),(7,'Wie oft haben Sie in den letzten 6 Monaten Liefertermine verfehlt?',2),(8,'Wie effizient ist Ihr Lagermanagement? (Durchschnittliche Lagerbestände im Verhältnis zu Bestellungen)',2),(9,'Durchschnittliche Bearbeitungszeit von Reklamationen (in Tagen)',2),(10,'Durchschnittliche Reaktionszeit auf Anfragen und Anliegen (in Stunden)',2),(11,'Entspricht Ihr Produktionsprozess den internationalen Normen?',3),(12,'Welche Kriterien legen Sie bei der Auswahl der Rohmaterialien an?',3),(13,'Unterliegen die Produkte',3),(14,'Arbeiten Sie mit anderen Unternehmen oder Lieferketten zusammen, um die Produktqualität sicherzustellen?',3),(15,'Wie gehen Sie mit Beschwerden über die Produktqualität und mit Rücksendungen um?',3),(16,'Über welche Kanäle und Plattformen verbreiten Sie die Informationen?',4),(17,'Stellen Sie Anleitungen zur Produktnutzung oder Hilfedateien zur Verfügung?',4),(18,'Wie aktualisieren Sie Produktinformationen und stellen sie zeitnah zur Verfügung, um der Marktnachfrage gerecht zu werden?',4),(19,'Wie gehen Sie mit Fragen der Informationssicherheit und des Datenschutzes der Kunden um?',4),(20,'Wie reagieren Sie auf Informationen und Fragen von Kunden?',4),(21,'Können sie flexibel auf Änderungen in den Bestellungen reagieren?',1),(22,'Können sie sich schnell an sich ändernde Marktbedingungen anpassen?',1),(23,'Sind sie bereit, kleine Bestellungen anzunehmen?',1),(24,'Können sie flexibel auf andere Teile der Lieferkette reagieren?',1),(25,'Sind sie bereit, neue Technologien oder Methoden zu übernehmen, um die Produktionsflexibilität zu erhöhen?',1),(26,'Können sie Liefertermine zuverlässig einhalten?',2),(27,'Können sie in Notfällen schnell reagieren?',2),(28,'Können sie rechtzeitig über mögliche Lieferverzögerungen informieren?',2),(29,'Können sie die Produktionszyklen effektiv verwalten?',2),(30,'Sind sie bereit, mit Kunden gemeinsam einen Lieferzeitplan zu erstellen?',2),(31,'Haben sie strenge Qualitätskontrollstandards?',3),(32,'Sind sie bereit, eine Qualitätsgarantie für ihre Produkte zu geben?',3),(33,'Führen sie regelmäßige Qualitätsprüfungen durch?',3),(34,'Sind sie bereit, mit Kunden zusammenzuarbeiten, um Qualitätsprobleme zu lösen?',3),(35,'Sind sie bereit, sich einer Qualitätsbewertung durch eine unabhängige Instanz zu unterziehen?',3),(36,'Können sie zeitnah Informationen zu Bestellstatus und Lieferung bereitstellen?',4),(37,'Können sie detaillierte Produkt- und technische Informationen bereitstellen?',4),(38,'Können sie bei Problemen schnell reagieren und Lösungen anbieten?',4),(39,'Haben sie effektive Kommunikationskanäle und Kundensupport?',4),(40,'Sind sie bereit, Informationen über die Lieferkette zu teilen, um die Transparenz zu erhöhen?',4);
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
INSERT INTO `fragebogen` VALUES (1,'Für Logistik Zeit'),(2,'Für Supply Chain Flexibilität'),(3,'Für Vertrieb Information'),(4,'Für Produktion Qualität');
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
INSERT INTO `fragebogen_has_frage` VALUES (1,1),(1,2),(2,2),(1,3),(2,3),(3,3),(1,4),(2,4),(3,4),(4,4),(1,5),(2,5),(3,5),(4,5),(1,6),(1,7),(2,7),(1,8),(2,8),(3,8),(1,9),(2,9),(3,9),(4,9),(1,10),(2,10),(3,10),(4,10),(1,11),(1,12),(2,12),(1,13),(2,13),(3,13),(1,14),(2,14),(3,14),(4,14),(1,15),(2,15),(3,15),(4,15),(1,16),(1,17),(2,17),(1,18),(2,18),(3,18),(1,19),(2,19),(3,19),(4,19),(1,20),(2,20),(3,20),(4,20),(2,21),(3,21),(4,21),(3,22),(4,22),(4,23),(2,26),(3,26),(4,26),(3,27),(4,27),(4,28),(2,31),(3,31),(4,31),(3,32),(4,32),(4,33),(2,36),(3,36),(4,36),(3,37),(4,37),(4,38);
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
INSERT INTO `kategoriegewicht_in_projekt` VALUES (1,1,0.4),(1,2,0.2),(1,3,0.2),(1,4,0.2),(2,1,0.2),(2,2,0.4),(2,3,0.2),(2,4,0.2),(3,1,0.2),(3,2,0.2),(3,3,0.4),(3,4,0.2),(4,1,0.2),(4,2,0.2),(4,3,0.2),(4,4,0.4);
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
INSERT INTO `projekt` VALUES (1,'Supply Chain(Flexibilität)','1',2),(2,'Logistik  Zeit','2',1),(3,'Produktion Qualität','3',4),(4,'Vertrieb Infomationsfähigkeit','4',3);
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
INSERT INTO `projekt_has_antwort` VALUES (1,8,4,1),(2,18,9,1),(3,28,14,1),(4,38,19,1);
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
INSERT INTO `projekt_has_lieferant` VALUES (1,1,11.4,2),(1,2,12.6,1),(1,3,10.2,3);
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
INSERT INTO `projekt_has_lieferant_has_antwort` VALUES (1,1,2,3),(1,2,2,3),(1,3,2,3),(1,1,3,5),(1,2,3,5),(1,3,3,5),(1,1,4,7),(1,3,4,7),(1,2,4,8),(1,1,5,9),(1,2,5,9),(1,3,5,10),(1,2,7,13),(1,1,7,14),(1,3,7,14),(1,2,8,15),(1,1,8,16),(1,3,8,16),(1,2,9,17),(1,1,9,18),(1,3,9,18),(1,1,10,19),(1,2,10,19),(1,3,10,19),(1,1,12,23),(1,2,12,23),(1,3,12,23),(1,2,13,25),(1,3,13,25),(1,1,13,26),(1,2,14,27),(1,3,14,27),(1,1,14,28),(1,1,15,29),(1,2,15,29),(1,3,15,29),(1,1,17,33),(1,2,17,34),(1,3,17,34),(1,1,18,35),(1,2,18,36),(1,3,18,36),(1,3,19,37),(1,1,19,38),(1,2,19,38),(1,1,20,39),(1,2,20,39),(1,3,20,39),(1,1,21,41),(1,2,21,41),(1,3,21,42),(1,1,26,51),(1,2,26,51),(1,3,26,51),(1,2,31,61),(1,1,31,62),(1,3,31,62),(1,2,36,71),(1,1,36,72),(1,3,36,72);
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

-- Dump completed on 2024-11-16 14:02:52
