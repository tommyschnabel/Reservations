package edu.spsu.swe3613.reservations;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("reservations/")
public class ReservationsWeb {

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
	public List<Flight> search() {
		//Start with one search param then move on!!!
		return new ArrayList<Flight>();
	}
	
}
