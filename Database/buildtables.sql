
-- Name: 	buildtables.sql
-- Authors:	Kenny and Nathan
-- Date:	September 2014
-- Description:	This script is intended to remove any existing tables, create the
--		tables and associations for the Web Reservation service, and 
--		populate the tables with data.
-- Assumptions:	Run in SQLite3


-- Turn on foreign key support
PRAGMA foreign_keys = ON;

-- Designate file name for SQLite
.open WebReserve.db
-- Create tables and insert business data

-- Price
drop table if exists Price;
create table Price(
	Time		text primary key,
	PriceRate	numeric unique
	);
insert into Price values
	('9:00AM',1.15),
	('1:00PM',1.5),
	('5:00PM',1),
	('8:00PM',.85);
-- Mileage
drop table if exists Mileage;
create table Mileage(
	ID		integer primary key,	
	LocationA	text,
	LocationB	text,
	Distance	numeric unique
	);
insert into Mileage values
	(NULL,'Atlanta','Chicago',586),
	(NULL,'Atlanta','Dallas',721),
	(NULL,'Atlanta','New York',746),
	(NULL,'Atlanta','San Francisco',2140),
	(NULL,'Chicago','Dallas',802),
	(NULL,'Chicago','New York',714),
	(NULL,'Chicago','San Francisco',1858),
	(NULL,'Dallas','New York',1373),
	(NULL,'Dallas','San Francisco',1483),
	(NULL,'New York','San Francisco',2572);
-- Airline
drop table if exists Airline;
create table Airline(
	Name		text primary key,
	Information	text
	);
insert into Airline values
	('American','insert description here'),
	('Delta','insert description here'),
	('Southwest','insert description here');
-- Customer
drop table if exists Customer;
create table Customer(
	ID		text primary key,
	FirstName	text,
	LastName	text,
	Email		text,
	Password	text
	);
insert into Customer values
	('admin','John','Doe','johndoe@gmail.com','root');
-- Airline Admin
drop table if exists AirlineAdmin;
create table AirlineAdmin(
	ID		text primary key,
	AirlineName	text,
	Password	text,
	foreign key (AirlineName) references Airline(Name)
		on delete cascade on update cascade
	);
insert into AirlineAdmin values
	('admin','Delta','root');
-- Marketing Admin
drop table if exists MarketingAdmin;
create table MarketingAdmin(
	ID		text primary key,
	Password	text
	);
insert into MarketingAdmin values
	('admin','root');
-- Flight
drop table if exists Flight;
create table Flight(
	FlightNumber	integer primary key,
	Date		text not null,
	Time		text not null,
	AirlineName	text not null,
	StartLocation	text not null,
	Destination	text not null,
	Mileage		numeric not null,
	RemainingFirstClass integer default 30,
	RemainingEconomy integer default 70,
	Price		numeric default 0,
	foreign key (Time) references Price(Time)
		on delete cascade on update cascade,
	foreign key (AirlineName) references Airline(Name)
		on delete cascade on update cascade
	foreign key (Mileage) references Mileage(Distance)
		on delete cascade on update cascade
	);
insert into Flight (Date,Time,AirlineName,StartLocation,Destination,Mileage) values
	('1/1/14','9:00AM','Delta','Atlanta','Chicago',(select Distance from Mileage where LocationA='Atlanta'and LocationB='Chicago' or LocationB='Atlanta' and LocationA='Chicago')),
	('1/2/14','9:00AM','American','Dallas','San Francisco',(select Distance from Mileage where LocationA='Dallas'and LocationB='San Francisco' or LocationB='Dallas' and LocationA='San Francisco'));

-- Reservation
drop table if exists Reservation;
create table Reservation(
	ID		integer primary key,
	CustomerID	text,
	FlightNumber	integer,
	foreign key (CustomerID) references Customer(ID)
		on delete cascade on update cascade,
	foreign key (FlightNumber) references Flight(FlightNumber)
		on delete cascade on update cascade
	);
insert into Reservation values
	(NULL,'admin',1);
-- Service
drop table if exists Service;
create table Service(
	ID		integer primary key,
	City		text,
	AdminID		text,
	Type		text,
	Name		text,
	Description	text,
	URL		text,
	
	foreign key (AdminID) references MarketingAdmin(ID)
		on delete cascade on update cascade
	);
insert into Service values
	(NULL,'Chicago','admin','Hotel','Holiday Inn','A nice yet affordable place to stay','www.holidayinn.com');