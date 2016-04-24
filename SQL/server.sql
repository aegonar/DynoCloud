#CREATE SCHEMA `server`;

DROP TABLE IF EXISTS `OverrideHistory`;
DROP TABLE IF EXISTS `Telemetry`;
DROP TABLE IF EXISTS `Alerts`;
DROP TABLE IF EXISTS `EnclosureNode`;
DROP TABLE IF EXISTS `PetProfiles`;
DROP TABLE IF EXISTS `CentralNode`;
DROP TABLE IF EXISTS `Session`;
DROP TABLE IF EXISTS `AlertSettings`;
DROP TABLE IF EXISTS `Users`;

CREATE TABLE `Users` (
	`UserID` INT NOT NULL AUTO_INCREMENT,
	`UserName` VARCHAR(32) NOT NULL,
	`Password` VARCHAR(64) NOT NULL,
	`Name` VARCHAR(32) NOT NULL,
	`LastName` VARCHAR(32),
	`Email` VARCHAR(32) NOT NULL,
	`Phone` VARCHAR(16),
	`AltEmail` VARCHAR(32),
	`AltPhone` VARCHAR(16),
PRIMARY KEY (`UserID`),
	UNIQUE KEY `UserName` (`UserName`),
	UNIQUE KEY `Email` (`Email`)
);

