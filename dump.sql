-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: gymweb
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `end_time` datetime(6) NOT NULL,
  `start_time` datetime(6) NOT NULL,
  `subscription_time` datetime(6) NOT NULL,
  `room_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq83pan5xy2a6rn0qsl9bckqai` (`room_id`),
  KEY `FKkgseyy7t56x7lkjgu3wah5s3t` (`user_id`),
  CONSTRAINT `FKkgseyy7t56x7lkjgu3wah5s3t` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKq83pan5xy2a6rn0qsl9bckqai` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (1,'2023-07-15 16:00:00.000000','2023-07-15 08:00:00.000000','2023-07-14 22:35:52.441809',5,5),(2,'2023-07-15 22:00:00.000000','2023-07-15 12:00:00.000000','2023-07-14 22:36:41.405122',5,6),(3,'2023-07-15 12:30:00.000000','2023-07-15 09:00:00.000000','2023-07-14 22:38:05.233552',5,3),(4,'2023-07-16 09:45:00.000000','2023-07-16 08:00:00.000000','2023-07-14 22:46:52.725721',5,3),(5,'2023-07-22 15:30:00.000000','2023-07-22 08:00:00.000000','2023-07-14 23:25:22.841958',5,5);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_time` datetime(6) NOT NULL,
  `text` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,'2023-07-14 22:49:49.398939','Il mio personal trainer è uno dei migliore nel settore'),(2,'2023-07-14 22:50:08.795256','Ottimo servizio'),(3,'2023-07-14 23:10:22.551927','Ottimo personal trainer'),(4,'2023-07-14 23:13:54.265028','Il mio voto è 10/10\n');
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback_user`
--

DROP TABLE IF EXISTS `feedback_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback_user` (
  `feedback_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  KEY `FK8o57sm7gquaoh0mvfyw22spbs` (`user_id`),
  KEY `FKpvwr7kq6908j4pf4orbfdx4q7` (`feedback_id`),
  CONSTRAINT `FK8o57sm7gquaoh0mvfyw22spbs` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKpvwr7kq6908j4pf4orbfdx4q7` FOREIGN KEY (`feedback_id`) REFERENCES `feedback` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback_user`
--

