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
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.app.entity.DepartmentEntity;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "departmentEntityManager", 
		transactionManagerRef = "departmentTransactionManager", 
		basePackages = "com.app.repository.dept")
public class DepartmentDatabaseConfig {
	
	@Autowired
	Environment environment;

	// DataSource Creation
	@Bean("departmentDataSource")
	@ConfigurationProperties(prefix = "spring.mysql.datasource")
	public DataSource departmenDataSource() {
		return DataSourceBuilder.create().build();
	}

	// EnitiyManager Creation
	@Bean("departmentEntityManager")
	public LocalContainerEntityManagerFactoryBean departmenEntityManager(EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(departmenDataSource())
				.packages(DepartmentEntity.class)
				.properties(hibernateProperties())
				.persistenceUnit("departmenPU")
				.build();
	}

	private Map<String, String> hibernateProperties() {
		HashMap<String, String> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.show-sql", environment.getProperty("hibernate.show-sql"));
		properties.put("hibernate.dialect", environment.getProperty("hibernate.mysql.dialect"));
		return properties;
	}

	// TransactionManager Creation
	@Bean(name = "departmentTransactionManager")
	public PlatformTransactionManager departmenTransactionManager(
			@Qualifier("departmentEntityManager") EntityManagerFactory entityManagerFactory) {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

}
