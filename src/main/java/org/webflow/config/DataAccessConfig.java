package org.webflow.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories({"org.webflow.admin","org.webflow.domain","org.webflow.order"})
@EnableTransactionManagement
public class DataAccessConfig {

//	use Local EntiytManager to manage persistence layer
	@Bean
	public EntityManagerFactory entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//		define as specific Persistence Unit
		factory.setPersistenceUnitName("OrderPU");
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("org.webflow.admin","org.webflow.domain","org.webflow.order");
		factory.setDataSource(dataSource());

		factory.setJpaProperties(additionalProperties());
		factory.afterPropertiesSet();
    
		return factory.getObject();
	}

//	use memory database for user registration and ordering data 
	@Bean
	public DataSource dataSource() {

		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder.setType(EmbeddedDatabaseType.HSQL)
    				  .setName("orderingDatabase")
    				  .build();

	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}
  
	Properties additionalProperties() {
		Properties properties = new Properties();
//		auto create/drop tables for JPA entities
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		properties.setProperty("hibernate.show_sql","true");
		properties.setProperty("hibernate.cache.provider_class","org.hibernate.cache.HashtableCacheProvider");
		return properties;
   }
}
