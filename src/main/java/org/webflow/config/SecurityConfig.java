package org.webflow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.AntPathRequestMatcher;
import org.webflow.admin.JpaUserDetailsService;
import org.webflow.admin.RegisterUsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvcSecurity
@ComponentScan({"org.webflow.admin","org.webflow.domain","org.webflow.order"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	@Qualifier("jpaUserDetailsService")
	private JpaUserDetailsService jpaUserDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//use authentication in memory first
		auth
			.inMemoryAuthentication()
				.passwordEncoder(new Md5PasswordEncoder())
				.withUser("keith").password("417c7382b16c395bc25b5da1398cf076").roles("USER", "SUPERVISOR").and()
				.withUser("erwin").password("12430911a8af075c6f41c6976af22b09").roles("USER", "SUPERVISOR").and()
				.withUser("jeremy").password("57c6cbff0d421449be820763f03139eb").roles("USER").and()
				.withUser("scott").password("942f2339bf50796de535a384f0d1af3e").roles("USER");
		
		//if not in-memory authentication, try database(Jpa) log on
		auth.userDetailsService(jpaUserDetailsService);
	}
	
	//this authentication manager is exposed from AuthenticationManagerBuilder for registration auto log on
	@Bean 
	public AuthenticationManager authMgr() throws Exception {
	     return super.authenticationManagerBean();
	}
	

	@Bean 
	public RegisterUsernamePasswordAuthenticationFilter registerUsernamePasswordAuthenticationFilter() throws Exception {
		RegisterUsernamePasswordAuthenticationFilter authFilter
			=new RegisterUsernamePasswordAuthenticationFilter();
		authFilter.setAuthenticationManager(authMgr());
		authFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/loginProcess", "POST"));
		authFilter.setAuthenticationFailureHandler(failureHandler());
	    authFilter.setUsernameParameter("username");
	    authFilter.setPasswordParameter("password");

		 return authFilter;
	}
	
	@Bean
	public SimpleUrlAuthenticationFailureHandler failureHandler() {
		return new SimpleUrlAuthenticationFailureHandler("/login?login_error=1");
	}
	
	@Override public void configure(WebSecurity web) throws Exception { 
		web
		.ignoring() 
		.antMatchers("/resources/**"); 
		} 

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.addFilterBefore(registerUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		http
			.authorizeRequests() 
				.antMatchers("/admin","/admin/**").hasRole("ADMIN")
			.and() 
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/loginProcess")
				.defaultSuccessUrl("/products/search")
				.failureUrl("/login?login_error=1")
				.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/logoutSuccess")
			.and().csrf().disable();
	}

}
