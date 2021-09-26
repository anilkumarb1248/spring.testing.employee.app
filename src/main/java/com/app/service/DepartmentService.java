package com.app.service;

import java.util.List;

import com.app.model.DepartmentDto;
import com.app.util.ResponseStatus;

public interface DepartmentService {

	public List<DepartmentDto> getDepartments();

	public DepartmentDto getDepartment(Long departmentId);

	public DepartmentDto getDepartment(String departmentName);

	public ResponseStatus addDepartment(DepartmentDto departmentDto);
	
	public ResponseStatus addDepartmentsList(List<DepartmentDto> departmentList);

	public ResponseStatus updateDepartment(DepartmentDto departmentDto);

	public ResponseStatus deleteDepartment(Long departmentId);

	public ResponseStatus deleteDepartment(String departmentName);

	public ResponseStatus deleteAll();

	public ResponseStatus addDummyData();

}
