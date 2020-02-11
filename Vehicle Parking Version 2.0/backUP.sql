-- MySQL dump 10.13  Distrib 5.7.29, for Linux (x86_64)
--
-- Host: localhost    Database: vehicleparking_v2
-- ------------------------------------------------------
-- Server version	5.7.29-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `empid` int(11) NOT NULL AUTO_INCREMENT,
  `ename` varchar(30) NOT NULL,
  `phoneno` bigint(20) NOT NULL,
  `mailid` varchar(30) NOT NULL,
  `teamname` varchar(30) DEFAULT NULL,
  `seatno` varchar(20) NOT NULL,
  `blockname` varchar(20) NOT NULL,
  PRIMARY KEY (`empid`),
  UNIQUE KEY `phoneno` (`phoneno`),
  UNIQUE KEY `mailid` (`mailid`)
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1000,'vengat',12345,'vengatpy@gmail.com','sdp od','12BR03','tower building'),(1001,'barath',12346,'vengatpy1@gmail.com','Im','5BN03','tower building'),(1002,'santhosh',12347,'vengatpy2@gmail.com','IM','5BS03','tower building');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employeetovehicle`
--

DROP TABLE IF EXISTS `employeetovehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employeetovehicle` (
  `mappingid` int(11) NOT NULL AUTO_INCREMENT,
  `empid` int(11) NOT NULL,
  `vehicleid` int(11) NOT NULL,
  PRIMARY KEY (`mappingid`),
  UNIQUE KEY `empid` (`empid`,`vehicleid`),
  KEY `fk_vehicleid` (`vehicleid`),
  CONSTRAINT `fk_empid` FOREIGN KEY (`empid`) REFERENCES `employee` (`empid`),
  CONSTRAINT `fk_vehicleid` FOREIGN KEY (`vehicleid`) REFERENCES `vehicle` (`vehicleid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employeetovehicle`
--

LOCK TABLES `employeetovehicle` WRITE;
/*!40000 ALTER TABLE `employeetovehicle` DISABLE KEYS */;
INSERT INTO `employeetovehicle` VALUES (1,1000,1),(4,1000,5),(2,1001,2),(3,1002,3);
/*!40000 ALTER TABLE `employeetovehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `floors`
--

DROP TABLE IF EXISTS `floors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `floors` (
  `floorid` int(11) NOT NULL AUTO_INCREMENT,
  `floorname` varchar(30) NOT NULL,
  `blockid` int(11) NOT NULL,
  `floortypeid` int(11) NOT NULL,
  `slotcount` int(11) NOT NULL,
  PRIMARY KEY (`floorid`),
  UNIQUE KEY `uk_floor` (`floorname`,`blockid`),
  KEY `fk_blockid` (`blockid`),
  KEY `fk_typeid2` (`floortypeid`),
  CONSTRAINT `fk_blockid` FOREIGN KEY (`blockid`) REFERENCES `parkingblock` (`blockid`),
  CONSTRAINT `fk_typeid2` FOREIGN KEY (`floortypeid`) REFERENCES `vehicletypes` (`vehicletypeid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `floors`
--

