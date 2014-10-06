package edu.spsu.swe3613.login;

public interface LoginService {

	/**
	 * @param loginParams The email and password of the person loggin in
	 * @return Whether or not the person was successfully logged in
	 */
	public Boolean login(LoginParams loginParams);
}
