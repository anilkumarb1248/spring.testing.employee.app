package com.app.repository.emp;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

	Optional<EmployeeEntity> findByEmpName(String name);
}


