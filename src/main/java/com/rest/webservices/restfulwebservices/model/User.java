package com.rest.webservices.restfulwebservices.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity
//@JsonFilter("somefilter")
public class User {

	@NotEmpty
	@NotNull
	@Size(min = 2,message = "name should have atlease 2 chars")
	private String name;
	
	@Id
	@GeneratedValue
	private Integer id;
	
	//@JsonIgnore
	@Past(message = "dob should be in past date")
	private Date dob;
	
	@OneToMany(mappedBy = "user")
	private List<Post> posts;
	
	
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
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
	@Override
	public String toString() {
		return "User [name=" + name + ", id=" + id + ", dob=" + dob + "]";
	}
	public User(String name, Integer id, Date dob) {
		super();
		this.name = name;
		this.id = id;
		this.dob = dob;
	}
	
	User(){
		
	}
}
