package edu.spsu.swe3613.examples;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

//I figured this would be a good place to test whether or not Jersey is working
//Well maybe not the best place, but nonetheless, a place to do that
@Path("/test")
public class ExampleWeb {

	@GET
	public String test() {
		return "PASS";
	}
}
