-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema mercadolibre
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mercadolibre
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mercadolibre` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `mercadolibre` ;

-- -----------------------------------------------------
-- Table `mercadolibre`.`mutant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mercadolibre`.`mutant` (
  `mutant_id` INT NOT NULL AUTO_INCREMENT,
  `is_mutant` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`mutant_id`),
  INDEX `IX_MUTANT_IS_MUTANT` (`is_mutant` ASC) VISIBLE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
