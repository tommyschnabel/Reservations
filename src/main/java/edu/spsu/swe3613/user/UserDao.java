package edu.spsu.swe3613.user;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface UserDao {

	/**
	 * 
	 * @param email The email of the person logging in 
	 * @param loginTime The time that the person is logging in at
	 * @return Whether the save was successful or not
	 * @throws SQLException Thrown if something goes wrong
	 */
	public Boolean addOrUpdateLogin(String email, Timestamp loginTime) throws SQLException;
	
	//Methods for Customer
	public List<User> getAllUsers() throws SQLException;
	public User getUserById(Integer userId) throws SQLException;
	public void addUser(User user) throws SQLException;
	public void updateUser(User user) throws SQLException;
	public void deleteUser(User user) throws SQLException;
}
