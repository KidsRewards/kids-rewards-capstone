DROP DATABASE IF EXISTS kidsrewards_db;

CREATE DATABASE IF NOT EXISTS kidsrewards_db;

USE kidsrewards_db;

CREATE USER IF NOT EXISTS 'kidsrewards_user'@'localhost' IDENTIFIED BY 'p@$$w0rd';
GRANT ALL ON kidsrewards_db.* TO 'kidsrewards_user'@'localhost';

