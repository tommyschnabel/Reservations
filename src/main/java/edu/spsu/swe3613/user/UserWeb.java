package edu.spsu.swe3613.user;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user/")
public class UserWeb {
	
	private UserService userService;

	@Inject
	public UserWeb(UserService userService) {
		this.userService = userService;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("login/")
	public User login(LoginParams loginParams) {
		return userService.login(loginParams);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("register/")
	public Response.Status register(User newUser) {
		return userService.register(newUser);
	}
	
	
}
