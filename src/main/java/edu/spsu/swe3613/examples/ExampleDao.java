package edu.spsu.swe3613.examples;

import java.sql.SQLException;

public interface ExampleDao {
	
	public ExampleUser getUserById(Integer userId) throws SQLException; 
}
