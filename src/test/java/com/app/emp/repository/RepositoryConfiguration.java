package com.app.emp.repository;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.app.emp.controller.EmployeeController;
import com.app.emp.controller.EmployeeControllerImpl;
import com.app.emp.service.EmployeeService;
import com.app.emp.service.EmployeeServiceImpl;

@TestConfiguration
public class RepositoryConfiguration {
	
	@Bean
	public EmployeeController getEmployeeController() {
		return new EmployeeControllerImpl();
	}
	
	@Bean
	public EmployeeService getEmployeeService() {
		return new EmployeeServiceImpl();
	}

}
