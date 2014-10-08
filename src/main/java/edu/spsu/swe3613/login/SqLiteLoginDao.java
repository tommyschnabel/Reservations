package edu.spsu.swe3613.login;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.inject.Inject;

public class SqLiteLoginDao implements LoginDao {
	
	private Connection connection;
	
	@Inject
	public SqLiteLoginDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Boolean addOrUpdateLogin(String email, Timestamp loginTime)
			throws SQLException {
		// TODO Auto-generated method stub
		return true;
	}

}
