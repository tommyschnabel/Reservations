package edu.spsu.swe3613.login;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.google.inject.Inject;

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
}
