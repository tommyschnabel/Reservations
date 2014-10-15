package edu.spsu.swe3613.user;

import javax.ws.rs.core.Response;

public interface UserService {

	/**
	 * @param loginParams The email and password of the person loggin in
	 * @return Whether or not the person was successfully logged in
	 */
	public User login(LoginParams loginParams);
	
	/**
	 * @param user The user to register
	 */
	public Response.Status register(User user);
	
	/**
	 * @param userId The user's id
	 */
	public User getUserById(Integer userId);
}
