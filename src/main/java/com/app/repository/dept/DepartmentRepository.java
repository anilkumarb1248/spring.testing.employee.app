package com.app.repository.dept;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.DepartmentEntity;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

	Optional<DepartmentEntity> findByDepartmentName(String departmentName);

	void deleteByDepartmentName(String departmentName);

}
