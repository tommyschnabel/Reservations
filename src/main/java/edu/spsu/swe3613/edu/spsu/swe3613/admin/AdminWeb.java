package edu.spsu.swe3613.edu.spsu.swe3613.admin;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("admin/")
public class AdminWeb {

    private AdminService adminService;

    @Inject
    public AdminWeb(AdminService adminService) {
        this.adminService = adminService;
    }

    @GET
    public List<AirlineAdmin> getAdmins() {
        return adminService.getAllAirlineAdmins();
    }

    @POST
    @Path("add/")
    public Response.Status addAirlineAdmin(AirlineAdmin admin) {
        adminService.addAirlineAdmin(admin);
        return Response.Status.ACCEPTED;
    }

    @POST
    @Path("update/")
    public Response.Status updateAirlineAdmin(AirlineAdmin admin) {
        return adminService.updateAirlineAdmin(admin);
    }

    @DELETE
    public Response.Status deleteAirlineAdmin(AirlineAdmin admin) {
        return adminService.deleteAirlineAdmin(admin);
    }
}
