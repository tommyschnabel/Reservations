package edu.spsu.swe3613.login;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("login/")
public class LoginWeb {
	
	private LoginService loginService;

	@Inject
	public LoginWeb(LoginService loginService) {
		this.loginService = loginService;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean login(LoginParams loginParams) {
		return loginService.login(loginParams);
	}
	
}
