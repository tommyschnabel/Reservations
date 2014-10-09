package edu.spsu.swe3613.reservations;

import java.sql.SQLException;

public class Flight {
	private int id; //changed from String to int CHECK HERE FOR ERRORS 10.9.2014
	private String date; //This gives us Year, Month, Day, Hour, and Minutes.
	
	private float price;
	private String destination;
	private String startingCity;
	
	public Flight(int id, String date, float price, String destination, String start)
	{
		this.id = id;
		
		this.date = date; //Date and time are now saved together.
		
		this.price = price;
		this.destination = destination;
		this.startingCity = start;
	}
	
	public void setPrice(float price){
		this.price = price;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public int getFirstClass() {
		return firstClass;
	}

	public void setFirstClass(int firstClass) {
		this.firstClass = firstClass;
	}

	public int getEconomy() {
		return economy;
	}

	public void setEconomy(int economy) {
		this.economy = economy;
	}
	
	public int getID(){
	
		return id;
	}
	
	public String getDate(){
		
		return date;
	}
	
	public float getPrice(){
		
		return price;
	}
	
	public String getDestination(){
		
		return destination;
	}
	
	public String getStarting_City(){
		
		return startingCity;
	}
	
	@Override
	public boolean equals(Object obj) {
		Flight that;
		
		try {
			that = (Flight) obj;
		} catch (Exception e) {
			return false;
		}

		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (this.id == that.id 
				&& this.getDate().equals(that.getDate())
				&& this.getAirline().equals(that.getAirline())
				&& this.getStartingCity().equals(that.getStartingCity())
				&& this.getDestination().equals(that.getDestination())
				&& this.getDistance() == that.getDistance()
				&& this.getFirstClass() == that.getFirstClass()
				&& this.getEconomy() == that.getEconomy()
				&& this.getPrice() == that.getPrice()) 
		
		{
			return true;
		}
		
		return false;
	}
}
