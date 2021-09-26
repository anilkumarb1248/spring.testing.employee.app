package com.app.emp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import com.app.entity.EmployeeEntity;
import com.app.model.Employee;
import com.app.repository.emp.EmployeeRepository;
import com.app.service.EmployeeServiceImpl;
import com.app.util.ResponseStatus;

//@ExtendWith(SpringExtension.class) // Cannot instantiate @InjectMocks
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTests {

	@Mock
//	@MockBean
	EmployeeRepository employeeRepository;

	@InjectMocks // Cannot instantiate @InjectMocks field named 'employeeService'! Cause: the
					// type 'EmployeeService' is an interface.
//	@MockBean
//	@Autowired
//	@Qualifier("employeeService")
	EmployeeServiceImpl employeeService;

	@Test
	void testGetEmployeeList() {
		List<EmployeeEntity> entityList = prepareEmployeeEntityData();

		Mockito.when(employeeRepository.findAll()).thenReturn(entityList);

		List<Employee> dtoList = employeeService.getEmployeeList();
		Assertions.assertThat(dtoList).isNotNull();
		Assertions.assertThat(dtoList).isNotEmpty();
		Assertions.assertThat(dtoList).hasSize(20);
	}

//	@Test
//	void testGetEmployeesByPagination() {
//		fail("Not yet implemented");
//	}

	@Test
	void testGetEmployee() {
		EmployeeEntity entity = new EmployeeEntity();
		entity.setEmpId(100);
		entity.setEmpName("Anil");
		entity.setRole("TA");
		entity.setSalary(10000);
		entity.setMobileNumber("54643216");
		entity.setEmail("abcd@gmail.com");

		Optional<EmployeeEntity> optional = Optional.of(entity);

		Mockito.when(employeeRepository.findById(Mockito.anyInt())).thenReturn(optional);

		Employee employee = employeeService.getEmployee(100);
		Assertions.assertThat(employee).isNotNull();
		Assertions.assertThat(employee.getEmpId()).isEqualTo(100);
		Assertions.assertThat(employee.getEmpName()).isEqualTo("Anil");

	}

//	@Test
//	void testAddEmployee() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddEmployees() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUpdateEmployee() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteEmployee() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteAll() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetEmployeeByName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddDummyData() {
//		fail("Not yet implemented");
//	}
	
	@Test
	void testPrivateCreateResponseStatus() {
		ResponseStatus responseStatus = ReflectionTestUtils.invokeMethod(employeeService,
				"createResponseStatus", HttpStatus.OK, "private method called");
		Assertions.assertThat(responseStatus).isNotNull();
		Assertions.assertThat(responseStatus.getStatusCode()).isEqualTo("200");
		Assertions.assertThat(responseStatus.getMessage()).isEqualTo("private method called");
	}

	private List<EmployeeEntity> prepareEmployeeEntityData() {
		List<EmployeeEntity> entityList = new ArrayList<>();

		Random random = new Random();

		for (int i = 1; i <= 20; i++) {
			EmployeeEntity employee = new EmployeeEntity();
			employee.setEmpId(i);
			employee.setEmpName("Name_" + i);
			employee.setRole("TA_" + i);
			employee.setSalary(1000 * (i / 2));
			employee.setMobileNumber(String.valueOf(random.nextInt(10000000)));
			employee.setEmail("Email_" + i);
			entityList.add(employee);
		}
		return entityList;
	}

}
