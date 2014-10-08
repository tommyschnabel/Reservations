package edu.spsu.swe3613.reservations;



//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.ResponseBuilder;

public class Flight {
	private String id;
	private String date; //This gives us Year, Month, Day, Hour, and Minutes.
	
	private float price;
	private String destination;
	private String startingCity;
	
	
	
	public Flight(String id, String date, float price, String destination, String start)
	{
		this.id = id;
		
		this.date = date; //Date and time are now saved together.
		
		this.price = price;
		this.destination = destination;
		this.startingCity = start;
	}
	
	public String getID(){
	
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
	
	


	
}