LOCK TABLES `feedback_user` WRITE;
/*!40000 ALTER TABLE `feedback_user` DISABLE KEYS */;
INSERT INTO `feedback_user` VALUES (3,4),(3,5),(4,3),(4,5);
/*!40000 ALTER TABLE `feedback_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `size` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,_binary '','Calisthenics',10),(3,_binary '','Crossfit',20),(4,_binary '','Weight room',150),(5,_binary '','Crossfit 2',15);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created` datetime(6) NOT NULL,
  `email` varchar(255) NOT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `privacy` bit(1) NOT NULL,
  `role` varchar(255) NOT NULL,
  `subscription_end` date DEFAULT NULL,
  `subscription_start` date DEFAULT NULL,
  `surname` varchar(255) NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `pt_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  KEY `FKosd0n9pcysl9l3v9utfct651e` (`pt_id`),
  CONSTRAINT `FKosd0n9pcysl9l3v9utfct651e` FOREIGN KEY (`pt_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'2023-07-13 21:59:22.370974','admin@gmail.com',_binary '','Marco','$2a$10$ct3MEsMqPJh11Ps1PitY3enDu0Ey3s0IH3f9Zr2Sj6GaMKjFx55K2',_binary '\0','ADMIN',NULL,NULL,'Minaudo','456b2a85-5669-4fbc-9009-34622a3885b5',NULL),(3,'2023-07-14 13:38:58.260240','customer1@gmail.com',_binary '','Francesco','$2a$10$7oujStzjMlQBmpMTCezv6.cpfVzaiPA73fpADfrHta1zdH7h45chW',_binary '','CUSTOMER','2024-01-01','2023-07-14','Peluso','62d38b51-4565-40a2-9123-1824f8d0ac15',5),(4,'2023-07-14 13:39:27.006855','customer2@gmail.com',_binary '','Alessio','$2a$10$eltQGErYbNIwOnpsaqqDOugyA7WDlnToT3kflP4x9daFBjMVN6UFu',_binary '','CUSTOMER','2024-01-01','2023-07-14','Milicia','0ab9a219-7029-407b-a9d3-6e204e156a71',5),(5,'2023-07-14 13:40:06.038885','pt1@gmail.com',_binary '','Massimo','$2a$10$ylSqeqNJqXFj1FFTYwagtO3JXuhNVWrGmcXdOXFJaNJczdlnCtY2u',_binary '\0','PT','2024-07-14','2023-07-14','Portis','4227a8c4-72e5-420a-a761-ed6785bf09ee',NULL),(6,'2023-07-14 13:40:50.537183','pt2@gmail.com',_binary '','Lorentz','$2a$10$TWx3Vw6bW2v./HzIuj0mTumz/xlwsqNE1bpycfJla0NRB2p2pAB7C',_binary '\0','PT','2024-07-14','2023-07-14','Belli','a22dd999-4a20-44a3-ac49-727f1d6dd4bb',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_body_details`
--

DROP TABLE IF EXISTS `user_body_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_body_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bodyfat` int NOT NULL,
  `chest` int NOT NULL,
  `height` int NOT NULL,
  `shoulders` int NOT NULL,
  `upload_time` datetime(6) NOT NULL,
  `upper_arm` int NOT NULL,
  `waist` int NOT NULL,
  `weight` int NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2vktpmgoxwd2rgajax62lpa50` (`user_id`),
  CONSTRAINT `FK2vktpmgoxwd2rgajax62lpa50` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_body_details`
--

LOCK TABLES `user_body_details` WRITE;
/*!40000 ALTER TABLE `user_body_details` DISABLE KEYS */;
INSERT INTO `user_body_details` VALUES (1,20,120,190,100,'2023-07-14 22:29:44.067055',37,90,90,3),(2,18,120,190,98,'2023-07-14 22:30:07.321797',36,89,87,3),(3,15,120,190,98,'2023-07-14 22:30:32.907149',34,89,85,3),(4,15,118,190,95,'2023-07-14 22:30:55.589594',34,85,83,3),(5,13,118,190,95,'2023-07-14 22:31:08.876846',34,83,81,3),(6,11,118,190,95,'2023-07-14 22:31:25.767447',33,82,79,3),(7,9,118,190,95,'2023-07-14 22:31:42.035906',33,81,77,3),(8,9,118,190,95,'2023-07-14 22:34:35.429971',32,81,76,3);
/*!40000 ALTER TABLE `user_body_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workout_plan`
--

DROP TABLE IF EXISTS `workout_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workout_plan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `path` varchar(255) NOT NULL,
  `upload_time` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workout_plan`
--

LOCK TABLES `workout_plan` WRITE;
/*!40000 ALTER TABLE `workout_plan` DISABLE KEYS */;
INSERT INTO `workout_plan` VALUES (1,'62d38b51-4565-40a2-9123-1824f8d0ac15\\d43a1170-2577-4650-a334-bb335ff302df.pdf','2023-07-14 22:48:52.374776'),(2,'62d38b51-4565-40a2-9123-1824f8d0ac15\\bdd348e3-2620-4bfc-b893-e86c905a1fcf.pdf','2023-07-14 23:26:14.078432');
/*!40000 ALTER TABLE `workout_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workout_plan_user`
--

DROP TABLE IF EXISTS `workout_plan_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workout_plan_user` (
  `workout_plan_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  KEY `FKmsuts90834uswgm5lxgfgb2xa` (`user_id`),
  KEY `FKi58qfoex8ponh4k3wkk3krxa3` (`workout_plan_id`),
  CONSTRAINT `FKi58qfoex8ponh4k3wkk3krxa3` FOREIGN KEY (`workout_plan_id`) REFERENCES `workout_plan` (`id`),
  CONSTRAINT `FKmsuts90834uswgm5lxgfgb2xa` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workout_plan_user`
--

LOCK TABLES `workout_plan_user` WRITE;
/*!40000 ALTER TABLE `workout_plan_user` DISABLE KEYS */;
INSERT INTO `workout_plan_user` VALUES (1,3),(1,5),(2,3),(2,5);
/*!40000 ALTER TABLE `workout_plan_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-15 11:58:31
