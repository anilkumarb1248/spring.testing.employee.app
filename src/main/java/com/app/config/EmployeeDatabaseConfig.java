package com.app.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.app.entity.AddressEntity;
import com.app.entity.EmployeeEntity;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "employeeEntityManager", 
		transactionManagerRef = "employeeTransactionManager", 
		basePackages = "com.app.repository.emp")
public class EmployeeDatabaseConfig {

	@Autowired
	Environment environment;

	// DataSource Creation
	@Primary
	@Bean("employeeDataSource")
	@ConfigurationProperties(prefix = "spring.oracle.datasource")
	public DataSource employeeDataSource() {
		return DataSourceBuilder.create().build();
	}

	// EnitiyManager Creation
	@Primary
	@Bean("employeeEntityManager")
	public LocalContainerEntityManagerFactoryBean employeeEntityManager(EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(employeeDataSource())
				.packages(EmployeeEntity.class, AddressEntity.class)
				.properties(hibernateProperties())
				.persistenceUnit("employeePU")
				.build();
	}

	private Map<String, String> hibernateProperties() {
		HashMap<String, String> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.show-sql", environment.getProperty("hibernate.show-sql"));
		properties.put("hibernate.dialect", environment.getProperty("hibernate.oracele.dialect"));
		return properties;
	}

	// TransactionManager Creation
	@Primary
	@Bean(name = "employeeTransactionManager")
	public PlatformTransactionManager employeeTransactionManager(
			@Qualifier("employeeEntityManager") EntityManagerFactory entityManagerFactory) {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

}
