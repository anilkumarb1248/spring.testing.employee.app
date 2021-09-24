package com.app.emp.model;

import java.io.Serializable;

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
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	private int addressId;
	private String houseNumber;
	private String street;
	private String city;
	private String state;
	private String pincode;

}
