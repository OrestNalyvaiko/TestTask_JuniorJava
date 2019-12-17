
CREATE SCHEMA IF NOT EXISTS `university` DEFAULT CHARACTER SET utf8 ;
USE `university` ;


CREATE TABLE IF NOT EXISTS `university`.`degree` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `degree_name` VARCHAR(120) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `university`.`department` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `department_name` VARCHAR(120) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `university`.`lecturer` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(120) NOT NULL,
  `middle_name` VARCHAR(120) NOT NULL,
  `surname` VARCHAR(120) NOT NULL,
  `degree_id` INT(11) NOT NULL,
  `salary` DECIMAL(8,2) NOT NULL,
  `post` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_lector_degree1_idx` (`degree_id` ASC) VISIBLE,
  CONSTRAINT `fk_lector_degree1`
    FOREIGN KEY (`degree_id`)
    REFERENCES `university`.`degree` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `university`.`department_lecturer` (
  `department_id` INT(11) NOT NULL,
  `lecturer_id` INT(11) NOT NULL,
  PRIMARY KEY (`department_id`, `lecturer_id`),
  INDEX `fk_department_has_lector_lector1_idx` (`lecturer_id` ASC) VISIBLE,
  INDEX `fk_department_has_lector_department1_idx` (`department_id` ASC) VISIBLE,
  CONSTRAINT `fk_department_has_lector_department1`
    FOREIGN KEY (`department_id`)
    REFERENCES `university`.`department` (`id`),
  CONSTRAINT `fk_department_has_lector_lector1`
    FOREIGN KEY (`lecturer_id`)
    REFERENCES `university`.`lecturer` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
