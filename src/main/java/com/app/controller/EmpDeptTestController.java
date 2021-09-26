package com.app.controller;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.DepartmentEntity;
import com.app.entity.EmployeeEntity;
import com.app.repository.dept.DepartmentRepository;
import com.app.repository.emp.EmployeeRepository;

@RestController
@RequestMapping("/empdept")
public class EmpDeptTestController {

	private static Logger LOGGER = LoggerFactory.getLogger(EmpDeptTestController.class);

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	DepartmentRepository departmentRepository;

//	@Autowired
//	@Qualifier("employeeTransactionManager")
	private PlatformTransactionManager empTxnMngr;

//	@Autowired
//	@Qualifier("departmentTransactionManager")
	private PlatformTransactionManager deptTxnMngr;

//	@Autowired
//	@Qualifier("employeeEntityManager")
	private EntityManager empEntityManager;

//	@Autowired
//	@Qualifier("departmentEntityManager")
	private EntityManager deptEntityManager;

	private TransactionTemplate empTxnTemplate;
	private TransactionTemplate deptTxnTemplate;

	public EmpDeptTestController(
			@Autowired @Qualifier("employeeTransactionManager") PlatformTransactionManager empTxnMngr,
			@Autowired @Qualifier("departmentTransactionManager") PlatformTransactionManager deptTxnMngr,
			@Autowired @Qualifier("employeeEntityManager") EntityManager empEntityManager,
			@Autowired @Qualifier("departmentEntityManager") EntityManager deptEntityManager) {

		this.empTxnMngr = empTxnMngr;
		this.deptTxnMngr = deptTxnMngr;
		this.empEntityManager = empEntityManager;
		this.deptEntityManager = deptEntityManager;

		empTxnTemplate = new TransactionTemplate(empTxnMngr);
		deptTxnTemplate = new TransactionTemplate(deptTxnMngr);
	}

	@GetMapping("/insert")
	public void insertEmployeeAndDepertment() {
		EmployeeEntity emp = new EmployeeEntity();
		emp.setEmpName("AAA");
		emp.setDepartmentId(10L);
		emp.setEmail("abc@gmail.com");
		emp.setMobileNumber("354651");
		emp.setRole("TA");
		emp.setSalary(3241353);

		DepartmentEntity dept = new DepartmentEntity();
		dept.setDepartmentId(111L);
		dept.setDepartmentName("Dummy");

		try {
			saveEmpAndDept(emp, dept);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	// we're going to handle transactions manually
	public void saveEmpAndDept(EmployeeEntity emp, DepartmentEntity dept) {
		empTxnTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus status) {
				try {
//					employeeRepository.save(emp);
//					empEntityManager.persist(emp);
					saveEmployee(emp);
					saveDepartment(dept);
				} catch (Exception e) {
					status.setRollbackOnly();
				}
			}

		});

	}

	public void saveEmployee(EmployeeEntity emp) {
		try {
			employeeRepository.save(emp);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			throw new RuntimeException("Exception occurred while inserting Employee");
		}
	}

	public void saveDepartment(DepartmentEntity dept) {
		deptTxnTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					departmentRepository.save(dept);
//					deptEntityManager.persist(dept);
					int a = 10 / 0;
				} catch (Exception e) {
					status.setRollbackOnly();
					throw new RuntimeException("Exception occurred while inserting DepartmentEntity");
				}
			}

		});
	}

}
