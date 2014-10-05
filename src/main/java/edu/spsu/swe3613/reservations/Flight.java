package edu.spsu.swe3613.reservations;


import java.util.GregorianCalendar;
import com.google.inject.Inject;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.ResponseBuilder;

public class Flight {
	private String ID;
	private String Date; //This gives us Year, Month, Day, Hour, and Minutes.
	
	private float Price;
	private String Destination;
	private String Starting_City;
	private ReservationsDao DAO;
	
	
	public Flight(String id, String date, float price, String destination, String start)
	{
		this.ID = id;
		
		this.Date = date; //Date and time are now saved together.
		
		this.Price = price;
		this.Destination = destination;
		this.Starting_City = start;
	}
	
	public String getID(){
	
		return ID;
	}
	
	public String getDate(){
		
		return Date;
	}
	
	public float getPrice(){
		
		return Price;
	}
	
	public String getDestination(){
		
		return Destination;
	}
	
	public String getStarting_City(){
		
		return Starting_City;
	}
	
	@Inject
	public Flight(ReservationsDao dao){
		this.DAO = dao;
		
	}


	
}
