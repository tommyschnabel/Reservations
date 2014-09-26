package edu.spsu.swe3613.examples;

import java.sql.SQLException;

import com.google.inject.Inject;

public class ExampleService implements Service {
	
	private ExampleDao exampleDao;
	
	@Inject
	public ExampleService(ExampleDao exampleDao) {
		this.exampleDao = exampleDao;
	}

	@Override
	public User getUserById(Integer userId) {
		
		if (userId == null) {
			return null;
		}
		
		//We'll catch the exception up here because we
		//may handle the exception by saying that there's
		//no user with this id and then do some stuff with
		//that
		try {
			return exampleDao.getUserById(userId);
		} catch (SQLException e) {
			return null;
		}
	}

}
