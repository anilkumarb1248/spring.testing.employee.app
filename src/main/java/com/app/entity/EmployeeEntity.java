package com.app.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "EMPLOYEE")
public class EmployeeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EMPLOYEE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int empId;

	@Column(name = "EMP_NAME")
	private String empName;

	@Column(name = "ROLE")
	private String role;

	@Column(name = "SALARY")
	private double salary;

	@Column(name = "DATE_OF_BIRTH")
	private Date dateOfBirth;

	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;

	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "DEPARTMENT_ID")
	private Long departmentId;

	@OneToMany(targetEntity = AddressEntity.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "EMPLOYEE_REF_ID", referencedColumnName = "EMPLOYEE_ID")
//	@ElementCollection
	private List<AddressEntity> addressList;

}
