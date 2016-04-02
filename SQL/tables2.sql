#CREATE SCHEMA `rest_test` ;

DROP TABLE IF EXISTS `Telemetry`;
DROP TABLE IF EXISTS `PetProfiles`;
DROP TABLE IF EXISTS `EnclosureNode`;
DROP TABLE IF EXISTS `CentralNode`;
DROP TABLE IF EXISTS `Session`;
DROP TABLE IF EXISTS `Users`;

CREATE TABLE `Users` (
	`UserID` INT NOT NULL AUTO_INCREMENT,
	`UserName` VARCHAR(32) NOT NULL,
	`Password` VARCHAR(64) NOT NULL,
	`Name` VARCHAR(32) NOT NULL,
	`LastName` VARCHAR(32),
	`Email` VARCHAR(32) NOT NULL,
	`Phone` VARCHAR(16),
PRIMARY KEY (`UserID`),
UNIQUE KEY `UserID` (`UserID`),
UNIQUE KEY `UserName` (`UserName`)
);

CREATE TABLE `Session` (
	`UserID` INT NOT NULL,
	`Token` VARCHAR(28) NOT NULL,
PRIMARY KEY (`UserID`,`Token`),
CONSTRAINT fk_UserID_Session
	FOREIGN KEY (UserID)
	REFERENCES Users (UserID)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE `CentralNode` (
	`CentralNodeID` INT NOT NULL ,
	`UserID` INT NOT NULL ,
PRIMARY KEY (CentralNodeID) ,
CONSTRAINT fk_UserID_CentralNode
	FOREIGN KEY (UserID)
	REFERENCES Users (UserID)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE `EnclosureNode` (
	`EnclosureNodeID` INT NOT NULL ,
	`CentralNodeID` INT NOT NULL ,
	`Name` VARCHAR(32) NOT NULL ,
	`DEV_UV` BOOLEAN NOT NULL ,
	`DEV_IR` BOOLEAN NOT NULL ,
	`DEV_IC` BOOLEAN NOT NULL ,
	`DEV_HUM` BOOLEAN NOT NULL ,
PRIMARY KEY (EnclosureNodeID) ,
CONSTRAINT fk_CentralNodeID_EnclosureNode
	FOREIGN KEY (CentralNodeID)
	REFERENCES CentralNode (CentralNodeID)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE `Telemetry` (
	`ID` INT NOT NULL AUTO_INCREMENT,
	`EnclosureNodeID` INT NOT NULL ,

	`Temperature` FLOAT NOT NULL ,
	`Humidity` FLOAT NOT NULL ,

	`Load_IR` FLOAT NOT NULL ,
	`Load_IC` FLOAT NOT NULL ,

	`State_UV` INT NOT NULL ,
	`State_HUM` INT NOT NULL ,

PRIMARY KEY (ID) ,
CONSTRAINT fk_CentralNodeID_Telemetry
	FOREIGN KEY (EnclosureNodeID)
	REFERENCES EnclosureNode (EnclosureNodeID)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE `PetProfiles` (
	`ID` INT NOT NULL AUTO_INCREMENT,
	`UserID` INT NOT NULL ,

	`Name` VARCHAR(32) NOT NULL ,

	`Day_Temperature_SP` FLOAT NOT NULL ,
	`Day_Humidity_SP` FLOAT NOT NULL ,

	`Night_Temperature_SP` FLOAT NOT NULL ,
	`Night_Humidity_SP` FLOAT NOT NULL ,

	`Temperature_TH` FLOAT NOT NULL ,
	`Humidity_TH` FLOAT NOT NULL ,

PRIMARY KEY (ID) ,
CONSTRAINT fk_UserID_PetProfiles
	FOREIGN KEY (UserID)
	REFERENCES Users (UserID)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

INSERT INTO Users (`UserID`,`UserName`,`Password`,`Name`,`LastName`,`Email`,`Phone`)
VALUES ('1','DynoCloud','admin','DynoGod','Supreme','dyno@dynocare.xyz','');

INSERT INTO Users (`UserID`,`UserName`,`Password`,`Name`,`LastName`,`Email`,`Phone`)
VALUES ('2','agonar','1234','Alejandro','Gonzalez','alejandro.gonzalez3@upr.edu','');

INSERT INTO CentralNode (`CentralNodeID`, `UserID`) VALUES ('001', '001');

INSERT INTO EnclosureNode (`EnclosureNodeID`, `CentralNodeID`,`Name`,`DEV_UV`,`DEV_IR`,`DEV_IC`,`DEV_HUM`) 
VALUES ('001', '001','Chameleon',TRUE,TRUE,TRUE,TRUE);

INSERT INTO EnclosureNode (`EnclosureNodeID`, `CentralNodeID`,`Name`,`DEV_UV`,`DEV_IR`,`DEV_IC`,`DEV_HUM`) 
VALUES ('002', '001','Gecko',TRUE,TRUE,TRUE,TRUE);

INSERT INTO Telemetry (`EnclosureNodeID`,`Temperature`,`Humidity`,`Load_IR`,`Load_IC`,`State_UV`,`State_HUM`) 
VALUES ('001','80.9','50.2','90.0','75.0','1','0');

INSERT INTO PetProfiles (`ID`,`UserID`,`Name`,`Day_Temperature_SP`,`Day_Humidity_SP`,`Night_Temperature_SP`,`Night_Humidity_SP`,`Temperature_TH`,`Humidity_TH`)
VALUES ('1','1','Gecko','80','50','75','55','5','5');

INSERT INTO PetProfiles (`ID`,`UserID`,`Name`,`Day_Temperature_SP`,`Day_Humidity_SP`,`Night_Temperature_SP`,`Night_Humidity_SP`,`Temperature_TH`,`Humidity_TH`)
VALUES ('2','2','Pepe','80','50','75','55','5','5');