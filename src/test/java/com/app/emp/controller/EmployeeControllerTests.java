package com.app.emp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.app.emp.model.Employee;
import com.app.emp.service.EmployeeService;
import com.app.emp.util.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

//@ExtendWith(SpringExtension.class) //Already configured with @WebMvcTest
@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTests {

	@MockBean
	private EmployeeService employeeService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetEmployeeList() throws Exception {
		List<Employee> dtoList = prepareEmployeeData();

		Mockito.when(employeeService.getEmployeeList()).thenReturn(dtoList);

		mockMvc.perform(get("/employee/list")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(20)))
				.andExpect(jsonPath("$[0].empName", Matchers.is("Name_1")));

	}

	@Test
	void testGetEmployeesByPagination() throws Exception {
		List<Employee> dtoList = prepareEmployeeData();

		Mockito.when(employeeService.getEmployeesByPagination(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(dtoList);

		mockMvc.perform(get("/employee/getEmployeesByPagination").param("pageNumber", "1").param("pageSize", "20")
				.param("sortOrder", "ASC").param("sortingBy", "empName")).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(20)))
				.andExpect(jsonPath("$[0].empName", Matchers.is("Name_1")));
	}

	@Test
	void testGetEmployee() throws Exception {
		Employee employee = new Employee();
		employee.setEmpId(100);
		employee.setEmpName("Anil");
		employee.setRole("TA");
		employee.setSalary(10000);
		employee.setMobileNumber("54643216");
		employee.setEmail("abcd@gmail.com");

		Mockito.when(employeeService.getEmployee(Mockito.anyInt())).thenReturn(employee);

		mockMvc.perform(get("/employee/get/10")).andExpect(status().isOk())
				.andExpect(jsonPath("$.empName", Matchers.is("Anil"))).andExpect(jsonPath("$.empId", Matchers.is(100)));
	}

	@Test
	void testAddEmployee() throws Exception {

		Employee employee = new Employee();
		employee.setEmpId(100);
		employee.setEmpName("Anil");
		employee.setRole("TA");
		employee.setSalary(10000);
		String jsonString = new ObjectMapper().writeValueAsString(employee);

		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(String.valueOf(HttpStatus.CREATED.value()));
		responseStatus.setMessage("employee added");

		Mockito.when(employeeService.addEmployee(employee)).thenReturn(responseStatus);
		mockMvc.perform(post("/employee/add").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk()).andExpect(jsonPath("$.statusCode", Matchers.is("201")))
				.andExpect(jsonPath("$.message", Matchers.is("employee added")));
	}

	@Test
	void testAddEmployees() throws Exception {
		List<Employee> dtoList = prepareEmployeeData();
		String jsonString = new ObjectMapper().writeValueAsString(dtoList);

		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(String.valueOf(HttpStatus.CREATED.value()));
		responseStatus.setMessage("employees added");

		Mockito.when(employeeService.addEmployees(dtoList)).thenReturn(responseStatus);

		mockMvc.perform(post("/employee/addAll").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk());
		// .andExpect(jsonPath("$.statusCode", Matchers.is("201")))
		// .andExpect(jsonPath("$.message", Matchers.is("employees added")));
	}

	@Test
	void testUpdateEmployee() throws Exception {
		Employee employee = new Employee();
		employee.setEmpId(100);
		employee.setEmpName("Anil");
		employee.setRole("TA");
		employee.setSalary(10000);
		String jsonString = new ObjectMapper().writeValueAsString(employee);

		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		responseStatus.setMessage("employee updated");

		Mockito.when(employeeService.updateEmployee(employee)).thenReturn(responseStatus);
		mockMvc.perform(put("/employee/update").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk()).andExpect(jsonPath("$.statusCode", Matchers.is("200")))
				.andExpect(jsonPath("$.message", Matchers.is("employee updated")));
	}

	@Test
	void testDeleteEmployee() throws Exception {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		responseStatus.setMessage("employee deleted");

		Mockito.when(employeeService.deleteEmployee(Mockito.anyInt())).thenReturn(responseStatus);

		mockMvc.perform(delete("/employee/delete/10")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message", Matchers.is("employee deleted")));
	}

	@Test
	void testDeleteAll() throws Exception {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		responseStatus.setMessage("employees deleted");

		Mockito.when(employeeService.deleteAll()).thenReturn(responseStatus);

		mockMvc.perform(delete("/employee/deleteAll")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message", Matchers.is("employees deleted")));
	}

//
	@Test
	void testGetEmployeeByName() throws Exception {
		Employee employee = new Employee();
		employee.setEmpId(100);
		employee.setEmpName("Anil");
		employee.setRole("TA");
		employee.setSalary(10000);
		employee.setMobileNumber("54643216");
		employee.setEmail("abcd@gmail.com");

		Mockito.when(employeeService.getEmployeeByName(Mockito.anyString())).thenReturn(employee);

		mockMvc.perform(get("/employee/findByName").param("name", "Anil")).andExpect(status().isOk())
				.andExpect(jsonPath("$.empName", Matchers.is("Anil"))).andExpect(jsonPath("$.empId", Matchers.is(100)));
	}

//	@Test
//	void testAddDummyData() {
//		fail("Not yet implemented");
//	}

	private List<Employee> prepareEmployeeData() {
		List<Employee> dtoList = new ArrayList<>();

		Random random = new Random();

		for (int i = 1; i <= 20; i++) {
			Employee employee = new Employee();
			employee.setEmpId(i);
			employee.setEmpName("Name_" + i);
			employee.setRole("TA_" + i);
			employee.setSalary(1000 * (i / 2));
			employee.setMobileNumber(String.valueOf(random.nextInt(10000000)));
			employee.setEmail("Email_" + i);
			dtoList.add(employee);
		}
		return dtoList;
	}

}
