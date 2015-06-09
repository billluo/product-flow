package org.webflow.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.webflow.domain.User;

public class RegisterUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	@Autowired
    private UserRepository newUser;
/*	
	@Autowired
    private User testnewUser;
*/	
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, 
    			HttpServletResponse response) throws AuthenticationException {
    		
		final String FIRSTNAME = request.getParameter("firstName");
		final String LASTNAME = request.getParameter("lastName");
		final String USERNAME = request.getParameter("username");
		final String strDob = request.getParameter("dob");
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date DOB=null;
		if(strDob!=null) {
			if (!strDob.isEmpty()) {
				try {
					DOB = format.parse(strDob);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		final String EMAIL = request.getParameter("email");
		final String PASSWORD = request.getParameter("password");

		if (FIRSTNAME!=null && USERNAME!=null && PASSWORD!=null) {
			//newUser.findByUsername(USERNAME).setFirstName(FIRSTNAME);

			User loginUser =new User(USERNAME,PASSWORD,FIRSTNAME);
			loginUser.setEmail(EMAIL);
			loginUser.setLastName(LASTNAME);
			loginUser.setDob(DOB);
			//save user registration info
			
			newUser.save(loginUser);
		}

        return super.attemptAuthentication(request, response); 
    } 

}
