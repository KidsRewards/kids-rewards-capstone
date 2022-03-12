DROP DATABASE IF EXISTS kidsrewards_db;

CREATE DATABASE IF NOT EXISTS kidsrewards_db;

CREATE USER IF NOT EXISTS kidsrewards_user@localhost IDENTIFIED BY 'p@$$w0rd';
GRANT ALL ON kidsrewards_db.* TO kidsrewards_user@localhost;

USE kidsrewards_db;

INSERT INTO rewards(title, body, icon, points)
VALUES
       ('Title 1', 'Body 1', 'icon1 filepath', 10),
       ('Title 2', 'Body 2', 'icon2 filepath', 15),
       ('Title 3', 'Body 3', 'icon3 filepath', 5),
       ('Title 4', 'Body 4', 'icon4 filepath', 25),
       ('Title 5', 'Body 5', 'icon5 filepath', 30);

