/*
Name: 		displaytables.sql
Authors:	Kenny and Nathan
Date:		September 2014
Description:	This script is intended to display all of the stored data for testing purposes
Assumptions:	Run from command prompt using SQLite3
*/

-- Turn on headers, column mode, and adjust column width for better view
.header on
.mode column
.width 25

-- Select database file
.open WebReserve.db

-- Enter select all statements for each table in the database
select * from Customer;
select * from AirlineAdmin;
select * from Airline;
select * from MarketingAdmin;
select * from Reservation;
select * from Flight;
select * from Service;
select * from Price;
select * from Mileage;