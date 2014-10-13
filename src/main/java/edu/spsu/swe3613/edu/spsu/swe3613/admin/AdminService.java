package edu.spsu.swe3613.edu.spsu.swe3613.admin;

import javax.ws.rs.core.Response;
import java.util.List;

public interface AdminService {

    public List<AirlineAdmin> getAllAirlineAdmins();
    public AirlineAdmin getAirlineAdminById(String userId);
    public AirlineAdmin addAirlineAdmin(AirlineAdmin admin);
    public Response.Status updateAirlineAdmin(AirlineAdmin admin);
    public Response.Status deleteAirlineAdmin(AirlineAdmin admin);
}
