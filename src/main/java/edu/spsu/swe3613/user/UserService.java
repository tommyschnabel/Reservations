package edu.spsu.swe3613.user;

import javax.ws.rs.core.Response;

public interface UserService {

	/**
	 * @param loginParams The email and password of the person loggin in
	 * @return Whether or not the person was successfully logged in
	 */
	public Boolean login(LoginParams loginParams);
	
	/**
	 * @param customerId The user to register
	 */
	public Response.Status register(User user);
	
	/**
	 * @param customerId
	 */
	public User getUserById(Integer userId);
}
