package com.rest.webservices.restfulwebservices.model;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class User2 {


	@NotEmpty
	@NotNull
	@Size(min = 2,message = "name should have atlease 2 chars")
	private String name;
	private int salary;
	private Integer id;
	
	//@JsonIgnore
	@Past(message = "dob should be in past date")
	private Date dob;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public User2(@NotEmpty @NotNull @Size(min = 2, message = "name should have atlease 2 chars") String name,
			int salary, Integer id, @Past(message = "dob should be in past date") Date dob) {
		super();
		this.name = name;
		this.salary = salary;
		this.id = id;
		this.dob = dob;
	}
	public User2() {
		super();
	}
	
	
}
