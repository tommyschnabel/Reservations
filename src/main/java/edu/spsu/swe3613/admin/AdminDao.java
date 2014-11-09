package edu.spsu.swe3613.admin;

import java.sql.SQLException;
import java.util.List;

public interface AdminDao {

    //Methods for AirlineAdmin
    public List<AirlineAdmin> getAllAirlineAdmins() throws SQLException;
    public AirlineAdmin getAirlineAdminById(String userId) throws SQLException;
    public AirlineAdmin addAirlineAdmin(AirlineAdmin admin) throws SQLException;
    public void updateAirlineAdmin(AirlineAdmin admin) throws SQLException;
    public void deleteAirlineAdmin(AirlineAdmin admin) throws SQLException;
}
