-- noinspection SqlDialectInspectionForFile
INSERT INTO _user (id, username, password, enabled) VALUES (1,'admin', '1dfet$RT2az4', true);

INSERT INTO authoritie (id, user_id, authority) VALUES (1,1, 'ROLE_ADMIN');
INSERT INTO authoritie (id, user_id, authority) VALUES (2,1, 'ROLE_USER');

--sudo -u postgres psql		//sudo -u <user> psql
--CREATE DATABASE tags;  		//create database
--\list    			//list all databases

--sudo -u postgres psql tags
--tags=# INSERT INTO authoritie (id, user_id, authority) VALUES (1,1, 'ROLE_ADMIN');