-- noinspection SqlDialectInspectionForFile
INSERT INTO _user (id, user_name, password, enabled) VALUES (1,'admin', '1dfet$RT2az4', true);

INSERT INTO authoritie (id, user_id, authority) VALUES (1,1, 'ROLE_ADMIN');
INSERT INTO authoritie (id, user_id, authority) VALUES (2,1, 'ROLE_USER');


INSERT INTO tag (id, time_stamp, current_speed_line1, line1_on_off, with_material_line1,
                 expenditure_of_material_line1, current_speed_line2, line2_on_off, with_material_line2,
                 expenditure_of_material_line2, connection_ok, power_ok) VALUES (1, NOW(),0.0, false, false, 0.0, 0.0, false, false, 0.0, false, false);
--sudo -u postgres psql		//sudo -u <user> psql
--CREATE DATABASE tags;  		//create database
--\list    			//list all databases

--sudo -u postgres psql tags
--tags=# INSERT INTO authoritie (id, user_id, authority) VALUES (1,1, 'ROLE_ADMIN');

