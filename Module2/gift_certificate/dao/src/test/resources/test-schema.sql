-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gift_db` DEFAULT CHARACTER SET utf8 ;
USE `gift_db` ;

-- -----------------------------------------------------
-- Table `mydb`.`gift_sertificates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_db`.`gift_certificates` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL unique,
  `description` VARCHAR(300) NOT NULL unique,
  `price` INT NOT NULL,
  `duration` INT NOT NULL,
  `create_date` TIMESTAMP(2) not null ,
  `last_update_date` timestamp(2) not null,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_db`.`tags` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL unique,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`gift_sertificates_has_tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_db`.`gift_certificates_has_tags` (
  `gift_certificates_id` INT NOT NULL,
  `tags_id` INT NOT NULL,
  PRIMARY KEY (`gift_certificates_id`, `tags_id`),
  INDEX `fk_gift_certificates_has_tags_tags1_idx` (`tags_id` ASC) ,
  INDEX `fk_gift_certificates_has_tags_gift_certificates_idx` (`gift_certificates_id` ASC) ,
  CONSTRAINT `fk_gift_certificates_has_tags_gift_certificates`
    FOREIGN KEY (`gift_certificates_id`)
    REFERENCES `mydb`.`gift_certificates` (`id`),
  CONSTRAINT `fk_gift_certificates_has_tags_tags1`
    FOREIGN KEY (`tags_id`)
    REFERENCES `mydb`.`tags` (`id`)
   )
ENGINE = InnoDB;



DELIMITER $$
CREATE TRIGGER `insert_gift_certificates` before insert on `gift_certificates`
FOR EACH ROW begin
SET NEW.create_date = current_timestamp(), new.last_update_date=current_timestamp();
END;$$


DELIMITER $$
CREATE TRIGGER `update_gift_certificates` before update  ON `gift_certificates`
FOR EACH ROW BEGIN
   Set NEW.last_update_date = current_timestamp();
END;$$