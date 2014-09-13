package main.java.reservations;

import javax.ws.rs.Path;

@Path("reservations/")
public class ReservationsWeb {

	@Path("test/")
	public String test() {
		return "it works!";
	}
	
}
