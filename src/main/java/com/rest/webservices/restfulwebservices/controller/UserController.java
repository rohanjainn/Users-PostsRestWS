package com.rest.webservices.restfulwebservices.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rest.webservices.restfulwebservices.Exception.UserNotFoundException;
import com.rest.webservices.restfulwebservices.dao.UserDAO;
import com.rest.webservices.restfulwebservices.model.Post;
import com.rest.webservices.restfulwebservices.model.User;
import com.rest.webservices.restfulwebservices.repository.PostRepository;
import com.rest.webservices.restfulwebservices.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@GetMapping(path="/users")
	public List<User> getAllUsers() {
	//public MappingJacksonValue getAllUsers() {
		
		List<User> users = userRepository.findAll();
		//MappingJacksonValue mapping=new MappingJacksonValue(users);
		//SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.filterOutAllExcept("name","id","dob");
		//FilterProvider filters=new SimpleFilterProvider().addFilter("somefilter", filter);
		//mapping.setFilters(filters);
		return users;
	}
	
	@GetMapping(path="/users/{id}")
	public EntityModel<User> getUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			EntityModel<User> resource=EntityModel.of(user.get());
			WebMvcLinkBuilder linkTo=linkTo(methodOn(this.getClass()).getAllUsers());
			resource.add(linkTo.withRel("all-users"));
			return resource;
		}
		else
			//return new User("not found",null,null);
			throw new UserNotFoundException("id -"+id);
		
		
		
	}
	
	@PostMapping(path="/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		
		User save = userRepository.save(user);
		 URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(save.getId()).toUri();		
		return ResponseEntity.created(location).build();
		
	}
	
	@DeleteMapping(path="/users/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable int id) {
		
		//if(null!=userRepository.deleteById(id)) {
		userRepository.deleteById(id);
		return ResponseEntity.ok().build();
		//}else
			//throw new UserNotFoundException("id -"+id);
	}
	
	@GetMapping("/users/{id}/posts")
	public List<Post> getAllPostByUser(@PathVariable int id) {
		
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			
			if(null!=user.get().getPosts())
				return user.get().getPosts();
			else
				throw new UserNotFoundException("id -"+id);
		}else
			throw new UserNotFoundException("id -"+id);
	}
	
	@PostMapping("users/{id}/posts")
	public ResponseEntity createPost(@PathVariable int id,@RequestBody Post post) {
		
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			post.setUser(user.get());
			postRepository.save(post);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/posts").buildAndExpand(post.getId()).toUri();		
			
			return ResponseEntity.created(location).build();
		}else
			throw new UserNotFoundException("User not found:id -"+id);
			
	}
}
