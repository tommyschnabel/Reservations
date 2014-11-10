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
				+		"	User.ID				id,"
				+	 	"	User.FirstName		fname,"
				+ 		"	User.LastName		lname,"
				+ 		"	User.Email			email,"
				+ 		"	User.Password		password,"
				+		"	User.IsAdmin		isAdmin"	
				
				+ 		" FROM User";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		while(rs.next())
		{
			User resultUser = new User(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("password"));
			resultUser.setAdmin((rs.getInt("isAdmin")==1)? true :false );
			users.add(resultUser);
		}
		statement.close();
		return users;
	}

	@Override
	public User getUserById(Integer userId) throws SQLException {
		String query =  "SELECT" //Here we make our query
				+ 		"	User.FirstName		fname,"
				+		"	User.LastName		lname,"
				+		"	User.Email			email,"
				+		"	User.Password		password,"
				+		"	User.IsAdmin		isAdmin"
				
				+		"  FROM User"
				+	    " WHERE ID = "+"'"+userId+"'";
		//String.format(query, customerId); //Here we replace '%s with the userId that was passed in
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query); //Here we execute the query and get back the results
		
		//Now we make our Customer and then return it
		User resultCustomer = new User(userId, rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("password"));
		resultCustomer.setAdmin(rs.getInt("isAdmin")==1?true:false);
		statement.close();
		return resultCustomer;
		
	}

	@Override
	public void addUser(User user) throws SQLException {
		String query = 	"INSERT INTO User "
							+ "Values("
										+user.getId()+",'"
										+user.getFName()+"','" 
										+user.getLName()+"','"
										+user.getEmail()+"','"
										+user.getPassword()+"',"
										+((user.isAdmin())? 1:0)+")";
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public void updateUser(User user) throws SQLException {
		String query = 	"UPDATE User "
							+ "SET "
										+"FirstName='"	+user.getFName()+	"',"
										+"LastName='" 	+user.getLName()+	"',"
										+"Email='"	   	+user.getEmail()+	"',"
										+"Password='"	+user.getPassword()+"',"
										+"IsAdmin="		+((user.isAdmin())? 1:0)	
							+"WHERE Id="	+user.getId();
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public void deleteUser(User user) throws SQLException {
		String query = 	"DELETE FROM User "
					  + "WHERE Id="		+user.getId();
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

}
