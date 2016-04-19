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
	`Online` BOO
PRIMARY KEY (UserID)
);

CREATE TABLE `PetProfiles` (
	`PetProfileID` INT NOT NULL AUTO_INCREMENT,
	`Name` VARCHAR(32) NOT NULL ,
	`Day_Temperature_SP` FLOAT NOT NULL ,
	`Day_Humidity_SP` FLOAT NOT NULL ,
	`Night_Temperature_SP` FLOAT NOT NULL ,
	`Night_Humidity_SP` FLOAT NOT NULL ,
	`Temperature_TH` FLOAT NOT NULL ,
	`Humidity_TH` FLOAT NOT NULL ,

	`DayTime` TIMESTAMP NOT NULL ,
	`NightTime` TIMESTAMP NOT NULL ,
PRIMARY KEY (PetProfileID)
);

CREATE TABLE `EnclosureNode` (
	`EnclosureNodeID` INT NOT NULL AUTO_INCREMENT,
	`Name` VARCHAR(32) NOT NULL ,
	`OPTIONAL_LOAD` INT NOT NULL ,
	`PetProfileID` INT NOT NULL ,
PRIMARY KEY (EnclosureNodeID) ,
CONSTRAINT fk_PetProfileID_EnclosureNode
	FOREIGN KEY (PetProfileID)
	REFERENCES PetProfiles (PetProfileID)
	ON DELETE CASCADE
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
	`HUMI_STATUS` INT NOT NULL ,
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

INSERT INTO PetProfiles (`PetProfileID`,`Name`,`Day_Temperature_SP`,`Day_Humidity_SP`,`Night_Temperature_SP`,`Night_Humidity_SP`,`Temperature_TH`,`Humidity_TH`)
VALUES ('1','Gecko','80','50','75','55','5','5');

INSERT INTO PetProfiles (`PetProfileID`,`Name`,`Day_Temperature_SP`,`Day_Humidity_SP`,`Night_Temperature_SP`,`Night_Humidity_SP`,`Temperature_TH`,`Humidity_TH`)
VALUES ('2','Chameleon','80','50','75','55','5','5');

INSERT INTO EnclosureNode (`EnclosureNodeID`,`Name`,`OPTIONAL_LOAD`,`PetProfileID`) 
VALUES ('1','Chameleon','1','1');

INSERT INTO EnclosureNode (`EnclosureNodeID`,`Name`,`OPTIONAL_LOAD`,`PetProfileID`) 
VALUES ('2','Gecko','2','2');

INSERT INTO Telemetry (`DateTime`,`EnclosureNodeID`,`TEMP`,`RH`,`OPTIONAL_LOAD`,`HEAT_LOAD`,`UV_STATUS`,`HUMI_STATUS`) 
VALUES (now(),'2','80.9','50.2','90.0','75.0','1','1');

INSERT INTO Telemetry (`DateTime`,`EnclosureNodeID`,`TEMP`,`RH`,`OPTIONAL_LOAD`,`HEAT_LOAD`,`UV_STATUS`,`HUMI_STATUS`) 
VALUES (now(),'2','70.9','60.2','80.0','85.0','1','1');

INSERT INTO Telemetry (`DateTime`,`EnclosureNodeID`,`TEMP`,`RH`,`OPTIONAL_LOAD`,`HEAT_LOAD`,`UV_STATUS`,`HUMI_STATUS`) 
VALUES (now(),'2','70.9','60.2','80.0','85.0','1','1');

INSERT INTO OverrideHistory (`EnclosureNodeID`,`DateTime`,`IC_OW`,`IR_OW`,`UV_OW`,`HUM_OW`,`IC`,`IR`,`UV`,`HUM`) 
VALUES ('2', now(), '1','1','1','1','1','1','1','1');