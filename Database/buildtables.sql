
-- Name: 	buildtables.sql
-- Authors:	Kenny and Nathan
-- Date:	September 2014
-- Description:	This script is intended to remove any existing tables, create 
--		the tables and associations for the Web Reservation service, 
--		and populate the tables with Pricing and Mileage data.
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
	('0900',1.15),
	('1300',1.5),
	('1700',1),
	('2100',.85);
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
	(NULL,'Atlanta','NewYork',746),
	(NULL,'Atlanta','SanFrancisco',2140),
	(NULL,'Chicago','Dallas',802),
	(NULL,'Chicago','NewYork',714),
	(NULL,'Chicago','SanFrancisco',1858),
	(NULL,'Dallas','NewYork',1373),
	(NULL,'Dallas','SanFrancisco',1483),
	(NULL,'NewYork','SanFrancisco',2572);
-- Airline
drop table if exists Airline;
create table Airline(Name text primary key);
insert into Airline values
	('Delta'),
	('Southwest'),
	('American');
-- User
drop table if exists User;
create table User(
	ID		integer primary key,
	FirstName	text,
	LastName	text,
	Email		text,
	Password	text,
	IsAdmin		integer
	);
-- Airline Admin
drop table if exists AirlineAdmin;
create table AirlineAdmin(
	ID		text primary key,
	Airline		text,
	Password	text
	);
	
-- Flight
drop table if exists Flight;
create table Flight(
	ID		integer primary key,
	Date		text not null,
	AirlineName	text not null,
	StartLocation	text not null,
	Destination	text not null,
	Mileage		numeric not null,
	RemainingFirstClass integer default 30,
	RemainingEconomy integer default 70,
	Price		numeric default 0,
	
	foreign key (AirlineName) references Airline(Name)
		on delete cascade on update cascade
	foreign key (Mileage) references Mileage(Distance)
		on delete cascade on update cascade
	);
-- Reservation
drop table if exists Reservation;
create table Reservation(
	ID		integer primary key,
	CustomerID	integer,
	FlightID	integer,
	FlightClass	text,
	foreign key (CustomerID) references Customer(ID)
		on delete cascade on update cascade,
	foreign key (FlightID) references Flight(ID)
		on delete cascade on update cascade
	);