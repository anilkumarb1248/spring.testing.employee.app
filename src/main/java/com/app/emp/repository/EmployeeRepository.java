package com.app.emp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.emp.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

	Optional<EmployeeEntity> findByEmpName(String name);
}


