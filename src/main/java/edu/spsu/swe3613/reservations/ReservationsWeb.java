package edu.spsu.swe3613.reservations;

import java.util.ArrayList;
import java.util.List;
import java.util.GregorianCalendar;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import edu.spsu.swe3613.examples.Search;


@Path("reservations/")
public class ReservationsWeb {
	Search search_price;

	@GET
	@Path("test/")
	public String test() {
		return "the test, it works!";
	}
	
	@GET
	public String test2() {
		return "it works!";
	}
	
	@GET
	@Path("search/")
	public List<Flight> search(float p) {
		//Start with one search param then move on!!!
		//Start with all flights that are less than or equal to given price
		//ArrayList<Flight> All_Flights = new ArrayList<Flight>();
		//ArrayList<Flight> search_Flights = new ArrayList<Flight>();
		//@Inject
		//All_Flights = public List<Flight> getAllFlights()
		
		
		return search_price.Search_By_Price(p);
		
		
		
		
	}
	
	@GET
	@Path("search/")
	public List<Flight> search(String d) {
		//Start with one search param then move on!!!
		//ArrayList<Flight> All_Flights = new ArrayList<Flight>();
		
		
		
		return search_price.Search_By_Date(d);
	}
	
}
