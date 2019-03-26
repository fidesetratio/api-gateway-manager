package com.app;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory",
    basePackages = {"com.app.manager.repo"})
public class ManagerDbConfig {
	@Autowired
    private Environment env;
	
	@Bean
    public DataSource ds1Datasource() {
 
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name.1"));
        dataSource.setUrl(env.getProperty("spring.datasource.url.1"));
        dataSource.setUsername(env.getProperty("spring.datasource.username.1"));
        dataSource.setPassword(env.getProperty("spring.datasource.password.1"));
 
        return dataSource;
    }
	

	  @Bean(name = "entityManagerFactory")
	    public LocalContainerEntityManagerFactoryBean ds1EntityManager() {
	        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	        em.setDataSource(ds1Datasource());
	 
	        // Scan Entities in Package:
	        em.setPackagesToScan(new String[] { "com.app.manager.model" });
	        em.setPersistenceUnitName("PERSITENCE_UNIT_NAME_1"); // Important !!
	 
	        //
	        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	 
	        em.setJpaVendorAdapter(vendorAdapter);
	 
	        HashMap<String, Object> properties = new HashMap<String,Object>();
	 
	        // JPA & Hibernate
	        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect.1"));
	        properties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql.1"));
	        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto.1"));
	        
	        em.setJpaPropertyMap(properties);
	        em.afterPropertiesSet();
	        return em;
	    }
	  @Bean(name = "transactionManager")
	    public PlatformTransactionManager ds1TransactionManager() {
	 
	        JpaTransactionManager transactionManager = new JpaTransactionManager();
	        transactionManager.setEntityManagerFactory(ds1EntityManager().getObject());
	        return transactionManager;
	    }
}