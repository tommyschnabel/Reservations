package main.java.reservations;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("reservations/")
public class ReservationsWeb {

	@GET
	@Path("test/")
	public String test() {
		return "it works!";
	}
	
}
