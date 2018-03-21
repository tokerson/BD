-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`bonifikarta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`bonifikarta` (
  `idbonifikarta` INT NOT NULL AUTO_INCREMENT,
  `typ` INT NULL DEFAULT 0,
  `mozliwy_rabat` INT NOT NULL DEFAULT 0,
  `data_waznosci` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`idbonifikarta`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Pacjenci`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Pacjenci` (
  `PESEL` VARCHAR(11) NOT NULL,
  `imię` VARCHAR(45) NOT NULL,
  `nazwisko` VARCHAR(45) NOT NULL,
  `wiek` INT NOT NULL,
  `nr_telefonu` VARCHAR(12) NULL DEFAULT NULL,
  `bonifikarta_idbonifikarta` INT NULL DEFAULT NULL,
  PRIMARY KEY (`PESEL`),
  INDEX `fk_Pacjenci_bonifikarta1_idx` (`bonifikarta_idbonifikarta` ASC),
  CONSTRAINT `fk_Pacjenci_bonifikarta1`
    FOREIGN KEY (`bonifikarta_idbonifikarta`)
    REFERENCES `mydb`.`bonifikarta` (`idbonifikarta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Zabiegi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Zabiegi` (
  `idZabiegu` INT NOT NULL AUTO_INCREMENT,
  `nazwa_zabiegu` VARCHAR(80) NOT NULL,
  `cena` INT NOT NULL DEFAULT 0,
  `narkoza` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`idZabiegu`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Rejestracje`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Rejestracje` (
  `idRejestracji` INT NOT NULL AUTO_INCREMENT,
  `godzina` TIME(6) NOT NULL,
  `data` DATE NOT NULL,
  `zabieg` INT NOT NULL,
  `pacjent` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`idRejestracji`, `zabieg`, `pacjent`),
  INDEX `zabieg_idx` (`zabieg` ASC),
  INDEX `pacjent_idx` (`pacjent` ASC),
  UNIQUE INDEX `godzina_UNIQUE` (`godzina` ASC),
  CONSTRAINT `zabieg`
    FOREIGN KEY (`zabieg`)
    REFERENCES `mydb`.`Zabiegi` (`idZabiegu`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `pacjent`
    FOREIGN KEY (`pacjent`)
    REFERENCES `mydb`.`Pacjenci` (`PESEL`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Dentysci`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Dentysci` (
  `idDentysty` INT NOT NULL AUTO_INCREMENT,
  `imię` VARCHAR(45) NOT NULL,
  `nazwisko` VARCHAR(45) NOT NULL,
  `wynagrodzenie` INT NOT NULL DEFAULT 0,
  `data zatrudnienia` DATE NULL DEFAULT NULL,
  `nr_telefonu` VARCHAR(12) NULL DEFAULT NULL,
  PRIMARY KEY (`idDentysty`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Dentysci_do_zabiegow`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Dentysci_do_zabiegow` (
  `Rejestracje_idRejestracji` INT NOT NULL,
  `Dentyści_idDentysty` INT NOT NULL,
  PRIMARY KEY (`Rejestracje_idRejestracji`, `Dentyści_idDentysty`),
  INDEX `fk_Rejestracje_has_Dentyści_Dentyści1_idx` (`Dentyści_idDentysty` ASC),
  INDEX `fk_Rejestracje_has_Dentyści_Rejestracje1_idx` (`Rejestracje_idRejestracji` ASC),
  CONSTRAINT `fk_Rejestracje_has_Dentyści_Rejestracje1`
    FOREIGN KEY (`Rejestracje_idRejestracji`)
    REFERENCES `mydb`.`Rejestracje` (`idRejestracji`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Rejestracje_has_Dentyści_Dentyści1`
    FOREIGN KEY (`Dentyści_idDentysty`)
    REFERENCES `mydb`.`Dentysci` (`idDentysty`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
