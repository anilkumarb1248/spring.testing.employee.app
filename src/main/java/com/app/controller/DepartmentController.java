package com.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.model.DepartmentDto;
import com.app.util.ResponseStatus;

@RequestMapping("/department")
public interface DepartmentController {
	
	@GetMapping("/list")
	public List<DepartmentDto> getDepartments();
	
	@GetMapping("/getById/{id}")
	public DepartmentDto getDepartment(@PathVariable("id") Long departmentId);
	
	@GetMapping("/getByName")
	public DepartmentDto getDepartment(@RequestParam("name") String departmentName);
	
	@PostMapping("/add")
	public ResponseStatus addDepartment(@RequestBody DepartmentDto departmentDto);
	
	@PostMapping("/addList")
	public ResponseStatus addDepartmentsList(@RequestBody List<DepartmentDto> departmentList);
	
	@PutMapping("/update")
	public ResponseStatus updateDepartment(@RequestBody DepartmentDto departmentDto);

	@DeleteMapping("/deleteById/{id}")
	public ResponseStatus deleteDepartment(@PathVariable("id") Long departmentId);
	
	@DeleteMapping("/deleteByName")
	public ResponseStatus deleteDepartment(@RequestParam("name") String departmentName);

	@DeleteMapping("/deleteAll")
	public ResponseStatus deleteAll();
	
	@GetMapping("/dummyData")
	public ResponseStatus addDummyData();

}
