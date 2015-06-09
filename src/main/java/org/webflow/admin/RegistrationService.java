/**
 * 
 */
package org.webflow.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webflow.domain.User;

/**
 * @author bill luo
 *  
 */

@Service("registrationService")
public class RegistrationService {
	
	@Autowired
	private UserRepository userRepository;

	private int age(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		
		Calendar now = new GregorianCalendar();
		int res = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
		if ((cal.get(Calendar.MONTH) > now.get(Calendar.MONTH))
				|| (cal.get(Calendar.MONTH) == now.get(Calendar.MONTH) && cal
						.get(Calendar.DAY_OF_MONTH) > now
						.get(Calendar.DAY_OF_MONTH))) {
			res--;
		}
		return res;
	}
	

	public boolean isUserAdult(User user) {
		if (age(user.getDob()) >= 18) {
			return true;
		}
		return false;
	}

	public boolean isUserChild(User user) {
		if (age(user.getDob()) < 14) {
			return true;
		}
		return false;
	}
	

	public boolean isUserTeen(User user) {
		int age = age(user.getDob());
		if (age < 18 && age >= 14) {
			return true;
		}
		return false;
	}
	
	public void addUser(User user) {
		userRepository.save(user);
	}
	
}
