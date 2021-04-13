package com.rest.webservices.restfulwebservices.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.rest.webservices.restfulwebservices.Exception.UserNotFoundException;
import com.rest.webservices.restfulwebservices.dao.UserDAO;
import com.rest.webservices.restfulwebservices.model.User;
import com.rest.webservices.restfulwebservices.repository.UserRepository;

public class UserJPAController {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path="/users")
	public MappingJacksonValue getAllUsers() {
		
		List<User> users = userRepository.findAll();
		MappingJacksonValue mapping=new MappingJacksonValue(users);
		SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.filterOutAllExcept("name","id","dob");
		FilterProvider filters=new SimpleFilterProvider().addFilter("somefilter", filter);
		mapping.setFilters(filters);
		return mapping;
	}
	
	@GetMapping(path="jpa/users/{id}")
	public EntityModel<User> getUser(@PathVariable int id) {
		if(null!=userDAO.findOne(id)) {
			User user = userDAO.findOne(id);
			EntityModel<User> resource=EntityModel.of(user);
			WebMvcLinkBuilder linkTo=linkTo(methodOn(this.getClass()).getAllUsers());
			resource.add(linkTo.withRel("all-users"));
			return resource;
		}
		else
			//return new User("not found",null,null);
			throw new UserNotFoundException("id -"+id);
		
		
		
	}
	
	@PostMapping(path="jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		
		User save = userDAO.save(user);
		 URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(save.getId()).toUri();		
		return ResponseEntity.created(location).build();
		
	}
	
	@DeleteMapping(path="jpa/users/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable int id) {
		
		if(null!=userDAO.deleteById(id)) {
		 return ResponseEntity.ok().build();
		}else
			throw new UserNotFoundException("id -"+id);
	}
	
}
