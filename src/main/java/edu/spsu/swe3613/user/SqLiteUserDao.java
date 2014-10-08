package edu.spsu.swe3613.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SqLiteUserDao implements UserDao {
	
	private Connection connection;
	
	@Inject
	public SqLiteUserDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Boolean addOrUpdateLogin(String email, Timestamp loginTime)
			throws SQLException {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public List<User> getAllUsers() throws SQLException {
		List<User> users = new ArrayList<User>();
		String query = 	"SELECT"
				+		"	Customer.ID				id,"
				+	 	"	Customer.FirstName		fname,"
				+ 		"	Customer.LastName		lname,"
				+ 		"	Customer.Email			email,"
				+ 		"	Customer.Password		password"
				
				+ 		" FROM Customer";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		while(rs.next())
		{
			users.add(new User(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("password")));
		}
		statement.close();
		return users;
	}

	@Override
	public User getUserById(Integer userId) throws SQLException {
		String query =  "SELECT" //Here we make our query
				+ 		"	Customer.FirstName		fname,"
				+		"	Customer.LastName		lname,"
				+		"	Customer.Email			email,"
				+		"	Customer.Password		password"
				
				+		"  FROM Customer"
				+	    " WHERE ID = "+"'"+userId+"'";
		//String.format(query, customerId); //Here we replace '%s with the userId that was passed in
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query); //Here we execute the query and get back the results
		
		//Now we make our Customer and then return it
		User resultCustomer = new User(userId, rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("password"));
		statement.close();
		return resultCustomer;
		
	}

	@Override
	public void addUser(User user) throws SQLException {
		String query = 	"INSERT"
				+ 		" INTO Customer Values("
				+ 		"'"+user.getId()+"'"	+	","
				+		"'"+user.getFName()+"'"	+	"," 
				+		"'"+user.getLName()+"'"	+	","
				+		"'"+user.getEmail()+"'"	+	","
				+		"'"+user.getPassword()+"'"	+	")";
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public void updateUser(User user) throws SQLException {
		String query = 	"UPDATE "
				+		"Customer SET "
				+ 			" FirstName="	+"'"+user.getFName()+"'"		+	","
				+ 			" LastName="	+"'"+user.getLName()+"'"		+	","
				+ 			" Email="		+"'"+user.getEmail()+"'"		+	","
				+ 			" Password="	+"'"+user.getPassword()+"'"
				+			" WHERE Id="	+	"'"+user.getId()+"'";
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public void deleteUser(User user) throws SQLException {
		String query = 	"DELETE FROM Customer WHERE"
				+ 		" Id="	+	user.getId();
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

}
