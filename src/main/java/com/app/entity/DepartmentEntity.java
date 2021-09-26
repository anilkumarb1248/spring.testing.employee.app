package com.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
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
@Entity
@Table(name = "DEPARTMENT")
public class DepartmentEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DEPARTMENT_ID")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long departmentId;

	@Column(name = "DEPARTMENT_NAME")
	private String departmentName;

}
