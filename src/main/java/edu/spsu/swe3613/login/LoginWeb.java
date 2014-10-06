package edu.spsu.swe3613.login;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.inject.Inject;
import com.sun.jersey.api.core.InjectParam;

@Path("login/")
public class LoginWeb {
	
	private DefaultLoginService loginService;

	@Inject
	public LoginWeb(@InjectParam DefaultLoginService loginService) {
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
		return "works";
	}
	
}
