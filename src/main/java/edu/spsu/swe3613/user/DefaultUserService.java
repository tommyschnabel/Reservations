package edu.spsu.swe3613.user;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;

public class DefaultUserService implements UserService {
	
	private UserDao userDao;
	
	@Inject
	public DefaultUserService(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User login(LoginParams loginParams) {
		List<User> users;
		Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());
		try {
			users = userDao.getAllUsers();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		
		for (User user : users) {
			if (user.getEmail().equals(loginParams.getEmail()) && user.getPassword().equals(loginParams.getPassword())) {
				try {
					userDao.addOrUpdateLogin(loginParams.getEmail(), time);
                    System.out.println("Logged in user " + user.getFName() + " " + user.getLName());
                    return user;
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					return null;
				}
			}
		}
        return null;
	}

	@Override
	public Response.Status register(User newUser) {
		try {
			List<User> allUsers = userDao.getAllUsers();
			Integer newUserId = (allUsers.get(allUsers.size() -1).getId()) + 1;
			newUser.setId(newUserId);
			
			if (!validateUserDoesNotExist(allUsers, newUser)) {
                return Status.NOT_ACCEPTABLE;
            }
			userDao.addUser(newUser);
			System.out.println("Registered user " + newUser.getFName() + " " + newUser.getLName());
			return Status.OK;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Status.NOT_ACCEPTABLE;
		}
	}

	@Override
	public User getUserById(Integer customerId) {
		try {
			return userDao.getUserById(customerId);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null; //May change to throwing a runtime exception, but 
						 // we don't want to break absolutely everything...
		}
	}
	
	private boolean validateUserDoesNotExist(List<User> allUsers, User newUser) throws Exception {
		for (User u : allUsers) {
			//We want to allow users to have same first/last name, or have the same password
			// so we only check the id and the email of the new user
			if (u.getId().equals(newUser.getId()) || u.getEmail().equals(newUser.getEmail())) {
				return false;
			}
		}
        return true;
	}

}
