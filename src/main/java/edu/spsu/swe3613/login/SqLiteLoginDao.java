package edu.spsu.swe3613.login;

import java.sql.SQLException;
import java.sql.Timestamp;

public class SqLiteLoginDao implements LoginDao {

	@Override
	public Boolean addOrUpdateLogin(String email, Timestamp loginTime)
			throws SQLException {
		// TODO Auto-generated method stub
		return true;
	}

}
