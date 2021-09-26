package com.app.emp.repository;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.app.controller.DepartmentController;
import com.app.controller.DepartmentControllerImpl;
import com.app.controller.EmployeeController;
import com.app.controller.EmployeeControllerImpl;
import com.app.service.DepartmentService;
import com.app.service.DepartmentServiceImpl;
import com.app.service.EmployeeService;
import com.app.service.EmployeeServiceImpl;

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
	
	@Bean 
	public DepartmentController getDepartmentController() {
		return new DepartmentControllerImpl(getDepartmentService());
	}
	
	@Bean 
	public DepartmentService getDepartmentService() {
		return new DepartmentServiceImpl();
	}

}
