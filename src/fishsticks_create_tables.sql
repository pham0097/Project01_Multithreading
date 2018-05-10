DROP DATABASE IF EXISTS assignment1;

CREATE DATABASE assignment1;

USE assignment1;

CREATE TABLE FishSticks(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	recordnumber int  NOT NULL,
	omega VARCHAR(42) NOT NULL,
	lambda VARCHAR(42) NOT NULL,
	uuid VARCHAR(42) NOT NULL
);

