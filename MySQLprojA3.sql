-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema db_biblioteca
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_biblioteca
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_biblioteca` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `db_biblioteca` ;

-- -----------------------------------------------------
-- Table `db_biblioteca`.`clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_biblioteca`.`clientes` (
  `cpfCli` VARCHAR(20) NULL DEFAULT NULL,
  `idcli` INT NOT NULL AUTO_INCREMENT,
  `nomeclie` VARCHAR(50) NOT NULL,
  `endereço` VARCHAR(100) NULL DEFAULT NULL,
  `foneCli` VARCHAR(50) NOT NULL,
  `emailcli` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`idcli`))
ENGINE = InnoDB
AUTO_INCREMENT = 33
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_biblioteca`.`os`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_biblioteca`.`os` (
  `os` INT NOT NULL AUTO_INCREMENT,
  `data_os` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `tipo` VARCHAR(15) NOT NULL,
  `situacao` VARCHAR(20) NOT NULL,
  `equipamento` VARCHAR(150) NOT NULL,
  `defeito` VARCHAR(150) NOT NULL,
  `servico` VARCHAR(150) NULL DEFAULT NULL,
  `tecnico` VARCHAR(30) NULL DEFAULT NULL,
  `valor` DECIMAL(10,2) NULL DEFAULT NULL,
  `idcli` INT NOT NULL,
  PRIMARY KEY (`os`),
  INDEX `idcli` (`idcli` ASC) VISIBLE,
  CONSTRAINT `os_ibfk_1`
    FOREIGN KEY (`idcli`)
    REFERENCES `db_biblioteca`.`clientes` (`idcli`))
ENGINE = InnoDB
AUTO_INCREMENT = 27
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_biblioteca`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_biblioteca`.`usuario` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Usuario` VARCHAR(45) NOT NULL,
  `Fone` VARCHAR(45) NOT NULL,
  `Login` VARCHAR(45) NOT NULL,
  `Senha` VARCHAR(45) NOT NULL,
  `perfil` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
