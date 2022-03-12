CREATE DATABASE IF NOT EXISTS kidsrewards_db;

CREATE USER IF NOT EXISTS kidsrewards_user@localhost IDENTIFIED BY 'p@$$w0rd';
GRANT ALL ON kidsrewards_db.* TO kidsrewards_user@localhost;

USE kidsrewards_db;

INSERT INTO wishitems(title, body)
VALUES
    ('Title 1', 'Body 1'),
    ('Title 2', 'Body 2'),
    ('Title 3', 'Body 3'),
    ('Title 4', 'Body 4'),
    ('Title 5', 'Body 5');

DESCRIBE wishitems;