CREATE TABLE `AlertSettings` (
	`UserID` INT NOT NULL ,
	`Retries` INT NOT NULL, 
	`Threshold` INT NOT NULL,
	`Email` BOOLEAN NOT NULL,
	`Phone` BOOLEAN NOT NULL,
	`OnScreen`BOOLEAN NOT NULL,
PRIMARY KEY (`UserID`),
CONSTRAINT fk_UserID_AlertSettings
	FOREIGN KEY (UserID)
	REFERENCES Users (UserID)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE `Session` (
	`UserID` INT NOT NULL,
	`Token` VARCHAR(28) NOT NULL,
PRIMARY KEY (`UserID`,`Token`),
	UNIQUE KEY `Token` (`Token`),
CONSTRAINT fk_UserID_Session
	FOREIGN KEY (UserID)
	REFERENCES Users (UserID)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE `CentralNode` (
	`CentralNodeID` INT NOT NULL AUTO_INCREMENT ,
	`UserID` INT NOT NULL ,
	`Added` TIMESTAMP NOT NULL ,
PRIMARY KEY (CentralNodeID, UserID) ,
CONSTRAINT fk_UserID_CentralNode
	FOREIGN KEY (UserID)
	REFERENCES Users (UserID)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE `PetProfiles` (
	`PetProfileID` VARCHAR(32) NOT NULL ,
	`UserID` INT NOT NULL ,
	-- `Name` VARCHAR(32) NOT NULL ,
	`Day_Temperature_SP` FLOAT NOT NULL ,
	`Day_Humidity_SP` FLOAT NOT NULL ,
	`Night_Temperature_SP` FLOAT NOT NULL ,
	`Night_Humidity_SP` FLOAT NOT NULL ,
	`Temperature_TH` FLOAT NOT NULL ,
	`Humidity_TH` FLOAT NOT NULL ,
	`DayTime` VARCHAR(8) ,
	`NightTime` VARCHAR(8) ,
PRIMARY KEY (PetProfileID, UserID) ,
CONSTRAINT fk_UserID_PetProfiles
	FOREIGN KEY (UserID)
	REFERENCES Users (UserID)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE `EnclosureNode` (
	`EnclosureNodeID` INT NOT NULL AUTO_INCREMENT,
	`CentralNodeID` INT NOT NULL ,
	`UserID` INT NOT NULL ,
	`Name` VARCHAR(32) NOT NULL ,
	`OPTIONAL_LOAD` INT NOT NULL ,
	`PetProfileID` VARCHAR(32) NOT NULL ,
PRIMARY KEY (EnclosureNodeID, CentralNodeID, UserID) ,
CONSTRAINT fk_UserID_EnclosureNode
	FOREIGN KEY (UserID)
	REFERENCES Users (UserID)
	ON DELETE CASCADE
	ON UPDATE CASCADE ,
CONSTRAINT fk_CentralNodeID_EnclosureNode
	FOREIGN KEY (CentralNodeID)
	REFERENCES CentralNode (CentralNodeID)
	ON DELETE CASCADE
	ON UPDATE CASCADE ,
CONSTRAINT fk_PetProfileID_EnclosureNode
	FOREIGN KEY (PetProfileID)
	REFERENCES PetProfiles (PetProfileID)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE `Alerts` (
	`AlertID` INT NOT NULL AUTO_INCREMENT,
	`EnclosureNodeID` INT NOT NULL ,
	`CentralNodeID` INT NOT NULL ,
	`UserID` INT NOT NULL ,
	`DateTime` TIMESTAMP NOT NULL ,
	`Message` VARCHAR(256) ,
	`Destination` VARCHAR(32) ,
PRIMARY KEY (AlertID) ,
CONSTRAINT fk_UserID_Alerts
	FOREIGN KEY (UserID)
	REFERENCES Users (UserID)
	ON DELETE CASCADE
	ON UPDATE CASCADE ,
CONSTRAINT fk_CentralNodeID_Alerts
	FOREIGN KEY (CentralNodeID)
	REFERENCES CentralNode (CentralNodeID)
	ON DELETE CASCADE
	ON UPDATE CASCADE ,
CONSTRAINT fk_EnclosureNodeID_Alerts
	FOREIGN KEY (EnclosureNodeID)
	REFERENCES EnclosureNode (EnclosureNodeID)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE `Telemetry` (
	`TelemetryID` INT NOT NULL AUTO_INCREMENT,

	`CentralNodeID` INT ,
	`UserID` INT ,

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
CONSTRAINT fk_UserID_Telemetry
	FOREIGN KEY (UserID)
	REFERENCES Users (UserID)
	ON DELETE CASCADE
	ON UPDATE CASCADE ,
CONSTRAINT fk_CentralNodeID_Telemetry
	FOREIGN KEY (CentralNodeID)
	REFERENCES CentralNode (CentralNodeID)
	ON DELETE CASCADE
	ON UPDATE CASCADE ,
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
	`CentralNodeID` INT NOT NULL ,
	`UserID` INT NOT NULL ,
	`IC_OW` INT ,
	`IR_OW` INT ,
	`UV_OW` INT ,
	`HUM_OW` INT ,
	`IC` INT ,
	`IR` INT ,
	`UV` INT ,
	`HUM` INT ,
PRIMARY KEY (OverrideHistoryID) ,
CONSTRAINT fk_UserID_OverrideHistory
	FOREIGN KEY (UserID)
	REFERENCES Users (UserID)
	ON DELETE CASCADE
	ON UPDATE CASCADE ,
CONSTRAINT fk_CentralNodeID_OverrideHistory
	FOREIGN KEY (CentralNodeID)
	REFERENCES CentralNode (CentralNodeID)
	ON DELETE CASCADE
	ON UPDATE CASCADE ,
CONSTRAINT fk_EnclosureNodeID_OverrideHistory
	FOREIGN KEY (EnclosureNodeID)
	REFERENCES EnclosureNode (EnclosureNodeID)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

INSERT INTO Users (`UserID`,`UserName`,`Password`,`Name`,`LastName`,`Email`,`Phone`)
VALUES ('1','DynoCloud','admin','DynoGod','Supreme','dyno@dynocare.xyz','');

INSERT INTO Users (`UserID`,`UserName`,`Password`,`Name`,`LastName`,`Email`,`Phone`)
VALUES ('2','agonar','1234','Alejandro','Gonzalez','alejandro.gonzalez3@upr.edu','');

INSERT INTO AlertSettings (`UserID`, `Retries`, `Threshold`, `Email`, `Phone`, `OnScreen`) 
VALUES ('2', '3', '5', TRUE, FALSE, TRUE);

INSERT INTO Session (`UserID`,`Token`) VALUES ('2', '56me538k6mevqf41tvjqe10nqj');

INSERT INTO CentralNode (`UserID`, `CentralNodeID`, `Added`) VALUES ('2', '1', now());

-- INSERT INTO PetProfiles (`PetProfileID`,`UserID`,`Name`,`Day_Temperature_SP`,`Day_Humidity_SP`,`Night_Temperature_SP`,`Night_Humidity_SP`,`Temperature_TH`,`Humidity_TH`)
-- VALUES ('1','1','Gecko','80','50','75','55','5','5');

-- INSERT INTO PetProfiles (`PetProfileID`,`UserID`,`Name`,`Day_Temperature_SP`,`Day_Humidity_SP`,`Night_Temperature_SP`,`Night_Humidity_SP`,`Temperature_TH`,`Humidity_TH`)
-- VALUES ('2','2','Chameleon','80','50','75','55','5','5');

INSERT INTO PetProfiles (`UserID`,`PetProfileID`,`Day_Temperature_SP`,`Day_Humidity_SP`,`Night_Temperature_SP`,`Night_Humidity_SP`,`Temperature_TH`,`Humidity_TH`)
VALUES ('2','Gecko','80','50','75','55','5','5');

INSERT INTO PetProfiles (`UserID`,`PetProfileID`,`Day_Temperature_SP`,`Day_Humidity_SP`,`Night_Temperature_SP`,`Night_Humidity_SP`,`Temperature_TH`,`Humidity_TH`)
VALUES ('2','Chameleon','80','50','75','55','5','5');

INSERT INTO EnclosureNode (`EnclosureNodeID`, `CentralNodeID`, `UserID`,`Name`,`OPTIONAL_LOAD`,`PetProfileID`) 
VALUES ('1', '1', '2','Petra','1','Chameleon');

INSERT INTO EnclosureNode (`EnclosureNodeID`, `CentralNodeID`, `UserID`,`Name`,`OPTIONAL_LOAD`,`PetProfileID`) 
VALUES ('2', '1', '2','Nomo','2','Gecko');

INSERT INTO Alerts (`UserID`, `CentralNodeID`, `EnclosureNodeID`, `DateTime`, `Message`, `Destination`) 
VALUES ('2', '1', '1',now(), 'Too hot!', 'email');

INSERT INTO Telemetry (`DateTime`,`EnclosureNodeID`,`TEMP`,`RH`,`OPTIONAL_LOAD`,`HEAT_LOAD`,`UV_STATUS`,`HUM_STATUS`,`HEAT_STATUS`,`OPTIONAL_STATUS`,`HUM_OR`,`HEAT_OR`,`UV_OR`,`OPTIONAL_OR`,`CentralNodeID`,`UserID`)
VALUES (now(),'1','75.5','45','80.0','80.0','1','1','1','1','0','0','0','0','1','2');

INSERT INTO Telemetry (`DateTime`,`EnclosureNodeID`,`TEMP`,`RH`,`OPTIONAL_LOAD`,`HEAT_LOAD`,`UV_STATUS`,`HUM_STATUS`,`HEAT_STATUS`,`OPTIONAL_STATUS`,`HUM_OR`,`HEAT_OR`,`UV_OR`,`OPTIONAL_OR`,`CentralNodeID`,`UserID`)
VALUES (now(),'2','75.5','45','80.0','80.0','1','1','1','1','0','0','0','0','1','2');

INSERT INTO OverrideHistory (`UserID`,`CentralNodeID`,`EnclosureNodeID`,`DateTime`,`IC_OW`,`IR_OW`,`UV_OW`,`HUM_OW`,`IC`,`IR`,`UV`,`HUM`) 
VALUES ('2', '1', '2', now(), '1','1','1','1','1','1','1','1');