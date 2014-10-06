package edu.spsu.swe3613.login;

import java.sql.SQLException;
import java.sql.Timestamp;

public interface LoginDao {

	/**
	 * 
	 * @param email The email of the person logging in 
	 * @param loginTime The time that the person is logging in at
	 * @return Whether the save was successful or not
	 * @throws SQLException Thrown if something goes wrong
	 */
	public Boolean addOrUpdateLogin(String email, Timestamp loginTime) throws SQLException;
}
