package com.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.entity.AddressEntity;
import com.app.entity.EmployeeEntity;
import com.app.model.Address;
import com.app.model.Employee;
import com.app.repository.emp.EmployeeRepository;

@Service
public class EmployeeCustomQueryService {

	@Autowired
	EmployeeRepository employeeRepository;

	public ResponseEntity<?> callCustomQuery(String flag) {

		switch (flag) {
		case "salary-between":
			return searchBySalaryBetween(10000.00, 20000.00);
		case "salary-lessthan":
			return searchBySalaryLessThan(30000.00);

		case "salary-greaterthan":
			return searchBySalaryGreaterThanEqual(30000.00);

		case "dob-before": // Not working
			return searchByDobBefore(new Date("1990-05-11T00:00:00.000+00:00"));

		case "mobile-startwith":
			return searchByMobileNoStartWith("6");
		case "groupby-dept":
			return groupByDepartment();

		default:
			return searchByDepartment(10L);
		}

	}

	private ResponseEntity<?> groupByDepartment() {
		List<Object[]> list = employeeRepository.findEmployeeCountByDept();
		System.out.println(list);
		return ResponseEntity.status(200).body(list);
	}

	private ResponseEntity<?> searchByMobileNoStartWith(String start) {
		List<EmployeeEntity> entityList = employeeRepository.findByMobileNumberStartingWith(start);
		return ResponseEntity.status(200).body(convertToDtoList(entityList));
				
	}

	private ResponseEntity<?> searchByDobBefore(Date date) {
		List<EmployeeEntity> entityList = employeeRepository.findByDateOfBirthBefore(date);
		return ResponseEntity.status(200).body(convertToDtoList(entityList));
	}

	private ResponseEntity<?> searchBySalaryGreaterThanEqual(double salary) {
		List<EmployeeEntity> entityList = employeeRepository.findBySalaryGreaterThanEqual(salary);
		return ResponseEntity.status(200).body(convertToDtoList(entityList));
	}

	private ResponseEntity<?> searchBySalaryLessThan(double salary) {
		List<EmployeeEntity> entityList = employeeRepository.findBySalaryLessThan(salary);
		return ResponseEntity.status(200).body(convertToDtoList(entityList));
	}

	private ResponseEntity<?> searchBySalaryBetween(double start, double end) {
		List<EmployeeEntity> entityList = employeeRepository.findBySalaryBetween(start, end);
		return ResponseEntity.status(200).body(convertToDtoList(entityList));
	}

	private ResponseEntity<?> searchByDepartment(Long departmentId) {
		List<EmployeeEntity> entityList = employeeRepository.findByDepartmentId(departmentId);
		return ResponseEntity.status(200).body(convertToDtoList(entityList));
	}

	private List<Employee> convertToDtoList(List<EmployeeEntity> entityList) {
		List<Employee> dtoList = new ArrayList<>();
		entityList.stream().forEach(entity -> {
			dtoList.add(convertToBean(entity));
		});

		return dtoList;
	}

	private Employee convertToBean(EmployeeEntity employeeEntity) {
		Employee employee = new Employee();

		BeanUtils.copyProperties(employeeEntity, employee);
		List<AddressEntity> addressEntityList = employeeEntity.getAddressList();

		if (null != addressEntityList && !addressEntityList.isEmpty()) {
			List<Address> addressList = new ArrayList<>();

			addressEntityList.forEach(addressEntity -> {
				Address address = new Address();
				BeanUtils.copyProperties(addressEntity, address);
				addressList.add(address);
			});
			employee.setAddressList(addressList);
		}
		return employee;
	}

}
