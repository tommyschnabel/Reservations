package edu.spsu.swe3613.admin;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import edu.spsu.swe3613.reservations.Flight;

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
        return Response.Status.OK;
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
    
    @POST
    @Path("flight/create")
    public Response.Status createFlight(Flight flight) {
    	System.out.println("Creating flight on date " + flight.getDate());
        return adminService.createFlight(flight);
    }
    
    @DELETE
    @Path("flight/delete/")
    public Response.Status deleteFlight(@QueryParam("flightId") int flightId) {
    	return adminService.deleteFlight(flightId);
    }
    
    @DELETE
    @Path("reservation/delete/")
    public Response.Status deleteReservation(@QueryParam("reservaionId") int reservationId) {
    	return adminService.deleteReservation(reservationId);
    }
}
