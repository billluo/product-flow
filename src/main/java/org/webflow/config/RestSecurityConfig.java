package org.webflow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

//curl -X POST -vu android-purchase:123456 http://localhost:8080/purchase/oauth/token -H "Accept: application/json" -d "password=melbourne&username=keith&grant_type=password&scope=write&client_secret=123456&client_id=android-purchase"
//curl -X POST -vu android-purchase:123456 http://localhost:8080/purchase/oauth/token -H "Accept: application/json" -d "password=melbourne&username=keith&grant_type=password&scope=read&client_secret=123456&client_id=android-purchase"
//curl -H "Authorization: Bearer 862b3f45-ad4e-415f-8489-fbb6268c6db0" http://localhost:8080/purchase/categories/1/products/1 
//curl http://localhost:8080/purchase/categories/1/products/1?access_token=01c1922e-3ad9-4305-b18e-ec969afce89d
//curl -i -X POST -H "Content-Type:application/json" -H "Authorization: Bearer 9d74431f-e577-41f0-a15d-3764fc91409b" -d '{ "name":"Philips CRT", "description":"old & used Philips CRT TV" }' http://localhost:8080/purchase/categories/1/products

@Configuration
@EnableAuthorizationServer
@ComponentScan({"org.webflow.admin","org.webflow.domain","org.webflow.order"})
public class RestSecurityConfig extends AuthorizationServerConfigurerAdapter{

	String applicationName = "purchase";

	// This is required for password grants, which we specify below as one of the
	// {@literal authorizedGrantTypes()}.	
	@Autowired
	AuthenticationManager authRestMgr;

	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception {
		endpoints.authenticationManager(authRestMgr);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {


		clients.inMemory().withClient("android-" + applicationName)
				.authorizedGrantTypes("password", "authorization_code", "refresh_token","client_credentials")
				.authorities("ROLE_USER").scopes("read", "write").resourceIds(applicationName)
				.secret("123456")
			.and()
				.withClient("test")
				.authorities("ROLE_USER")
				.resourceIds("applicationName")
				.scopes("read", "write")
				.authorizedGrantTypes("client_credentials")
				.secret("password")
			.and()
				.withClient("keith")
				.redirectUris("http://localhost:8080/purchase/login")
				.resourceIds("applicationName")
				.scopes("read")
				.authorizedGrantTypes("implicit")
			.and()
				.withClient("acme")
				.secret("acmesecret")
				.authorizedGrantTypes("authorization_code", "refresh_token","password")
				.scopes("openid");
	}
}

