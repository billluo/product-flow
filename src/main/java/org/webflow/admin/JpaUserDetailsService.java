package org.webflow.admin;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException;  
import org.springframework.stereotype.Service;
import org.webflow.domain.User;

@Service
public class JpaUserDetailsService implements UserDetailsService {
	
	@Autowired private JpaUserService jpauserService; 
	 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 

		User user = jpauserService.findByName(username); 
		if(user == null){ throw new UsernameNotFoundException("UserName "+username+" not found"); 
		} 
		
		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		if (username.equals("admin")) {
			auth = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
		}
		
		String password = user.getPassword();
				
		return new org.springframework.security.core.userdetails.User(username, password,	auth);
	}
}