package edu.spsu.swe3613.edu.spsu.swe3613.admin;

import com.google.inject.Inject;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

public class DefaultAdminService implements AdminService {

    private AdminDao adminDao;

    @Inject
    public DefaultAdminService(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public List<AirlineAdmin> getAllAirlineAdmins() {
        try {
            return adminDao.getAllAirlineAdmins();
        } catch (SQLException e) {
            System.out.println("Something went wrong while getting all airline admins");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AirlineAdmin getAirlineAdminById(String userId) {
        try {
            return adminDao.getAirlineAdminById(userId);
        } catch (SQLException e) {
            System.out.println("something went wrong while getting admin");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AirlineAdmin addAirlineAdmin(AirlineAdmin admin) {
        try {
            return adminDao.addAirlineAdmin(admin);
        } catch (SQLException e) {
            System.out.println("Something went wrong while adding admin");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Response.Status updateAirlineAdmin(AirlineAdmin admin) {
        try {
            adminDao.updateAirlineAdmin(admin);
            return Response.Status.OK;
        } catch (SQLException e) {
            System.out.println("Something went wrong updating the admin");
            e.printStackTrace();
        }
        return Response.Status.CONFLICT;
    }

    @Override
    public Response.Status deleteAirlineAdmin(AirlineAdmin admin) {
        try {
            adminDao.deleteAirlineAdmin(admin);
            return Response.Status.OK;
        } catch (SQLException e) {
            System.out.println("Something went wrong while deleting admin");
            System.out.println("Admin was not deleted");
            e.printStackTrace();
        }
        return Response.Status.CONFLICT;
    }
}
