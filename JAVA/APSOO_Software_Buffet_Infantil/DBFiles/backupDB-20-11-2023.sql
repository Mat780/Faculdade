CREATE DATABASE  IF NOT EXISTS `dbname` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `dbname`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dbname
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `bolo`
--

DROP TABLE IF EXISTS `bolo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bolo` (
  `id` int NOT NULL,
  `descricao` varchar(200) NOT NULL,
  `valorUnitario` double NOT NULL,
  `peso` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bolo`
--

LOCK TABLES `bolo` WRITE;
/*!40000 ALTER TABLE `bolo` DISABLE KEYS */;
INSERT INTO `bolo` VALUES (1,'Bolo de Brigadeiro',53,1),(2,'Bolo de Prestigio',53,1),(3,'Bolo de Abacaxi',53,1),(4,'Bolo de Pessego',53,1),(5,'Bolo Dois Amores',53,1),(6,'Bolo Viuva Negra',53,1);
/*!40000 ALTER TABLE `bolo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `cpf` varchar(11) NOT NULL,
  `nome` varchar(200) NOT NULL,
  `RG` varchar(20) DEFAULT NULL,
  `endereco` varchar(100) NOT NULL,
  `CEP` varchar(20) NOT NULL,
  `celular` varchar(13) NOT NULL,
  `telefoneResidencial` varchar(13) DEFAULT NULL,
  `telefoneComercial` varchar(13) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES ('03467579167','Guilherme',NULL,'Rua JAGUARÃO','79010-160','67 99154-3111',NULL,NULL,'gui@gmail.com'),('07360676126','M',NULL,'a','12345-123','67 99287-5382',NULL,NULL,'a@a.com'),('08010842109','Pedro Silvq','123456452','Rua Aguas Claras','45667-898','12 16156-5464','45 5454-5454','67 99988-1718','pedro@gmail.com'),('88149699066','Joao da Silva','267584288','Rua Aguas Claras','12345-678','67 99988-1235','67 9999-4444','67 88888-4545','jonasgames123@gmail.com');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doce`
--

DROP TABLE IF EXISTS `doce`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doce` (
  `id` int NOT NULL,
  `descricao` varchar(200) NOT NULL,
  `valorUnitario` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doce`
--

LOCK TABLES `doce` WRITE;
/*!40000 ALTER TABLE `doce` DISABLE KEYS */;
INSERT INTO `doce` VALUES (1,'Brigadeiro',1.2),(2,'Brigadeiro Branco',1.2),(3,'Brigadeiro Colorido',1.2),(4,'Beijinho de Coco',1.2),(5,'Casadinho',1.2),(6,'Cajuzinho',1.2);
/*!40000 ALTER TABLE `doce` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doceselecionado`
--

DROP TABLE IF EXISTS `doceselecionado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doceselecionado` (
  `quantidade` int NOT NULL,
  `fk_idDoce` int NOT NULL,
  `fk_idOrcamentoBuffetCompleto` int NOT NULL,
  PRIMARY KEY (`fk_idOrcamentoBuffetCompleto`,`fk_idDoce`),
  KEY `fk_idOrcamentoBuffetCompleto_idx` (`fk_idOrcamentoBuffetCompleto`),
  KEY `fk_idDoce_idx` (`fk_idDoce`),
  CONSTRAINT `fk_idDoce` FOREIGN KEY (`fk_idDoce`) REFERENCES `doce` (`id`),
  CONSTRAINT `fk_idOrcamentoBuffetCompleto` FOREIGN KEY (`fk_idOrcamentoBuffetCompleto`) REFERENCES `orcamentobuffetcompleto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doceselecionado`
--

LOCK TABLES `doceselecionado` WRITE;
/*!40000 ALTER TABLE `doceselecionado` DISABLE KEYS */;
INSERT INTO `doceselecionado` VALUES (50,1,598419440),(50,2,598419440),(50,3,598419440),(60,1,611690111),(60,2,611690111),(60,3,611690111),(111,1,668338314),(111,2,668338314),(111,3,668338314),(50,1,730981915),(50,2,730981915),(50,3,730981915),(60,1,863633081),(60,2,863633081),(60,3,863633081),(50,1,873911542),(50,2,873911542),(50,6,873911542),(50,1,1107957184),(50,2,1107957184),(50,3,1107957184),(50,2,1940083541),(50,3,1940083541),(50,4,1940083541),(50,1,2048215508),(50,2,2048215508),(50,3,2048215508);
/*!40000 ALTER TABLE `doceselecionado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orcamentobuffetcompleto`
--

DROP TABLE IF EXISTS `orcamentobuffetcompleto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orcamentobuffetcompleto` (
  `id` int NOT NULL,
  `numeroDeConvidados` int NOT NULL,
  `numeroDeColaboradores` int NOT NULL,
  `horaDeInicio` varchar(8) NOT NULL,
  `data` varchar(10) NOT NULL,
  `teraCerveja` tinyint NOT NULL,
  `fk_cpfCliente` varchar(11) NOT NULL,
  `fk_idBolo` int NOT NULL,
  `fk_idPagamento` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cpfCliente_idx` (`fk_cpfCliente`),
  KEY `fk_idBolo` (`fk_idBolo`),
  KEY `fk_idPagamento` (`fk_idPagamento`),
  CONSTRAINT `fk_cpfCliente` FOREIGN KEY (`fk_cpfCliente`) REFERENCES `cliente` (`cpf`),
  CONSTRAINT `fk_idBolo` FOREIGN KEY (`fk_idBolo`) REFERENCES `bolo` (`id`),
  CONSTRAINT `fk_idPagamento` FOREIGN KEY (`fk_idPagamento`) REFERENCES `pagamento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orcamentobuffetcompleto`
--

LOCK TABLES `orcamentobuffetcompleto` WRITE;
/*!40000 ALTER TABLE `orcamentobuffetcompleto` DISABLE KEYS */;
INSERT INTO `orcamentobuffetcompleto` VALUES (598419440,50,50,'13:30','13/11/2023',0,'08010842109',2,-1),(611690111,60,60,'12:12','15/05/2024',0,'07360676126',2,-1),(668338314,111,111,'11:11','12/03/2024',0,'07360676126',1,-1),(730981915,50,50,'13:13','11/10/2024',0,'08010842109',1,-1),(863633081,60,60,'15:15','15/02/2024',0,'07360676126',4,-1),(873911542,50,50,'15:15','12/05/2024',0,'08010842109',2,-1),(1107957184,50,50,'13:30','13/11/2023',0,'08010842109',2,-1),(1940083541,50,50,'13:56','12/11/2023',0,'08010842109',2,-1),(2048215508,50,50,'12:64','30/10/2024',0,'03467579167',1,-1);
/*!40000 ALTER TABLE `orcamentobuffetcompleto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orcamentolocacaodeespaco`
--

DROP TABLE IF EXISTS `orcamentolocacaodeespaco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orcamentolocacaodeespaco` (
  `id` int NOT NULL,
  `numeroDeConvidados` int NOT NULL,
  `numeroDeColaboradores` int NOT NULL,
  `horaDeInicio` varchar(8) NOT NULL,
  `data` varchar(10) NOT NULL,
  `fk_cpf` varchar(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cpfCliente_idx` (`fk_cpf`),
  CONSTRAINT `fk_cpf` FOREIGN KEY (`fk_cpf`) REFERENCES `cliente` (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orcamentolocacaodeespaco`
--

LOCK TABLES `orcamentolocacaodeespaco` WRITE;
/*!40000 ALTER TABLE `orcamentolocacaodeespaco` DISABLE KEYS */;
/*!40000 ALTER TABLE `orcamentolocacaodeespaco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagamento`
--

DROP TABLE IF EXISTS `pagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pagamento` (
  `id` int NOT NULL,
  `valorTotal` double NOT NULL,
  `formaDePagamento` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagamento`
--

LOCK TABLES `pagamento` WRITE;
/*!40000 ALTER TABLE `pagamento` DISABLE KEYS */;
INSERT INTO `pagamento` VALUES (-1,100,'debito');
/*!40000 ALTER TABLE `pagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parcela`
--

DROP TABLE IF EXISTS `parcela`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parcela` (
  `id` int NOT NULL,
  `valorPago` double NOT NULL,
  `dataVencimento` varchar(10) NOT NULL,
  `dataPagamento` varchar(10) NOT NULL,
  `fk_idPagam` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_idPagam` FOREIGN KEY (`id`) REFERENCES `exgrupo`.`pagamento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parcela`
--

LOCK TABLES `parcela` WRITE;
/*!40000 ALTER TABLE `parcela` DISABLE KEYS */;
/*!40000 ALTER TABLE `parcela` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salgado`
--

DROP TABLE IF EXISTS `salgado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salgado` (
  `id` int NOT NULL,
  `descricao` varchar(200) NOT NULL,
  `valorUnitario` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salgado`
--

LOCK TABLES `salgado` WRITE;
/*!40000 ALTER TABLE `salgado` DISABLE KEYS */;
INSERT INTO `salgado` VALUES (1,'Coxinha de Frango com Catupiry',0.7),(2,'Risoles de Carne',0.7),(3,'Quibe',0.7),(4,'Esfiha de Carne',0.7),(5,'Empadinha de Palmito',0.7),(6,'Enroladinho de Salsicha',0.7),(7,'Bolinha de Queijo',0.7),(8,'Bolinha de Queijo com Milho',0.7),(9,'Risoles de Frango',0.7),(10,'Risoles de Presunto e Queijo',0.7),(11,'Enroladinho de Presunto e Queijo',0.7);
/*!40000 ALTER TABLE `salgado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salgadoselecionado`
--

DROP TABLE IF EXISTS `salgadoselecionado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salgadoselecionado` (
  `quantidade` int NOT NULL,
  `fk_idSalg` int NOT NULL,
  `fk_idOrcamentoBuffetComp` int NOT NULL,
  PRIMARY KEY (`fk_idOrcamentoBuffetComp`,`fk_idSalg`),
  KEY `fk_idSalgado_idx` (`fk_idSalg`),
  KEY `fk_idOrcamentoBuffetCompleto_idx` (`fk_idOrcamentoBuffetComp`),
  CONSTRAINT `fk_idOrcamentoBuffetComp` FOREIGN KEY (`fk_idOrcamentoBuffetComp`) REFERENCES `orcamentobuffetcompleto` (`id`),
  CONSTRAINT `fk_idSalg` FOREIGN KEY (`fk_idSalg`) REFERENCES `salgado` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salgadoselecionado`
--

LOCK TABLES `salgadoselecionado` WRITE;
/*!40000 ALTER TABLE `salgadoselecionado` DISABLE KEYS */;
INSERT INTO `salgadoselecionado` VALUES (85,1,598419440),(85,2,598419440),(85,3,598419440),(85,4,598419440),(85,8,598419440),(85,9,598419440),(85,10,598419440),(102,1,611690111),(102,3,611690111),(102,4,611690111),(102,5,611690111),(102,8,611690111),(102,9,611690111),(102,10,611690111),(190,1,668338314),(190,2,668338314),(190,3,668338314),(190,4,668338314),(190,8,668338314),(190,9,668338314),(190,10,668338314),(75,1,730981915),(75,2,730981915),(75,3,730981915),(75,4,730981915),(75,8,730981915),(75,9,730981915),(75,10,730981915),(75,11,730981915),(90,1,863633081),(90,2,863633081),(90,5,863633081),(90,6,863633081),(90,7,863633081),(90,9,863633081),(90,10,863633081),(90,11,863633081),(66,1,873911542),(66,2,873911542),(66,3,873911542),(66,4,873911542),(66,5,873911542),(66,8,873911542),(66,9,873911542),(66,10,873911542),(66,11,873911542),(85,1,1107957184),(85,2,1107957184),(85,3,1107957184),(85,4,1107957184),(85,8,1107957184),(85,9,1107957184),(85,10,1107957184),(85,1,1940083541),(85,2,1940083541),(85,3,1940083541),(85,4,1940083541),(85,8,1940083541),(85,9,1940083541),(85,10,1940083541),(66,1,2048215508),(66,2,2048215508),(66,3,2048215508),(66,4,2048215508),(66,5,2048215508),(66,8,2048215508),(66,9,2048215508),(66,10,2048215508),(66,11,2048215508);
/*!40000 ALTER TABLE `salgadoselecionado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicoadicional`
--

DROP TABLE IF EXISTS `servicoadicional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicoadicional` (
  `id` int NOT NULL,
  `nome` varchar(200) NOT NULL,
  `valor` double NOT NULL,
  `servicoUnico` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicoadicional`
--

LOCK TABLES `servicoadicional` WRITE;
/*!40000 ALTER TABLE `servicoadicional` DISABLE KEYS */;
INSERT INTO `servicoadicional` VALUES (1,'Mesa a mais',15,0),(2,'Garçom',120,0),(3,'Copeira',120,0),(4,'Monitora',60,0),(5,'Recepcionista',60,0),(6,'Kit festa',150,1);
/*!40000 ALTER TABLE `servicoadicional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicocontratado`
--

DROP TABLE IF EXISTS `servicocontratado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicocontratado` (
  `fk_idServicoAdicional` int NOT NULL,
  `fk_idOrcamentoLocacaoDeEspaco` int NOT NULL,
  `quantidade` int NOT NULL,
  PRIMARY KEY (`fk_idServicoAdicional`,`fk_idOrcamentoLocacaoDeEspaco`),
  KEY `fk_idOrcamentoLocacaoDeEspaco_idx` (`fk_idOrcamentoLocacaoDeEspaco`),
  CONSTRAINT `fk_idOrcamentoLocacaoDeEspaco` FOREIGN KEY (`fk_idOrcamentoLocacaoDeEspaco`) REFERENCES `orcamentolocacaodeespaco` (`id`),
  CONSTRAINT `fk_idServicoAdicional` FOREIGN KEY (`fk_idServicoAdicional`) REFERENCES `servicoadicional` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicocontratado`
--

LOCK TABLES `servicocontratado` WRITE;
/*!40000 ALTER TABLE `servicocontratado` DISABLE KEYS */;
/*!40000 ALTER TABLE `servicocontratado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `login` varchar(100) NOT NULL,
  `senha` varchar(500) NOT NULL,
  `nome` varchar(150) NOT NULL,
  `tipoDeUsuario` varchar(45) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-20  8:56:35