LOCK TABLES `floors` WRITE;
/*!40000 ALTER TABLE `floors` DISABLE KEYS */;
INSERT INTO `floors` VALUES (1,'FIRST FLOOR',1,1,50),(2,'SECOND FLOOR',1,1,50),(3,'THIRD FLOOR',1,2,20),(4,'FOURTH FLOOR',1,2,20),(5,'FIFTH FLOOR',1,2,20),(6,'SIXTH FLOOR',1,2,20),(7,'SEVENTH FLOOR',1,2,20);
/*!40000 ALTER TABLE `floors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parkingblock`
--

DROP TABLE IF EXISTS `parkingblock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parkingblock` (
  `blockid` int(11) NOT NULL AUTO_INCREMENT,
  `blockname` varchar(40) NOT NULL,
  `totalfloors` int(11) NOT NULL,
  `location` varchar(40) NOT NULL,
  `twowheelerslotcount` int(11) NOT NULL,
  `fourwheelerslotcount` int(11) NOT NULL,
  PRIMARY KEY (`blockid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkingblock`
--

LOCK TABLES `parkingblock` WRITE;
/*!40000 ALTER TABLE `parkingblock` DISABLE KEYS */;
INSERT INTO `parkingblock` VALUES (1,'MLCP',7,'opposite to tower builidng',100,100);
/*!40000 ALTER TABLE `parkingblock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parkingslot`
--

DROP TABLE IF EXISTS `parkingslot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parkingslot` (
  `slotid` int(11) NOT NULL,
  `floorid` int(11) NOT NULL,
  `empid` int(11) DEFAULT NULL,
  PRIMARY KEY (`slotid`),
  UNIQUE KEY `empid_UNIQUE` (`empid`),
  UNIQUE KEY `empid` (`empid`),
  UNIQUE KEY `empid_2` (`empid`),
  KEY `fk_floorid` (`floorid`),
  CONSTRAINT `fk_empid4` FOREIGN KEY (`empid`) REFERENCES `employee` (`empid`),
  CONSTRAINT `fk_floorid` FOREIGN KEY (`floorid`) REFERENCES `floors` (`floorid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkingslot`
--

LOCK TABLES `parkingslot` WRITE;
/*!40000 ALTER TABLE `parkingslot` DISABLE KEYS */;
INSERT INTO `parkingslot` VALUES (101,1,NULL),(102,1,NULL),(103,1,NULL),(104,1,NULL),(105,1,NULL),(106,1,NULL),(201,2,NULL),(202,2,NULL),(203,2,NULL),(204,2,NULL),(205,2,NULL),(209,2,NULL),(301,3,NULL),(302,3,NULL),(303,3,NULL),(304,3,NULL),(305,3,NULL),(318,3,NULL),(401,4,NULL),(402,4,NULL),(403,4,NULL),(404,4,NULL),(405,4,NULL),(501,5,NULL),(502,5,NULL),(503,5,NULL),(504,5,NULL),(505,5,NULL),(601,6,NULL),(602,6,NULL),(603,6,NULL),(604,6,NULL),(605,6,NULL);
/*!40000 ALTER TABLE `parkingslot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slotoperation`
--

DROP TABLE IF EXISTS `slotoperation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `slotoperation` (
  `slotoperationid` int(11) NOT NULL AUTO_INCREMENT,
  `empid` int(11) NOT NULL,
  `slotid` int(11) NOT NULL,
  `slotoperationtypeid` int(11) NOT NULL,
  `dateofoperation` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`slotoperationid`),
  KEY `fk_empid2` (`empid`),
  KEY `fk_slotid` (`slotid`),
  KEY `fk_typeid1` (`slotoperationtypeid`),
  CONSTRAINT `fk_empid2` FOREIGN KEY (`empid`) REFERENCES `employee` (`empid`),
  CONSTRAINT `fk_slotid` FOREIGN KEY (`slotid`) REFERENCES `parkingslot` (`slotid`),
  CONSTRAINT `fk_typeid1` FOREIGN KEY (`slotoperationtypeid`) REFERENCES `slotservicetypes` (`servicetypeid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slotoperation`
--

LOCK TABLES `slotoperation` WRITE;
/*!40000 ALTER TABLE `slotoperation` DISABLE KEYS */;
INSERT INTO `slotoperation` VALUES (1,1002,101,1,'2020-02-06 09:56:38'),(2,1000,102,1,'2020-02-06 13:29:39'),(3,1000,102,2,'2020-02-06 13:29:51'),(4,1000,102,1,'2020-02-06 13:29:58'),(5,1002,101,2,'2020-02-06 13:38:01'),(6,1000,102,2,'2020-02-06 13:47:51'),(7,1000,101,1,'2020-02-06 15:50:12'),(8,1000,101,2,'2020-02-06 15:50:19');
/*!40000 ALTER TABLE `slotoperation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slotservicetypes`
--

DROP TABLE IF EXISTS `slotservicetypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `slotservicetypes` (
  `servicetypeid` int(11) NOT NULL AUTO_INCREMENT,
  `servicetype` varchar(20) NOT NULL,
  PRIMARY KEY (`servicetypeid`),
  UNIQUE KEY `servicetype` (`servicetype`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slotservicetypes`
--

LOCK TABLES `slotservicetypes` WRITE;
/*!40000 ALTER TABLE `slotservicetypes` DISABLE KEYS */;
INSERT INTO `slotservicetypes` VALUES (1,'SLOT BOOKING'),(2,'SLOT CANCELLING');
/*!40000 ALTER TABLE `slotservicetypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unavailablebookedslot`
--

DROP TABLE IF EXISTS `unavailablebookedslot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unavailablebookedslot` (
  `operationid` int(11) NOT NULL AUTO_INCREMENT,
  `empid` int(11) NOT NULL,
  `slotid` int(11) NOT NULL,
  `dateandtime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`operationid`),
  KEY `fk_empid3` (`empid`),
  KEY `fk_slotid3` (`slotid`),
  CONSTRAINT `fk_empid3` FOREIGN KEY (`empid`) REFERENCES `employee` (`empid`),
  CONSTRAINT `fk_slotid3` FOREIGN KEY (`slotid`) REFERENCES `parkingslot` (`slotid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unavailablebookedslot`
--

LOCK TABLES `unavailablebookedslot` WRITE;
/*!40000 ALTER TABLE `unavailablebookedslot` DISABLE KEYS */;
INSERT INTO `unavailablebookedslot` VALUES (1,1002,101,'2020-02-06 10:02:18');
/*!40000 ALTER TABLE `unavailablebookedslot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicle` (
  `vehicleid` int(11) NOT NULL AUTO_INCREMENT,
  `vehicleno` varchar(20) NOT NULL,
  `vehiclename` varchar(20) NOT NULL,
  `vehicletypeid` int(11) NOT NULL,
  `vehiclecolour` varchar(30) NOT NULL,
  PRIMARY KEY (`vehicleid`),
  UNIQUE KEY `vehicleno` (`vehicleno`),
  KEY `fk_type` (`vehicletypeid`),
  CONSTRAINT `fk_type` FOREIGN KEY (`vehicletypeid`) REFERENCES `vehicletypes` (`vehicletypeid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle`
--

LOCK TABLES `vehicle` WRITE;
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
INSERT INTO `vehicle` VALUES (1,'TN 05 FN 1230','ROYAL ENFEILD',1,'RED'),(2,'TN 15 CD 7410','RENAULT KWID',2,'BLACK'),(3,'TN 27 MN 1230','PULSAR',1,'BLUE'),(4,'TN 39 AB 7400','INNOVA',2,'WHITE'),(5,'TN 01 AN 1230','APACHE',1,'YELLOW');
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicleparking`
--

DROP TABLE IF EXISTS `vehicleparking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicleparking` (
  `parkingid` int(11) NOT NULL AUTO_INCREMENT,
  `slotid` int(11) NOT NULL,
  `intime` datetime DEFAULT CURRENT_TIMESTAMP,
  `outtime` datetime DEFAULT NULL,
  `vehicleid` int(11) NOT NULL,
  PRIMARY KEY (`parkingid`),
  KEY `fk_vehicleid1` (`vehicleid`),
  KEY `fk_slotid1` (`slotid`),
  CONSTRAINT `fk_slotid1` FOREIGN KEY (`slotid`) REFERENCES `parkingslot` (`slotid`),
  CONSTRAINT `fk_vehicleid1` FOREIGN KEY (`vehicleid`) REFERENCES `vehicle` (`vehicleid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicleparking`
--

LOCK TABLES `vehicleparking` WRITE;
/*!40000 ALTER TABLE `vehicleparking` DISABLE KEYS */;
INSERT INTO `vehicleparking` VALUES (1,101,'2020-02-06 10:01:27','2020-02-06 13:37:30',1);
/*!40000 ALTER TABLE `vehicleparking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicletypes`
--

DROP TABLE IF EXISTS `vehicletypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicletypes` (
  `vehicletypeid` int(11) NOT NULL AUTO_INCREMENT,
  `vehicletype` varchar(40) NOT NULL,
  PRIMARY KEY (`vehicletypeid`),
  UNIQUE KEY `vehicletype` (`vehicletype`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicletypes`
--

LOCK TABLES `vehicletypes` WRITE;
/*!40000 ALTER TABLE `vehicletypes` DISABLE KEYS */;
INSERT INTO `vehicletypes` VALUES (3,'E-VEHICLE'),(2,'FOUR WHEELER'),(1,'TWO WHEELER');
/*!40000 ALTER TABLE `vehicletypes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-06 17:06:38
Your password will expire in 11 days

