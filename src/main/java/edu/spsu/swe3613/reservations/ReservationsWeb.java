package edu.spsu.swe3613.reservations;

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
	
}
