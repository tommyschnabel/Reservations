package edu.spsu.swe3613.login;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import com.google.inject.Inject;

import edu.spsu.swe3613.reservations.Customer;
import edu.spsu.swe3613.reservations.ReservationsDao;

public class DefaultLoginService implements LoginService {
	
	private LoginDao loginDao;
	private ReservationsDao reservationsDao;
	
	@Inject
	public DefaultLoginService(LoginDao loginDao, ReservationsDao reservationsDao) {
		this.loginDao = loginDao;
		this.reservationsDao = reservationsDao;
	}

	@Override
	public Boolean login(LoginParams loginParams) {
		List<Customer> customers;
		Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());
		try {
			customers = reservationsDao.getAllCustomers();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		for (Customer c : customers) {
			if (c.getEmail().equals(loginParams.getEmail()) && c.getPassword().equals(loginParams.getPassword())) {
				try {
					loginDao.addOrUpdateLogin(loginParams.getEmail(), time);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					return false;
				}
			}
		}

		return true;
	}

}
