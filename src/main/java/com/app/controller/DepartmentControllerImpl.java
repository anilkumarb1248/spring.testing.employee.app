package com.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.app.model.DepartmentDto;
import com.app.service.DepartmentService;
import com.app.util.ResponseStatus;

@RestController
public class DepartmentControllerImpl implements DepartmentController {

//	@Autowired
	DepartmentService departmentService;
	
//	@Autowired // @Autowired is not required as we are using single constructor
	public DepartmentControllerImpl(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Override
	public List<DepartmentDto> getDepartments() {
		return departmentService.getDepartments();
	}

	@Override
	public DepartmentDto getDepartment(Long departmentId) {
		return departmentService.getDepartment(departmentId);
	}

	@Override
	public DepartmentDto getDepartment(String departmentName) {
		return departmentService.getDepartment(departmentName);
	}

	@Override
	public ResponseStatus addDepartment(DepartmentDto departmentDto) {
		return departmentService.addDepartment(departmentDto);
	}
	
	@Override
	public ResponseStatus addDepartmentsList(List<DepartmentDto> departmentList) {
		return departmentService.addDepartmentsList(departmentList);
	}

	@Override
	public ResponseStatus updateDepartment(DepartmentDto departmentDto) {
		return departmentService.updateDepartment(departmentDto);
	}

	@Override
	public ResponseStatus deleteDepartment(Long departmentId) {
		return departmentService.deleteDepartment(departmentId);
	}

	@Override
	public ResponseStatus deleteDepartment(String departmentName) {
		return departmentService.deleteDepartment(departmentName);
	}

	@Override
	public ResponseStatus deleteAll() {
		return departmentService.deleteAll();
	}

	@Override
	public ResponseStatus addDummyData() {
		return departmentService.addDummyData();
	}

}
