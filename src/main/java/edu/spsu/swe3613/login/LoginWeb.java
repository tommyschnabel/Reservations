package edu.spsu.swe3613.login;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("login/")
public class LoginWeb {
	
	private LoginService loginService;

	@Inject
	public LoginWeb(LoginService loginService) {
		this.loginService = loginService;
	}

	@POST
	public Boolean login(LoginParams loginParams) {
		return loginService.login(loginParams);
	}
	
	@GET
	@Path("test/")
	@Produces("text/plain")
	public String test() {
		System.out.println("it got to the server");
		return "works";
	}
	
}
