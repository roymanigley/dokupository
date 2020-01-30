-- MySQL dump 10.13  Distrib 5.5.62, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: dokupository
-- ------------------------------------------------------
-- Server version       5.5.5-10.2.29-MariaDB

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
-- Table structure for table `application`
--

USE dokupository;

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id_application` bigint(20) NOT NULL,
  `app_type` int(11) DEFAULT NULL,
  `branch` varchar(255) NOT NULL,
  `name_de` varchar(255) NOT NULL,
  `name_fr` varchar(255) NOT NULL,
  `remark_de` longtext DEFAULT NULL,
  `remark_fr` longtext DEFAULT NULL,
  `repo_type` int(11) NOT NULL,
  `repo_url` varchar(255) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `id_user_responsible` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_application`),
  KEY `FKqt00mkfl985ia4unieyy8d7gn` (`id_user_responsible`),
  CONSTRAINT `FKqt00mkfl985ia4unieyy8d7gn` FOREIGN KEY (`id_user_responsible`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_application_versions`
--

DROP TABLE IF EXISTS `application_application_versions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_application_versions` (
  `application_id_application` bigint(20) NOT NULL,
  `application_versions_id_version` bigint(20) NOT NULL,
  UNIQUE KEY `UK_orgvq78vhhqg94vpkn70a67db` (`application_versions_id_version`),
  KEY `FKr2i4k6g06custbupmsak127jv` (`application_id_application`),
  CONSTRAINT `FK1m2qeslt6uln68dae7k3y0oqr` FOREIGN KEY (`application_versions_id_version`) REFERENCES `application_version` (`id_version`),
  CONSTRAINT `FKr2i4k6g06custbupmsak127jv` FOREIGN KEY (`application_id_application`) REFERENCES `application` (`id_application`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_application_versions`
--

LOCK TABLES `application_application_versions` WRITE;
/*!40000 ALTER TABLE `application_application_versions` DISABLE KEYS */;
/*!40000 ALTER TABLE `application_application_versions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_version`
--

DROP TABLE IF EXISTS `application_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_version` (
  `id_version` bigint(20) NOT NULL,
  `release_date` date DEFAULT NULL,
  `remark_de` varchar(255) DEFAULT NULL,
  `remark_fr` varchar(255) DEFAULT NULL,
  `sortierung` int(11) DEFAULT NULL,
  `version_state` int(11) DEFAULT NULL,
  `version` varchar(255) NOT NULL,
  `id_application` bigint(20) NOT NULL,
  `id_user_responsible` bigint(20) DEFAULT NULL,
  `id_user_tester` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_version`),
  KEY `FK422qei50s3hrg0ydot99cpkca` (`id_application`),
  KEY `FKm7bi8xxeg605bn3byuoqc2wyy` (`id_user_responsible`),
  KEY `FKl9c9bevbljtkoosks9bepp3sl` (`id_user_tester`),
  CONSTRAINT `FK422qei50s3hrg0ydot99cpkca` FOREIGN KEY (`id_application`) REFERENCES `application` (`id_application`),
  CONSTRAINT `FKl9c9bevbljtkoosks9bepp3sl` FOREIGN KEY (`id_user_tester`) REFERENCES `user` (`id_user`),
  CONSTRAINT `FKm7bi8xxeg605bn3byuoqc2wyy` FOREIGN KEY (`id_user_responsible`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_version`
--

LOCK TABLES `application_version` WRITE;
/*!40000 ALTER TABLE `application_version` DISABLE KEYS */;
/*!40000 ALTER TABLE `application_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commit`
--

DROP TABLE IF EXISTS `commit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `commit` (
  `id_commit` bigint(20) NOT NULL,
  `hash` varchar(255) NOT NULL,
  `ignore_for_documentation` bit(1) NOT NULL,
  `id_application` bigint(20) NOT NULL,
  `id_documentation_node` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_commit`),
  KEY `FKgw0hx1ssjed7irb9tnmq2exwd` (`id_application`),
  KEY `FKg2c3h7ixtvfifvj6vhv4gx2ve` (`id_documentation_node`),
  CONSTRAINT `FKg2c3h7ixtvfifvj6vhv4gx2ve` FOREIGN KEY (`id_documentation_node`) REFERENCES `documentation_node` (`id_documentation_node`),
  CONSTRAINT `FKgw0hx1ssjed7irb9tnmq2exwd` FOREIGN KEY (`id_application`) REFERENCES `application` (`id_application`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commit`
--

LOCK TABLES `commit` WRITE;
/*!40000 ALTER TABLE `commit` DISABLE KEYS */;
/*!40000 ALTER TABLE `commit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documentation_node`
--

DROP TABLE IF EXISTS `documentation_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documentation_node` (
  `id_documentation_node` bigint(20) NOT NULL,
  `documentation_type` int(11) NOT NULL,
  `sortierung` int(11) DEFAULT NULL,
  `style` varchar(255) DEFAULT NULL,
  `text_de` longtext DEFAULT NULL,
  `text_fr` longtext DEFAULT NULL,
  `id_version_begin` bigint(20) NOT NULL,
  `id_version_end` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_documentation_node`),
  KEY `FKjyxoc35causvpycl8rur95ea4` (`id_version_begin`),
  KEY `FKmktif41g031ojtpks5ir32jt0` (`id_version_end`),
  CONSTRAINT `FKjyxoc35causvpycl8rur95ea4` FOREIGN KEY (`id_version_begin`) REFERENCES `application_version` (`id_version`),
  CONSTRAINT `FKmktif41g031ojtpks5ir32jt0` FOREIGN KEY (`id_version_end`) REFERENCES `application_version` (`id_version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documentation_node`
--


--
-- Table structure for table `documentation_node_commit`
--

DROP TABLE IF EXISTS `documentation_node_commit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documentation_node_commit` (
  `id_documentation_node` bigint(20) NOT NULL,
  `id_commit` bigint(20) NOT NULL,
  KEY `FK8mx3lrll6gyi0rgbkduv53ob5` (`id_commit`),
  KEY `FK47o3t18hqrpmsnnpwj0l9br1p` (`id_documentation_node`),
  CONSTRAINT `FK47o3t18hqrpmsnnpwj0l9br1p` FOREIGN KEY (`id_documentation_node`) REFERENCES `documentation_node` (`id_documentation_node`),
  CONSTRAINT `FK8mx3lrll6gyi0rgbkduv53ob5` FOREIGN KEY (`id_commit`) REFERENCES `commit` (`id_commit`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documentation_node_commit`
--

LOCK TABLES `documentation_node_commit` WRITE;
/*!40000 ALTER TABLE `documentation_node_commit` DISABLE KEYS */;
/*!40000 ALTER TABLE `documentation_node_commit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--


--
-- Table structure for table `print_screen`
--

DROP TABLE IF EXISTS `print_screen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `print_screen` (
  `id_print_screen` bigint(20) NOT NULL,
  `data` longblob NOT NULL,
  `footnote` longtext DEFAULT NULL,
  `height` double DEFAULT NULL,
  `language` int(11) NOT NULL,
  `width` double DEFAULT NULL,
  `id_documentation_node` bigint(20) NOT NULL,
  PRIMARY KEY (`id_print_screen`),
  KEY `FKr5p8cqqk0kx8qdu0mansbei` (`id_documentation_node`),
  CONSTRAINT `FKr5p8cqqk0kx8qdu0mansbei` FOREIGN KEY (`id_documentation_node`) REFERENCES `documentation_node` (`id_documentation_node`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `print_screen`
--

LOCK TABLES `print_screen` WRITE;
/*!40000 ALTER TABLE `print_screen` DISABLE KEYS */;
/*!40000 ALTER TABLE `print_screen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id_user` bigint(20) NOT NULL,
  `acronym` varchar(255) NOT NULL,
  `acvive` bit(1) DEFAULT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--


--
-- Table structure for table `word_template`
--

DROP TABLE IF EXISTS `word_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `word_template` (
  `id_word_template` bigint(20) NOT NULL,
  `bytes` longblob NOT NULL,
  `documentation_type` int(11) NOT NULL,
  `language` int(11) NOT NULL,
  `name` longtext NOT NULL,
  PRIMARY KEY (`id_word_template`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `word_template`
--

LOCK TABLES `word_template` WRITE;
/*!40000 ALTER TABLE `word_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `word_template` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-13  9:54:43