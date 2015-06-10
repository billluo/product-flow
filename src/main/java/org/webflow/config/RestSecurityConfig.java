package org.webflow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

//curl -H "Content-Type: application/json" http://localhost:8080/purchase/categories/1/products/1

@Configuration
@EnableAuthorizationServer
@ComponentScan({"org.webflow.admin","org.webflow.domain","org.webflow.order"})
public class RestSecurityConfig extends AuthorizationServerConfigurerAdapter{

	String applicationName = "purchase";

	// This is required for password grants, which we specify below as one of the
	// {@literal authorizedGrantTypes()}.
	@Autowired
	AuthenticationManagerBuilder authenticationManager;
	
	@Autowired
	AuthenticationManager authRestMgr;

	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception {
		endpoints.authenticationManager(authRestMgr);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory().withClient( applicationName)
				.authorizedGrantTypes("password", "authorization_code", "refresh_token")
				.authorities("ROLE_USER").resourceIds(applicationName)
				.secret("123456");
	}
}

