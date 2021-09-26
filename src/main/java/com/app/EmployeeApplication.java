package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.service.DepartmentService;
import com.app.service.EmployeeService;

@SpringBootApplication
public class EmployeeApplication implements ApplicationRunner {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	DepartmentService departmentService;

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// Setup or initialization activities
		departmentService.addDummyData();
		employeeService.addDummyData();
	}
}
