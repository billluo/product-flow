package org.webflow.admin;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException;  
import org.springframework.stereotype.Service;
import org.webflow.domain.User;

/**
 * This user service is used to authenticate user in database table.
 * It implements the UserDetailService to pass Spring formatted user authentication info
 * @author bluo
 *
 */
@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {
	
	@Autowired 
	@Qualifier("jpaUserService")
	private JpaUserService jpauserService; 
	 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
		
		//username entered by user can't be null
		if (username==null) throw new UsernameNotFoundException("enter empty "
				+ "user name! Please reenter user name");
		//if user is not found throws an exception
		User user = jpauserService.findByName(username); 
			if(user == null){ throw new UsernameNotFoundException("UserName "+username+" not found"); 
		}
			
//		get user granted permission
		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		if (username.toLowerCase().equals("admin")) {
			auth = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
		}
//		get password
		String password = user.getPassword();
				
		return new org.springframework.security.core.userdetails.User(username, password,	auth);
	}
}