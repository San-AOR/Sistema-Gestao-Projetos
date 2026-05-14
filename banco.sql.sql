-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: sistema_gestao_projetos
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `arquivo`
--

DROP TABLE IF EXISTS `arquivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `arquivo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tarefa_id` int NOT NULL,
  `nome_arquivo` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `caminho_arquivo` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `data_envio` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tarefa_id` (`tarefa_id`),
  CONSTRAINT `arquivo_ibfk_1` FOREIGN KEY (`tarefa_id`) REFERENCES `tarefa` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arquivo`
--

LOCK TABLES `arquivo` WRITE;
/*!40000 ALTER TABLE `arquivo` DISABLE KEYS */;
/*!40000 ALTER TABLE `arquivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipe`
--

DROP TABLE IF EXISTS `equipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipe` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `descricao` text COLLATE utf8mb4_general_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipe`
--

LOCK TABLES `equipe` WRITE;
/*!40000 ALTER TABLE `equipe` DISABLE KEYS */;
INSERT INTO `equipe` VALUES (1,'Equipe Frontend','Responsável pelo desenvolvimento da interface');
/*!40000 ALTER TABLE `equipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipe_projeto`
--

DROP TABLE IF EXISTS `equipe_projeto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipe_projeto` (
  `equipe_id` int NOT NULL,
  `projeto_id` int NOT NULL,
  PRIMARY KEY (`equipe_id`,`projeto_id`),
  KEY `projeto_id` (`projeto_id`),
  CONSTRAINT `equipe_projeto_ibfk_1` FOREIGN KEY (`equipe_id`) REFERENCES `equipe` (`id`),
  CONSTRAINT `equipe_projeto_ibfk_2` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipe_projeto`
--

LOCK TABLES `equipe_projeto` WRITE;
/*!40000 ALTER TABLE `equipe_projeto` DISABLE KEYS */;
/*!40000 ALTER TABLE `equipe_projeto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projeto`
--

DROP TABLE IF EXISTS `projeto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projeto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `descricao` text COLLATE utf8mb4_general_ci,
  `data_inicio` date NOT NULL,
  `data_fim_prevista` date NOT NULL,
  `status` enum('PLANEJADO','EM_ANDAMENTO','CONCLUIDO','CANCELADO') COLLATE utf8mb4_general_ci NOT NULL,
  `gerente_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `gerente_id` (`gerente_id`),
  CONSTRAINT `projeto_ibfk_1` FOREIGN KEY (`gerente_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projeto`
--

LOCK TABLES `projeto` WRITE;
/*!40000 ALTER TABLE `projeto` DISABLE KEYS */;
INSERT INTO `projeto` VALUES (1,'Projeto Sistema Web','Desenvolvimento de sistema web','2026-05-01','2026-12-31','EM_ANDAMENTO',6),(2,'TRILHA','TRILHA EM GRUPO','2026-05-01','2026-06-01','PLANEJADO',1),(3,'TESTE FRONT-1','FRONT','2026-05-07','2026-05-16','PLANEJADO',6),(4,'ANS versão TISS 4.03.00','Implantar nova versão Tiss 4.03.00 atendendo os parâmetros e regulamento da ANS','2026-05-11','2026-06-30','PLANEJADO',6);
/*!40000 ALTER TABLE `projeto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarefa`
--

DROP TABLE IF EXISTS `tarefa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tarefa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `descricao` text COLLATE utf8mb4_general_ci,
  `status` enum('PENDENTE','EM_ANDAMENTO','CONCLUIDA') COLLATE utf8mb4_general_ci NOT NULL,
  `projeto_id` int NOT NULL,
  `responsavel_id` int DEFAULT NULL,
  `documento` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `detalhes` text COLLATE utf8mb4_general_ci,
  `observacao` text COLLATE utf8mb4_general_ci,
  `resolucao` text COLLATE utf8mb4_general_ci,
  `observacao_interna` text COLLATE utf8mb4_general_ci,
  `detalhes_tratados` tinyint(1) DEFAULT '0',
  `data_inicio` datetime DEFAULT CURRENT_TIMESTAMP,
  `data_atribuicao` datetime DEFAULT NULL,
  `data_ultima_atualizacao` datetime DEFAULT NULL,
  `data_fim` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `projeto_id` (`projeto_id`),
  KEY `fk_responsavel` (`responsavel_id`),
  CONSTRAINT `fk_responsavel` FOREIGN KEY (`responsavel_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `tarefa_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarefa`
--

LOCK TABLES `tarefa` WRITE;
/*!40000 ALTER TABLE `tarefa` DISABLE KEYS */;
INSERT INTO `tarefa` VALUES (1,'Criar banco de dados','Modelagem e criacao das tabelas','CONCLUIDA',1,8,'','tabela criada conforme solicitado.','informação campos novos','teste de tela concluído','',1,'2026-05-11 18:03:54',NULL,'2026-05-11 19:23:33','2026-05-11 19:23:33'),(3,'CORRIGIR ERRO DO SISTEMA','BUG EXISTENTE NÃO PERMITE NAVEGAR NO SISTEMA','EM_ANDAMENTO',2,10,'','','',NULL,NULL,0,'2026-05-11 18:03:54',NULL,'2026-05-12 18:24:13',NULL),(4,'BUG','O sistema apresenta bug na consulta da guia','EM_ANDAMENTO',1,8,NULL,'teste 123',NULL,NULL,NULL,0,'2026-05-11 18:03:54','2026-05-12 16:24:38','2026-05-12 16:24:38',NULL),(5,'Testes Sistêmicos','Realizar testes de tela sistema principal','PENDENTE',2,7,NULL,NULL,NULL,NULL,NULL,0,'2026-05-11 19:28:10','2026-05-11 19:28:32','2026-05-11 19:28:32',NULL),(6,'ANS versão TISS 4.03.00','Implantar nova versão Tiss 4.03.00 atendendo os parâmetros e regulamento da ANS','CONCLUIDA',4,9,'','Implantado versão TISS com novas regras de negócio','Essa demanda exigiu esforços dos times Web e Sistema P15. Importamos tabelas 18 e 19 e realizamos melhorias nas guias de internação e SADT.',NULL,NULL,0,'2026-05-12 16:30:04','2026-05-12 16:31:50','2026-05-12 16:41:28','2026-05-12 16:41:28');
/*!40000 ALTER TABLE `tarefa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarefa_arquivo`
--

DROP TABLE IF EXISTS `tarefa_arquivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tarefa_arquivo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tarefa_id` int NOT NULL,
  `nome_arquivo` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `caminho_arquivo` varchar(500) COLLATE utf8mb4_general_ci NOT NULL,
  `data_envio` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tarefa_id` (`tarefa_id`),
  CONSTRAINT `tarefa_arquivo_ibfk_1` FOREIGN KEY (`tarefa_id`) REFERENCES `tarefa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarefa_arquivo`
--

LOCK TABLES `tarefa_arquivo` WRITE;
/*!40000 ALTER TABLE `tarefa_arquivo` DISABLE KEYS */;
INSERT INTO `tarefa_arquivo` VALUES (1,1,'Teste 1.docx','C:\\Users\\salme\\OneDrive\\Desktop\\Teste 1.docx','2026-05-11 19:22:04'),(2,1,'Teste 2.docx','C:\\Users\\salme\\OneDrive\\Desktop\\Teste 2.docx','2026-05-11 19:22:46'),(3,6,'Teste 1.docx','C:\\Users\\salme\\OneDrive\\Desktop\\Teste 1.docx','2026-05-12 16:38:14'),(4,6,'Teste 2.docx','C:\\Users\\salme\\OneDrive\\Desktop\\Teste 2.docx','2026-05-12 16:39:18'),(5,3,'Teste 1.docx','C:\\Users\\salme\\OneDrive\\Desktop\\Teste 1.docx','2026-05-12 18:22:12'),(6,3,'Teste 2.docx','C:\\Users\\salme\\OneDrive\\Desktop\\Teste 2.docx','2026-05-12 18:22:41');
/*!40000 ALTER TABLE `tarefa_arquivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `cpf` varchar(14) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `cargo` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `login` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `senha` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `perfil` enum('ADMIN','GERENTE','COLABORADOR') COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_cpf` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Karla Maria','444.444.555-00','karlamaria@gmail.com','COLABORADOR','karla','69c4a4de956d8910740f76ef33ea5d61','COLABORADOR'),(6,'Administrador','000.000.000-00','admin@sistema.com','Gerente','admin','0192023a7bbd73250516f069df18b500','ADMIN'),(7,'Joao Silva','111.111.111-11','joao@email.com','Desenvolvedor','joao','3dfcab79ed21fd89c9eb25e9864a6155','COLABORADOR'),(8,'Sandra Almeida','112.222.333-11','sandra@gmail.com','Desenvolvedor','sandra','3fc5586bed4fb9f745500c0605197924','COLABORADOR'),(9,'Rafaela Lima','299.550.100-21','rafaela@gmail.com','Desenvolvedor','rafaela','5b403b4601faa790e7633377da53ddf6','COLABORADOR'),(10,'Leticia Paz','166.666.777-01','leticia@gmail.com','DESENVOLVEDOR','leticia','aa23a7b3c60d604946f20de6ac2b3777','GERENTE'),(11,'Marcela Ribeiro','166.000.000-12','marcela@gmail.com','Gerente','marcela','49483d444148e769bbdf95b5c21dda44','GERENTE'),(16,'THeo Henrique','111.222.333-04','theo@gmail.com','DESENVOLVEDOR','theo','478d5000594ef50c56e98681961aee6d','COLABORADOR');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_equipe`
--

DROP TABLE IF EXISTS `usuario_equipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_equipe` (
  `usuario_id` int NOT NULL,
  `equipe_id` int NOT NULL,
  PRIMARY KEY (`usuario_id`,`equipe_id`),
  KEY `equipe_id` (`equipe_id`),
  CONSTRAINT `usuario_equipe_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `usuario_equipe_ibfk_2` FOREIGN KEY (`equipe_id`) REFERENCES `equipe` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_equipe`
--

LOCK TABLES `usuario_equipe` WRITE;
/*!40000 ALTER TABLE `usuario_equipe` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario_equipe` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-12 18:48:19
