#CREATE SCHEMA `node`;

DROP TABLE IF EXISTS `Config`;
DROP TABLE IF EXISTS `OverrideHistory`;
DROP TABLE IF EXISTS `Telemetry`;
DROP TABLE IF EXISTS `EnclosureNode`;
DROP TABLE IF EXISTS `PetProfiles`;

CREATE TABLE `Config` (
	`UserID` INT,
	`UserName` VARCHAR(32),
	`Password` VARCHAR(64),
	`Token` VARCHAR(28),
	`CentralNodeID` INT ,
	-- `Retries` INT NOT NULL, 
	-- `Threshold` INT NOT NULL,
	`Online` BOOLEAN NOT NULL,
PRIMARY KEY (UserID)
);

CREATE TABLE `PetProfiles` (
	`PetProfileID` VARCHAR(32) NOT NULL ,
	-- `Name` VARCHAR(32) NOT NULL ,
	`Day_Temperature_SP` FLOAT NOT NULL ,
	`Day_Humidity_SP` FLOAT NOT NULL ,
	`Night_Temperature_SP` FLOAT NOT NULL ,
	`Night_Humidity_SP` FLOAT NOT NULL ,
	`Temperature_TH` FLOAT NOT NULL ,
	`Humidity_TH` FLOAT NOT NULL ,

	`DayTime` VARCHAR(8) ,
	`NightTime` VARCHAR(8) ,
PRIMARY KEY (PetProfileID)
);

CREATE TABLE `EnclosureNode` (
	`EnclosureNodeID` INT NOT NULL AUTO_INCREMENT,
	`Name` VARCHAR(32) NOT NULL ,
	`OPTIONAL_LOAD` INT NOT NULL ,
	`PetProfileID` VARCHAR(32) NOT NULL ,
PRIMARY KEY (EnclosureNodeID) ,
CONSTRAINT fk_PetProfileID_EnclosureNode
	FOREIGN KEY (PetProfileID)
	REFERENCES PetProfiles (PetProfileID)
	-- ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE `Telemetry` (
	`TelemetryID` INT NOT NULL AUTO_INCREMENT,
	
	`DateTime` TIMESTAMP NOT NULL ,
	`EnclosureNodeID` INT NOT NULL ,

	`TEMP` FLOAT NOT NULL ,
	`RH` FLOAT NOT NULL ,

	`OPTIONAL_LOAD` FLOAT NOT NULL ,
	`HEAT_LOAD` FLOAT NOT NULL ,

	`UV_STATUS` INT NOT NULL ,
	`HUM_STATUS` INT NOT NULL ,
	`HEAT_STATUS` INT NOT NULL ,
	`OPTIONAL_STATUS` INT NOT NULL ,

	`HUM_OR` INT NOT NULL ,
	`HEAT_OR` INT NOT NULL ,
	`UV_OR` INT NOT NULL ,
	`OPTIONAL_OR` INT NOT NULL ,
PRIMARY KEY (TelemetryID) ,
CONSTRAINT fk_EnclosureNodeID_Telemetry
	FOREIGN KEY (EnclosureNodeID)
	REFERENCES EnclosureNode (EnclosureNodeID)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE `OverrideHistory` (
	`OverrideHistoryID` INT NOT NULL AUTO_INCREMENT,
	`DateTime` TIMESTAMP NOT NULL ,
	`EnclosureNodeID` INT NOT NULL ,
	`IC_OW` INT ,
	`IR_OW` INT ,
	`UV_OW` INT ,
	`HUM_OW` INT ,
	`IC` INT ,
	`IR` INT ,
	`UV` INT ,
	`HUM` INT ,
PRIMARY KEY (OverrideHistoryID) ,
CONSTRAINT fk_EnclosureNodeID_OverrideHistory
	FOREIGN KEY (EnclosureNodeID)
	REFERENCES EnclosureNode (EnclosureNodeID)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

INSERT INTO Config (`UserID`,`UserName`,`Password`,`Token`,`CentralNodeID`,`Online`)
VALUES ('2','agonar','1234','56me538k6mevqf41tvjqe10nqj','1',TRUE);


INSERT INTO PetProfiles (`PetProfileID`,`Day_Temperature_SP`,`Day_Humidity_SP`,`Night_Temperature_SP`,`Night_Humidity_SP`,`Temperature_TH`,`Humidity_TH`)
VALUES ('Gecko','80','50','75','55','5','5');

INSERT INTO PetProfiles (`PetProfileID`,`Day_Temperature_SP`,`Day_Humidity_SP`,`Night_Temperature_SP`,`Night_Humidity_SP`,`Temperature_TH`,`Humidity_TH`)
VALUES ('Chameleon','80','50','75','55','5','5');

INSERT INTO EnclosureNode (`EnclosureNodeID`,`Name`,`OPTIONAL_LOAD`,`PetProfileID`) 
VALUES ('1','Petra','1','Chameleon');

INSERT INTO EnclosureNode (`EnclosureNodeID`,`Name`,`OPTIONAL_LOAD`,`PetProfileID`)
VALUES ('2','Nomo','2','Gecko');

INSERT INTO Telemetry (`DateTime`,`EnclosureNodeID`,`TEMP`,`RH`,`OPTIONAL_LOAD`,`HEAT_LOAD`,`UV_STATUS`,`HUM_STATUS`,`HEAT_STATUS`,`OPTIONAL_STATUS`,`HUM_OR`,`HEAT_OR`,`UV_OR`,`OPTIONAL_OR`)
VALUES (now(),'1','75.5','45','80.0','80.0','1','1','1','1','0','0','0','0');

INSERT INTO Telemetry (`DateTime`,`EnclosureNodeID`,`TEMP`,`RH`,`OPTIONAL_LOAD`,`HEAT_LOAD`,`UV_STATUS`,`HUM_STATUS`,`HEAT_STATUS`,`OPTIONAL_STATUS`,`HUM_OR`,`HEAT_OR`,`UV_OR`,`OPTIONAL_OR`)
VALUES (now(),'2','75.5','45','80.0','80.0','1','1','1','1','0','0','0','0');

INSERT INTO OverrideHistory (`EnclosureNodeID`,`DateTime`,`IC_OW`,`IR_OW`,`UV_OW`,`HUM_OW`,`IC`,`IR`,`UV`,`HUM`) 
VALUES ('2', now(), '1','1','1','1','1','1','1','1');