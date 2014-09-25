package edu.spsu.swe3613.reservations;

//import java.util.Calendar;
import java.util.GregorianCalendar;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.ResponseBuilder;

public class Flight {
	private int ID;
	private GregorianCalendar Date; //Change data type?
	//private String Time; //Change data type?
	private float Price;
	private String Destination;
	private String Starting_City;
	
	
	public Flight(int id, int year, int month, int day, int hour, int minute, float price, String destination, String start)
	{
		ID = id;
		
		Date = new GregorianCalendar(year, month, day, hour, minute, 0); //Date and time are now saved together.
		//Time = time;
		Price = price;
		Destination = destination;
		Starting_City = start;
	}


	
}
