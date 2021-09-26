package com.app.emp.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.app.entity.EmployeeEntity;
import com.app.repository.emp.EmployeeRepository;

@DataJpaTest
@Import(RepositoryConfiguration.class)
class EmployeeRepositoryTests {

	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	public void testGetEmployeeList() {
		List<EmployeeEntity> entityList = prepareEmployeeEntityData();
		employeeRepository.saveAll(entityList);

		List<EmployeeEntity> entitiesList = employeeRepository.findAll();
		Assertions.assertThat(entitiesList).hasSize(10);

	}

	@Test
	void testAddEmployee() {
		EmployeeEntity entity = getEntity();
		employeeRepository.save(entity);
		Assertions.assertThat(entity.getEmpId()).isGreaterThan(0);
	}

	@Test
	void testDeleteEmployee() {
		EmployeeEntity entity = getEntity();
		EmployeeEntity dbEntity = employeeRepository.save(entity);
		employeeRepository.deleteById(dbEntity.getEmpId());

		Optional<EmployeeEntity> optional = employeeRepository.findById(dbEntity.getEmpId());

		Assertions.assertThat(optional).isNotNull();
		Assertions.assertThat(optional.isPresent()).isFalse();

	}

	private EmployeeEntity getEntity() {
		EmployeeEntity entity = EmployeeEntity.builder().empName("Anil").role("TA").salary(10000)
				.mobileNumber("6565465").email("abc@gmail.com").build();
////		entity.setEmpId(100);
//		entity.setEmpName("Anil");
//		entity.setRole("TA");
//		entity.setSalary(10000);
//		entity.setMobileNumber("54643216");
//		entity.setEmail("abcd@gmail.com");
		return entity;
	}

	private List<EmployeeEntity> prepareEmployeeEntityData() {
		List<EmployeeEntity> entityList = new ArrayList<>();

		Random random = new Random();

		for (int i = 1; i <= 10; i++) {
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
