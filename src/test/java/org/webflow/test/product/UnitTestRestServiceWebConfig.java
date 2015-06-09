package org.webflow.test.product;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
//@EnableHypermediaSupport(type = { HypermediaType.HAL })
@ComponentScan({"org.webflow.domain","org.webflow.order",
	"org.webflow.admin","org.webflow.config"})
public class UnitTestRestServiceWebConfig extends WebMvcConfigurerAdapter {
	
}
