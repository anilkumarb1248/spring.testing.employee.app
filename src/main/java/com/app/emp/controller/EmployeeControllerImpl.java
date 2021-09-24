package com.app.emp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.app.emp.model.Employee;
import com.app.emp.service.EmployeeService;
import com.app.emp.util.ResponseStatus;

@RestController
public class EmployeeControllerImpl implements EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@Override
	public List<Employee> getEmployeeList() {
		return employeeService.getEmployeeList();
	}

	@Override
	public List<Employee> getEmployeesByPagination(int pageNumber, int pageSize, String sortOrder, String sortingBy) {
		return employeeService.getEmployeesByPagination(pageNumber, pageSize, sortOrder, sortingBy);
	}

	@Override
	public Employee getEmployee(int employeeId) {
		return employeeService.getEmployee(employeeId);
	}

	@Override
	public ResponseStatus addEmployee(Employee employee) {
		return employeeService.addEmployee(employee);
	}

	@Override
	public ResponseStatus addEmployees(List<Employee> employees) {
		return null;
	}

	@Override
	public ResponseStatus updateEmployee(Employee employee) {
		return employeeService.updateEmployee(employee);
	}

	@Override
	public ResponseStatus deleteEmployee(int employeeId) {
		return employeeService.deleteEmployee(employeeId);
	}

	@Override
	public ResponseStatus deleteAll() {
		return employeeService.deleteAll();
	}

	@Override
	public Employee getEmployeeByName(String name) {
		return employeeService.getEmployeeByName(name);
	}

	@Override
	public boolean addDummyData() {
		return employeeService.addDummyData();
	}

}
