package com.app.emp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.emp.controller.EmployeeController;

//@ExtendWith(SpringExtension.class) // Already configured with @SpringBootTest
@SpringBootTest
public class EmployeeApplicationTests {

	@Autowired
	EmployeeController employeeController;

	@Test
	void contextLoads() {
		Assertions.assertThat(employeeController).isNotNull();
	}
}
