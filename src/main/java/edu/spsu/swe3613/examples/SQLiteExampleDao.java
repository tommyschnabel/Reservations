package edu.spsu.swe3613.examples;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.inject.Inject;

public class SQLiteExampleDao implements ExampleDao {
	private Connection connection;
	
	//We inject the connection so we can switch it up if we need to
	//And if we do that then we don't have to switch it in multiple places
	//Just in the module
	@Inject
	public SQLiteExampleDao(Connection connection) {
		this.connection = connection;
	}

	//This throws an SQLException because we want stuff to break
	//And us be notified if something goes wrong
	@Override
	public User getUserById(Integer userId) throws SQLException {
		String query =  "SELECT" //Here we make our query
				+		"          user.id   id"
				+ 		"          user.name name"
				
				+		"  FROM schema.user user"
				+	    " WHERE user.id = %s";
		String.format(query, userId); //Here we replace '%s with the userId that was passed in
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query); //Here we execute the query and get back the results
		
		//Now we make our User and then return it
		User resultUser = new User(Integer.valueOf(rs.getInt("id")));
		resultUser.setName(rs.getString("name"));
		return resultUser;
	}

}
