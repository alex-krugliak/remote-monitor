-- noinspection SqlDialectInspectionForFile
INSERT INTO _user (id, user_name, password, enabled) VALUES (1,'admin', '1dfet$RT2az4', true);

INSERT INTO authoritie (id, user_id, authority) VALUES (1,1, 'ROLE_ADMIN');
INSERT INTO authoritie (id, user_id, authority) VALUES (2,1, 'ROLE_USER');

CREATE USER monitor WITH CREATEDB PASSWORD "some_password";
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO monitor;

sudo -u user_name psql db_name
ALTER USER "user_name" WITH PASSWORD 'new_password';


--sudo -u postgres psql		//sudo -u <user> psql
--CREATE DATABASE tags;  		//create database
--\list    			//list all databases

create table "_user"
(
  id        bigint not null
    constraint "_user_pkey"
    primary key,
  enabled   boolean,
  password  varchar(255),
  user_name varchar(255)
);

create table authoritie
(
  id        bigint not null
    constraint authoritie_pkey
    primary key,
  authority varchar(255),
  user_id   bigint
    constraint fk_k3l5ttabsnryo69ed47d678pd
    references "_user"
);

create table day_total
(
  id                              bigint not null
    constraint day_total_pkey
    primary key,
  average_speed1                  double precision,
  average_speed2                  double precision,
  downtime_line1                  bigint,
  down_time_line2                 bigint,
  expenditure_of_material_line1   double precision,
  expenditure_of_material_line2   double precision,
  period_work_with_material_line1 bigint,
  period_work_with_material_line2 bigint,
  time_power_off1                 bigint,
  time_power_off2                 bigint,
  time_stamp                      date   not null
    constraint uk_43dvrwill2bksan67v2g3i0fw
    unique,
  turn_off_time_line1             timestamp,
  turn_off_time_line2             timestamp,
  turn_on_time_today_line1        timestamp,
  turn_on_time_today_line2        timestamp
);

create table tag
(
  id                            bigint    not null
    constraint tag_pkey
    primary key,
  connection_ok                 boolean,
  current_speed_line1           double precision,
  current_speed_line2           double precision,
  expenditure_of_material_line1 double precision,
  expenditure_of_material_line2 double precision,
  line1_on_off                  boolean,
  line2_on_off                  boolean,
  power_ok                      boolean,
  time_stamp                    timestamp not null,
  with_material_line1           boolean,
  with_material_line2           boolean
);

create table event
(
  id          bigint not null
    constraint event_pkey
    primary key,
  description varchar(255),
  tag_id      bigint
    constraint fk_1861p7v3rmg0lv7ejmn1fy50h
    references tag
);

create table expenditure
(
  id                bigint    not null
    constraint expenditure_pkey
    primary key,
  expenditure_line1 double precision,
  expenditure_line2 double precision,
  time_stamp        timestamp not null
);

create table module_counter
(
  module_id        bigint  not null
    constraint module_counter_pkey
    primary key,
  counter_i1       integer not null,
  counter_i5       integer not null,
  total_counter_i1 bigint  not null,
  total_counter_i5 bigint  not null
);

create table token
(
  id          bigint not null
    constraint token_pkey
    primary key,
  date        timestamp,
  token_value varchar(255),
  user_id     bigint
    constraint fk_g7im3j7f0g31yhl6qco2iboy5
    references "_user"
);
