CREATE DATABASE  IF NOT EXISTS `peek` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `peek`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: roundhouse.proxy.rlwy.net    Database: peek
-- ------------------------------------------------------
-- Server version	8.2.0

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
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) DEFAULT NULL,
  `contact_email` varchar(255) DEFAULT NULL,
  `contact_person` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `body` varchar(1000) DEFAULT NULL,
  `date_created` datetime(6) DEFAULT NULL,
  `posted_on_id` bigint DEFAULT NULL,
  `poster_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8yi8r0b4i12podwg2hr5fd6q8` (`poster_id`),
  KEY `FKrb94sn2qs1tbl9vc3kef8rdje` (`posted_on_id`),
  CONSTRAINT `FK8yi8r0b4i12podwg2hr5fd6q8` FOREIGN KEY (`poster_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKrb94sn2qs1tbl9vc3kef8rdje` FOREIGN KEY (`posted_on_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `expert_profile`
--

DROP TABLE IF EXISTS `expert_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expert_profile` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `preferred_client_type` varchar(255) DEFAULT NULL,
  `preferred_project_type` varchar(255) DEFAULT NULL,
  `sector` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6oirtpi7rp4545alfp5gxlv8l` (`user_id`),
  CONSTRAINT `FKo8a3q6ow06a3pfi3ibupqs8xq` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `expert_profile_project_roles`
--

DROP TABLE IF EXISTS `expert_profile_project_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expert_profile_project_roles` (
  `expert_profile_id` bigint NOT NULL,
  `project_roles_id` bigint NOT NULL,
  PRIMARY KEY (`expert_profile_id`,`project_roles_id`),
  KEY `FK62d6ji1i8f1ajctfv7xyat2yx` (`project_roles_id`),
  CONSTRAINT `FK2nexwdxr80qgwtr445r5visne` FOREIGN KEY (`expert_profile_id`) REFERENCES `expert_profile` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK62d6ji1i8f1ajctfv7xyat2yx` FOREIGN KEY (`project_roles_id`) REFERENCES `project_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invitation`
--

DROP TABLE IF EXISTS `invitation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invitation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `max_users` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `times_used` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4v0yivl00fvmgx0b9h4rt87pg` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=296 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes` (
  `projects_id` bigint NOT NULL,
  `users_id` bigint NOT NULL,
  PRIMARY KEY (`projects_id`,`users_id`),
  KEY `FK6fduwr215oj8apgs09bwut3ld` (`users_id`),
  CONSTRAINT `FK6fduwr215oj8apgs09bwut3ld` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKndwb3eh7k90l34vgpb9dvmgjv` FOREIGN KEY (`projects_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `match_history`
--

DROP TABLE IF EXISTS `match_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `match_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_time_matched` datetime(6) DEFAULT NULL,
  `admin_user_id` bigint DEFAULT NULL,
  `project_id` bigint DEFAULT NULL,
  `admin_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdso4a1bb2ve1mj3tqmtvlsp1l` (`admin_user_id`),
  KEY `FK7hka8x8ebgss2796a6iqy0ap6` (`admin_id`),
  KEY `FK2kpslk0f5vrmyt3gkyi50eycs` (`project_id`),
  CONSTRAINT `FK2kpslk0f5vrmyt3gkyi50eycs` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK7hka8x8ebgss2796a6iqy0ap6` FOREIGN KEY (`admin_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKdso4a1bb2ve1mj3tqmtvlsp1l` FOREIGN KEY (`admin_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `matched_experts`
--

DROP TABLE IF EXISTS `matched_experts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matched_experts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `matching_score` double DEFAULT NULL,
  `match_history_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `expert_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1vq6n907s9rimgju9n568t675` (`user_id`),
  KEY `FKcs9amkmx3nqfet2who5g4kwvr` (`expert_id`),
  KEY `FKfgbyycvse9e7gehqfcsd5lo2y` (`match_history_id`),
  CONSTRAINT `FK1vq6n907s9rimgju9n568t675` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKcs9amkmx3nqfet2who5g4kwvr` FOREIGN KEY (`expert_id`) REFERENCES `expert_profile` (`id`),
  CONSTRAINT `FKfgbyycvse9e7gehqfcsd5lo2y` FOREIGN KEY (`match_history_id`) REFERENCES `match_history` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action_required` bit(1) NOT NULL,
  `date_created` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_read` bit(1) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `date_created` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `status` int NOT NULL,
  `image_data` mediumblob,
  `image` varchar(255) DEFAULT NULL,
  `client_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8nw995uro0115f1go0dmrtn2d` (`client_id`),
  CONSTRAINT `FK8nw995uro0115f1go0dmrtn2d` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_candidates`
--

DROP TABLE IF EXISTS `project_candidates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_candidates` (
  `project_id` bigint NOT NULL,
  `candidates_id` bigint NOT NULL,
  PRIMARY KEY (`project_id`,`candidates_id`),
  KEY `FKqb1xfnip5rlebdy1hyt5e8ifl` (`candidates_id`),
  CONSTRAINT `FK5rhdxc6gfhxmi1modjnovcg6j` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `FKavnpgx0jb3w4p5tt7sijkpw32` FOREIGN KEY (`candidates_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_project_roles`
--

DROP TABLE IF EXISTS `project_project_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_project_roles` (
  `project_id` bigint NOT NULL,
  `project_roles_id` bigint NOT NULL,
  PRIMARY KEY (`project_id`,`project_roles_id`),
  KEY `FKs5d4imdy0cgq94fr6kvfy6diw` (`project_roles_id`),
  CONSTRAINT `FKdxopqvc2itqj9npcy2sjusikw` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKs5d4imdy0cgq94fr6kvfy6diw` FOREIGN KEY (`project_roles_id`) REFERENCES `project_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_role`
--

DROP TABLE IF EXISTS `project_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `qualifier_value` int NOT NULL,
  `sector_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb0f88whqsweabw9kv5wxr8h5i` (`sector_id`),
  CONSTRAINT `FKb0f88whqsweabw9kv5wxr8h5i` FOREIGN KEY (`sector_id`) REFERENCES `sector` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_role_roles`
--

DROP TABLE IF EXISTS `project_role_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_role_roles` (
  `project_role_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`project_role_id`,`role_id`),
  KEY `FK5k60r781ibvah7p4q21o999xd` (`role_id`),
  CONSTRAINT `FK5k60r781ibvah7p4q21o999xd` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKb46uh9te2q37be8ovib8l8x20` FOREIGN KEY (`project_role_id`) REFERENCES `project_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_role_tools`
--

DROP TABLE IF EXISTS `project_role_tools`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_role_tools` (
  `project_role_id` bigint NOT NULL,
  `tool_id` bigint NOT NULL,
  PRIMARY KEY (`project_role_id`,`tool_id`),
  KEY `FKbb6k8bqv8pgaxvfilijyh6kka` (`tool_id`),
  CONSTRAINT `FK35mx6eh58k0oot2hxe5p0b4u1` FOREIGN KEY (`project_role_id`) REFERENCES `project_role` (`id`),
  CONSTRAINT `FKbb6k8bqv8pgaxvfilijyh6kka` FOREIGN KEY (`tool_id`) REFERENCES `tool` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reset_password`
--

DROP TABLE IF EXISTS `reset_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reset_password` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1ydkm8ypee8uwhvjyfvcny6pl` (`user_id`),
  CONSTRAINT `FK1ydkm8ypee8uwhvjyfvcny6pl` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  `in_list` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `role_name_UNIQUE` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sector`
--

DROP TABLE IF EXISTS `sector`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sector` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sector_name` varchar(255) DEFAULT NULL,
  `in_list` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `sector_name_UNIQUE` (`sector_name`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tool`
--

DROP TABLE IF EXISTS `tool`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tool` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tool_name` varchar(255) DEFAULT NULL,
  `in_list` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `tool_name_UNIQUE` (`tool_name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `date_created` datetime(6) NOT NULL,
  `invitationurl` varchar(255) DEFAULT NULL,
  `user_profile_id` bigint DEFAULT NULL,
  `reset_password_id` bigint DEFAULT NULL,
  `invitation_id` bigint DEFAULT NULL,
  `expert_profile_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `UK_2ek1mbe9ojg3q7p83vusnrj15` (`user_profile_id`),
  UNIQUE KEY `UK_6sk3vapaxopdqte3fvyhcxodb` (`expert_profile_id`),
  KEY `FKbfbxh38a6bwtf9apfuqa2loan` (`reset_password_id`),
  KEY `FK8gsq57e01mugl8x1g9lkk4g4x` (`invitation_id`),
  CONSTRAINT `FK8gsq57e01mugl8x1g9lkk4g4x` FOREIGN KEY (`invitation_id`) REFERENCES `invitation` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `FKbfbxh38a6bwtf9apfuqa2loan` FOREIGN KEY (`reset_password_id`) REFERENCES `reset_password` (`id`),
  CONSTRAINT `FKfrx3tq5ur97ttt6b30m3hj5pw` FOREIGN KEY (`expert_profile_id`) REFERENCES `expert_profile` (`id`) ON DELETE SET NULL,
  CONSTRAINT `FKjjes1f6tjhqns02054ou51m00` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_notification`
--

DROP TABLE IF EXISTS `user_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_notification` (
  `user_id` bigint NOT NULL,
  `notification_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`notification_id`),
  KEY `FKi5naecliicmigrk01qx5me5sp` (`notification_id`),
  CONSTRAINT `FKi5naecliicmigrk01qx5me5sp` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`id`),
  CONSTRAINT `FKnbuq84cli119n9cdakdw0kv5v` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_profile` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addition` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `house_number` varchar(255) DEFAULT NULL,
  `image_data` mediumblob,
  `last_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `residence` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `kvk_number` varchar(255) DEFAULT NULL,
  `linked_in_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-10  1:39:30

USE peek;
-- Insert into 'user_profile' table
INSERT INTO user_profile (first_name, addition, last_name) VALUES ("Marius", " ", "Peek");

-- Retrieve the last generated ID from 'user_profile' table
SET @last_user_profile_id = LAST_INSERT_ID();

-- Insert into 'user' table using the retrieved ID
INSERT INTO user (email, password, role, date_created, user_profile_id)
VALUES ("marius.peek@gmail.com", "LPJNul+wow4m6DsqxbninhsWHlwfp0JecwQzYpOLmCQ=", "admin", NOW(), @last_user_profile_id);

