package com.app.emp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.emp.model.Employee;
import com.app.emp.util.ResponseStatus;

@RequestMapping("/employee")
public interface EmployeeController {

	@GetMapping({ "/list", "/all", "/employees" })
	public List<Employee> getEmployeeList();

	@GetMapping("/getEmployeesByPagination")
	public List<Employee> getEmployeesByPagination(@RequestParam int pageNumber, @RequestParam int pageSize,
			@RequestParam String sortOrder, @RequestParam String sortingBy);

	@GetMapping("/get/{employeeId}")
	public Employee getEmployee(@PathVariable(value = "employeeId") int employeeId);

	@PostMapping("/add")
	public ResponseStatus addEmployee(@RequestBody Employee employee);

	@PostMapping("/addAll")
	public ResponseStatus addEmployees(@RequestBody List<Employee> employees);

	@PutMapping("/update")
	public ResponseStatus updateEmployee(@RequestBody Employee employee);

	@DeleteMapping("/delete/{employeeId}")
	public ResponseStatus deleteEmployee(@PathVariable int employeeId);

	@DeleteMapping("/deleteAll")
	public ResponseStatus deleteAll();

	@GetMapping("/findByName")
	public Employee getEmployeeByName(@RequestParam String name);

	@GetMapping("/dummyData")
	public boolean addDummyData();

}
