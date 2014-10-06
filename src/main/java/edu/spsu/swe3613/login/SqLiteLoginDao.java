package edu.spsu.swe3613.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SqLiteLoginDao implements LoginDao {
	
	private Connection connection;
	
	public SqLiteLoginDao() {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:WebReserve.db");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public Boolean addOrUpdateLogin(String email, Timestamp loginTime)
			throws SQLException {
		// TODO Auto-generated method stub
		return true;
	}

}
