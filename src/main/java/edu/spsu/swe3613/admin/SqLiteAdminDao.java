package edu.spsu.swe3613.admin;

import com.google.inject.Inject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqLiteAdminDao implements AdminDao {

    private Connection connection;

    @Inject
    public SqLiteAdminDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<AirlineAdmin> getAllAirlineAdmins() throws SQLException {
        List<AirlineAdmin> admins = new ArrayList<AirlineAdmin>();
        String query = 	"SELECT"
                +		"	AirlineAdmin.ID				id,"
                + 		"	AirlineAdmin.Airline		airline,"
                + 		"	AirlineAdmin.Password		password"

                + 		" FROM AirlineAdmin";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);

        while(rs.next())
        {
            admins.add( new AirlineAdmin(rs.getString("id"), rs.getString("airline"), rs.getString("password")));
        }
        statement.close();
        return admins;
    }

    @Override
    public AirlineAdmin getAirlineAdminById(String userId) throws SQLException{
        String query =  "SELECT" //Here we make our query
                +		"	AirlineAdmin.ID				id,"
                + 		"	AirlineAdmin.Airline		airline,"
                + 		"	AirlineAdmin.Password		password"

                +		"  FROM AirlineAdmin"
                +	    " WHERE ID = "+"'"+userId+"'";
        String.format(query, userId);

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);

        AirlineAdmin resultAdmin = new AirlineAdmin(rs.getString("id"), rs.getString("airline"), rs.getString("password"));
        statement.close();
        return resultAdmin;
    }

    @Override
    public AirlineAdmin addAirlineAdmin(AirlineAdmin admin) throws SQLException{
        String query = 	"INSERT"
                + 		" INTO AirlineAdmin Values("
                + 		"'"+admin.getId()+"'"		+	","
                +		"'"+admin.getAirline()+"'"	+	","
                +		"'"+admin.getPassword()+"'"	+	")";
        String query2 = "SELECT * FROM AirlineAdmin WHERE "
                + "(ID='"+admin.getId()+"' AND Airline='"+admin.getAirline()+"' AND Password='"+admin.getPassword()+"')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        ResultSet rs = statement.executeQuery(query2);
        AirlineAdmin resultAdmin = new AirlineAdmin(rs.getString(1),rs.getString(2),rs.getString(3));
        statement.close();
        return resultAdmin;
    }

    @Override
    public void updateAirlineAdmin(AirlineAdmin admin) throws SQLException{
        String query = 	"UPDATE "
                +		" AirlineAdmin SET"
                + 			" Airline	="	+	"'"+admin.getAirline()+"'"		+	","
                + 			" Password="	+	"'"+admin.getPassword()+"'"

                +			" WHERE Id="	+	"'"+admin.getId()+"'";

        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }

    @Override
    public void deleteAirlineAdmin(AirlineAdmin admin) throws SQLException{
        String query = 	"DELETE FROM AirlineAdmin WHERE"
                + 		" Id="	+"'"+admin.getId()+"'";

        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }

}
