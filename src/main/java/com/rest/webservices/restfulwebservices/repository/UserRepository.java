package com.rest.webservices.restfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.webservices.restfulwebservices.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	
}
