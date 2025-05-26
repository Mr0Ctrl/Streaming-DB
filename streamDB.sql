-- MySQL dump 10.13  Distrib 8.0.42, for Linux (x86_64)
--
-- Host: localhost    Database: streaming_platform
-- ------------------------------------------------------
-- Server version	8.0.42-0ubuntu0.24.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Actor`
--

DROP TABLE IF EXISTS `Actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Actor` (
  `ActorID` varchar(45) NOT NULL,
  `FirstName` varchar(100) NOT NULL,
  `LastName` varchar(100) NOT NULL,
  `DateOfBirth` date DEFAULT NULL,
  `Biography` text,
  PRIMARY KEY (`ActorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Actor`
--

LOCK TABLES `Actor` WRITE;
/*!40000 ALTER TABLE `Actor` DISABLE KEYS */;
INSERT INTO `Actor` VALUES ('ACT001','Emma','Stone','1988-11-06','Academy Award-winning actress.'),('ACT002','Michael','Fassbender','1977-04-02','Known for dramatic and sci-fi roles.'),('ACT003','Lupita','Nyong\'o','1983-03-01','Award-winning actress and activist.'),('ACT004','Benedict','Cumberbatch','1976-07-19','British actor known for Sherlock and Doctor Strange.'),('ACT005','Zendaya','Coleman','1996-09-01','Singer and actress starring in fantasy and drama films.');
/*!40000 ALTER TABLE `Actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Commented`
--

DROP TABLE IF EXISTS `Commented`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Commented` (
  `CommentID` varchar(45) NOT NULL,
  `UserID` varchar(45) DEFAULT NULL,
  `VideoID` varchar(45) DEFAULT NULL,
  `CommentedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `Content` text,
  `ParentCommentID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`CommentID`),
  KEY `UserID` (`UserID`),
  KEY `VideoID` (`VideoID`),
  KEY `ParentCommentID` (`ParentCommentID`),
  CONSTRAINT `Commented_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Commented_ibfk_2` FOREIGN KEY (`VideoID`) REFERENCES `Video` (`VideoID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Commented_ibfk_3` FOREIGN KEY (`ParentCommentID`) REFERENCES `Commented` (`CommentID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Commented_chk_1` CHECK ((char_length(`Content`) <= 1000))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Commented`
--

LOCK TABLES `Commented` WRITE;
/*!40000 ALTER TABLE `Commented` DISABLE KEYS */;
INSERT INTO `Commented` VALUES ('39db3462-39fc-48c9-9097-6511f1f76edc','USR000','VID001','2025-05-26 15:54:51','Helllo word',NULL);
/*!40000 ALTER TABLE `Commented` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Liked`
--

DROP TABLE IF EXISTS `Liked`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Liked` (
  `LikeID` varchar(45) NOT NULL,
  `UserID` varchar(45) DEFAULT NULL,
  `VideoID` varchar(45) DEFAULT NULL,
  `LikedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`LikeID`),
  UNIQUE KEY `UserID` (`UserID`,`VideoID`),
  KEY `VideoID` (`VideoID`),
  CONSTRAINT `Liked_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Liked_ibfk_2` FOREIGN KEY (`VideoID`) REFERENCES `Video` (`VideoID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Liked`
--

LOCK TABLES `Liked` WRITE;
/*!40000 ALTER TABLE `Liked` DISABLE KEYS */;
INSERT INTO `Liked` VALUES ('77b7ca40-8cb2-4233-b264-d741a1ea3374','USR000','VID001','2025-05-26 15:54:55');
/*!40000 ALTER TABLE `Liked` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PlaybackHistory`
--

DROP TABLE IF EXISTS `PlaybackHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PlaybackHistory` (
  `HistoryID` varchar(45) NOT NULL,
  `UserID` varchar(45) DEFAULT NULL,
  `VideoID` varchar(45) DEFAULT NULL,
  `WatchedAt` datetime NOT NULL,
  `WatchedDuration` int DEFAULT NULL,
  `IsCompleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`HistoryID`),
  KEY `UserID` (`UserID`),
  KEY `VideoID` (`VideoID`),
  CONSTRAINT `PlaybackHistory_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `PlaybackHistory_ibfk_2` FOREIGN KEY (`VideoID`) REFERENCES `Video` (`VideoID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `PlaybackHistory_chk_1` CHECK ((`WatchedDuration` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PlaybackHistory`
--

LOCK TABLES `PlaybackHistory` WRITE;
/*!40000 ALTER TABLE `PlaybackHistory` DISABLE KEYS */;
INSERT INTO `PlaybackHistory` VALUES ('1dc374e2-84ff-4792-a571-be9d4120f59f','USR000','VID001','2025-05-26 15:54:33',0,0),('622d06d0-e1ff-4494-88ce-193755c4caca','USR000','VID007','2025-05-26 15:54:38',0,0);
/*!40000 ALTER TABLE `PlaybackHistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Playlist`
--

DROP TABLE IF EXISTS `Playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Playlist` (
  `PlaylistID` varchar(45) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `UserID` varchar(45) DEFAULT NULL,
  `Description` text,
  `CreatedOn` date DEFAULT NULL,
  `IsPublic` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`PlaylistID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `Playlist_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Playlist`
--

LOCK TABLES `Playlist` WRITE;
/*!40000 ALTER TABLE `Playlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `Playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PlaylistReferenceVideo`
--

DROP TABLE IF EXISTS `PlaylistReferenceVideo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PlaylistReferenceVideo` (
  `PlaylistID` varchar(45) NOT NULL,
  `VideoID` varchar(45) NOT NULL,
  `VideoOrder` int DEFAULT NULL,
  PRIMARY KEY (`PlaylistID`,`VideoID`),
  KEY `VideoID` (`VideoID`),
  CONSTRAINT `PlaylistReferenceVideo_ibfk_1` FOREIGN KEY (`PlaylistID`) REFERENCES `Playlist` (`PlaylistID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `PlaylistReferenceVideo_ibfk_2` FOREIGN KEY (`VideoID`) REFERENCES `Video` (`VideoID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `PlaylistReferenceVideo_chk_1` CHECK ((`VideoOrder` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PlaylistReferenceVideo`
--

LOCK TABLES `PlaylistReferenceVideo` WRITE;
/*!40000 ALTER TABLE `PlaylistReferenceVideo` DISABLE KEYS */;
/*!40000 ALTER TABLE `PlaylistReferenceVideo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Subscription`
--

DROP TABLE IF EXISTS `Subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Subscription` (
  `SubscriptionID` varchar(45) NOT NULL,
  `UserID` varchar(45) DEFAULT NULL,
  `StartDate` date NOT NULL,
  `EndDate` date DEFAULT NULL,
  `SubscriptionType` enum('Basic','Standard','Premium') NOT NULL,
  PRIMARY KEY (`SubscriptionID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `Subscription_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Subscription`
--

LOCK TABLES `Subscription` WRITE;
/*!40000 ALTER TABLE `Subscription` DISABLE KEYS */;
INSERT INTO `Subscription` VALUES ('SUB001','USR001','2025-01-01','2025-12-31','Premium'),('SUB002','USR002','2025-02-15',NULL,'Standard'),('SUB003','USR003','2025-03-10',NULL,'Basic');
/*!40000 ALTER TABLE `Subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `UserID` varchar(45) NOT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Username` varchar(50) DEFAULT NULL,
  `Password` varchar(100) NOT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Email` (`Email`),
  UNIQUE KEY `Username` (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES ('USR000','root@streaming.com','root','root'),('USR001','alice@example.com','alice_wonder','passAlice123'),('USR002','bob@example.com','bobby99','passBob456'),('USR003','charlie@example.com','charlie_the_coder','passCharlie789');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Video`
--

DROP TABLE IF EXISTS `Video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Video` (
  `VideoID` varchar(45) NOT NULL,
  `Title` varchar(100) NOT NULL,
  `Genre` varchar(50) DEFAULT NULL,
  `Description` text,
  `Duration` int DEFAULT NULL,
  PRIMARY KEY (`VideoID`),
  CONSTRAINT `Video_chk_1` CHECK ((`Duration` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Video`
--

LOCK TABLES `Video` WRITE;
/*!40000 ALTER TABLE `Video` DISABLE KEYS */;
INSERT INTO `Video` VALUES ('VID001','The Last Horizon','Sci-Fi','A crew explores the edge of the galaxy.',125),('VID002','Whispers in the Dark','Thriller','A detective uncovers a mysterious cult.',98),('VID003','Echoes of the Past','Drama','A touching story about family and memory.',112),('VID004','Rise of the Phoenix','Action','A former soldier rises against injustice.',130),('VID005','Dreamscape','Fantasy','A girl discovers a magical world through dreams.',108),('VID006','The Algorithm','Sci-Fi','An AI system gains sentience and challenges society.',117),('VID007','Crimson Tide','War','A submarine captain faces mutiny in wartime.',110),('VID008','Shadows of Time','Mystery','Time travelers fight to correct history.',105),('VID009','The Painted Sky','Romance','Artists fall in love in Tuscany.',95),('VID010','Code Black','Tech Thriller','Hackers uncover a secret government project.',102);
/*!40000 ALTER TABLE `Video` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Video_Actor`
--

DROP TABLE IF EXISTS `Video_Actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Video_Actor` (
  `VideoID` varchar(45) NOT NULL,
  `ActorID` varchar(45) NOT NULL,
  `RoleName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`VideoID`,`ActorID`),
  KEY `ActorID` (`ActorID`),
  CONSTRAINT `Video_Actor_ibfk_1` FOREIGN KEY (`VideoID`) REFERENCES `Video` (`VideoID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Video_Actor_ibfk_2` FOREIGN KEY (`ActorID`) REFERENCES `Actor` (`ActorID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Video_Actor`
--

LOCK TABLES `Video_Actor` WRITE;
/*!40000 ALTER TABLE `Video_Actor` DISABLE KEYS */;
INSERT INTO `Video_Actor` VALUES ('VID001','ACT002','Captain Raine'),('VID001','ACT003','Dr. Elara'),('VID002','ACT001','Detective Rose'),('VID004','ACT004','Commander Drake'),('VID005','ACT005','Lyra'),('VID006','ACT004','Professor Vale'),('VID007','ACT002','Captain Walsh'),('VID009','ACT001','Sophia'),('VID009','ACT005','Isabella'),('VID010','ACT003','Agent Vega');
/*!40000 ALTER TABLE `Video_Actor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-26 15:56:21
